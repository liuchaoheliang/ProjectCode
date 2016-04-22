package com.froad.cbank.coremodule.normal.boss.pojo.order;

/**
 * 购物订单/面对面惠付订单汇总
 * @author liaopeixin
 *	@date 2016年1月22日 上午10:01:05
 */
public class PointReportListRes {
	/**
	 * 订单编号
	 */
	private String orderId;	
	/**
	 * 会员号
	 */
	private String memberCode;	
	/**
	 * 会员名
	 */
	private String memberName;	
	/**
	 * 会员手机号
	 */
	private String mobile;	
	/**
	 * 商品名称
	 */
	private String productName	;	
	/**
	 * 商品单价
	 */
	private double productPrice;	
	/**
	 * 商品数量
	 */
	private String productQuantity;	
	/**
	 * 商品总价
	 */
	private double productTotalPrice;	
	/**
	 * 结算总价
	 */
	private double totalPrice;	
	/**
	 * 支付方式
	 */
	private String paymentMethod;	
	/**
	 * 银行积分
	 */
	private double bankPoint;		 	
	/**
	 *	银行积分比例
	 */
	private String bankPointRate;	
	/**
	 * 联盟积分
	 */
	private double froadPoint;	
	/**
	 * 现金
	 */
	private double cash;		
	/**
	 * 结算时间
	 */
	private Long settlementTime;	
	/**
	 * 所属商户
	 */
	private String merchantName;
	/**
	 * 客户端Id
	 */
	private String clientId;
	/**
	 * 客户端名称
	 */
	private String clientName;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public double getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}
	public String getProductQuantity() {
		return productQuantity;
	}
	public void setProductQuantity(String productQuantity) {
		this.productQuantity = productQuantity;
	}
	public double getProductTotalPrice() {
		return productTotalPrice;
	}
	public void setProductTotalPrice(double productTotalPrice) {
		this.productTotalPrice = productTotalPrice;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public double getBankPoint() {
		return bankPoint;
	}
	public void setBankPoint(double bankPoint) {
		this.bankPoint = bankPoint;
	}
	public String getBankPointRate() {
		return bankPointRate;
	}
	public void setBankPointRate(String bankPointRate) {
		this.bankPointRate = bankPointRate;
	}
	public double getFroadPoint() {
		return froadPoint;
	}
	public void setFroadPoint(double froadPoint) {
		this.froadPoint = froadPoint;
	}
	public double getCash() {
		return cash;
	}
	public void setCash(double cash) {
		this.cash = cash;
	}
	public Long getSettlementTime() {
		return settlementTime;
	}
	public void setSettlementTime(Long settlementTime) {
		this.settlementTime = settlementTime;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
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
	
	
}
