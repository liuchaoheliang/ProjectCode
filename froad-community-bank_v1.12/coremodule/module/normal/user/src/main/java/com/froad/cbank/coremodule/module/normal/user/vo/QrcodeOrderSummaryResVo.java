package com.froad.cbank.coremodule.module.normal.user.vo;

public class QrcodeOrderSummaryResVo {

	/**
	   * 订单ID
	   */
	  public String orderId; // required
	  /**
	   * 订单状态
	   */
	  public String orderStatus; // required
	  /**
	   * 订单实际总货币值
	   */
	  public double realPrice; // required
	  /**
	   * 商户ID
	   */
	  public String merchantId; // required
	  /**
	   * 商户名称
	   */
	  public String merchantName; // required
	  /**
	   * 创建时间
	   */
	  public long createTime; // required
	  /**
	   * 总金额
	   */
	  public double totalPrice; // required
	  /**
	   * 支付时间
	   */
	  public long paymentTime; // required
	  
	  /**
	 * outletName:门店名称
	 */
	private String outletName;
	  
	  /**
	 * outletId:门店Id
	 */
	private String outletId;
	
	/**
	 * isEnableCancel:是否可取消
	 */
	private Boolean isEnableCancel;
	
	/**
	 * isEnablePay:是否可再次支付
	 */
	private Boolean isEnablePay;
	  
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
	public double getRealPrice() {
		return realPrice;
	}
	public void setRealPrice(double realPrice) {
		this.realPrice = realPrice;
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
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public long getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(long paymentTime) {
		this.paymentTime = paymentTime;
	}
	public String getOutletName() {
		return outletName;
	}
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}
	public String getOutletId() {
		return outletId;
	}
	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}
	public Boolean getIsEnableCancel() {
		return isEnableCancel;
	}
	public void setIsEnableCancel(Boolean isEnableCancel) {
		this.isEnableCancel = isEnableCancel;
	}
	public Boolean getIsEnablePay() {
		return isEnablePay;
	}
	public void setIsEnablePay(Boolean isEnablePay) {
		this.isEnablePay = isEnablePay;
	}
	
}
