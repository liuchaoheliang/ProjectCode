package com.froad.fft.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

/**
 * 地区
 * @author FQ
 *
 */
public class AreaDto extends TreeDto{

	private String name;// 地区名称
	private Integer orderValue;// 排序

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getOrderValue() {
		return orderValue;
	}
	public void setOrderValue(Integer orderValue) {
		this.orderValue = orderValue;
	}
}
