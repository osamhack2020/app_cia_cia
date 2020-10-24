package com.hs.common.service;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hs.common.support.SupportLog;
import com.hs.common.vo.FileResult;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;


public class FileService {
	
	private static final Logger logger = LoggerFactory.getLogger(FileService.class);
	
	private void resizeImg(String path, String name, String type) {
		
		System.out.println("패스: "+path);
		System.out.println("이름: "+name);
		System.out.println("확장자: "+type);
		
		
		String imgOriginalPath= path + name;// "C:/dev/temp/test.png";           // 원본 이미지 파일명
        String imgTargetPath= path + name;// "C:/dev/temp/test_resize.png";    // 새 이미지 파일명
        String imgFormat = type;//"png";                             // 새 이미지 포맷. jpg, gif 등
        int newWidth = 600;                                  // 변경 할 넓이
        int newHeight = 600;                                 // 변경 할 높이
        String mainPosition = "W";                             // W:넓이중심, H:높이중심, X:설정한 수치로(비율무시)
 
        Image image;
        int imageWidth;
        int imageHeight;
        double ratio;
        int w;
        int h;
 
        try{
            // 원본 이미지 가져오기
            image = ImageIO.read(new File(imgOriginalPath));
 
            // 원본 이미지 사이즈 가져오기
            imageWidth = image.getWidth(null);
            imageHeight = image.getHeight(null);
 
            if(mainPosition.equals("W")){    // 넓이기준
 
                ratio = (double)newWidth/(double)imageWidth;
                w = (int)(imageWidth * ratio);
                h = (int)(imageHeight * ratio);
 
            }else if(mainPosition.equals("H")){ // 높이기준
 
                ratio = (double)newHeight/(double)imageHeight;
                w = (int)(imageWidth * ratio);
                h = (int)(imageHeight * ratio);
 
            }else{ //설정값 (비율무시)
 
                w = newWidth;
                h = newHeight;
            }
 
            // 이미지 리사이즈
            // Image.SCALE_DEFAULT : 기본 이미지 스케일링 알고리즘 사용
            // Image.SCALE_FAST    : 이미지 부드러움보다 속도 우선
            // Image.SCALE_REPLICATE : ReplicateScaleFilter 클래스로 구체화 된 이미지 크기 조절 알고리즘
            // Image.SCALE_SMOOTH  : 속도보다 이미지 부드러움을 우선
            // Image.SCALE_AREA_AVERAGING  : 평균 알고리즘 사용
            Image resizeImage = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
 
            // 새 이미지  저장하기
            BufferedImage newImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            Graphics g = newImage.getGraphics();
            g.drawImage(resizeImage, 0, 0, null);
            g.dispose();
            ImageIO.write(newImage, imgFormat, new File(imgTargetPath));
 
        }catch (Exception e){
 
            e.printStackTrace();
 
        }
	}
	
