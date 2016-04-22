package com.froad.po;

public class UserTradeInfo {
	
	private String userName;		//用户名称
	private String mobile;			//注册手机号
	private String isVip;			//是否VIP
	private Integer orderCount;		//订单总数
	private Double totalAmount;		//订单总金额
	private Integer productNumber;	//购买商品数
	private Double productAmount;	//购买商品金额
	private Double refundsAmount;	//退款金额
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getIsVip() {
		return isVip;
	}
	public void setIsVip(String isVip) {
		this.isVip = isVip;
	}
	public Integer getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Integer getProductNumber() {
		return productNumber;
	}
	public void setProductNumber(Integer productNumber) {
		this.productNumber = productNumber;
	}
	public Double getProductAmount() {
		return productAmount;
	}
	public void setProductAmount(Double productAmount) {
		this.productAmount = productAmount;
	}
	public Double getRefundsAmount() {
		return refundsAmount;
	}
	public void setRefundsAmount(Double refundsAmount) {
		this.refundsAmount = refundsAmount;
	}
	
	
}
