package com.hs.app.user.vo;

public class ClassInfo {
	
	private Integer idx;
	private Integer userIdx;
	private String regdate;
	private String img;
	private boolean enabled;
	private String title;
	private String note;
	private int viewCount;
	private Integer catIdx;
	private String tags;
	private String userName;
	private String catName;
	private String userImg;
	
	public ClassInfo() {}
	public ClassInfo(Integer userIdx, String img, String title, String note, String tags, Integer catIdx) {
		this.userIdx = userIdx;
		this.img = img;
		this.title = title;
		this.note = note;
		this.tags = tags;
		this.catIdx = catIdx;
	}
	
	
	public String getUserImg() {
		return userImg;
	}
	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}
	public Integer getIdx() {
		return idx;
	}
	public void setIdx(Integer idx) {
		this.idx = idx;
	}
	public Integer getUserIdx() {
		return userIdx;
	}
	public void setUserIdx(Integer userIdx) {
		this.userIdx = userIdx;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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
	public Integer getCatIdx() {
		return catIdx;
	}
	public void setCatIdx(Integer catIdx) {
		this.catIdx = catIdx;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	
	

}
