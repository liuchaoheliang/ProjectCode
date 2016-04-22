/**
 * Project Name:coremodule-bank
 * File Name:BankOrderProductVo.java
 * Package Name:com.froad.cbank.coremodule.module.normal.bank.vo
 * Date:2015年9月1日下午4:48:33
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * ClassName:BankOrderProductVo
 * Reason:	 TODO ADD REASON.
 * Date:     2015年9月1日 下午4:48:33
 * @author   明灿
 * @version  
 * @see 	 
 */
public class BankOrderProductVo implements Serializable {

	/**
	 * serialVersionUID:
	 */
	private static final long serialVersionUID = 2019823504977089161L;
	private String productId;// 商品id
	private String productName;// 商品名称
	private int quantity;// 购买数量
	private double price;// 购买单价
	private double point;// 积分
	private double sumPoint;// 积分
	private String ticket;// 券码
	private String takeStatus;// 提货状态
	private String consumeStatus;// 消费状态
	private String outletName;// 消费门店
	private long checkCodeTime;// 验码时间
	private String settlementStatus;// 结算状态
	private long userfulLifeStart;// 有效期开始
	private long usefulLife;// 有效期
	private long refundApplyTime;// 退款申请时间
	private long refundSuccessTime;// 退款成功时间
	private String refundReason;// 退款原因
	private String refundStatus;// 1:部分退款 2：退款中 3:已退款
	private int takeNumber;// 已提货数量
	private int refundNumber;//退款成功的数量
	private long takeTime;// 提货时间
	private String merchantUserName;// 操作员
	private String deliveryOption;// 配送或者自提
	private double sumMoney ; //总金额
	private double vipMoney;
	private int refindNumber;//退款中的数量
	private String productShippingStatus;//商品发货状态
	private int vipQuantity;// vip价购买数量
	
	public int getRefindNumber() {
		return refindNumber;
	}

	public void setRefindNumber(int refindNumber) {
		this.refindNumber = refindNumber;
	}

	public double getVipMoney() {
		return vipMoney;
	}

	public void setVipMoney(double vipMoney) {
		this.vipMoney = vipMoney;
	}

	public double getSumMoney() {
		return sumMoney;
	}

	public void setSumMoney(double sumMoney) {
		this.sumMoney = sumMoney;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getPoint() {
		return point;
	}

	public void setPoint(double point) {
		this.point = point;
	}

	public double getSumPoint() {
		return sumPoint;
	}

	public void setSumPoint(double sumPoint) {
		this.sumPoint = sumPoint;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getTakeStatus() {
		return takeStatus;
	}

	public void setTakeStatus(String takeStatus) {
		this.takeStatus = takeStatus;
	}

	public String getConsumeStatus() {
		return consumeStatus;
	}

	public void setConsumeStatus(String consumeStatus) {
		this.consumeStatus = consumeStatus;
	}

	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	public long getCheckCodeTime() {
		return checkCodeTime;
	}

	public void setCheckCodeTime(long checkCodeTime) {
		this.checkCodeTime = checkCodeTime;
	}

	public String getSettlementStatus() {
		return settlementStatus;
	}

	public void setSettlementStatus(String settlementStatus) {
		this.settlementStatus = settlementStatus;
	}

	public long getUsefulLife() {
		return usefulLife;
	}

	public void setUsefulLife(long usefulLife) {
		this.usefulLife = usefulLife;
	}

	public String getRefundReason() {
		return refundReason;
	}

	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}

	public int getTakeNumber() {
		return takeNumber;
	}

	public void setTakeNumber(int takeNumber) {
		this.takeNumber = takeNumber;
	}

	public long getTakeTime() {
		return takeTime;
	}

	public void setTakeTime(long takeTime) {
		this.takeTime = takeTime;
	}

	public String getMerchantUserName() {
		return merchantUserName;
	}

	public void setMerchantUserName(String merchantUserName) {
		this.merchantUserName = merchantUserName;
	}

	public long getUserfulLifeStart() {
		return userfulLifeStart;
	}

	public void setUserfulLifeStart(long userfulLifeStart) {
		this.userfulLifeStart = userfulLifeStart;
	}

	public long getRefundApplyTime() {
		return refundApplyTime;
	}

	public void setRefundApplyTime(long refundApplyTime) {
		this.refundApplyTime = refundApplyTime;
	}

	public long getRefundSuccessTime() {
		return refundSuccessTime;
	}

	public void setRefundSuccessTime(long refundSuccessTime) {
		this.refundSuccessTime = refundSuccessTime;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	public int getRefundNumber() {
		return refundNumber;
	}

	public void setRefundNumber(int refundNumber) {
		this.refundNumber = refundNumber;
	}

	public String getDeliveryOption() {
		return deliveryOption;
	}

	public void setDeliveryOption(String deliveryOption) {
		this.deliveryOption = deliveryOption;
	}

	public String getProductShippingStatus() {
		return productShippingStatus;
	}

	public void setProductShippingStatus(String productShippingStatus) {
		this.productShippingStatus = productShippingStatus;
	}

	public int getVipQuantity() {
		return vipQuantity;
	}

	public void setVipQuantity(int vipQuantity) {
		this.vipQuantity = vipQuantity;
	}
	

}
