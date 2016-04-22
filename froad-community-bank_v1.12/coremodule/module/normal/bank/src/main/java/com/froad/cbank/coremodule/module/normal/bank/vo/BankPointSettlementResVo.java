package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

public class BankPointSettlementResVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4911546798900767920L;

	/**
	 * 订单编号
	 */
	public String orderId; // required
	/**
	 * 会员号
	 */
	public String memberCode; // required
	/**
	 * 会员名
	 */
	public String memberName; // required
	/**
	 * 会员手机号
	 */
	public String mobile; // required
	/**
	 * 商品名称
	 */
	public String productName; // required
	/**
	 * 商品单价
	 */
	public double productPrice; // required
	/**
	 * 商品数量
	 */
	public int productQuantity; // required
	/**
	 * 商品总价
	 */
	public double productTotalPrice; // required
	/**
	 * 结算总价
	 */
	public double totalPrice; // required
	/**
	 * 支付方式
	 */
	public String paymentMethod; // required
	/**
	 * 银行积分
	 */
	public double bankPoint; // required
	/**
	 * 银行积分比例
	 */
	public String bankPointRate; // required
	/**
	 * 联盟积分
	 */
	public double froadPoint; // required
	/**
	 * 现金
	 */
	public double cash; // required
	/**
	 * 结算时间
	 */
	public long settlementTime; // required
	/**
	 * 所属商户
	 */
	public String merchantName; // required
	/**
	 * 客户端Id
	 */
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public double getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}
	public int getProductQuantity() {
		return productQuantity;
	}
	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}
	public double getProductTotalPrice() {
		return productTotalPrice;
	}
	public void setProductTotalPrice(double productTotalPrice) {
		this.productTotalPrice = productTotalPrice;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public double getBankPoint() {
		return bankPoint;
	}

	public void setBankPoint(double bankPoint) {
		this.bankPoint = bankPoint;
	}
	public String getBankPointRate() {
		return bankPointRate;
	}
	public void setBankPointRate(String bankPointRate) {
		this.bankPointRate = bankPointRate;
	}

	public double getFroadPoint() {
		return froadPoint;
	}

	public void setFroadPoint(double froadPoint) {
		this.froadPoint = froadPoint;
	}

	public double getCash() {
		return cash;
	}

	public void setCash(double cash) {
		this.cash = cash;
	}
	public long getSettlementTime() {
		return settlementTime;
	}
	public void setSettlementTime(long settlementTime) {
		this.settlementTime = settlementTime;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}


}
