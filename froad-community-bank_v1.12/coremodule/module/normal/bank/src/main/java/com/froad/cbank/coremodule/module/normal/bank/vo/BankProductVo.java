package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 商品
 * 
 * @author ylchu
 *
 */
public class BankProductVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6028266756444857112L;

	private String productId; // 商品ID
	private String productName; // 商品名称
	private String productImage;// 商品图片
	private Integer quantity; // 购买数量
	private String money; // 商品单价
	private String subTotalMoney;//金额小计
	private boolean isOutletComment;//是否评价
	private String takeCode;//提货码（团购交易使用）
	private String deliverState; // 配送或者自提状态(0.未发货，1.已发货，2.已收货，3.未提货，4.已提货,5.部分提货)
	private String settlementStatus;//结算状态（0.未结算，2.结算成功）--只要结算一个则结算成功并带结算数量
	private String settlementNumber;//结算数量--实际结算数量
	private String takeStartDate; //提货开始时间
	private String takeEndDate; //提货结束时间
	private String consumeStatus; //消费状态1.未消费，2.已消费，3.部分消费*/
	private String takeState;  //提货或配送方式
	private String deliveryMoney; //运费-新增
	private Integer refundNumber;//退款数量
	private Integer surplusNumber;//剩余数量
	
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

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public boolean isOutletComment() {
		return isOutletComment;
	}

	public void setOutletComment(boolean isOutletComment) {
		this.isOutletComment = isOutletComment;
	}

	public String getTakeCode() {
		return takeCode;
	}

	public void setTakeCode(String takeCode) {
		this.takeCode = takeCode;
	}

	public String getDeliverState() {
		return deliverState;
	}

	public void setDeliverState(String deliverState) {
		this.deliverState = deliverState;
	}

	public String getSettlementStatus() {
		return settlementStatus;
	}

	public void setSettlementStatus(String settlementStatus) {
		this.settlementStatus = settlementStatus;
	}

	public String getSettlementNumber() {
		return settlementNumber;
	}

	public void setSettlementNumber(String settlementNumber) {
		this.settlementNumber = settlementNumber;
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

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getSubTotalMoney() {
		return subTotalMoney;
	}

	public void setSubTotalMoney(String subTotalMoney) {
		this.subTotalMoney = subTotalMoney;
	}

	public String getDeliveryMoney() {
		return deliveryMoney;
	}

	public void setDeliveryMoney(String deliveryMoney) {
		this.deliveryMoney = deliveryMoney;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getRefundNumber() {
		return refundNumber;
	}

	public void setRefundNumber(Integer refundNumber) {
		this.refundNumber = refundNumber;
	}

	public Integer getSurplusNumber() {
		return surplusNumber;
	}

	public void setSurplusNumber(Integer surplusNumber) {
		this.surplusNumber = surplusNumber;
	}

	public String getConsumeStatus() {
		return consumeStatus;
	}

	public void setConsumeStatus(String consumeStatus) {
		this.consumeStatus = consumeStatus;
	}
	
}