	/** 파일로 파일 업로드&덮어쓰기*/
	public boolean copyFile(File resource, File dest) {
		
		try {
			
			FileUtils.copyFile(resource, dest);
			
			return true;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	/** 문자열로 파일(텍스트형식) 업로드&덮어쓰기*/
	public boolean overwriteTxtFile(HttpServletRequest request, String filePath, String fileName, String note){
		
		// 파일을 업로드할 경로
		String uploadPath = request.getSession().getServletContext().getRealPath(filePath);
		File dir = new File(uploadPath);
		if (!dir.isDirectory()) { dir.mkdirs(); }
		
	
		FileOutputStream fileOutputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		BufferedWriter writer = null;
		
		
		// 이미 존재하는 파일일경우 파일을 먼저 선 삭제처리한다.
		File file = new File(uploadPath+fileName);
		if(file.exists()==true){
			logger.debug("파일이 중복입니다. 경로 : " + uploadPath + fileName);
			if(file.delete()){
				logger.debug("파일이 정상적으로 삭제되었습니다. 경로 : " + uploadPath + fileName);
			} 
		}else{
			logger.debug("파일이 중복이 아닙니다. 경로 : " + uploadPath + fileName);
		}
		
		
		try {
			
			fileOutputStream = new FileOutputStream(uploadPath+fileName);
			outputStreamWriter = new OutputStreamWriter(fileOutputStream, "utf-8");
			writer = new BufferedWriter(outputStreamWriter);
			
			writer.write(note);
			writer.flush();
			
		} catch (Exception e) {			
			logger.error(SupportLog.getStackTrace(e));						
			return false;
		} finally {			
			try {
				if(fileOutputStream!=null)			
					fileOutputStream.close();								
				if(fileOutputStream!=null)
					outputStreamWriter.close();				
				if(writer!=null)
					writer.close();					
			} catch (Exception e2) {}			
		}
		
		
		return true;
	}
	
	
	
	/**
	 * 파일풀패스를 리턴한다.(중복을 방지한다.)
	 * 
	 * @params
	 * 		isAutoName : 파일이름자동생성여부
	 * 		uploadPath : 서버업로드경로
	 * 		originName : 원본파일이름
	 * @returns	
	 * 		[0] : 서버업로드경로
	 * 		[1] : 서버업로드파일이름
	 */
	private String getFileNameForUpload(boolean isAutoName, String uploadPath, String originName){
		
		String uploadName = "";		// 업로드할 파일이름

		String type = originName.substring(originName.lastIndexOf(".") + 1, originName.length()); // 확장자
		
		
		if(isAutoName)			
			uploadName = "temp_" + UUID.randomUUID() + "." + type;
		else
			uploadName = originName;

		
		// 중복이름방지
		int i = 1;
		String nm = uploadName;
		while (true) {
			if(new File(uploadPath + nm + "." + type).exists()) 
				nm = uploadName + "("+i+")";
			else
				break;				
			i++;
		}
		
		return nm;
		
	}
	

	/**
	 * 파일업로드
	 * 회원들이 업로드하는 파일들의 디렉터리는 /resources/upload/user/ 하위에 배치한다.
	 * 업로가능한확장자목록:jpeg,jpg,png,gif,xls,xlsx,
	 * 만약 중복을 허용하지않고 파일이름을 직접 지정했는데 이미 중복파일명이 있으면 파일명을 자동으로 생성 후 저장한다.
	 * 
	 * @param mRequest       : 파일정보
	 * @param directoryPath  : 업로드할디렉터리경로(예: "/resources/upload/temp/"), null이면 /resources/upload/temp 경로에 저장된다.
	 * @param isFileOverlap  : 파일을 덮어쓰기할지여부
	 * @param directFileName : 파일이름직접지정할경우, null이면 안하는것으로 판단
	 * @return
	 * 		FileResult객체
	 */
	public FileResult upload(MultipartHttpServletRequest mRequest, String directoryPath, boolean isFileOverlap, String directFileName) {
		
		logger.debug("{}{}{}{}{}{}", new Object[]{"파일업로드시작.. 업로드디렉터리경로:",directoryPath,",파일덮어쓰기여부:",isFileOverlap,",파일이름(직접지정안할시null):",directFileName});
		
		FileResult result = new FileResult();
		
		
		List<String> extension = new ArrayList<String>();
		extension.add("jpeg");extension.add("JPEG");
		extension.add("png");extension.add("PNG");
		extension.add("gif");extension.add("GIF"); 
		extension.add("jpg");extension.add("JPG"); 
		extension.add("xls");extension.add("XLS");
		extension.add("xlsx");extension.add("XLSX");
		
		extension.add("mp4");extension.add("MP4");
		extension.add("avi");extension.add("AVI");
		extension.add("mpeg");extension.add("MPEG");
		extension.add("wmv");extension.add("WMV");
		extension.add("mkv");extension.add("MKV");
		
//		String rootPath = "resources/upload/";
//		if(directoryPath==null){
//			directoryPath = "temp/";
//		}
//		directoryPath = rootPath + directoryPath;
		
		String file_type = null;
		
		// 파일을 업로드할 경로
		String uploadPath = mRequest.getSession().getServletContext().getRealPath(directoryPath) + File.separator;
		File dir = new File(uploadPath);
		if (!dir.isDirectory()) { dir.mkdirs(); }
	
		Iterator<String> iter = mRequest.getFileNames();

		if (iter.hasNext()) {
		
			// 파일정보를 가져옴
			MultipartFile mFile = mRequest.getFile(iter.next());
			
			// 파일 존재 유뮤 검사
			if (mFile.isEmpty()) {
				result.setSorf("0001");
				return result;
			}
			// 파일 최대크기 제한 검사
//			long maxsize = 0;
//			if (maxsize != 0 && maxsize < mFile.getSize()) {			
//				result.setSorf("0002");
//				return result;	
//			}
			
			// 최초 클라이언트 파일패스를 가져옴
			String originalFileName = mFile.getOriginalFilename();
			
			
			if(directFileName!=null){// 파일이름 직접지정한 경우
				
				originalFileName = directFileName;
			
			}else{// 파일이름직접지정하지 않은 경우
				
				// 확장자 가져옴
				String type = originalFileName.substring(originalFileName.lastIndexOf(".") + 1, originalFileName.length());
				
				file_type = type;
				
				// 확장자 검사
				boolean isAvaliableExtension = false;
				for (int i = 0; i < extension.size(); i++) {
					if (type.toString().equals(extension.get(i).toString())) {
						isAvaliableExtension = true;
						break;
					}
				}
				if (!isAvaliableExtension) {
					result.setSorf("0003");
					return result;
				}
				
			}
			
			// 덮어쓰기 여부
			String uploadFileName = null;
			if(isFileOverlap){				
				uploadFileName = originalFileName;			
			}else{
				uploadFileName = getFileNameForUpload(true, uploadPath, originalFileName);
			}
			
			
			/*
			 * directoryPath  : 실제 업로드시킬 파일디렉터리경로(ex:resources/images/test.png)
			 * uploadPath     : 실제 업로드시킬 파일경로
			 * uploadFileName : 실제 업로드될 파일이름(확장자포함)
			 */
			try{
				
				logger.info("{}{}{}{}{}", new Object[]{"파일쓰기를 시도합니다.. 원본파일명:",originalFileName,", 업로드타깃경로:",uploadPath,uploadFileName});
				
				mFile.transferTo(new File(uploadPath+uploadFileName));
				
				resizeImg(uploadPath, uploadFileName, file_type);
				
				result.setFilePath(directoryPath);
				result.setFileName(uploadFileName);
				result.setOriginFileName(originalFileName);
				result.setSize(mFile.getSize());				
				result.setSorf("0000");
			
			} catch (IllegalStateException e) {			
				logger.error(SupportLog.getStackTrace(e));				
				result.setSorf("0004");
			} catch (IOException e) {				
				logger.error(SupportLog.getStackTrace(e));
				result.setSorf("0005");
			} 

			
		} // if end
		return result;
	} // fileUpload end
	
	
	public void createPDF(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String CSS_FILE = request.getSession().getServletContext().getRealPath("/resources/edunschool/file/pdf.css");
		String TTF_FILE = request.getSession().getServletContext().getRealPath("/resources/edunschool/file/malgun.ttf");
		String PDF_FILE = request.getSession().getServletContext().getRealPath("/resources/edunschool/file/pdf.html"); 
		
		// Document 생성
		Document document = new Document(PageSize.A4, 50, 50, 50, 50); // 용지 및 여백 설정
		     
		// PdfWriter 생성
		//PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("d:/test.pdf")); // 바로 다운로드.
		PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
		writer.setInitialLeading(12.5f);
		 
		// 파일 다운로드 설정
		response.setContentType("application/pdf");
		String fileName = URLEncoder.encode("교육 참석 확인증", "UTF-8"); // 파일명이 한글일 땐 인코딩 필요
		response.setHeader("Content-Transper-Encoding", "binary");
		response.setHeader("Content-Disposition", "inline; filename=" + fileName + ".pdf");
		 
		// Document 오픈
		document.open();
		XMLWorkerHelper helper = XMLWorkerHelper.getInstance();
		     
		// CSS
		CSSResolver cssResolver = new StyleAttrCSSResolver();
		CssFile cssFile = helper.getCSS(new FileInputStream(CSS_FILE));
		cssResolver.addCss(cssFile);
		     
		// HTML, 폰트 설정
		XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
		fontProvider.register(TTF_FILE, "MalgunGothic"); // MalgunGothic은 alias,
		CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);
		 
		HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
		htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
		 
		// Pipelines
		PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
		HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
		CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);
		 
		XMLWorker worker = new XMLWorker(css, true);
		XMLParser xmlParser = new XMLParser(worker, Charset.forName("UTF-8"));
		 
//		// 폰트 설정에서 별칭으로 줬던 "MalgunGothic"을 html 안에 폰트로 지정한다.
//		String htmlStr = "<html><head><body style='font-family: MalgunGothic;'>"
//		            + "<p>PDF 안에 들어갈 내용입니다.</p>"
//		            + "<h3>한글, English, 漢字.</h3>"
//		        + "</body></head></html>";
//		 
//		StringReader strReader = new StringReader(htmlStr);
//		xmlParser.parse(strReader);
		
		xmlParser.parse(new FileReader(PDF_FILE));
		
		 
		document.close();
		writer.close();
	}
	
