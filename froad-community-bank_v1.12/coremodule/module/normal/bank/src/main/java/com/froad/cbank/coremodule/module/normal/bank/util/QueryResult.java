package com.froad.cbank.coremodule.module.normal.bank.util;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class QueryResult<T> {
	protected List<T> result = null;
	protected int totalCount = 0;
	protected int pageCount = 0;
	protected int pageNumber = 1;
	private int lastPageNumber = 0;
	private Long firstRecordTime = 0l;
	private Long lastRecordTime = 0l; 
	public boolean hasNext; // required

	public QueryResult() {
	}

	public boolean getHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	/**
	 * 取得页内的记录列表.
	 */
	public List<T> getResult() {
		if (result == null)
			return Collections.emptyList();
		return result;
	}

	public void setResult(final List<T> result) {
		this.result = result;
	}

	/**
	 * 取得总记录数,默认值为-1.
	 */
	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(final int totalCount) {
		this.totalCount = totalCount;
	}
	
	public void setTotalCount(final String totalCount) {
		if(StringUtils.isEmpty(totalCount) || "undefined".equalsIgnoreCase(totalCount))
			this.totalCount = 0;
		else
			this.totalCount = Integer.parseInt(totalCount);
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(final int pageCount) {
		this.pageCount = pageCount;
	}
	
	public void setPageCount(final String pageCount) {
		if(StringUtils.isEmpty(pageCount) || "undefined".equalsIgnoreCase(pageCount))
			this.pageCount = 0;
		else
			this.pageCount = Integer.parseInt(pageCount);
	}
	
	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(final int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	public void setPageNumber(final String pageNumber) {
		if(StringUtils.isEmpty(pageNumber) || "undefined".equalsIgnoreCase(pageNumber))
			this.pageNumber = 1;
		else
			this.pageNumber = Integer.parseInt(pageNumber);
	}

	public int getLastPageNumber() {
		return lastPageNumber;
	}
	
	public void setLastPageNumber(final int lastPageNumber) {
		this.lastPageNumber = lastPageNumber;
	}
	
	public void setLastPageNumber(final String lastPageNumber) {
		if(StringUtils.isEmpty(lastPageNumber) || "undefined".equalsIgnoreCase(lastPageNumber))
			this.lastPageNumber = 0;
		else
			this.lastPageNumber = Integer.parseInt(lastPageNumber);
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
	
}
