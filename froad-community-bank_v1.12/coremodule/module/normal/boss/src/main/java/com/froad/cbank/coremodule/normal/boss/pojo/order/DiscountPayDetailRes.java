package com.froad.cbank.coremodule.normal.boss.pojo.order;

import java.io.Serializable;

/**
 * 
 * @ClassName: DiscountPayDetailRes
 * @author chenzhangwei@f-road.com.cn
 * @createTime 2015年12月29日 下午4:29:08 
 * @desc <p>惠付订单详情返回结果集</p>
 */
public class DiscountPayDetailRes implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3808136777576049504L;
	/**
	   * 订单编号
	   */
	  private String orderId; // optional
	  /**
	   * 订单创建时间
	   */
	  private String createTime; // optional
	  /**
	   * 用户会员名
	   */
	  private String memberName; // optional
	  /**
	   * 用户真实姓名
	   */
	  private String userName; // optional
	  /**
	   * 客户端名称
	   */
	  private String clientName; // optional
	  /**
	   * 订单创建来源
	   */
	  private String createSource; // optional
	  /**
	   * 支付类型
	   */
	  private String orderType; // optional
	  /**
	   * 支付方式
	   */
	  private String paymentMethod; // optional
	  /**
	   * 订单总金额
	   */
	  private double totalPrice; // optional
	  /**
	   * 订单现金
	   */
	  private double cash; // optional
	  /**
	   * 联盟积分
	   */
	  private double fftPoints; // optional
	  /**
	   * 银行积分
	   */
	  private double bankPoints; // optional
	  /**
	   * 银行积分兑换比例
	   */
	  private String pointRate; // optional
	  /**
	   * 优惠金额
	   */
	  private double discount; // optional
	  /**
	   * 红包折扣
	   */
	  private double redPacketDiscount; // optional
	  /**
	   * 订单状态
	   */
	  private String orderStatus; // optional
	  /**
	   * 订单支付时间
	   */
	  private String paymentTime; // optional
	  /**
	   * 支付账单号
	   */
	  private String billNos; // optional
	  /**
	   * 结算所属商户全称
	   */
	  private String merchantName; // optional
	  /**
	   * 交易所属门店全称
	   */
	  private String outletName; // optional
	  /**
	   * 结算状态
	   */
	  private String settleStatus; // optional
	  
	  /**
	   * 结算账单号
	   */
	  private String settleBillNos; // optional
	  /**
	   * 结算时间
	   */
	  private String settleTime; // optional
	  
	  
	  public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getCreateSource() {
		return createSource;
	}
	public void setCreateSource(String createSource) {
		this.createSource = createSource;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public double getCash() {
		return cash;
	}
	public void setCash(double cash) {
		this.cash = cash;
	}
	public double getFftPoints() {
		return fftPoints;
	}
	public void setFftPoints(double fftPoints) {
		this.fftPoints = fftPoints;
	}
	public double getBankPoints() {
		return bankPoints;
	}
	public void setBankPoints(double bankPoints) {
		this.bankPoints = bankPoints;
	}
	public String getPointRate() {
		return pointRate;
	}
	public void setPointRate(String pointRate) {
		this.pointRate = pointRate;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public double getRedPacketDiscount() {
		return redPacketDiscount;
	}
	public void setRedPacketDiscount(double redPacketDiscount) {
		this.redPacketDiscount = redPacketDiscount;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(String paymentTime) {
		this.paymentTime = paymentTime;
	}
	public String getBillNos() {
		return billNos;
	}
	public void setBillNos(String billNos) {
		this.billNos = billNos;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getOutletName() {
		return outletName;
	}
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}
	public String getSettleStatus() {
		return settleStatus;
	}
	public void setSettleStatus(String settleStatus) {
		this.settleStatus = settleStatus;
	}
	public String getSettleBillNos() {
		return settleBillNos;
	}
	public void setSettleBillNos(String settleBillNos) {
		this.settleBillNos = settleBillNos;
	}
	public String getSettleTime() {
		return settleTime;
	}
	public void setSettleTime(String settleTime) {
		this.settleTime = settleTime;
	}
	
	  
}
