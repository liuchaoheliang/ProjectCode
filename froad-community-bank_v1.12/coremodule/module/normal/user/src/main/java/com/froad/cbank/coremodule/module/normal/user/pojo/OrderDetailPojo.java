package com.froad.cbank.coremodule.module.normal.user.pojo;

import java.util.List;

public class OrderDetailPojo {

	private String orderId;
	
	private String orderStatus;
	
	private double totalPrice;
	
	private double fftPoints;
	
	private double bankPoints;
	
	private long createTime;
	
	private long paymentTime;
	
	private String paymentMethod;
	
	private boolean isEnableCancel;
	
	private boolean isEnablePay;
	
	private boolean isEnableRefund;
	
	private List<SubOrderDetailPojo> subOrderDetailList;
	
	private DeliverInfoPojo deliverInfo;
	
	private String subOrderId;//确认收货接口参数
	
	private String address;
	
	/**
	   * 是否秒杀
	   */
	private boolean isSeckill; // required
	  /**
	   * 是否补全配送信息:
	   * 0：秒杀成功+已填信息
	   * 1：秒杀成功+自提
	   * 2：秒杀成功+配送
	   * 3：秒杀成功+自提、配送
	   */
	private String deliveryFlag; // required
	/**
	   * 是否VIP订单
	   */
	public boolean isVipOrder; // required
	/**
	   * VIP优惠金额
	   */
	public double vipDiscount; // required
	
	/**
	 * fftPointsAmount:联盟积分抵扣金额
	 */
	private double fftPointsAmount;
	
	/**
	 * bankPointsAmount:银行积分抵扣金额 
	 */
	private double bankPointsAmount;
	
	/**
	 * giveMoney:赠送金额
	 */
	private double giveMoney;
	  
	/**
	 * givePoints:赠送积分
	 */
	private double givePoints;
	
	
	
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

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public double getFftPoints() {
		return fftPoints;
	}

	public void setFftPoints(double fftPoints) {
		this.fftPoints = fftPoints;
	}

	public double getBankPoints() {
		return bankPoints;
	}

	public void setBankPoints(double bankPoints) {
		
		this.bankPoints = bankPoints;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(long paymentTime) {
		this.paymentTime = paymentTime;
	}

	public List<SubOrderDetailPojo> getSubOrderDetailList() {
		return subOrderDetailList;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public void setSubOrderDetailList(List<SubOrderDetailPojo> subOrderDetailList) {
		this.subOrderDetailList = subOrderDetailList;
	}

	public boolean isIsEnableCancel() {
		return isEnableCancel;
	}

	public void setIsEnableCancel(boolean isEnableCancel) {
		this.isEnableCancel = isEnableCancel;
	}

	public boolean isIsEnablePay() {
		return isEnablePay;
	}

	public void setIsEnablePay(boolean isEnablePay) {
		this.isEnablePay = isEnablePay;
	}

	public boolean isIsEnableRefund() {
		return isEnableRefund;
	}

	public void setIsEnableRefund(boolean isEnableRefund) {
		this.isEnableRefund = isEnableRefund;
	}

	public DeliverInfoPojo getDeliverInfo() {
		return deliverInfo;
	}

	public void setDeliverInfo(DeliverInfoPojo deliverInfo) {
		this.deliverInfo = deliverInfo;
	}

	public String getSubOrderId() {
		return subOrderId;
	}

	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isIsSeckill() {
		return isSeckill;
	}

	public void setIsSeckill(boolean isSeckill) {
		this.isSeckill = isSeckill;
	}

	public String getDeliveryFlag() {
		return deliveryFlag;
	}

	public void setDeliveryFlag(String deliveryFlag) {
		this.deliveryFlag = deliveryFlag;
	}

	public boolean isIsVipOrder() {
		return isVipOrder;
	}

	public void setIsVipOrder(boolean isVipOrder) {
		this.isVipOrder = isVipOrder;
	}

	public double getVipDiscount() {
		return vipDiscount;
	}

	public void setVipDiscount(double vipDiscount) {
		this.vipDiscount = vipDiscount;
	}

	public double getFftPointsAmount() {
		return fftPointsAmount;
	}

	public void setFftPointsAmount(double fftPointsAmount) {
		this.fftPointsAmount = fftPointsAmount;
	}

	public double getBankPointsAmount() {
		return bankPointsAmount;
	}

	public void setBankPointsAmount(double bankPointsAmount) {
		this.bankPointsAmount = bankPointsAmount;
	}

	public double getGiveMoney() {
		return giveMoney;
	}

	public void setGiveMoney(double giveMoney) {
		this.giveMoney = giveMoney;
	}

	public double getGivePoints() {
		return givePoints;
	}

	public void setGivePoints(double givePoints) {
		this.givePoints = givePoints;
	}
	
}
