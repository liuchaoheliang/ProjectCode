package com.froad.CB.po.bill;

/**
 * 文件名称:OrderDetailInfo.java 文件描述: 订单明细数据项 产品标识: 分分通 单元描述: server 编写人:
 * gq.hou_Mimosa 编写时间: 14-3-4 历史修改:
 */
public class OrderDetail {
	/**
	 * 订单编号
	 */
	public String orderID;

	/**
	 * 订单金额
	 */
	public String orderAmount;

	/**
	 * 订单供应商ID
	 */
	public String orderSupplier;

	/**
	 * 订单显示名
	 */
	public String orderDisplay;

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

	public String getOrderSupplier() {
		return orderSupplier;
	}

	public void setOrderSupplier(String orderSupplier) {
		this.orderSupplier = orderSupplier;
	}

	public String getOrderDisplay() {
		return orderDisplay;
	}

	public void setOrderDisplay(String orderDisplay) {
		this.orderDisplay = orderDisplay;
	}
}
