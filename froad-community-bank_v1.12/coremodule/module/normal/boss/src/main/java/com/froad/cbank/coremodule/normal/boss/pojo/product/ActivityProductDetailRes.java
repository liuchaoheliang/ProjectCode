package com.froad.cbank.coremodule.normal.boss.pojo.product;

import java.util.List;

public class ActivityProductDetailRes {
	
	/**
	 * 客户端
	 */
	private String clientId;
	/**
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 子订单号
	 */
	private String subOrderId;
	/**
	 * 支付方式(对应枚举类PaymentMethod)
	 */
	private String paymentMethod;
	/**
	 * 订单状态(对应枚举类OrderStatus)
	 */
	private String orderStatus;
	/**
	 * 订单子状态，即退款状态(对应枚举类SubOrderRefundState)：{"1":"未退款"，"2":"退款中","3":"退款完成","4"
	 * :"部分退款"}
	 */
	private String refundState;
	/**
	 * 下单时间
	 */
	private String createTime;
	/**
	 * 订单总金额
	 */
	private double totalPrice;
	/**
	 * 子订单总送积分值
	 */
	private double totalGivePoints;
	/**
	 * 商品信息
	 */
	private List<ActivityProductPointsRes> product;
	
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getSubOrderId() {
		return subOrderId;
	}

	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getRefundState() {
		return refundState;
	}

	public void setRefundState(String refundState) {
		this.refundState = refundState;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public double getTotalGivePoints() {
		return totalGivePoints;
	}

	public void setTotalGivePoints(double totalGivePoints) {
		this.totalGivePoints = totalGivePoints;
	}

	public List<ActivityProductPointsRes> getProduct() {
		return product;
	}

	public void setProduct(List<ActivityProductPointsRes> product) {
		this.product = product;
	}

	
}
