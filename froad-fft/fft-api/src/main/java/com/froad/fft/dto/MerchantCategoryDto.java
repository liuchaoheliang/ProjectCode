package com.froad.fft.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 商户分类
 * @author FQ
 *
 */
public class MerchantCategoryDto extends TreeDto{

	private String name;// 分类名称
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
