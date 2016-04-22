package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class OrderVoOfOptimize implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 952541352994383561L;

	private String orderId; // 订单编号
	private String subOrderId; // 订单编号
	private long createTime; // 创建时间
	private long paymentTime; // 支付时间
	private long shippingDate; // 发货时间
	private long signforTime; // 收货时间
	private long takeTime; // 提货时间
	// 订单状态(1. 订单创建，2.订单关闭，3.订单系统关闭，4.订单支付中，5. 订单支付失败，6.订单支付完成)
	private String orderStatus;
	private String closeReason; // 关闭原因
	private long refundApplyTime; // 退货申请时间
	private long refundFinishTime; // 退货完成时间
	private String refundStatus;// 退款状态
	private double sumMoney; // 总金额
	private double realityMoney; // 实付款
	private double pointDiscount;// 积分抵扣
	private double carriage; // 运费
	private String settlementStatus; // 结算状态
	private String taker; // 消费人
	private String phone; // 手机号
	private String address; // 地址
	private long ticketTime; // 验码时间
	private String takeAddress;// 提货地址
	private String takeCode;// 提货码
	private String orgNames; // 所属机构
	private String deliveryOption;// 配送方式（0.未发货，1.已发货，2.已收货，3.未提货，4.已提货,5.出货中）；
	private String businessType;// 交易类型
	private String merchantName;// 所属商户
	private String merchantUserName;// 操作员
	private long takePoint;// 消费百分比
	private int takeTotalNumber;// 提货总数量
	private int buyTotalPoint;// 购买总数量
	private String isAllRefund;// 是否全单退款
	private double totalCutMoney;// 子订单总优惠金额
	private double totalCash;// 子订单总实付金额
	private List<BankOrderProductVo> productList;// 商品集合
	private String memberName;//用户名
	private String contactPhone; // 联系人手机号
	private String deliveryState;//子订单配送状态
	private List<Map<String,String>> msgList;
   
	/**
	 * 物流创建时间
	 */
	private long shippingCreateTime; // optional
	/**
	 * 更新时间
	 */
	private long updateTime; // optional

	/**
	 * 物流公司编码
	 */
	private String shippingCorpCode; // optional
	/**
	 * 物流公司
	 */
	private String shippingCorp; // optional
	/**
	 * 物流单号
	 */
	private String shippingId; // optional
	/**
	 * 运单状态
	 */
	private String status; // optional
	/**
	 * 消息
	 */
	private String message; // optional
	/**
	 * 运输时间
	 */
	private long shippingTime; // optional
	/**
	 * 确认收货时间
	 */
	private long arriveTime; // optional
	/**
	 * 确认收货状态 0-未收货 1-人工确认 2-系统确认
	 */
	private int shippingState; // optional
	private String trackingNo;//物流单号

	public String getTrackingNo() {
		return trackingNo;
	}

	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
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

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(long paymentTime) {
		this.paymentTime = paymentTime;
	}

	public long getShippingDate() {
		return shippingDate;
	}

	public void setShippingDate(long shippingDate) {
		this.shippingDate = shippingDate;
	}

	public long getSignforTime() {
		return signforTime;
	}

	public void setSignforTime(long signforTime) {
		this.signforTime = signforTime;
	}

	public long getTakeTime() {
		return takeTime;
	}

	public void setTakeTime(long takeTime) {
		this.takeTime = takeTime;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getCloseReason() {
		return closeReason;
	}

	public void setCloseReason(String closeReason) {
		this.closeReason = closeReason;
	}

	public long getRefundApplyTime() {
		return refundApplyTime;
	}

	public void setRefundApplyTime(long refundApplyTime) {
		this.refundApplyTime = refundApplyTime;
	}

	public long getRefundFinishTime() {
		return refundFinishTime;
	}

	public void setRefundFinishTime(long refundFinishTime) {
		this.refundFinishTime = refundFinishTime;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	public double getSumMoney() {
		return sumMoney;
	}

	public void setSumMoney(double sumMoney) {
		this.sumMoney = sumMoney;
	}

	public double getRealityMoney() {
		return realityMoney;
	}

	public void setRealityMoney(double realityMoney) {
		this.realityMoney = realityMoney;
	}

	public double getPointDiscount() {
		return pointDiscount;
	}

	public void setPointDiscount(double pointDiscount) {
		this.pointDiscount = pointDiscount;
	}

	public double getCarriage() {
		return carriage;
	}

	public void setCarriage(double carriage) {
		this.carriage = carriage;
	}

	public String getSettlementStatus() {
		return settlementStatus;
	}

	public void setSettlementStatus(String settlementStatus) {
		this.settlementStatus = settlementStatus;
	}

	public String getTaker() {
		return taker;
	}

	public void setTaker(String taker) {
		this.taker = taker;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getTicketTime() {
		return ticketTime;
	}

	public void setTicketTime(long ticketTime) {
		this.ticketTime = ticketTime;
	}

	public String getTakeAddress() {
		return takeAddress;
	}

	public void setTakeAddress(String takeAddress) {
		this.takeAddress = takeAddress;
	}

	public String getTakeCode() {
		return takeCode;
	}

	public void setTakeCode(String takeCode) {
		this.takeCode = takeCode;
	}

	public String getOrgNames() {
		return orgNames;
	}

	public void setOrgNames(String orgNames) {
		this.orgNames = orgNames;
	}

	public String getDeliveryOption() {
		return deliveryOption;
	}

	public void setDeliveryOption(String deliveryOption) {
		this.deliveryOption = deliveryOption;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantUserName() {
		return merchantUserName;
	}

	public void setMerchantUserName(String merchantUserName) {
		this.merchantUserName = merchantUserName;
	}

	public long getTakePoint() {
		return takePoint;
	}

	public void setTakePoint(long takePoint) {
		this.takePoint = takePoint;
	}

	public int getTakeTotalNumber() {
		return takeTotalNumber;
	}

	public void setTakeTotalNumber(int takeTotalNumber) {
		this.takeTotalNumber = takeTotalNumber;
	}

	public int getBuyTotalPoint() {
		return buyTotalPoint;
	}

	public void setBuyTotalPoint(int buyTotalPoint) {
		this.buyTotalPoint = buyTotalPoint;
	}

	public String getIsAllRefund() {
		return isAllRefund;
	}

	public void setIsAllRefund(String isAllRefund) {
		this.isAllRefund = isAllRefund;
	}

	public double getTotalCutMoney() {
		return totalCutMoney;
	}

	public void setTotalCutMoney(double totalCutMoney) {
		this.totalCutMoney = totalCutMoney;
	}

	public double getTotalCash() {
		return totalCash;
	}

	public void setTotalCash(double totalCash) {
		this.totalCash = totalCash;
	}

	public List<BankOrderProductVo> getProductList() {
		return productList;
	}

	public void setProductList(List<BankOrderProductVo> productList) {
		this.productList = productList;
	}

	public long getShippingCreateTime() {
		return shippingCreateTime;
	}

	public void setShippingCreateTime(long shippingCreateTime) {
		this.shippingCreateTime = shippingCreateTime;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public String getShippingCorpCode() {
		return shippingCorpCode;
	}

	public void setShippingCorpCode(String shippingCorpCode) {
		this.shippingCorpCode = shippingCorpCode;
	}

	public String getShippingCorp() {
		return shippingCorp;
	}

	public void setShippingCorp(String shippingCorp) {
		this.shippingCorp = shippingCorp;
	}

	public String getShippingId() {
		return shippingId;
	}

	public void setShippingId(String shippingId) {
		this.shippingId = shippingId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getShippingTime() {
		return shippingTime;
	}

	public void setShippingTime(long shippingTime) {
		this.shippingTime = shippingTime;
	}

	public long getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(long arriveTime) {
		this.arriveTime = arriveTime;
	}

	public int getShippingState() {
		return shippingState;
	}

	public void setShippingState(int shippingState) {
		this.shippingState = shippingState;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getDeliveryState() {
		return deliveryState;
	}

	public void setDeliveryState(String deliveryState) {
		this.deliveryState = deliveryState;
	}

	public List<Map<String, String>> getMsgList() {
		return msgList;
	}

	public void setMsgList(List<Map<String, String>> msgList) {
		this.msgList = msgList;
	}

	
}
