package com.froad.fft.dto;

import java.io.Serializable;
import java.util.Date;

import com.froad.fft.enums.DataState;

/**
 * 商品类型
 * @author FQ
 *
 */
public class ProductTypeDto implements Serializable{

	private Long id;
	private Date createTime;
	private String name;//类型名称
	private DataState dataState;//数据状态
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public DataState getDataState() {
		return dataState;
	}
	public void setDataState(DataState dataState) {
		this.dataState = dataState;
	}
	
	
	
}
