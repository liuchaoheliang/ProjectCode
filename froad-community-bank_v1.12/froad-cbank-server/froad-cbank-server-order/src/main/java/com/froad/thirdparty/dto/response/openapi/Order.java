package com.froad.thirdparty.dto.response.openapi;

public class Order {

	private String trackNo;// 跟踪号

	private String refundOrderID;// 退款订单编号

	private String refundAmount;// 退款金额

	/**
	 * 状态码  0：支付成功 1：支付失败
	 */
	private String stateCode;// 状态码  0：支付成功 1：支付失败

	private String orderID;// 订单号

	private String orderAmount;// 订单金额

	private String orderCurrency = "1";// 订单货币

	private String orderRemark;// 订单拓展信息

	private String orderAcquiringTime;// 收单时间

	private String orderCompleteTime;// 处理完成时间

	private String remark; // 备注

	private String acquiringTime;// 收单时间
	private String completeTime;// 处理完成时间
	private String froadBillNo;// 方付通支付号

	public String getTrackNo() {
		return trackNo;
	}

	public void setTrackNo(String trackNo) {
		this.trackNo = trackNo;
	}

	public String getRefundOrderID() {
		return refundOrderID;
	}

	public void setRefundOrderID(String refundOrderID) {
		this.refundOrderID = refundOrderID;
	}

	public String getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getOrderCurrency() {
		return orderCurrency;
	}

	public void setOrderCurrency(String orderCurrency) {
		this.orderCurrency = orderCurrency;
	}

	public String getOrderRemark() {
		return orderRemark;
	}

	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}

	public String getOrderAcquiringTime() {
		return orderAcquiringTime;
	}

	public void setOrderAcquiringTime(String orderAcquiringTime) {
		this.orderAcquiringTime = orderAcquiringTime;
	}

	public String getOrderCompleteTime() {
		return orderCompleteTime;
	}

	public void setOrderCompleteTime(String orderCompleteTime) {
		this.orderCompleteTime = orderCompleteTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAcquiringTime() {
		return acquiringTime;
	}

	public void setAcquiringTime(String acquiringTime) {
		this.acquiringTime = acquiringTime;
	}

	public String getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}

	public String getFroadBillNo() {
		return froadBillNo;
	}

	public void setFroadBillNo(String froadBillNo) {
		this.froadBillNo = froadBillNo;
	}

}
