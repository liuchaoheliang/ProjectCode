package com.froad.common.beans;

/**
 * Copyright © 2015 F-Road. All rights reserved.
 * 
 * @ClassName: PaymentPoTemp
 * @Description: 支付流水Po临时传输Bean
 * @Author: zhaoxiaoyao@f-road.com.cn
 * @Date: 2015年3月27日 下午4:23:45
 */
public class PaymentPoTemp {

	private String clientId;
	private String orderId;
	private Integer payType;
	private Double payValue;
	private String payOrgNo;
	private Integer cashType;
	private Integer pointRate;
	private String paymentReason;
	private String paymentId;
	private String billNo;
	private String fromAccountNo;
	private String fromUserName;
	private String toAccountName;
	private String fromPhone;
	
	public String getOrderId() {
		return orderId;
	}
	public Integer getPayType() {
		return payType;
	}
	public String getPayOrgNo() {
		return payOrgNo;
	}
	public Integer getCashType() {
		return cashType;
	}
	public Integer getPointRate() {
		return pointRate;
	}
	public String getPaymentReason() {
		return paymentReason;
	}
	public String getPaymentId() {
		return paymentId;
	}
	public Double getPayValue() {
		return payValue;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public void setPayValue(Double payValue) {
		this.payValue = payValue;
	}
	public void setPayOrgNo(String payOrgNo) {
		this.payOrgNo = payOrgNo;
	}
	public void setCashType(Integer cashType) {
		this.cashType = cashType;
	}
	public void setPointRate(Integer pointRate) {
		this.pointRate = pointRate;
	}
	public String getToAccountName() {
		return toAccountName;
	}
	public void setToAccountName(String toAccountName) {
		this.toAccountName = toAccountName;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getFromAccountNo() {
		return fromAccountNo;
	}
	public void setFromAccountNo(String fromAccountNo) {
		this.fromAccountNo = fromAccountNo;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public void setPaymentReason(String paymentReason) {
		this.paymentReason = paymentReason;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	
	
	
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getFromPhone() {
		return fromPhone;
	}
	public void setFromPhone(String fromPhone) {
		this.fromPhone = fromPhone;
	}
	public PaymentPoTemp(String clientId, String orderId, Integer payType,
			Double payValue, String payOrgNo, Integer cashType,
			Integer pointRate, String paymentReason, String paymentId) {
		this.clientId = clientId;
		this.orderId = orderId;
		this.payType = payType;
		this.payValue = payValue;
		this.payOrgNo = payOrgNo;
		this.cashType = cashType;
		this.pointRate = pointRate;
		this.paymentReason = paymentReason;
		this.paymentId = paymentId;
	}
	public PaymentPoTemp(String clientId, String orderId, Integer payType,
			Double payValue, String payOrgNo, Integer cashType,
			Integer pointRate, String paymentReason) {
		this.clientId = clientId;
		this.orderId = orderId;
		this.payType = payType;
		this.payValue = payValue;
		this.payOrgNo = payOrgNo;
		this.cashType = cashType;
		this.pointRate = pointRate;
		this.paymentReason = paymentReason;
	}
	public PaymentPoTemp(String clientId, String orderId, Integer payType,
			Double payValue, String payOrgNo, Integer cashType,
			Integer pointRate, String paymentReason,String billNo, String fromAccountNo, String fromUserName) {
		this.clientId = clientId;
		this.orderId = orderId;
		this.payType = payType;
		this.payValue = payValue;
		this.payOrgNo = payOrgNo;
		this.cashType = cashType;
		this.pointRate = pointRate;
		this.paymentReason = paymentReason;
		this.billNo = billNo;
		this.fromAccountNo = fromAccountNo;
		this.fromUserName = fromUserName;
	}
	public PaymentPoTemp(String clientId, String orderId, Integer payType,
			Double payValue, String payOrgNo, Integer cashType,
			Integer pointRate, String paymentReason,String billNo, String fromAccountNo, String fromUserName,String toAccountName) {
		this.clientId = clientId;
		this.orderId = orderId;
		this.payType = payType;
		this.payValue = payValue;
		this.payOrgNo = payOrgNo;
		this.cashType = cashType;
		this.pointRate = pointRate;
		this.paymentReason = paymentReason;
		this.billNo = billNo;
		this.fromAccountNo = fromAccountNo;
		this.fromUserName = fromUserName;
		this.toAccountName = toAccountName;
	}
	
	
	
	
}
