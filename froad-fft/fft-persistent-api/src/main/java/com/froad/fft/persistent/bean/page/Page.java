package com.froad.fft.persistent.bean.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页
 * 
 * @author FQ
 * 
 * @param <T>
 */
public class Page<T> implements Serializable {

	public static final int MAX_PAGE_SIZE = 500;// 每页最大记录数限制

	private int pageNumber = 1;// 当前页码
	private int pageSize = 20;// 每页记录数
	private int totalCount = 0;// 总记录数
	private int pageCount = 0;// 总页数
	
	private Order order;//排序
	private PageFilter<T> pageFilter;//筛选条件对象
	
	private List<T> resultsContent = new ArrayList<T>();// 对应的当前页记录

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		if (pageNumber < 1) {
			pageNumber = 1;
		}
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		if (pageSize < 1) {
			pageSize = 1;
		} else if (pageSize > MAX_PAGE_SIZE) {
			pageSize = MAX_PAGE_SIZE;
		}
		this.pageSize = pageSize;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {

		this.totalCount = totalCount;
		
		// 设置总页数的时候计算出对应的总页数，在下面的三目运算中加法拥有更高的优先级，所以最后可以不加括号。
		int pageCount = totalCount % pageSize == 0 ? totalCount / pageSize
				: totalCount / pageSize + 1;
		this.setPageCount(pageCount);
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public List<T> getResultsContent() {
		return resultsContent;
	}

	public void setResultsContent(List<T> resultsContent) {
		this.resultsContent = resultsContent;
	}
	
	public PageFilter<T> getPageFilter() {
		return pageFilter;
	}

	public void setPageFilter(PageFilter<T> pageFilter) {
		this.pageFilter = pageFilter;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Pager [pageNumber=").append(pageNumber)
				.append(", pageSize=").append(pageSize)
				.append(", resultsContent=").append(resultsContent)
				.append(", pageCount=").append(pageCount)
				.append(", totalCount=").append(totalCount).append("]");
		return builder.toString();
	}
}
