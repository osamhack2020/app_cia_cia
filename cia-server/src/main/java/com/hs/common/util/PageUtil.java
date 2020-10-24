package com.hs.common.util;

public class PageUtil {

	private int pageNum;//현재페이지번호
	private int startRow;//시작행번호
	private int endRow;//끝행번호 
	private int totalPageCount;//전체페이지갯수
	private int startPageNum;//시작페이지번호
	private int endPageNum;//끝페이지번호
	private int rowBlockCount;//한페이지 보여줄 글의 갯수
	private int pageBlockCount;//한페이지 보여줄 페이지의 갯수
	private int totalRowCount;//전체 글의 갯수
	
	
	public String toString(){
		return "";
	}
	
	public PageUtil(){}
	
	/**
	 * 
	 * @param pageNum			: 페이지번호
	 * @param totalRowCount		: 총 데이터수
	 * @param rowBlockCount		: 한 페이지에 보여줄 데이터수
	 * @param pageBlockCount	: 페이지번호 나타나는 수 (ex: 1 2 3 4..)
	 */
	public PageUtil (int pageNum,int totalRowCount,int rowBlockCount,int pageBlockCount){
		//한페이지에 30개글
		this.pageNum=pageNum;
		this.totalRowCount=totalRowCount;
		this.rowBlockCount=rowBlockCount;
		this.pageBlockCount=pageBlockCount;
		
		//전체페이지갯수 totalRowCount 이거는 DB에서 셀렉해서 가져온다
		totalPageCount=(int)Math.ceil(totalRowCount/(double)rowBlockCount);//둫 중 하나만 실수로
		if(pageNum>totalPageCount){
			pageNum = totalPageCount;
			this.pageNum = pageNum;
		}
		if(pageNum<1){
			pageNum = 1;
			this.pageNum = pageNum;
		}
		//시작 행(한컬럼한게시글)번호
		startRow=(pageNum-1)*rowBlockCount;// +1 뻇음
		//끝행번호 20이 되면 안되니까 -1
		endRow=startRow+rowBlockCount-1;
		//시작페이지갯수
		startPageNum=(pageNum-1)/pageBlockCount*pageBlockCount+1;
		//끝페이지번호
		endPageNum=startPageNum+pageBlockCount-1;
		if(totalPageCount<endPageNum){endPageNum=totalPageCount;}
	}
	
	public void setpageNum(int pageNum){
		this.pageNum = pageNum;
	}
	public int getpageNum(){
		return pageNum;
	}
	public void setStartRow(int startRow){
		this.startRow = startRow;
	}
	public int getStartRow(){
		return startRow;
	}
	public void setEndRow(int endRow){
		this.endRow = endRow;
	}
	public int getEndRow(){
		return endRow;
	}
	public void setTotalPageCount(int totalPageCount){
		this.totalPageCount = totalPageCount;
	}
	public int getTotalPageCount(){
		return totalPageCount;
	}
	public void setStartPageNum(int startPageNum){
		this.startPageNum = startPageNum;
	}
	public int getStartPageNum(){
		return startPageNum;
	}
	public void setEndPageNum(int endPageNum){
		this.endPageNum = endPageNum;
	}
	public int getEndPageNum(){
		return endPageNum;
	}
	public void setRowBlockCount(int rowBlockCount){
		this.rowBlockCount = rowBlockCount;
	}
	public int getRowBlockCount(){
		return rowBlockCount;
	}
	public void setPageBlockCount(int pageBlockCount){
		this.pageBlockCount = pageBlockCount;
	}
	public int getPageBlockCount(){
		return pageBlockCount;
	}
	public void setTotalRowCount(int totalRowCount){
		this.totalRowCount = totalRowCount;
	}
	public int getTotalRowCount(){
		return totalRowCount;
	}
	
}