	public void createPDF(HttpServletRequest request, String filePath, String fileName, String content) {
		
		File dir = new File(request.getSession().getServletContext().getRealPath(filePath));
		if (!dir.isDirectory()) { dir.mkdirs(); }
		
	
		//파일을 만들어 주세요.
		File file = new File(request.getSession().getServletContext().getRealPath( filePath + fileName ));
		 
//			//css
		String scss = request.getSession().getServletContext().getRealPath("/resources/edunschool/file/pdf.css");
//			//font
//			String font = request.getSession().getServletContext().getRealPath("/font/printFont.ttf");

		
		try {
		    // Document 생성
		    Document document = new Document(PageSize.A4, 50, 50, 50, 50);
		 
		    // PdfWriter 생성
		    // PdfWriter writer = PdfWriter.getInstance(document, new
		    // FileOutputStream("d:/test.pdf")); // 바로 다운로드.
		    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
		    writer.setInitialLeading(12.5f);
		 
		    // Document 오픈
		    document.open();
		    XMLWorkerHelper helper = XMLWorkerHelper.getInstance();
		 
		    // CSS
		    CSSResolver cssResolver = new StyleAttrCSSResolver();
		    CssFile cssFile = helper.getCSS(new FileInputStream(scss));
		    cssResolver.addCss(cssFile);
		    
		    
		 
		    // HTML, 폰트 설정
		    XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
		    //fontProvider.register(sfont, "MalgunGothic"); // MalgunGothic은
		 
		    CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);
		 
		    HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
		    htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
		 
		    // Pipelines
		    PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
		    HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
		    CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);
		 
