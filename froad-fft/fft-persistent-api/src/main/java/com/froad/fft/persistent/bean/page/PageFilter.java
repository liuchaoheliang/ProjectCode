package com.froad.fft.persistent.bean.page;

import java.io.Serializable;
import java.util.Date;


/**
 * 分页面过滤条件
 * @author FQ
 *
 */
public class PageFilter<T> implements Serializable {
	
	private T filterEntity;//筛选条件对象
	
	private String property;//属性
	private Date startTime;//开始时间
	private Date endTime;//结束时间
	
	public T getFilterEntity() {
		return filterEntity;
	}
	public void setFilterEntity(T filterEntity) {
		this.filterEntity = filterEntity;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
}
