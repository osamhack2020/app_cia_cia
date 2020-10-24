package com.hs.app.board.vo;

public class BoardMenu {
 
    private Integer idx;
    private String nm;
    private String regdate;
    private int corder;
    private String type;
    
    private Integer dataPostCount; // 메뉴 총 게시물 수
    private Integer dataViewCount; // 메뉴 총 조회 수   
    
    public void setIdx(Integer idx) {this.idx = idx;}
    public Integer getIdx() {return this.idx;}
    
    public void setNm(String nm) {this.nm = nm;}
    public String getNm() {return this.nm;}
    
    public void setRegdate(String regdate) {this.regdate = regdate;}
    public String getRegdate() {return this.regdate;}
    
    public void setCorder(int corder) {this.corder = corder;}
    public int getCorder() {return this.corder;}
    
	public Integer getDataPostCount() {return dataPostCount;}
	public void setDataPostCount(Integer dataPostCount) {this.dataPostCount = dataPostCount;}
	
	public Integer getDataViewCount() {return dataViewCount;}
	public void setDataViewCount(Integer dataViewCount) {this.dataViewCount = dataViewCount;}
	
	public String getType() {return type;}
	public void setType(String type) {this.type = type;}
	
	
	@Override
	public String toString() {
		return "BoardMenu [idx=" + idx + ", nm=" + nm + ", regdate=" + regdate + ", corder=" + corder + ", type=" + type
				+ ", dataPostCount=" + dataPostCount + ", dataViewCount=" + dataViewCount + "]";
	}
    
	
	
}