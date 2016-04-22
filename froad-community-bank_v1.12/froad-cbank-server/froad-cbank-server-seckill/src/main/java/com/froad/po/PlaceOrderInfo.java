package com.froad.po;

import java.util.Date;

/**
 * 秒杀下单信息
 * 
 * @author wangzhangxu
 * @date 2015年4月15日 下午6:36:21
 * @version v1.0
 */
public class PlaceOrderInfo {
	
	/**
	 * 状态：-1-受理订单号不存在，0-已受理，1-已下单
	 */
	private Integer status;
	
	/**
	 * 受理订单号
	 */
	private String acceptOrderId;
	
	/**
	 * 订单号
	 */
	private String orderId;
	
	/**
	 * 受理时间
	 */
	private Date acceptDate;
	
	/**
	 * 下单时间
	 */
	private Date orderDate;
	
	public PlaceOrderInfo(){}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAcceptOrderId() {
		return acceptOrderId;
	}

	public void setAcceptOrderId(String acceptOrderId) {
		this.acceptOrderId = acceptOrderId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Date getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(Date acceptDate) {
		this.acceptDate = acceptDate;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	
	
}