		    XMLWorker worker = new XMLWorker(css, true);
		    XMLParser xmlParser = new XMLParser(worker, Charset.forName("UTF-8"));
		 
		    // 폰트 설정에서 별칭으로 줬던 "MalgunGothic"을 html 안에 폰트로 지정한다.
		    String sHtml = "<html><head></head><body style='font-family:MalgunGothic;'>" + content + "</body></html>";
		    sHtml = "<!DOCTYPE html><html><head><meta http-equiv='Content-Type'content='text/html; charset=utf-8'/><meta charset='UTF-8'/><title>교육참석확인증</title></head><body><style>*{font-family:'휴먼명조'}table{width:100%;margin:0px auto;border:2px solid black;border-collapse:collapse;padding:0px;font-weight:bold;font-size:14pt}table,tr,td{border-collapse:collapse}td{border:1px solid black;padding:15px 10px}.center{text-align:center}.f_w{width:20%}</style><div><span style='font-size:14px;padding:3px 0px;'>발급번호:YYYY-MM-DD</span></div><table border='0'><tr><td class='center'colspan='4'style='font-size:22pt;padding: 30px 0px;'>교육참석확인증</td></tr><tr><td class='center f_w'>아이디</td><td></td><td class='center f_w'>소&nbsp;&nbsp;속</td><td></td></tr><tr><td class='center f_w'>성&nbsp;&nbsp;명</td><td></td><td class='center f_w'>연락처</td><td></td></tr><tr><td colspan='4'style='font-size:15pt;padding:30px 10px;'>상기자는메디치교육센터에서실시한다음의교육과정에참석하여수료하였음을확인합니다.</td></tr><tr><td class='center'colspan='1'>과정명</td><td colspan='3'></td></tr><tr><td class='center'colspan='1'>기&nbsp;&nbsp;간</td><td colspan='3'></td></tr><tr><td class='center'colspan='1'>금&nbsp;&nbsp;액</td><td colspan='3'></td></tr><tr><td class='center'colspan='1'>장&nbsp;&nbsp;소</td><td colspan='3'></td></tr><tr><td colspan='4'><div style='padding:30px 0px 25px 0px;text-align: center;font-weight:normal;'>YYYY년MM월DD일</div><div style='padding:25px 0px 50px 0px;text-align: left;font-size:16pt;'><span style='display:inline-block;vertical-align: middle;width:45%;text-align: center;'>메디치이앤에스(주)<br/>메디치교육센터</span><span style='display:inline-block;vertical-align: middle;width:10%;;text-align: center;'>대&nbsp;표</span><span style='display:inline-block;vertical-align: middle;width:25%;;text-align: center;position:relative;'><span style='z-index:1;'>이&nbsp;종&nbsp;관</span><img style='position:absolute;top:-40px;left:80px;width:100px;z-index:-1;'src='http://localhost/resources/edunschool/file/i.jpg'/></span><span style='display:inline-block;vertical-align: middle;text-align: center;'>&nbsp;</span></div></td></tr></table><div><span style='font-size:14px;padding:3px 0px;'>[메디치교육센터www.medici-edu.co.kr전화:02-861-8568팩스:070-7525-8368]</span></div></body></html>";
		    // byte[] bHtml = (map.get("printData").toString()).getBytes();
		 
		    xmlParser.parse(new StringReader(sHtml));
		    //xmlParser.parse(new FileReader(request.getSession().getServletContext().getRealPath("/resources/edunschool/file/pdf.html")));
		    
//		    String pdfFile = request.getSession().getServletContext().getRealPath("/resources/edunschool/file/pdf.html");
//		    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pdfFile), Charset.forName("UTF-8")));
//		    String str;
//		    String fStr = "";
//		    while ((str = br.readLine()) != null) {
//		    	fStr += str;
//		    	System.out.println(str);
//	        }
//		    xmlParser.parse(new StringReader(fStr));
		    
		    document.close();
		    writer.close();
		    
		} catch (Exception e) {
			logger.error(SupportLog.getStackTrace(e));
		} 
	}
	
}
