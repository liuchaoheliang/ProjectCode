/**
 * <p>Project: cbank</p>
 * <p>module: coremodule</p>
 * <p>@version: Copyright © 2008 F-Road All Rights Reserved</p>
 */
package com.froad.cbank.coremodule.module.normal.user.pojo;

/**
 * <p>标题: —— 标题</p>
 * <p>说明: —— 简要描述职责、应用范围、使用注意事项等</p>
 * <p>创建时间：2015-4-11下午4:05:14</p>
 * <p>作者: 高峰</p>
 * <p>版本: 1.0</p>
 * <p>修订历史:（历次修订内容、修订人、修订时间等） </p>
 */
public class PagePojo {
	private int pageNumber;		//页码
	private int totalCount;		//总数
	private int pageCount;		//总页数
	private int pageSize;		//每页显示数量
	private int lastPageNumber;	//当前页
	private long firstRecordTime;//当前页第一条记录时间
	private long lastRecordTime;//当前页最后一条记录时间
	private boolean hasNext;    //是否还有下一页
	private String begDate;     //开始时间
	private String endDate;		//结束时间
	
	public PagePojo(){
		this.pageNumber = 1;
		this.pageSize = 10;
		this.pageCount = 0;
		this.totalCount = 0;
		this.lastPageNumber = 0;
		this.firstRecordTime = 0;
		this.lastRecordTime = 0;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
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
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
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

	public String getBegDate() {
		return begDate;
	}
	public void setBegDate(String begDate) {
		this.begDate = begDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
