
package com.froad.common;

import java.io.Serializable;
import java.util.List;

import com.froad.util.Assert;

/** 
 * @author FQ 
 * @date 2012-11-15 下午01:34:06
 * @version 1.0
 * 
 */

/**
 * Bean类 - 分页
 */
public class Pager implements Serializable{
	
	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;

	// 排序方式
	public enum OrderType{
		asc, desc
	}
	
	public static final Integer MAX_PAGE_SIZE = 5000;// 每页最大记录数限制

	private Integer pageNumber = 1;// 当前页码
	private Integer pageSize = 20;// 每页记录数
	private Integer totalCount = 0;// 总记录数
	private Integer pageCount = 0;// 总页数
	private Integer startRow = 1; // 查询起始记录数        
	private String property;// 查找属性名称
	private String keyword;// 查找关键字
	private String orderBy = "create_time";// 排序字段
	private OrderType orderType = OrderType.desc;// 排序方式
	private List list;// 数据List

	private String beginTime;// 开始日期
	private String endTime;// 结束日期
	
	public Integer getStartRow() {
		return (pageNumber - 1) * pageSize;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
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
		} else if(pageSize > MAX_PAGE_SIZE) {
			pageSize = MAX_PAGE_SIZE;
		}
		this.pageSize = pageSize;
	}
	
	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getPageCount() {
		pageCount = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			pageCount ++;
		}
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}
	
	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}


	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = Assert.empty(beginTime)?"":beginTime+"|00:00:00";
		
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = Assert.empty(endTime)?"":endTime+"|23:59:59";
	}
}
