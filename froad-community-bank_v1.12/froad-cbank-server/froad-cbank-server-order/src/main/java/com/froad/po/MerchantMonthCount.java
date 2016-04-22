package com.froad.po;

import java.io.Serializable;

public class MerchantMonthCount implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public Long id; 
	public String clientId; 
	public String merchantId; 
	public String year; 
	public String month; 
	public Integer monthMoney; 
	public Integer groupOrderCount; 
	public Integer groupOrderMoney; 
	public Integer sellOrderCount; 
	public Integer sellOrderMoney; 
	public Integer faceOrderCount; 
	public Integer faceOrderMoney; 
	
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
	public Integer getMonthMoney() {
		return monthMoney;
	}
	public void setMonthMoney(Integer monthMoney) {
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
	public Integer getFaceOrderCount() {
		return faceOrderCount;
	}
	public void setFaceOrderCount(Integer faceOrderCount) {
		this.faceOrderCount = faceOrderCount;
	}
	public Integer getGroupOrderMoney() {
		return groupOrderMoney;
	}
	public void setGroupOrderMoney(Integer groupOrderMoney) {
		this.groupOrderMoney = groupOrderMoney;
	}
	public Integer getSellOrderMoney() {
		return sellOrderMoney;
	}
	public void setSellOrderMoney(Integer sellOrderMoney) {
		this.sellOrderMoney = sellOrderMoney;
	}
	public Integer getFaceOrderMoney() {
		return faceOrderMoney;
	}
	public void setFaceOrderMoney(Integer faceOrderMoney) {
		this.faceOrderMoney = faceOrderMoney;
	}
	
}
