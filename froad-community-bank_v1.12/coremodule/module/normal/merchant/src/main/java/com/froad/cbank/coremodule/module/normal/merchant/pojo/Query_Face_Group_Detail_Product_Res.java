package com.froad.cbank.coremodule.module.normal.merchant.pojo;

public class Query_Face_Group_Detail_Product_Res {
	private String productName;
	private String productImage;
	private Integer quantity;
	private Double money;
	private Double subTotalMoney;
	private Boolean isOutletComment;
	private String takeCode;
	private String deliverState;
	private String settlementStatus;
	private String settlementNumber;
	private Double deliveryMoney;

	/**
	 * 预售-开始时间-预售为提货时间段， 团购为有效时间段
	 */
	private long startTime; // required
	/**
	 * 预售-结束时间
	 */
	private long endTime; // required
	/**
	 * 提货状态-1.未消费，2.已消费，3.部分消费
	 */
	private String consumeStatus; // required
	/**
	 * 商品ID
	 */
	private String productId; // required
	/**
	 * 退款数量
	 */
	private Integer refundNumber; // required
	/**
	 * 剩余数量
	 */
	private Integer surplusNumber; // required

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

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public String getConsumeStatus() {
		return consumeStatus;
	}

	public void setConsumeStatus(String consumeStatus) {
		this.consumeStatus = consumeStatus;
	}

	public Double getDeliveryMoney() {
		return deliveryMoney;
	}

	public void setDeliveryMoney(Double deliveryMoney) {
		this.deliveryMoney = deliveryMoney;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Double getSubTotalMoney() {
		return subTotalMoney;
	}

	public void setSubTotalMoney(Double subTotalMoney) {
		this.subTotalMoney = subTotalMoney;
	}

	public Boolean getIsOutletComment() {
		return isOutletComment;
	}

	public void setIsOutletComment(Boolean isOutletComment) {
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

}
