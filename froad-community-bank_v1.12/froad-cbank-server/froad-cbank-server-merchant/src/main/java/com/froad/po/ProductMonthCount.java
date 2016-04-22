package com.froad.po;

import java.io.Serializable;

import net.sf.oval.constraint.MaxLength;
import net.sf.oval.constraint.Range;
import net.sf.oval.guard.Guarded;

@Guarded
public class ProductMonthCount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Long id; // required
	@MaxLength(value = 20, message = "客户端id不能超过{max}个字符")
	public String clientId; // required
	@MaxLength(value = 20, message = "商户id不能超过{max}个字符")
	public String merchantId; // required
	@MaxLength(value = 20, message = "商品id不能超过{max}个字符")
	public String productId; // required
	@MaxLength(value = 16, message = "商品名称不能超过{max}个字符")
	public String productName; // required
	@MaxLength(value = 4, message = "年份不能超过{max}个字符")
	public String year; // required
	@Range(min = 1, max = 12,message = "月份必须为{min}至{max}范围以内")
	public String month; // required
	public Integer maxCount; // required
	public Double maxMoney;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public Integer getMaxCount() {
		return maxCount;
	}
	public void setMaxCount(Integer maxCount) {
		this.maxCount = maxCount;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Double getMaxMoney() {
		return maxMoney;
	}
	public void setMaxMoney(Double maxMoney) {
		this.maxMoney = maxMoney;
	}
	
}
