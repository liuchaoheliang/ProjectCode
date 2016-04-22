package com.froad.db.mongo.page;

import java.util.List;

public class MongoPage {

	/** 列表每页显示条数 */
	private Integer pageSize = 20;

	/** 列表当前页 */
	private Integer pageNumber = 1;

	/** 列表总页数 */
	private Integer pageCount = 1;

	/** 列表总数据量 */
	private Integer totalCount = 0;
	
	/** 排序*/
	private Sort sort;

	/** 数据集 */
	private List<?> items;
	
	/** 上次查询页码 */
	private Integer lastPageNumber = 0;
	
	/** 当前页第一条记录时间 */
	private Long firstRecordTime = 0l;
	
	/** 当前页最后一条记录时间 */
	private Long lastRecordTime = 0l;
	
	private Boolean hasNext = true; // 是否还有下一页

	public MongoPage(){
		
	}
	
	public MongoPage(int pageNumber, int pageSize) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}
	
	public Integer getPageSize() {

		return pageSize;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public List<?> getItems() {
		return items;
	}

	public void setItems(List<?> items) {
		this.items = items;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	public void build(List<?> items) {
		this.setItems(items);
		int count =  this.getTotalCount();
//		int divisor = count / this.getPageSize();
//		int remainder = count % this.getPageSize();
//		this.setPageCount(remainder == 0 ? divisor == 0 ? 1 : divisor : divisor + 1);
		this.setPageCount((int) Math.ceil(count / this.getPageSize().doubleValue()));
	}

	public Sort getSort() {
		return sort;
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}

	/**
	 * @return the lastPageNumber
	 */
	public Integer getLastPageNumber() {
		return lastPageNumber;
	}

	/**
	 * @param lastPageNumber the lastPageNumber to set
	 */
	public void setLastPageNumber(Integer lastPageNumber) {
		this.lastPageNumber = lastPageNumber;
	}

	/**
	 * @return the firstRecordTime
	 */
	public Long getFirstRecordTime() {
		return firstRecordTime;
	}

	/**
	 * @param firstRecordTime the firstRecordTime to set
	 */
	public void setFirstRecordTime(Long firstRecordTime) {
		this.firstRecordTime = firstRecordTime;
	}

	/**
	 * @return the lastRecordTime
	 */
	public Long getLastRecordTime() {
		return lastRecordTime;
	}

	/**
	 * @param lastRecordTime the lastRecordTime to set
	 */
	public void setLastRecordTime(Long lastRecordTime) {
		this.lastRecordTime = lastRecordTime;
	}

	public Boolean getHasNext() {
		return hasNext;
	}

	public void setHasNext(Boolean hasNext) {
		this.hasNext = hasNext;
	}

	
	
}