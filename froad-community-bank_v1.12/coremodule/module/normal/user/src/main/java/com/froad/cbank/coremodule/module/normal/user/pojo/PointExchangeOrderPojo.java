package com.froad.cbank.coremodule.module.normal.user.pojo;

public class PointExchangeOrderPojo {
	
	public String productId;
	
	public String productName;
	
	public String productImage;

	public double money;
	
	public int quantity;
	
	public String orderStatus;
	
	public long exchangeTime;
	
	public String orderId;
	 /**
	   * 线上:0 ，线下:1
	   */
	public String type; // required
	  /**
	   * 机构号
	   */
	public String orgCode; // required
	  /**
	   * 机构名称
	   */
	public String orgName; // required
	  
	  
	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public long getExchangeTime() {
		return exchangeTime;
	}

	public void setExchangeTime(long exchangeTime) {
		this.exchangeTime = exchangeTime;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	 
	 
}
