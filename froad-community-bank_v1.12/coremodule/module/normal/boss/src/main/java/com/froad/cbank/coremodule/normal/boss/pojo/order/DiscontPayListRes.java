package com.froad.cbank.coremodule.normal.boss.pojo.order;

import java.io.Serializable;

/**
 * 
 * @ClassName: DiscontPayListRes
 * @author chenzhangwei@f-road.com.cn
 * @createTime 2015年12月29日 下午1:54:00 
 * @desc <p>惠付订单列表返回Vo</p>
 */
public class DiscontPayListRes implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = -8457014599828744870L;
	
	/**
	   * 订单编号
	   */
	  private String orderId; // optional
	  /**
	   * 订单状态
	   */
	  private String orderStatus; // optional
	  /**
	   * 用户会员名
	   */
	  private String memberName; // optional
	  /**
	   * 用户手机号
	   */
	  private String phone; // optional
	  /**
	   * 支付类型
	   */
	  private String orderType; // optional
	  /**
	   * 所属商户
	   */
	  private String merchantName; // optional
	  /**
	   * 所属门店
	   */
	  private String outletName; // optional
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
	   * 订单创建时间
	   */
	  private String createTime; // optional
	  /**
	   * 订单支付时间
	   */
	  private String paymentTime; // optional
	  /**
	   * 客户端ID
	   */
	  private String clientId; // optional
	  /**
	   * 客户端名称
	   */
	  private String clientName; // optional
	  /**
	   * 支付账单号
	   */
	  private String billNos; // optional
	  /**
	   * 订单创建来源
	   */
	  private String createSource; // optional
	  
	  
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(String paymentTime) {
		this.paymentTime = paymentTime;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getBillNos() {
		return billNos;
	}
	public void setBillNos(String billNos) {
		this.billNos = billNos;
	}
	public String getCreateSource() {
		return createSource;
	}
	public void setCreateSource(String createSource) {
		this.createSource = createSource;
	}
	  
	  
	
}
