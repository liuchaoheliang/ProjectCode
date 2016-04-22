package com.froad.fft.persistent.entity;

import com.froad.fft.persistent.common.enums.DataState;

/**
 * 商品类型
 * @author FQ
 *
 */
public class ProductType extends BaseEntity {
	
	private String name;//类型名称
	
	private DataState dataState;//数据状态：有效、删除

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
