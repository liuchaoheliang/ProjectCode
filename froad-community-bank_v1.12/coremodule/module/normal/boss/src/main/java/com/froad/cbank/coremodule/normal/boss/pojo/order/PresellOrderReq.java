package com.froad.cbank.coremodule.normal.boss.pojo.order;

import java.io.Serializable;

import com.froad.cbank.coremodule.normal.boss.pojo.Page;

public class PresellOrderReq extends Page implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 订单编号
	 */
	private String subOrderId;
	/**
	 * 订单状态1:订单创建；2:订单用户关闭;3:订单系统关闭;4:订单支付中;5:订单支付失败;6:订单支付完成
	 */
	private String orderStatus;
	/**
	 * 商品名称
	 */
	private String productName;
	/**
	 * 客户端
	 */
	private String clientId;
	/**
	 * 订单创建开始时间
	 */
	private String startTime;
	/**
	 * 订单创建结束时间
	 */
	private String endTime;

	public String getSubOrderId() {
		return subOrderId;
	}

	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
