/**
 * Project Name:froad-cbank-server-boss-1.10.0-SNAPSHOT
 * File Name:BossPrefPayOrderListInfo.java
 * Package Name:com.froad.po
 * Date:2015年12月29日下午1:45:57
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.po;

import java.util.Date;

/**
 * ClassName:BossPrefPayOrderListInfo
 * Reason:	 TODO ADD REASON.
 * Date:     2015年12月29日 下午1:45:57
 * @author   huangyihao
 * @version  
 * @see 	 
 */
public class BossPrefPayOrderListInfo {
	
	/**
	 * 订单编号
	 */
	private String orderId;
	
	/**
	 * 订单状态
	 */
	private String orderStatus;
	
	/**
	 * 用户会员名
	 */
	private String memberName;
	
	/**
	 * 用户手机号
	 */
	private String phone;
	
	/**
	 * 支付类型
	 */
	private String orderType;
	
	/**
	 * 所属商户
	 */
	private String merchantName;
	
	/**
	 * 所属门店
	 */
	private String outletName;

	/**
	 * 支付方式
	 */
	private String paymentMethod;

	/**
	 * 订单总金额
	 */
	private Double totalPrice;

	/**
	 * 订单现金
	 */
	private Double cash;

	/**
	 * 联盟积分
	 */
	private Double fftPoints;

	/**
	 * 银行积分
	 */
	private Double bankPoints;

	/**
	 * 银行积分兑换比例
	 */
	private String pointRate;

	/**
	 * 订单创建时间
	 */
	private Long createTime;

	/**
	 * 订单支付时间
	 */
	private Long paymentTime;

	/**
	 * 客户端ID
	 */
	private String clientId;

	/**
	 * 客户端名称
	 */
	private String clientName;

	/**
	 * 支付账单号
	 */
	private String billNos;

	/**
	 * 订单创建来源
	 */
	private String createSource;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getCash() {
		return cash;
	}

	public void setCash(Double cash) {
		this.cash = cash;
	}

	public Double getFftPoints() {
		return fftPoints;
	}

	public void setFftPoints(Double fftPoints) {
		this.fftPoints = fftPoints;
	}

	public Double getBankPoints() {
		return bankPoints;
	}

	public void setBankPoints(Double bankPoints) {
		this.bankPoints = bankPoints;
	}

	public String getPointRate() {
		return pointRate;
	}

	public void setPointRate(String pointRate) {
		this.pointRate = pointRate;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Long paymentTime) {
		this.paymentTime = paymentTime;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getBillNos() {
		return billNos;
	}

	public void setBillNos(String billNos) {
		this.billNos = billNos;
	}

	public String getCreateSource() {
		return createSource;
	}

	public void setCreateSource(String createSource) {
		this.createSource = createSource;
	}

}
