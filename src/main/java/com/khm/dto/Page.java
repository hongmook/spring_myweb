package com.khm.dto;

public class Page {
	
	private int startPage;
	private int endPage;
	private int totalPage;
	private Criteria cri;
	private boolean prev;
	private boolean next;

	public Page(int totalPage, Criteria cri) {
		super();
		this.totalPage = totalPage;
		this.cri = cri;
		
		//화면상에 보이는 페이지 갯수
		this.endPage = (int)Math.ceil(cri.getCurrentPage()/(cri.getRowPerPage()*1.0))*cri.getRowPerPage(); 
		this.startPage = this.endPage - (cri.getRowPerPage()-1);
		
		//데이터베이스에서 가져온 갯수만큼 계산한 최종 페이지 수
		int realEnd = (int)(Math.ceil((totalPage*1.0)/cri.getRowPerPage()));
		
		if(realEnd < this.endPage) {
			this.endPage = realEnd;
		}
		
		this.prev = this.startPage > 1;
		this.next = this.endPage < realEnd;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public Criteria getCri() {
		return cri;
	}

	public void setCri(Criteria cri) {
		this.cri = cri;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}
	
	
}
