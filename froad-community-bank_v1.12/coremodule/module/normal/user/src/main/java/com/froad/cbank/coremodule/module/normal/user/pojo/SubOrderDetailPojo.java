package com.froad.cbank.coremodule.module.normal.user.pojo;

import java.util.List;

public class SubOrderDetailPojo {

	private String subOrderId;
	
	private String type;
	
	private String merchantId;
	
	private String merchantName;
	
	private double subTotalMoney;
	
	private boolean isEnableConfirmReceive;
	
	private boolean isEnableRefund;
	
	private boolean isEnableSeeTicket;
	
	private List<OrderProductPojo> orderProductList;
	
	private ShippingDetailPojo shippingDetail;
	
	private String refundState;
	/**
	 * 精品商城物流配送状态。0:未发货;1:已发货;2:已收货
	 */
	private String deliveryState;

	public String getRefundState() {
		return refundState;
	}

	public void setRefundState(String refundState) {
		this.refundState = refundState;
	}

	public ShippingDetailPojo getShippingDetail() {
		return shippingDetail;
	}

	public void setShippingDetail(ShippingDetailPojo shippingDetail) {
		this.shippingDetail = shippingDetail;
	}

	public String getSubOrderId() {
		return subOrderId;
	}

	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public double getSubTotalMoney() {
		return subTotalMoney;
	}

	public void setSubTotalMoney(double subTotalMoney) {
		this.subTotalMoney = subTotalMoney;
	}
	

	public List<OrderProductPojo> getOrderProductList() {
		return orderProductList;
	}

	public void setOrderProductList(List<OrderProductPojo> orderProductList) {
		this.orderProductList = orderProductList;
	}

	public boolean isIsEnableConfirmReceive() {
		return isEnableConfirmReceive;
	}

	public void setIsEnableConfirmReceive(boolean isEnableConfirmReceive) {
		this.isEnableConfirmReceive = isEnableConfirmReceive;
	}

	public boolean isIsEnableRefund() {
		return isEnableRefund;
	}

	public void setIsEnableRefund(boolean isEnableRefund) {
		this.isEnableRefund = isEnableRefund;
	}

	public boolean isIsEnableSeeTicket() {
		return isEnableSeeTicket;
	}

	public void setIsEnableSeeTicket(boolean isEnableSeeTicket) {
		this.isEnableSeeTicket = isEnableSeeTicket;
	}

	public String getDeliveryState() {
		return deliveryState;
	}

	public void setDeliveryState(String deliveryState) {
		this.deliveryState = deliveryState;
	}
	
	
	
}