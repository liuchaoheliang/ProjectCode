package com.froad.po;

import java.io.Serializable;

import net.sf.oval.constraint.MaxLength;
import net.sf.oval.constraint.Range;
import net.sf.oval.guard.Guarded;
@Guarded
public class MerchantMonthCount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Long id; // required
	@MaxLength(value = 20, message = "客户端id不能超过{max}个字符")
	public String clientId; // required
	@MaxLength(value = 20, message = "商户id不能超过{max}个字符")
	public String merchantId; // required
	@MaxLength(value = 4, message = "年份不能超过{max}个字符")
	public String year; // required
	@Range(min = 1, max = 12,message = "月份必须为{min}至{max}范围以内")
	public String month; // required
	public Double monthMoney; // required
	public Integer groupOrderCount; // required
	public Double groupOrderMoney; // required
	public Integer sellOrderCount; // required
	public Double sellOrderMoney; // required
	public Integer faceOrderCount; // required
	public Double faceOrderMoney; // required
	@MaxLength(value = 20, message = "商品id不能超过{max}个字符")
	public String productId; // required
	@MaxLength(value = 16, message = "商品名称不能超过{max}个字符")
	public String productName; // required
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
	public Double getMonthMoney() {
		return monthMoney;
	}
	public void setMonthMoney(Double monthMoney) {
		this.monthMoney = monthMoney;
	}
	public Integer getGroupOrderCount() {
		return groupOrderCount;
	}
	public void setGroupOrderCount(Integer groupOrderCount) {
		this.groupOrderCount = groupOrderCount;
	}
	public Integer getSellOrderCount() {
		return sellOrderCount;
	}
	public void setSellOrderCount(Integer sellOrderCount) {
		this.sellOrderCount = sellOrderCount;
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
	public Integer getFaceOrderCount() {
		return faceOrderCount;
	}
	public void setFaceOrderCount(Integer faceOrderCount) {
		this.faceOrderCount = faceOrderCount;
	}
	public Double getGroupOrderMoney() {
		return groupOrderMoney;
	}
	public void setGroupOrderMoney(Double groupOrderMoney) {
		this.groupOrderMoney = groupOrderMoney;
	}
	public Double getSellOrderMoney() {
		return sellOrderMoney;
	}
	public void setSellOrderMoney(Double sellOrderMoney) {
		this.sellOrderMoney = sellOrderMoney;
	}
	public Double getFaceOrderMoney() {
		return faceOrderMoney;
	}
	public void setFaceOrderMoney(Double faceOrderMoney) {
		this.faceOrderMoney = faceOrderMoney;
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
