package com.hs.app.jwt.vo;

import java.io.Serializable;

public class JwtUser implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idx;
	private String email;
	private String name;
	private String img;
	private String phonenm;
	
	public JwtUser() {}
	public JwtUser(Integer idx, String email, String name, String img, String phonenm) {
		this.idx = idx;
		this.email = email;
		this.name = name;
		this.img = img;
		this.phonenm = phonenm;
		this.img = img;
	}
	
	public Integer getIdx() {
		return idx;
	}
	public void setIdx(Integer idx) {
		this.idx = idx;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	
	
	public String getPhonenm() {
		return phonenm;
	}
	public void setPhonenm(String phonenm) {
		this.phonenm = phonenm;
	}
	@Override
	public String toString() {
		return "JwtUser [idx=" + idx + ", email=" + email + ", name=" + name + ", img=" + img + "]";
	}

	
}
