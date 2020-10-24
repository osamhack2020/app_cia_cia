package com.hs.app.user.vo;

public class UserAlba {
	
	private int idx;
	private int userIdx;
	private Integer amount;
	private Boolean proFlag;
	private Boolean pickupFlag;
	private String regionList;
	private String intro;
	private boolean activeFlag;
	
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public int getUserIdx() {
		return userIdx;
	}
	public void setUserIdx(int userIdx) {
		this.userIdx = userIdx;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Boolean getProFlag() {
		return proFlag;
	}
	public void setProFlag(Boolean proFlag) {
		this.proFlag = proFlag;
	}
	public Boolean getPickupFlag() {
		return pickupFlag;
	}
	public void setPickupFlag(Boolean pickupFlag) {
		this.pickupFlag = pickupFlag;
	}
	public String getRegionList() {
		return regionList;
	}
	public void setRegionList(String regionList) {
		this.regionList = regionList;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	
	
	public boolean isActiveFlag() {
		return activeFlag;
	}
	public void setActiveFlag(boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	
}
