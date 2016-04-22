package com.froad.cbank.coremodule.normal.boss.pojo.order;

import java.io.Serializable;

/**
 * 运营商订单 列表返回实体
 * @author songzichao
 */
public class BusinessOrderVoRes implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 子订单编号
	 */
	private String subOrderId; // required
	/**
	 * 订单状态1:订单创建；2:订单用户关闭;3:订单系统关闭;4:订单支付中;5:订单支付失败;6:订单支付完成
	 */
	private String orderStatus;
	/**
	 * 用户编号
	 */
	private long memberCode; 
	/**
	 * 支付方式 1:现金支付;2:联盟积分支付;3:银行积分支付;4:联盟积分+现金支付;5:银行积分+现金支付;6:赠送积分
	 */
	private String paymentMethod;
	/**
	 * 总金额
	 */
	private double totalPrice; 
	/**
	 * 现金
	 */
	private double realPrice; 
	/**
	 * 联盟积分
	 */
	private double fftPoints;
	/**
	 * 银行积分
	 */
	private double bankPoints;
	/**
	 * 银行积分兑换比例
	 */
	private String poinRate; 
	/**
	 * 订单创建时间
	 */
	private String createTime;
	/**
	 * 支付时间
	 */
	private String paymentTime; 
	/**
	 * 所属客户端
	 */
	private String clientId;
	
	/**
	 * 客户端名称
	 */
	private String clientName;
	/**
	 * 发货状态 0:未发货;1:已发货;2:已收货;3:未提货;4:已提货
	 */
	private String deliveryStatus;
	
	 /**
	   * 退款状态 1:未退款;2:退款中;3:退款完成;4:部分退款
	 */
	private String refundState; // required
	
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
	public long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(long memberCode) {
		this.memberCode = memberCode;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public double getRealPrice() {
		return realPrice;
	}
	public void setRealPrice(double realPrice) {
		this.realPrice = realPrice;
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
	public String getPoinRate() {
		return poinRate;
	}
	public void setPoinRate(String poinRate) {
		this.poinRate = poinRate;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(String paymentTime) {
		this.paymentTime = paymentTime;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getDeliveryStatus() {
		return deliveryStatus;
	}
	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
	public String getSubOrderId() {
		return subOrderId;
	}
	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getRefundState() {
		return refundState;
	}
	public void setRefundState(String refundState) {
		this.refundState = refundState;
	}
	
	
	

}
