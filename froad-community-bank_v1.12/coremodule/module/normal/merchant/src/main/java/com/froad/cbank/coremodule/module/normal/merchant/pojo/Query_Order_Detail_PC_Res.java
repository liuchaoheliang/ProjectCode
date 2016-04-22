/**
 * Project Name:coremodule-merchant
 * File Name:Query_Order_Detail_PC_Req.java
 * Package Name:com.froad.cbank.coremodule.module.normal.merchant.pojo
 * Date:2015年9月11日下午2:34:53
 * Copyright (c) 2015, F-Road, Inc.
 *
 */

package com.froad.cbank.coremodule.module.normal.merchant.pojo;

import java.util.List;

/**
 * 订单详情新需求 ClassName:Query_Order_Detail_PC_Req Reason: TODO ADD REASON. Date:
 * 2015年9月11日 下午2:34:53
 * 
 * @author wm
 * @version
 * @see
 */
public class Query_Order_Detail_PC_Res {

	/**
	 * 创建时间
	 */
	public long createTime;
	/**
	 * 订单状态(1. 订单创建，2.订单关闭，3.订单系统关闭，4.订单支付中，5. 订单支付失败，6.订单支付完成)
	 */
	public String orderStatus;
	/**
	 * 积分抵扣
	 */
	public double pointDiscount;
	public double subTotalMoney;
	public String businessType;
	public String outletId;
	public String outletName;

	public String deliveryCorpName;
	public String trackingNo;
	public long shippingTime;
	/**
	 * 增加退款状态，2015-05-06 新增 TODO 待补充类型
	 */
	public String refundState;
	/**
	 * 商户ID
	 */
	public String merchantId;
	/**
	 * 商户名
	 */
	public String merchantName;
	/**
	 * 支付时间（付款时间）
	 */
	public long paymentTime;
	/**
	 * 验码时间（团购验码时间）
	 */
	public long ticketTime;
	/**
	 * 订单关闭类型：1:用户关闭，2:系统关闭，3:退款关闭
	 */
	public String closeType;
	/**
	 * 所属机构
	 */
	public String orgNames;
	/**
	 * 订单关闭原因
	 */
	public String closeReason;
	/**
	 * 结算状态:已结算、未结算 新增
	 */
	public String settleState;
	/**
	 * 提货时间
	 */
	public long takeTime;
	/**
	 * 提货码
	 */
	public String takeCode;
	/**
	 * 配送方式 自提、配送
	 */
	public String deliveryOption;
	/**
	 * 退款申请时间
	 */
	public long refundApplyTime;
	/**
	 * 退款成功时间
	 */
	public long refundSuccessTime;
	/**
	 * 确认收货时间
	 */
	public long confirmReceiveTime;
	/**
	 * 结算时间
	 */
	public long settleTime;
	/**
	 * 验码有效期截止时间
	 */
	public long expireTime;
	/**
	 * 商户用户ID
	 */
	public long merchantUserId;
	/**
	 * 实付款
	 */
	public double realTotalMoney;
	/**
	 * 订单号
	 */
	public String orderId;
	/**
	 * 子订单号
	 */
	public String subOrderId;
	/**
	 * 已消费数量、已提货数量
	 */
	public int takeNumber;

	/**
	 * 是否全单
	 */
	public String isAllRefund;
	/**
	 * 购买数量
	 */
	public int buyTotalPoint;

	/**
	 * 收货人信息|提货人信息|消费人信息
	 */
	public Query_Order_Detail_PC_DeliverInfo_Res deliverInfoVo;

	public List<Query_Order_Detail_PC_Product_Res> productInfos;

	// ////////////////////////////////

	/**
	 * 操作员
	 */
	private String merchantUserName; // required
	/**
	 * 订单商品金额-预售详情自提商品总价
	 */
	private double subTotalMoneyZiti; // required
	/**
	 * 订单商品金额-预售列表配送商品总价
	 */
	private double subTotalMoneyPeisong; // required
	/**
	 * 运费-新增
	 */
	private double deliveryMoney; // required
	
	 /**
	   * 子订单总优惠金额
	   */
	  public double totalCutMoney; // required
	  /**
	   * 子订单总实付金额
	   */
	  public double totalCash; // required
	  
	   //惠付新增字段
	 
	    /**
	     * 消费总金额 
	     */
	    private Double consumeMoney ;
	    /**
	     * 门店折扣金额 
	     */
	    private Double discountMoney ;
	    /**
	     * 优惠金额 
	     */
	    private Double cutMoney ;
	    /**
	     * 积分抵扣的金额
	     */
	    private Double pointMoney;

	public String getMerchantUserName() {
		return merchantUserName;
	}

	public void setMerchantUserName(String merchantUserName) {
		this.merchantUserName = merchantUserName;
	}

	public double getSubTotalMoneyZiti() {
		return subTotalMoneyZiti;
	}

	public void setSubTotalMoneyZiti(double subTotalMoneyZiti) {
		this.subTotalMoneyZiti = subTotalMoneyZiti;
	}

	public double getSubTotalMoneyPeisong() {
		return subTotalMoneyPeisong;
	}

