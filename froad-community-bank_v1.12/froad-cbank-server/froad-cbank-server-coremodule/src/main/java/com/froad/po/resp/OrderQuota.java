package com.froad.po.resp;

/**
 * 订单指标
 * @author bruce
 *
 */
public class OrderQuota {
	
	private Integer orderCount; //订单（数）
	
	private Integer orderUmulation; //累计订单
	
	private Integer orderRefund; //退款订单
	
	private Integer refundTotalCount;//累计退款订单
	
	private Integer orderTurnover;//成交订单=订单-退款订单
	 
	private Integer orderCumulationTurnover;//累积成交订单=累积订单-累积退款订单

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	public Integer getOrderUmulation() {
		return orderUmulation;
	}

	public void setOrderUmulation(Integer orderUmulation) {
		this.orderUmulation = orderUmulation;
	}

	public Integer getOrderRefund() {
		return orderRefund;
	}

	public void setOrderRefund(Integer orderRefund) {
		this.orderRefund = orderRefund;
	}

	public Integer getRefundTotalCount() {
		return refundTotalCount;
	}

	public void setRefundTotalCount(Integer refundTotalCount) {
		this.refundTotalCount = refundTotalCount;
	}

	public Integer getOrderTurnover() {
		return orderTurnover;
	}

	public void setOrderTurnover(Integer orderTurnover) {
		this.orderTurnover = orderTurnover;
	}

	public Integer getOrderCumulationTurnover() {
		return orderCumulationTurnover;
	}

	public void setOrderCumulationTurnover(Integer orderCumulationTurnover) {
		this.orderCumulationTurnover = orderCumulationTurnover;
	}
 
}
