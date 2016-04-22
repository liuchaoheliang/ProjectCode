package com.froad.fft.thirdparty.dto.request.openapi;

import com.froad.fft.thirdparty.common.SystemCommand;

public class OpenApiOrderDetail {

	// 合并支付传入的多个订单

	private String orderID;// 订单号
	private String orderAmount;// 订单金额
	private String orderSupplier;// 订单供应商ID
	private String orderDisplay;// 订单显示名

	public String getOrderID() {
		return orderID;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public String getOrderSupplier() {
		return orderSupplier;
	}

	public String getOrderDisplay() {
		return orderDisplay;
	}

	public OpenApiOrderDetail(String orderID, String orderAmount,
			String orderSupplier, String orderDisplay) {
		this.orderID = SystemCommand.FFT_CLIENT_PREFIX + orderID;
		this.orderAmount = orderAmount;
		this.orderSupplier = orderSupplier;
		this.orderDisplay = orderDisplay;
	}
}