	public void setSubTotalMoneyPeisong(double subTotalMoneyPeisong) {
		this.subTotalMoneyPeisong = subTotalMoneyPeisong;
	}

	public double getDeliveryMoney() {
		return deliveryMoney;
	}

	public void setDeliveryMoney(double deliveryMoney) {
		this.deliveryMoney = deliveryMoney;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public double getPointDiscount() {
		return pointDiscount;
	}

	public void setPointDiscount(double pointDiscount) {
		this.pointDiscount = pointDiscount;
	}

	public double getSubTotalMoney() {
		return subTotalMoney;
	}

	public void setSubTotalMoney(double subTotalMoney) {
		this.subTotalMoney = subTotalMoney;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getOutletId() {
		return outletId;
	}

	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}

	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	public String getDeliveryCorpName() {
		return deliveryCorpName;
	}

	public void setDeliveryCorpName(String deliveryCorpName) {
		this.deliveryCorpName = deliveryCorpName;
	}

	public String getTrackingNo() {
		return trackingNo;
	}

	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}

	public long getShippingTime() {
		return shippingTime;
	}

	public void setShippingTime(long shippingTime) {
		this.shippingTime = shippingTime;
	}

	public String getRefundState() {
		return refundState;
	}

	public void setRefundState(String refundState) {
		this.refundState = refundState;
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

	public long getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(long paymentTime) {
		this.paymentTime = paymentTime;
	}

	public long getTicketTime() {
		return ticketTime;
	}

	public void setTicketTime(long ticketTime) {
		this.ticketTime = ticketTime;
	}

	public String getCloseType() {
		return closeType;
	}

	public void setCloseType(String closeType) {
		this.closeType = closeType;
	}

	public Query_Order_Detail_PC_DeliverInfo_Res getDeliverInfoVo() {
		return deliverInfoVo;
	}

	public void setDeliverInfoVo(
			Query_Order_Detail_PC_DeliverInfo_Res deliverInfoVo) {
		this.deliverInfoVo = deliverInfoVo;
	}

	public String getOrgNames() {
		return orgNames;
	}

	public void setOrgNames(String orgNames) {
		this.orgNames = orgNames;
	}

	public String getCloseReason() {
		return closeReason;
	}

	public void setCloseReason(String closeReason) {
		this.closeReason = closeReason;
	}

	public String getSettleState() {
		return settleState;
	}

	public void setSettleState(String settleState) {
		this.settleState = settleState;
	}

	public long getTakeTime() {
		return takeTime;
	}

	public void setTakeTime(long takeTime) {
		this.takeTime = takeTime;
	}

	public String getTakeCode() {
		return takeCode;
	}

	public void setTakeCode(String takeCode) {
		this.takeCode = takeCode;
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

	public long getConfirmReceiveTime() {
		return confirmReceiveTime;
	}

	public void setConfirmReceiveTime(long confirmReceiveTime) {
		this.confirmReceiveTime = confirmReceiveTime;
	}

	public long getSettleTime() {
		return settleTime;
	}

	public void setSettleTime(long settleTime) {
		this.settleTime = settleTime;
	}

	public long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getSubOrderId() {
		return subOrderId;
	}

	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}

	public int getTakeNumber() {
		return takeNumber;
	}

	public void setTakeNumber(int takeNumber) {
		this.takeNumber = takeNumber;
	}

	public String getDeliveryOption() {
		return deliveryOption;
	}

	public void setDeliveryOption(String deliveryOption) {
		this.deliveryOption = deliveryOption;
	}

	public List<Query_Order_Detail_PC_Product_Res> getProductInfos() {
		return productInfos;
	}

	public void setProductInfos(
			List<Query_Order_Detail_PC_Product_Res> productInfos) {
		this.productInfos = productInfos;
	}

	public String getIsAllRefund() {
		return isAllRefund;
	}

	public void setIsAllRefund(String isAllRefund) {
		this.isAllRefund = isAllRefund;
	}

	public int getBuyTotalPoint() {
		return buyTotalPoint;
	}

	public void setBuyTotalPoint(int buyTotalPoint) {
		this.buyTotalPoint = buyTotalPoint;
	}

	public double getTotalCash() {
		return totalCash;
	}

	public void setTotalCash(double totalCash) {
		this.totalCash = totalCash;
	}

	public double getTotalCutMoney() {
		return totalCutMoney;
	}

	public void setTotalCutMoney(double totalCutMoney) {
		this.totalCutMoney = totalCutMoney;
	}

	public Double getConsumeMoney() {
		return consumeMoney;
	}

	public void setConsumeMoney(Double consumeMoney) {
		this.consumeMoney = consumeMoney;
	}

	public Double getDiscountMoney() {
		return discountMoney;
	}

	public void setDiscountMoney(Double discountMoney) {
		this.discountMoney = discountMoney;
	}

	public Double getCutMoney() {
		return cutMoney;
	}

	public void setCutMoney(Double cutMoney) {
		this.cutMoney = cutMoney;
	}

	public Double getPointMoney() {
		return pointMoney;
	}

	public void setPointMoney(Double pointMoney) {
		this.pointMoney = pointMoney;
	}
	
	
}
