package com.froad.po;

import java.util.Date;

public class ReportOrder {
	private Long id = 0l;
	private Integer createDate = null;
	private String clientId = null;
	private String subOrderId = null;
	private String orderId = null;
	private Long memberCode = null;
	private String memberName = null;
	private String merchantId = null;
	private String merchantName = null;
	private String orgCode = null;
	private String forgCode = null;
	private String forgName = null;
	private String sorgCode = null;
	private String sorgName = null;
	private String torgCode = null;
	private String torgName = null;
	private String lorgCode = null;
	private String lorgName = null;
	private Date orderDate = null;
	private String orderStatus = null;
	private String orderType = null;
	private String refundState = null;
	private long subOrderAmount = 0l;
	private long realPrice = 0l;
	private long totalPrice = 0l;
	private long bankPoint = 0l;
	private long fftPoint = 0l;
	private String pointRate = null;
	private String productId = null;//面对面虚拟商品ID
	private String payType = null;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the createDate
	 */
	public Integer getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Integer createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the clientId
	 */
	public String getClientId() {
		return clientId;
	}
	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	/**
	 * @return the subOrderId
	 */
	public String getSubOrderId() {
		return subOrderId;
	}
	/**
	 * @param subOrderId the subOrderId to set
	 */
	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}
	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return the memberCode
	 */
	public Long getMemberCode() {
		return memberCode;
	}
	/**
	 * @param memberCode the memberCode to set
	 */
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	/**
	 * @return the memberName
	 */
	public String getMemberName() {
		return memberName;
	}
	/**
	 * @param memberName the memberName to set
	 */
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	/**
	 * @return the merchantId
	 */
	public String getMerchantId() {
		return merchantId;
	}
	/**
	 * @param merchantId the merchantId to set
	 */
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	/**
	 * @return the merchantName
	 */
	public String getMerchantName() {
		return merchantName;
	}
	/**
	 * @param merchantName the merchantName to set
	 */
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}
	/**
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	/**
	 * @return the forgCode
	 */
	public String getForgCode() {
		return forgCode;
	}
	/**
	 * @param forgCode the forgCode to set
	 */
	public void setForgCode(String forgCode) {
		this.forgCode = forgCode;
	}
	/**
	 * @return the forgName
	 */
	public String getForgName() {
		return forgName;
	}
	/**
	 * @param forgName the forgName to set
	 */
	public void setForgName(String forgName) {
		this.forgName = forgName;
	}
	/**
	 * @return the sorgCode
	 */
	public String getSorgCode() {
		return sorgCode;
	}
	/**
	 * @param sorgCode the sorgCode to set
	 */
	public void setSorgCode(String sorgCode) {
		this.sorgCode = sorgCode;
	}
	/**
	 * @return the sorgName
	 */
	public String getSorgName() {
		return sorgName;
	}
	/**
	 * @param sorgName the sorgName to set
	 */
	public void setSorgName(String sorgName) {
		this.sorgName = sorgName;
	}
	/**
	 * @return the torgCode
	 */
	public String getTorgCode() {
		return torgCode;
	}
	/**
	 * @param torgCode the torgCode to set
	 */
	public void setTorgCode(String torgCode) {
		this.torgCode = torgCode;
	}
	/**
	 * @return the torgName
	 */
	public String getTorgName() {
		return torgName;
	}
	/**
	 * @param torgName the torgName to set
	 */
	public void setTorgName(String torgName) {
		this.torgName = torgName;
	}
	/**
	 * @return the lorgCode
	 */
	public String getLorgCode() {
		return lorgCode;
	}
	/**
	 * @param lorgCode the lorgCode to set
	 */
	public void setLorgCode(String lorgCode) {
		this.lorgCode = lorgCode;
	}
	/**
	 * @return the lorgName
	 */
	public String getLorgName() {
		return lorgName;
	}
	/**
	 * @param lorgName the lorgName to set
	 */
	public void setLorgName(String lorgName) {
		this.lorgName = lorgName;
	}
	/**
	 * @return the orderDate
	 */
	public Date getOrderDate() {
		return orderDate;
	}
	/**
	 * @param orderDate the orderDate to set
	 */
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	/**
	 * @return the orderStatus
	 */
	public String getOrderStatus() {
		return orderStatus;
	}
	/**
	 * @param orderStatus the orderStatus to set
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	/**
	 * @return the orderType
	 */
	public String getOrderType() {
		return orderType;
	}
	/**
	 * @param orderType the orderType to set
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	/**
	 * @return the refundState
	 */
	public String getRefundState() {
		return refundState;
	}
	/**
	 * @param refundState the refundState to set
	 */
	public void setRefundState(String refundState) {
		this.refundState = refundState;
	}
	/**
	 * @return the subOrderAmount
	 */
	public long getSubOrderAmount() {
		return subOrderAmount;
	}
	/**
	 * @param subOrderAmount the subOrderAmount to set
	 */
	public void setSubOrderAmount(long subOrderAmount) {
		this.subOrderAmount = subOrderAmount;
	}
	/**
	 * @return the realPrice
	 */
	public long getRealPrice() {
		return realPrice;
	}
	/**
	 * @param realPrice the realPrice to set
	 */
	public void setRealPrice(long realPrice) {
		this.realPrice = realPrice;
	}
	/**
	 * @return the totalPrice
	 */
	public long getTotalPrice() {
		return totalPrice;
	}
	/**
	 * @param totalPrice the totalPrice to set
	 */
	public void setTotalPrice(long totalPrice) {
		this.totalPrice = totalPrice;
	}
	/**
	 * @return the bankPoint
	 */
	public long getBankPoint() {
		return bankPoint;
	}
	/**
	 * @param bankPoint the bankPoint to set
	 */
	public void setBankPoint(long bankPoint) {
		this.bankPoint = bankPoint;
	}
	/**
	 * @return the fftPoint
	 */
	public long getFftPoint() {
		return fftPoint;
	}
	/**
	 * @param fftPoint the fftPoint to set
	 */
	public void setFftPoint(long fftPoint) {
		this.fftPoint = fftPoint;
	}
	/**
	 * @return the pointRate
	 */
	public String getPointRate() {
		return pointRate;
	}
	/**
	 * @param pointRate the pointRate to set
	 */
	public void setPointRate(String pointRate) {
		this.pointRate = pointRate;
	}
	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}
	/**
	 * @return the payType
	 */
	public String getPayType() {
		return payType;
	}
	/**
	 * @param payType the payType to set
	 */
	public void setPayType(String payType) {
		this.payType = payType;
	}
	
}
