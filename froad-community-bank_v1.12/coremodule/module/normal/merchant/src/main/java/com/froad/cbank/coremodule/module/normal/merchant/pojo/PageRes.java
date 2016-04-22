package com.froad.cbank.coremodule.module.normal.merchant.pojo;

public class PageRes {

	private int pageNumber;
	private int pageSize;
	private int totalCount;
	private int pageCount;
	private int lastPageNumber; 
	private long firstRecordTime; 
	private long lastRecordTime; 
	
	
	public int getLastPageNumber() {
		return lastPageNumber;
	}
	public void setLastPageNumber(int lastPageNumber) {
		this.lastPageNumber = lastPageNumber;
	}
	public long getFirstRecordTime() {
		return firstRecordTime;
	}
	public void setFirstRecordTime(long firstRecordTime) {
		this.firstRecordTime = firstRecordTime;
	}
	public long getLastRecordTime() {
		return lastRecordTime;
	}
	public void setLastRecordTime(long lastRecordTime) {
		this.lastRecordTime = lastRecordTime;
	}
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
