/**
 * Project Name:froad-cbank-server-boss-1.10.0-SNAPSHOT
 * File Name:BossPrefPayOrderDetailInfo.java
 * Package Name:com.froad.po
 * Date:2015年12月29日下午1:54:26
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.po;


/**
 * ClassName:BossPrefPayOrderDetailInfo
 * Reason:	 TODO ADD REASON.
 * Date:     2015年12月29日 下午1:54:26
 * @author   huangyihao
 * @version  
 * @see 	 
 */
public class BossPrefPayOrderDetailInfo {
	
	/**
	 * 订单编号
	 */
	private String orderId;

	/**
	 * 订单创建时间
	 */
	private Long createTime;

	/**
	 * 用户会员名
	 */
	private String memberName;

	/**
	 * 用户真实姓名
	 */
	private String userName;

	/**
	 * 客户端名称
	 */
	private String clientName;
	
	/**
	 * 订单创建来源
	 */
	private String createSource;

	/**
	 * 支付类型
	 */
	private String orderType;

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
	 * 优惠金额
	 */
	private Double discount;

	/**
	 * 红包折扣
	 */
	private Double redPacketDiscount;

	/**
	 * 订单状态
	 */
	private String orderStatus;

	/**
	 * 订单支付时间
	 */
	private Long paymentTime;

	/**
	 * 支付账单号
	 */
	private String billNos;

	/**
	 * 结算所属商户全称
	 */
	private String merchantName;

	/**
	 * 交易所属门店全称
	 */
	private String outletName;

	/**
	 * 结算状态
	 */
	private String settleStatus;

	/**
	 * 结算账单号
	 */
	private String settleBillNos;

	/**
	 * 结算时间
	 */
	private Long settleTime;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getCreateSource() {
		return createSource;
	}

	public void setCreateSource(String createSource) {
		this.createSource = createSource;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
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

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getRedPacketDiscount() {
		return redPacketDiscount;
	}

	public void setRedPacketDiscount(Double redPacketDiscount) {
		this.redPacketDiscount = redPacketDiscount;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Long getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Long paymentTime) {
		this.paymentTime = paymentTime;
	}

	public String getBillNos() {
		return billNos;
	}

	public void setBillNos(String billNos) {
		this.billNos = billNos;
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

	public String getSettleStatus() {
		return settleStatus;
	}

	public void setSettleStatus(String settleStatus) {
		this.settleStatus = settleStatus;
	}

	public String getSettleBillNos() {
		return settleBillNos;
	}

	public void setSettleBillNos(String settleBillNos) {
		this.settleBillNos = settleBillNos;
	}

	public Long getSettleTime() {
		return settleTime;
	}

	public void setSettleTime(Long settleTime) {
		this.settleTime = settleTime;
	}
	
}
