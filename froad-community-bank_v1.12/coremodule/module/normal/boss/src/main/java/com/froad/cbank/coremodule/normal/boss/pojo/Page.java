package com.froad.cbank.coremodule.normal.boss.pojo;

public class Page {

	/**
	 * 当前页码
	 */
	private int pageNumber = 1;
	/**
	 * 每页记录数
	 */
	private int pageSize = 10;
	/**
	 * 总记录数
	 */
	private int totalCount;
	/**
	 * 总页数
	 */
	private int pageCount;
	/**
	 * 上次查询页码
	 */
	private int lastPageNumber;
	/**
	 * 当前页第一条记录时间
	 */
	private long firstRecordTime;
	/**
	 * 当前页最后一条记录时间
	 */
	private long lastRecordTime;
	/**
	 * 是否还有下一页
	 */
	private boolean hasNext;

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

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

}
