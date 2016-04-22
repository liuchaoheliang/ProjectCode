package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 商品
 * 
 * @author ylchu
 *
 */
public class SubProductVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6028266756444857112L;

	private String productId; // 商品编号
	private String productName; // 商品名称
	private Integer quantity; // 购买数量
	private Integer vipQuantity; // vip购买数量
	private String money; // 商品单价
	private String vipMoney; // 商品vip价
	private String points;  //赠送积分
	private String deliverMoney; //运费
	private String takeStartDate; //提货开始时间
	private String takeEndDate; //提货结束时间
	private String takeState;  //配送或者自提方式
	private String deliveryState; // 配送或者自提状态(0.未发货，1.已发货，2.已收货，3.未提货，4.已提货,5.部分提货)
	private String consumeStatus; //消费状态1.未消费，2.已消费，3.部分消费*/
	private String subTotalMoney;//小计
	private String refundNumber; // 退款数量
	
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
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Integer getVipQuantity() {
		return vipQuantity;
	}
	public void setVipQuantity(Integer vipQuantity) {
		this.vipQuantity = vipQuantity;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getVipMoney() {
		return vipMoney;
	}
	public void setVipMoney(String vipMoney) {
		this.vipMoney = vipMoney;
	}
	public String getPoints() {
		return points;
	}
	public void setPoints(String points) {
		this.points = points;
	}
	public String getSubTotalMoney() {
		return subTotalMoney;
	}
	public void setSubTotalMoney(String subTotalMoney) {
		this.subTotalMoney = subTotalMoney;
	}
	public String getDeliverMoney() {
		return deliverMoney;
	}
	public void setDeliverMoney(String deliverMoney) {
		this.deliverMoney = deliverMoney;
	}
	public String getTakeStartDate() {
		return takeStartDate;
	}
	public void setTakeStartDate(String takeStartDate) {
		this.takeStartDate = takeStartDate;
	}
	public String getTakeEndDate() {
		return takeEndDate;
	}
	public void setTakeEndDate(String takeEndDate) {
		this.takeEndDate = takeEndDate;
	}
	public String getTakeState() {
		return takeState;
	}
	public void setTakeState(String takeState) {
		this.takeState = takeState;
	}
	public String getDeliveryState() {
		return deliveryState;
	}
	public void setDeliveryState(String deliveryState) {
		this.deliveryState = deliveryState;
	}
	public String getConsumeStatus() {
		return consumeStatus;
	}
	public void setConsumeStatus(String consumeStatus) {
		this.consumeStatus = consumeStatus;
	}

	public String getRefundNumber() {
		return refundNumber;
	}

	public void setRefundNumber(String refundNumber) {
		this.refundNumber = refundNumber;
	}
	
}
