/**
 * Project Name:coremodule-merchant
 * File Name:Query_Order_Detail_PC_Product_Res.java
 * Package Name:com.froad.cbank.coremodule.module.normal.merchant.pojo
 * Date:2015年9月17日下午6:10:39
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.merchant.pojo;
/**
 * ClassName:Query_Order_Detail_PC_Product_Res
 * Reason:	 TODO ADD REASON.
 * Date:     2015年9月17日 下午6:10:39
 * @author   mi
 * @version  
 * @see 	 
 */
public class Query_Order_Detail_PC_Product_Res {
	
	  public String productName; 
	  public String productImage; 
	  public int quantity; 
	  public double money; 
	  public double subTotalMoney; 
	  public boolean isOutletComment; 
	  public String takeCode; 
	  public String deliverState; 
	  public String settlementStatus; 
	  public String settlementNumber; 
	  /**
	   * 预售-开始时间-预售为提货时间段， 团购为有效时间段
	   */
	  public long startTime; 
	  /**
	   * 预售-结束时间
	   */
	  public long endTime; 
	  /**
	   * 提货状态-1.未消费，2.已消费，3.部分消费
	   */
	  public String consumeStatus; 
	  /**
	   * 运费-新增
	   */
	  public double deliveryMoney; 
	  /**
	   * 商品ID
	   */
	  public String productId; 
	  /**
	   * 退款数量
	   */
	  public int refundNumber; 
	  /**
	   * 剩余数量
	   */
	  public int surplusNumber; 
	  /**
	   * 配送方式
	   */
	  public String deliveryOption; 
	  /**
	   * 验码时间
	   */
	  public long checkCodeTime; 
	  /**
	   * 消费门店
	   */
	  public String outletName; 
	  /**
	   * 操作员
	   */
	  public long merchantUserId; 
	  
	  /**
	   * 操作员
	   */
	  public String merchantUserName; 
	  /**
	   * 实付款
	   */
	  public double realTotalMoney; 
	  /**
	   * 退款原因
	   */
	  public String refundReason; 
	  /**
	   * 已提货数量
	   */
	  public int takeNumber; 
	  /**
	   * 提货时间
	   */
	  public long takeTime; 
	  /**
	   * 退款申请时间
	   */
	  public long refundApplyTime; 
	  /**
	   * 退款成功时间
	   */
	  public long refundSuccessTime; 
	  /**
	   * 退款状态
	   */
	  public String refundState; 
	  /**
	   * 退款中数量
	   */
	  public int refundingNumber;
	  
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
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public double getSubTotalMoney() {
		return subTotalMoney;
	}
	public void setSubTotalMoney(double subTotalMoney) {
		this.subTotalMoney = subTotalMoney;
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
	public double getDeliveryMoney() {
		return deliveryMoney;
	}
	public void setDeliveryMoney(double deliveryMoney) {
		this.deliveryMoney = deliveryMoney;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public int getRefundNumber() {
		return refundNumber;
	}
	public void setRefundNumber(int refundNumber) {
		this.refundNumber = refundNumber;
	}
	public int getSurplusNumber() {
		return surplusNumber;
	}
	public void setSurplusNumber(int surplusNumber) {
		this.surplusNumber = surplusNumber;
	}
	public String getDeliveryOption() {
		return deliveryOption;
	}
	public void setDeliveryOption(String deliveryOption) {
		this.deliveryOption = deliveryOption;
	}
	public long getCheckCodeTime() {
		return checkCodeTime;
	}
	public void setCheckCodeTime(long checkCodeTime) {
		this.checkCodeTime = checkCodeTime;
	}
	public String getOutletName() {
		return outletName;
	}
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}
	public long getMerchantUserId() {
		return merchantUserId;
	}
	public void setMerchantUserId(long merchantUserId) {
		this.merchantUserId = merchantUserId;
	}
	public double getRealTotalMoney() {
		return realTotalMoney;
	}
	public void setRealTotalMoney(double realTotalMoney) {
		this.realTotalMoney = realTotalMoney;
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
	public String getRefundState() {
		return refundState;
	}
	public void setRefundState(String refundState) {
		this.refundState = refundState;
	}
	public int getRefundingNumber() {
		return refundingNumber;
	}
	public void setRefundingNumber(int refundingNumber) {
		this.refundingNumber = refundingNumber;
	}
	public String getMerchantUserName() {
		return merchantUserName;
	}
	public void setMerchantUserName(String merchantUserName) {
		this.merchantUserName = merchantUserName;
	}
	

}
