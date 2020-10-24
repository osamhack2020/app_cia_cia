package com.hs.app.user.vo;

import java.util.Calendar;


public class UserInfo {
	
	private Integer idx;
	private String id;
	private String password;
	private String name;
	private String email;
	private String phonenm;
	private String regdate;	// 가입일
	private String img;
	private Boolean enabled;
	private String authList;
	private int myClassCount;	// 내가 개설한 강의수
	private int myStudyCount;	// 내가 개설한 모임수
	private int regClassCount;	// 내가 수강중 강의수
	private int regStudyCount;	// 내가 수장궁 모임수
	
	
	
	
	public int getMyClassCount() {
		return myClassCount;
	}
	public void setMyClassCount(int myClassCount) {
		this.myClassCount = myClassCount;
	}
	public int getMyStudyCount() {
		return myStudyCount;
	}
	public void setMyStudyCount(int myStudyCount) {
		this.myStudyCount = myStudyCount;
	}
	public int getRegClassCount() {
		return regClassCount;
	}
	public void setRegClassCount(int regClassCount) {
		this.regClassCount = regClassCount;
	}
	public int getRegStudyCount() {
		return regStudyCount;
	}
	public void setRegStudyCount(int regStudyCount) {
		this.regStudyCount = regStudyCount;
	}
	public String getAuthList() {
		return authList;
	}
	public void setAuthList(String authList) {
		this.authList = authList;
	}
	public Integer getIdx() {
		return idx;
	}
	public void setIdx(Integer idx) {
		this.idx = idx;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhonenm() {
		return phonenm;
	}
	public void setPhonenm(String phonenm) {
		this.phonenm = phonenm;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	

}
