package com.froad.cbank.coremodule.normal.boss.pojo.order;

import java.io.Serializable;

//--------------交易概要信息-------------------------
public class BusinessOrderTradeRes implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 结算状态 */
	private String settleState;
	/** 结算账单号 */
	private String settlement_id;
	/** 结算时间 */
	private String settlementTime;
	/** 订单类型 */
	private String orderType;
	/** 商品名称 */
	private String productName;
	/** 购买数量 */
	private int productCount;
	/** 提货开始日期 */
	private String deliveryTime;
	/** 购买单价 */
	private double price;
	/** 金额小计 */
	private double moneyLittleCount;
	/** 提货方式 */
	private String deliveryOption;
	/** 提货网点 */
	private String deliveryAddress;
	/** 提（收）货人 */
	private String consignee;
	/** 提（收）货手机号 */
	private String phone;
	/** 最后消费时间 */
	private String dealineConsumeTime;
	/** 消费数量 */
	private int consumeAmount;
	/** 商品ID */
	private String productId;
	/**
	 * 该商品是否已退款 true已退款 false未退款
	 */
	private Boolean isRefund;
	
	public String getSettleState() {
		return settleState;
	}
	public void setSettleState(String settleState) {
		this.settleState = settleState;
	}
	public String getSettlement_id() {
		return settlement_id;
	}
	public void setSettlement_id(String settlement_id) {
		this.settlement_id = settlement_id;
	}
	public String getSettlementTime() {
		return settlementTime;
	}
	public void setSettlementTime(String settlementTime) {
		this.settlementTime = settlementTime;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getProductCount() {
		return productCount;
	}
	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}
	public String getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getMoneyLittleCount() {
		return moneyLittleCount;
	}
	public void setMoneyLittleCount(double moneyLittleCount) {
		this.moneyLittleCount = moneyLittleCount;
	}
	public String getDeliveryOption() {
		return deliveryOption;
	}
	public void setDeliveryOption(String deliveryOption) {
		this.deliveryOption = deliveryOption;
	}
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDealineConsumeTime() {
		return dealineConsumeTime;
	}
	public void setDealineConsumeTime(String dealineConsumeTime) {
		this.dealineConsumeTime = dealineConsumeTime;
	}
	public int getConsumeAmount() {
		return consumeAmount;
	}
	public void setConsumeAmount(int consumeAmount) {
		this.consumeAmount = consumeAmount;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public Boolean getIsRefund() {
		return isRefund;
	}
	public void setIsRefund(Boolean isRefund) {
		this.isRefund = isRefund;
	}

}
