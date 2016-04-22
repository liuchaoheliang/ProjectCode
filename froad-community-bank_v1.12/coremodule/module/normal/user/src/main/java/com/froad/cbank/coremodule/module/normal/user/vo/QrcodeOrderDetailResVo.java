package com.froad.cbank.coremodule.module.normal.user.vo;

import com.froad.cbank.coremodule.framework.common.valid.Regular;

/**
 * 面对面订单详情vo
 */
/**
 * ClassName: QrcodeOrderDetailResVo
 * Function: TODO ADD FUNCTION
 * date: 2016年1月5日 上午11:42:38
 *
 * @author 刘超 liuchao@f-road.com.cn
 * @version 
 */
public class QrcodeOrderDetailResVo {
	
	/**
	   * 订单ID
	   */
	  private String orderId; 
	  /**
	   * 订单状态
	   */
	  private String orderStatus; 
	  /**
	   * 订单创建时间
	   */
	  private Long createTime; 
	  /**
	   * 商户号
	   */
	  private String merchantId; 
	  /**
	   * 商户名称
	   */
	  private String merchantName; 
	  /**
	   * 商户图片
	   */
	  private String merchantImg; 
	  /**
	   * 备注
	   */
	  private String remark;
	  /**
	   * 支付方式
	   */
	  private String paymentMethod;
	  
	  /**
	 * totalPrice:订单总金额
	 */
	private double totalPrice;
	  
	  /**
	 * outletName:门店名称
	 */
	private String outletName;
	/**
	 * 门店id
	 */
	private String outletId;
	/**
	 * 是否评论过
	 */
	private Boolean isComment;
	
	/**
	 * outletImg:门店图片
	 */
	
	private String outletImg;
	
	/**
	 * phone:手机号
	 */
	private String phone;
	
	private double fftPoints;
	
	private double bankPoints;
	
	/**
	 * discountMoney:折扣金额
	 */
	private double discountMoney;
	
	/**
	 * pointMoney:积分抵扣金额
	 */
	private double pointMoney;
	
	/**
	 * cutMoney:满减活动或者其他优惠金额
	 */
	private double cutMoney;
	
	/**
	 * consumeMoney:消费金额
	 */
	private double consumeMoney;
	
	/**
	 * realPrice:实际支付金额
	 */
	private double realPrice;
	
	public String getOutletImg() {
		return outletImg;
	}
	public void setOutletImg(String outletImg) {
		this.outletImg = outletImg;
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
	public double getDiscountMoney() {
		return discountMoney;
	}
	public void setDiscountMoney(double discountMoney) {
		this.discountMoney = discountMoney;
	}
	public double getPointMoney() {
		return pointMoney;
	}
	public void setPointMoney(double pointMoney) {
		this.pointMoney = pointMoney;
	}
	public double getCutMoney() {
		return cutMoney;
	}
	public void setCutMoney(double cutMoney) {
		this.cutMoney = cutMoney;
	}
	public double getConsumeMoney() {
		return consumeMoney;
	}
	public void setConsumeMoney(double consumeMoney) {
		this.consumeMoney = consumeMoney;
	}
	
	public Boolean getIsComment() {
		return isComment;
	}
	public void setIsComment(Boolean isComment) {
		this.isComment = isComment;
	}
	public String getOutletId() {
		return outletId;
	}
	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}
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

	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
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
	public String getMerchantImg() {
		return merchantImg;
	}
	public void setMerchantImg(String merchantImg) {
		this.merchantImg = merchantImg;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getOutletName() {
		return outletName;
	}
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public double getRealPrice() {
		return realPrice;
	}
	public void setRealPrice(double realPrice) {
		this.realPrice = realPrice;
	}
	

}
