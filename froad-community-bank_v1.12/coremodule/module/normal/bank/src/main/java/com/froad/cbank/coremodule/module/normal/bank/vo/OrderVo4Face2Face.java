package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

public class OrderVo4Face2Face implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 952541352994383563L;

	private String orderId;// 订单编号
	private long createTime; // 创建时间
	private long settlementTime; // 结算时间
	private String settlementStatus; // 结算状态
	private long paymentTime; // 支付时间
	// (1. 订单创建，2.订单关闭，3.订单系统关闭，4.订单支付中，5. 订单支付失败，6.订单支付完成)
	private String orderState; // 订单状态
	private String closeReason; // 关闭原因
	private String merchantName; // 商户名称
	private String name; // 消费人
	private String phone; // 手机号
	private String orgNames; // 所属机构
	private double pointDiscount; // 积分抵扣
	private double disbursements; // 实付款
	private String merchantUserName; // 操作员
	private String outletName; // 门店名称
	private double totalCutMoney;// 子订单总优惠金额
	private double totalCash;// 子订单总实付金额
	private double subTotalMoney;//子订单总金额
	private double pointMoney;// 积分抵扣金额
	private double consmeMoney;// 消费总金额
	private double discountMoney;// 门店折扣金额
	
	
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getSettlementTime() {
		return settlementTime;
	}

	public void setSettlementTime(long settlementTime) {
		this.settlementTime = settlementTime;
	}

	public long getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(long paymentTime) {
		this.paymentTime = paymentTime;
	}

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public String getCloseReason() {
		return closeReason;
	}

	public void setCloseReason(String closeReason) {
		this.closeReason = closeReason;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOrgNames() {
		return orgNames;
	}

	public void setOrgNames(String orgNames) {
		this.orgNames = orgNames;
	}

	public double getPointDiscount() {
		return pointDiscount;
	}

	public void setPointDiscount(double pointDiscount) {
		this.pointDiscount = pointDiscount;
	}

	public double getDisbursements() {
		return disbursements;
	}

	public void setDisbursements(double disbursements) {
		this.disbursements = disbursements;
	}

	public String getMerchantUserName() {
		return merchantUserName;
	}

	public void setMerchantUserName(String merchantUserName) {
		this.merchantUserName = merchantUserName;
	}

	public String getSettlementStatus() {
		return settlementStatus;
	}

	public void setSettlementStatus(String settlementStatus) {
		this.settlementStatus = settlementStatus;
	}

	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	public double getTotalCutMoney() {
		return totalCutMoney;
	}

	public void setTotalCutMoney(double totalCutMoney) {
		this.totalCutMoney = totalCutMoney;
	}

	public double getTotalCash() {
		return totalCash;
	}

	public void setTotalCash(double totalCash) {
		this.totalCash = totalCash;
	}

	public double getSubTotalMoney() {
		return subTotalMoney;
	}

	public void setSubTotalMoney(double subTotalMoney) {
		this.subTotalMoney = subTotalMoney;
	}

	public double getPointMoney() {
		return pointMoney;
	}

	public void setPointMoney(double pointMoney) {
		this.pointMoney = pointMoney;
	}

	public double getConsmeMoney() {
		return consmeMoney;
	}

	public void setConsmeMoney(double consmeMoney) {
		this.consmeMoney = consmeMoney;
	}

	public double getDiscountMoney() {
		return discountMoney;
	}

	public void setDiscountMoney(double discountMoney) {
		this.discountMoney = discountMoney;
	}
	
}
