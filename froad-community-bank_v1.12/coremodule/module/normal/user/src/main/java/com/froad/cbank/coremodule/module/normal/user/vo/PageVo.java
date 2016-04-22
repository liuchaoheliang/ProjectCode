package com.froad.cbank.coremodule.module.normal.user.vo;

/**
 * 分页对象
 */
public class PageVo {

	/* 当前页码 */
	private int pageNumber;
	/* 每页记录数 */
	private int pageSize;
	/* 总记录数 */
	private int totalCount;
	/* 总页数 */
	private int pageCount;
	
	
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

}
