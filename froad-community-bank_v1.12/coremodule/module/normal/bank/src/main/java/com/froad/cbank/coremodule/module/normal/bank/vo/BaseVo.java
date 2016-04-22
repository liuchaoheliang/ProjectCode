package com.froad.cbank.coremodule.module.normal.bank.vo;

public class BaseVo {

	private String clientId;
	private Integer pageNumber;
	private Integer pageSize;
	private Integer lastPageNumber;//当前页
	private Long firstRecordTime;//当前页第一条记录时间
	private Long lastRecordTime; //当前页最后一条记录时间
	private String startDate;//开始日期
	private String endDate;	//结束日期
	private String userId;// 用户id
	/***********报表增加字段***********/
	/**总记录数*/
	private Integer totalCount;
	/**总页数*/
	private Integer pageCount;
	/**0 没有下一页 1 还有下一页*/
	private Integer hasNext;
	/***********报表增加字段***********/
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getLastPageNumber() {
		return lastPageNumber;
	}
	public void setLastPageNumber(Integer lastPageNumber) {
		this.lastPageNumber = lastPageNumber;
	}
	public Long getFirstRecordTime() {
		return firstRecordTime;
	}
	public void setFirstRecordTime(Long firstRecordTime) {
		this.firstRecordTime = firstRecordTime;
	}
	public Long getLastRecordTime() {
		return lastRecordTime;
	}
	public void setLastRecordTime(Long lastRecordTime) {
		this.lastRecordTime = lastRecordTime;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public Integer getPageCount() {
		return pageCount;
	}
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	public Integer getHasNext() {
		return hasNext;
	}
	public void setHasNext(Integer hasNext) {
		this.hasNext = hasNext;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
