package com.froad.cbank.coremodule.module.normal.user.pojo;

import java.util.List;

public class OrderSummaryPojo{

	private String orderId;
	
	private double totalPrice;
	
	private String orderStatus;
	
	private List<OrderProductSummaryPojo> productList;
	
	private boolean isEnableCancel;
	
	private boolean isEnablePay;

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
	 * 能否显示vip退款
	 */
	public boolean isVipRefund;
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public List<OrderProductSummaryPojo> getProductList() {
		return productList;
	}

	public void setProductList(List<OrderProductSummaryPojo> productList) {
		this.productList = productList;
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

	public boolean isVipRefund() {
		return isVipRefund;
	}

	public void setVipRefund(boolean isVipRefund) {
		this.isVipRefund = isVipRefund;
	}

	
}
