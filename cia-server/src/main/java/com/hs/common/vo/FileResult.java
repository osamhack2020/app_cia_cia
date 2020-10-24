package com.hs.common.vo;

public class FileResult {
	
	
	private String filePath;			// 업로드된 파일경로
	private String fileName;			// 업로드된 파일이름(확장자포함한이름만)
	
	private String originFileName;		// 원본파일이름
	
	private long size;					// 파일크기
	
	private int width;					// 이미지경우 넓이
	private int height;					// 이미지경우 높이

	private String sorf;		
	
	
	
	
	// 로그
	public String toLog(){
		
		String str = "\n";
		str += "[결과코드] : " + sorf + "\n";		
		str += "[결과메세지] : " + getSorfMessage() + "\n";
		
		
		return str;
	}
	
	// 성공여부
	public boolean isSuccess(){
		if(sorf!=null && sorf.equals("0000")){
			return true;
		}
		return false;
	}
	
	// 결과메시지
	public String getSorfMessage(){
		
		if(sorf==null){
			return "파일이 존재하지 않습니다.";
		}
		else if(sorf.equals("0000")){
			return "정상적으로 처리되었습니다.";
		}
		else if(sorf.equals("0001")){
			return "파일이 존재하지 않습니다.";
		}
		else if(sorf.equals("0002")){
			return "파일의 최대용량을 초과했습니다.";
		}
		else if(sorf.equals("0003")){
			return "지원하지 않는 확장자입니다.";
		}
		else if(sorf.equals("0004")){
			return "IllegalStateException";
		}
		else if(sorf.equals("0005")){
			return "IOException";
		}
		
		
		
		return "알 수 없는 오류입니다.";
	}
	
	
	
	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getOriginFileName() {
		return originFileName;
	}

	public void setOriginFileName(String originFileName) {
		this.originFileName = originFileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getSorf() {
		return sorf;
	}

	public void setSorf(String sorf) {
		this.sorf = sorf;
	}
	
	
	
	
	
	
	
}
