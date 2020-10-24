package com.hs.common.vo;

import java.util.Map;

public class Email {

	private String subject;						// 제목
    private String[] receiver;					// 받는사람
    
    private String template;					// 템플릿파일이름
    private Map<String,Object> resultMap;		// 모델데이터
    
    private boolean htmlYn;						// HTML사용여부
    
    
    
    
    // 1개 일때도 셋팅 되도록 하기 위함.
    public void setReceiver(String receiver){
    	this.receiver = new String[]{receiver};
    }
    
    public String[] getReceiver() {
        return receiver;
    }
    public void setReceiver(String[] receiver) {
        this.receiver = receiver;
    }
    public boolean isHtmlYn() {
		return htmlYn;
	}
	public void setHtmlYn(boolean htmlYn) {
		this.htmlYn = htmlYn;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public Map<String, Object> getResultMap() {
		return resultMap;
	}
	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
	@Override
	public String toString() {
		return "Email [subject=" + subject + ", receiver=" + receiver
				+ ", template=" + template + ", resultMap=" + resultMap
				+ ", htmlYn=" + htmlYn + "]";
	}

	public String toLog(){
		
		String content = "메일제목:"+subject+"\n";
		if(receiver.length>1){
			content += "받는사람:"+receiver[0]+"외 "+(receiver.length-1)+"명";
		}else{
			content += "받는사람:"+receiver[0];
		}
		content += "\nHTML형식여부:"+htmlYn+"\n";
		content += "데이터맵:"+resultMap;
		return content;
	}
 
}
