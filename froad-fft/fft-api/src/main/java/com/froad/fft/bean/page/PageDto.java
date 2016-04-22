package com.froad.fft.bean.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页
 * @author FQ
 *
 * @param <T>
 */
public class PageDto<T> implements Serializable{
	
	private int pageNumber = 1;// 当前页码
	private int pageSize = 20;// 每页记录数
	private int totalCount = 0;// 总记录数
	private int pageCount = 0;// 总页数
	
	private OrderDto orderDto;
	private PageFilterDto<T> pageFilterDto;//筛选条件对象

	private List<T> resultsContent = new ArrayList<T>();// 对应的当前页记录
	
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
	
	public List<T> getResultsContent() {
		return resultsContent;
	}

	public void setResultsContent(List<T> resultsContent) {
		this.resultsContent = resultsContent;
	}

	public OrderDto getOrderDto() {
		return orderDto;
	}

	public void setOrderDto(OrderDto orderDto) {
		this.orderDto = orderDto;
	}
	
	public PageFilterDto<T> getPageFilterDto() {
		return pageFilterDto;
	}

	public void setPageFilterDto(PageFilterDto<T> pageFilterDto) {
		this.pageFilterDto = pageFilterDto;
	}
	
}
