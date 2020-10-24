package com.hs.common.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

import com.hs.common.service.FileService;
import com.hs.common.support.SupportLog;

public class DownloadView extends AbstractView {
	
	private Logger logger = LoggerFactory.getLogger(FileService.class);
	
	public DownloadView() {
		setContentType("application/download;charset=utf-8");
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			
			request.setCharacterEncoding("UTF-8");
			
			Map<String, Object> fileInfo = (Map<String, Object>) model.get("downloadFile");
			
			String filePhysicPath = (String) fileInfo.get("filePhysicPath"); 	// 실제파일 업로드경로
			filePhysicPath = request.getSession().getServletContext().getRealPath("/"+filePhysicPath)+"/";
			
			String filePhysicName = (String) fileInfo.get("filePhysicName"); 	// 실제파일 이름
			String fileLogicName = (String) fileInfo.get("fileLogicName");		// 다운로드파일이름(클라이언트노출)							

			// 다운로드할 파일 생성
			File file = new File(filePhysicPath, filePhysicName);
			response.setContentType(getContentType());
			response.setContentLength((int) file.length());
			String userAgent = request.getHeader("User-Agent");
//			boolean ie = userAgent.indexOf("MSIE") > -1;
			String fileName = null;
			
			logger.info("디스플레이:"+fileLogicName);
			
//			if (ie) {
//				fileName = URLEncoder.encode(fileLogicName, "UTF-8");
//			} else {
//				fileName = new String(fileLogicName.getBytes("utf-8"), "utf-8");
//			}
			fileName = URLEncoder.encode(fileLogicName, "UTF-8");
			
			logger.info("디스플레이:"+fileLogicName);
			
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
			response.setHeader("Content-Transfer-Encoding", "binary");
			OutputStream out = response.getOutputStream();

			FileInputStream fis = null;

			try {
				fis = new FileInputStream(file);
				FileCopyUtils.copy(fis, out);
			} finally {
				if (fis != null)
					try {
						fis.close();
					} catch (IOException ex) {
					}
			}
			out.flush();
		} catch (Exception ec) {
			logger.error(SupportLog.getIpURL(request) + " [파일다운실패] [" + SupportLog.getStackTrace(ec) + "]");
		}

	}
}
