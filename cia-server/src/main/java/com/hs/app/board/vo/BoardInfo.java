package com.hs.app.board.vo;

import java.io.Serializable;

public class BoardInfo implements Serializable {
	
	private static final long serialVersionUID = -1L;
	
    private Integer idx;
    private Integer menuIdx;
    private Integer userIdx;
    private String regdate;
    private String moddate;
    private String writer;
    private String title;
    private String note;
    private String thumbImg;
    private Integer views;
    private String tags;
    
    private String menuName;
    
    
    
    public void setThumbImg(String thumbImg) {this.thumbImg = thumbImg;}
    public String getThumbImg() {return this.thumbImg;}
    
    public void setTitle(String title) {this.title = title;}
    public String getTitle() {return this.title;}
    
    public void setNote(String note) {this.note = note;}
    public String getNote() {return this.note;}
    
    public void setIdx(Integer idx) {this.idx = idx;}
    public Integer getIdx() {return this.idx;}
    
    public void setMenuIdx(Integer menuIdx) {this.menuIdx = menuIdx;}
    public Integer getMenuIdx() {return this.menuIdx;}
    
    public void setUserIdx(Integer userIdx) {this.userIdx = userIdx;}
    public Integer getUserIdx() {return this.userIdx;}
    
    public void setRegdate(String regdate) {this.regdate = regdate;}
    public String getRegdate() {return this.regdate;}
    
    public void setModdate(String moddate) {this.moddate = moddate;}
    public String getModdate() {return this.moddate;}
    
    public void setWriter(String writer) {this.writer = writer;}
    public String getWriter() {return this.writer;}
    
    public void setViews(Integer views) {this.views = views;}
    public Integer getViews() {return this.views;}
        
    public void setMenuName(String menuName) {this.menuName = menuName;}
    public String getMenuName() {return this.menuName;}
    
    public void setTags(String tags) {this.tags = tags;}
    public String getTags() {return this.tags;}
    
    
	@Override
	public String toString() {
		return "BoardInfo [idx=" + idx + ", menuIdx=" + menuIdx + ", userIdx=" + userIdx + ", regdate=" + regdate
				+ ", moddate=" + moddate + ", writer=" + writer + ", title=" + title + ", note=" + note + ", thumbImg="
				+ thumbImg + ", views=" + views + ", tags=" + tags + ", menuName=" + menuName + "]";
	}
    
   
    
	
    
	
    
    
	
    
}