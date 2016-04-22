package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 订单Response
 * 
 * @author ylchu
 *
 */
public class OrderRes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4002342196239168006L;

	private String orderId; // required
	private String orgName; // required
	private String orgCode; // required
	private String subOrderId; // required
	private String paymentMethod; // required
	private String paymentMethodName; // required
	private String orderState; // required
	private String orderStateName; // required
	private String subTotalMoney; // required
	private String point; // required
	private String cash; // required
	private String createSoure; // required
	private String merchantName; // required
	private String productName; // required
	private String DeliveryState;
	private String DeliveryStateName;
	private String createTime;
	public String refundState;  //退款状态
	public String refundStateName;  //退款状态
	public String quantity;//销售数量
	
	public String getOrgName() {
		return orgName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
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

	public String getPaymentMethodName() {
		return paymentMethodName;
	}

	public void setPaymentMethodName(String paymentMethodName) {
		this.paymentMethodName = paymentMethodName;
	}

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public String getSubTotalMoney() {
		return subTotalMoney;
	}

	public void setSubTotalMoney(String subTotalMoney) {
		this.subTotalMoney = subTotalMoney;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public String getCash() {
		return cash;
	}

	public void setCash(String cash) {
		this.cash = cash;
	}

	public String getCreateSoure() {
		return createSoure;
	}

	public void setCreateSoure(String createSoure) {
		this.createSoure = createSoure;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDeliveryState() {
		return DeliveryState;
	}

	public void setDeliveryState(String deliveryState) {
		DeliveryState = deliveryState;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getOrderStateName() {
		return orderStateName;
	}

	public void setOrderStateName(String orderStateName) {
		this.orderStateName = orderStateName;
	}

	public String getDeliveryStateName() {
		return DeliveryStateName;
	}

	public void setDeliveryStateName(String deliveryStateName) {
		DeliveryStateName = deliveryStateName;
	}

	public String getRefundState() {
		return refundState;
	}

	public void setRefundState(String refundState) {
		this.refundState = refundState;
	}

	public String getRefundStateName() {
		return refundStateName;
	}

	public void setRefundStateName(String refundStateName) {
		this.refundStateName = refundStateName;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
}
