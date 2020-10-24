package com.hs.app.user.vo;

public class CurInfo {
	
	private Integer idx;
	private Integer classIdx;
	private String regdate;
	private int numb;
	private String title;
	private String note;
	private int viewCount;
	private String videoPath;
	private int videoSeconds;
	private String img;
	
	public CurInfo() {
		
	}
	public CurInfo(Integer classIdx, int numb, String title, String note, String videoPath, String img) {
		this.classIdx = classIdx;
		this.numb = numb;
		this.title = title;
		this.note = note;
		this.videoPath = videoPath;
		this.img = img;
	}
		
	
	public Integer getIdx() {
		return idx;
	}
	public void setIdx(Integer idx) {
		this.idx = idx;
	}
	public Integer getClassIdx() {
		return classIdx;
	}
	public void setClassIdx(Integer classIdx) {
		this.classIdx = classIdx;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public int getNumb() {
		return numb;
	}
	public void setNumb(int numb) {
		this.numb = numb;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getViewCount() {
		return viewCount;
	}
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
	
	public String getVideoPath() {
		return videoPath;
	}
	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}
	public int getVideoSeconds() {
		return videoSeconds;
	}
	public void setVideoSeconds(int videoSeconds) {
		this.videoSeconds = videoSeconds;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	
	
}
