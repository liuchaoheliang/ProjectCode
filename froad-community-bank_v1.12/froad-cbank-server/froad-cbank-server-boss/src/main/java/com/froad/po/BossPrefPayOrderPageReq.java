/**
 * Project Name:froad-cbank-server-boss-1.10.0-SNAPSHOT
 * File Name:BossPrefPayOrderPageReq.java
 * Package Name:com.froad.po
 * Date:2015年12月29日下午1:52:24
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.po;
/**
 * ClassName:BossPrefPayOrderPageReq
 * Reason:	 TODO ADD REASON.
 * Date:     2015年12月29日 下午1:52:24
 * @author   huangyihao
 * @version  
 * @see 	 
 */
public class BossPrefPayOrderPageReq {
	
	/**
	 * 订单编号
	 */
	private String orderId;

	/**
	 * 用户会员名
	 */
	private String memberName;

	/**
	 * 所属客户端
	 */
	private String clientId;

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
	 * 订单状态
	 */
	private String orderStatus;

	/**
	 * 订单创建开始时间
	 */
	private long begTime;

	/**
	 * 订单创建结束时间
	 */
	private long endTime;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
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

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public long getBegTime() {
		return begTime;
	}

	public void setBegTime(long begTime) {
		this.begTime = begTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

}
