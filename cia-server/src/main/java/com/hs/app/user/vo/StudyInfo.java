package com.hs.app.user.vo;

public class StudyInfo {

	private Integer idx;
	private Integer userIdx;
	private String regdate;
	private String img;
	private String note;
	private int viewCount;
	private String station;
	private String signdate;
	private Integer maxPeople;
	private boolean enabled;
	private String title;
	private String tags;
	private Integer catIdx;  
	private String catName;
	private String userName;
	private String userImg;
	
	public StudyInfo() {}
	public StudyInfo(int userIdx, String title, String note, 
			String img, String station, String signdate, 
			int maxPeople, String tags, Integer catIdx) {
		this.userIdx = userIdx;
		this.title = title;
		this.note = note;
		this.img = img;
		this.station = station;
		this.signdate = signdate;
		this.maxPeople = maxPeople;
		this.catIdx = catIdx;
	}
	
	
	
	public String getUserImg() {
		return userImg;
	}
	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getSigndate() {
		return signdate;
	}
	public void setSigndate(String signdate) {
		this.signdate = signdate;
	}
	public Integer getMaxPeople() {
		return maxPeople;
	}
	public void setMaxPeople(Integer maxPeople) {
		this.maxPeople = maxPeople;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public Integer getCatIdx() {
		return catIdx;
	}
	public void setCatIdx(Integer catIdx) {
		this.catIdx = catIdx;
	}
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	
	
	
}
