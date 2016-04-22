package com.froad.cbank.coremodule.normal.boss.pojo.order;

import java.io.Serializable;
import java.math.BigInteger;

import com.froad.cbank.coremodule.normal.boss.pojo.Page;

/**
 * 运营商订单 列表请求实体
 * @author songzichao
 * 
 */
public class BusinessOrderVoReq extends Page implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 用户编号
	 */
	private BigInteger memberCode;
	/**
	 * 所属客户端
	 */
	private String clientId;
	/**
	 * 发货状态 0:未发货;1:已发货;2:已收货;3:未提货;4:已提货
	 */
	private String deliveryStatus;
	/**
	 * 支付方式 1:现金支付;2:联盟积分支付;3:银行积分支付;4:联盟积分+现金支付;5:银行积分+现金支付;6:赠送积分
	 */
	private String paymentMethod;
	/**
	 * 订单状态1:订单创建；2:订单用户关闭;3:订单系统关闭;4:订单支付中;5:订单支付失败;6:订单支付完成
	 */
	private String orderStatus;
	/**
	 * 订单创建开始时间
	 */
	private String createStartTime; // required
	/**
	 * 订单创建结束时间
	 */
	private String createEndTime; // required
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public BigInteger getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(BigInteger memberCode) {
		this.memberCode = memberCode;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
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
	public String getCreateStartTime() {
		return createStartTime;
	}
	public void setCreateStartTime(String createStartTime) {
		this.createStartTime = createStartTime;
	}
	public String getCreateEndTime() {
		return createEndTime;
	}
	public void setCreateEndTime(String createEndTime) {
		this.createEndTime = createEndTime;
	}
	public String getDeliveryStatus() {
		return deliveryStatus;
	}
	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	 
	 

}
