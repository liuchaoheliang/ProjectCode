package com.froad.po;

import java.io.Serializable;

public class ProductMonthCount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Long id; // required
	public String clientId; // required
	public String merchantId; // required
	public String productId; // required
	public String productName; // required
	public String year; // required
	public String month; // required
	public Integer sellCount; // required
	public Integer sellMoney; // required
	
	
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
	public Integer getSellCount() {
		return sellCount;
	}
	public void setSellCount(Integer sellCount) {
		this.sellCount = sellCount;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getSellMoney() {
		return sellMoney;
	}
	public void setSellMoney(Integer sellMoney) {
		this.sellMoney = sellMoney;
	}
}
