package com.froad.po.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.froad.thrift.vo.PageVo;


/**
 * 该实体用于实现分页功能
* <p>Function: PageEntity</p>
* <p>Description: </p>
* @author zhaoxy@thankjava.com
* @date 2014年12月22日 下午4:36:31
* @version 1.0
 */
public class PageEntity<T> implements Serializable{
	
	private static final long serialVersionUID = -1357196555818179804L;

	public static final int MAX_PAGE_SIZE = 500;// 每页最大记录数限制

	public enum OrderByType{
		desc,asc
	}
	
	/**
	 * 分页第几页
	 */
	private int pageNumber = 1;
	
	/**
	 * 分页每页数据条数
	 */
	private int pageSize = 20;
	
	/**
	 * 总数据条数
	 */
	private int totalCount = 0;
	
	/**
	 * 总分页条数
	 */
	private int pageCount = 0;
	
	/**
	 * 返回当前页数数据集合
	 */
	private List<T> dataRes = new ArrayList<T>();
	
	/**
	 * 用于分页的筛选条件对象
	 */
	private T condition;
	
	/**
	 * 排序具体列
	 */
	private String orderByColumn;

	/**
	 * 默认降序
	 */
	private OrderByType orderByType = OrderByType.desc;

    /** 上次查找页面 */
    public int          lastPageNumber;                   // required
    /** 当前页开始时间 */
    public long         firstRecordTime;                 // required
    /** 当前页结束时间 */
    public long         lastRecordTime;                  // required
	
    
    /**
     * 优化分页查询条件
     * @param pageVo
     * @return PageEntity<T>
     *<pre>
     *
     * @version V1.0 修改人：Arron 日期：2015年5月12日 下午7:27:18 
     *
     *</pre>
     */
    public PageEntity<T> convert(PageVo pageVo) {
        this.pageNumber = pageVo.getPageNumber();
        this.pageSize = pageVo.getPageSize();
        this.lastPageNumber = pageVo.getLastPageNumber();
        this.firstRecordTime = pageVo.getFirstRecordTime();
        this.lastRecordTime = pageVo.getLastRecordTime();
        return this;
    }
    
	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		if(pageNumber < 1){
			pageNumber = 1;
		}
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if (pageSize < 1) {
			pageSize = 1;
		} else if (pageSize > MAX_PAGE_SIZE) {
			pageSize = MAX_PAGE_SIZE;
		}
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		this.pageCount = totalCount % this.pageSize == 0 ? totalCount / this.pageSize : totalCount / this.pageSize + 1;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public List<T> getDataRes() {
		return dataRes;
	}

	public void setDataRes(List<T> dataRes) {
		this.dataRes = dataRes;
	}

	public T getCondition() {
		return condition;
	}

	public void setCondition(T condition) {
		this.condition = condition;
	}

	public String getOrderByColumn() {
		return orderByColumn;
	}

	public void setOrderByColumn(String orderByColumn) {
		this.orderByColumn = orderByColumn;
	}

	public OrderByType getOrderByType() {
		return orderByType;
	}

	public void setOrderByType(OrderByType orderByType) {
		this.orderByType = orderByType;
	}

    /**
     * @return the lasPageNumber
     */
    public int getLastPageNumber() {
        return lastPageNumber;
    }

    /**
     * @param lasPageNumber the lasPageNumber to set
     */
    public void setLastPageNumber(int lasPageNumber) {
        this.lastPageNumber = lasPageNumber;
    }

    /**
     * @return the firstRecordTime
     */
    public long getFirstRecordTime() {
        return firstRecordTime;
    }

    /**
     * @param firstRecordTime the firstRecordTime to set
     */
    public void setFirstRecordTime(long firstRecordTime) {
        this.firstRecordTime = firstRecordTime;
    }

    /**
     * @return the lastRecordTime
     */
    public long getLastRecordTime() {
        return lastRecordTime;
    }

    /**
     * @param lastRecordTime the lastRecordTime to set
     */
    public void setLastRecordTime(long lastRecordTime) {
        this.lastRecordTime = lastRecordTime;
    }
}
