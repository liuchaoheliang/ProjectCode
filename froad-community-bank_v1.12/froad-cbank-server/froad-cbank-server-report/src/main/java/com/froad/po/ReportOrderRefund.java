package com.froad.po;

import java.util.Date;

public class ReportOrderRefund {
	private Long id = 0l;
	private Integer createDate = null;
	private String clientId = null;
	private String refundId = null;
	private String subOrderId = null;
	private String orderId = null;
	private String orderType = null;
	private Long memberCode = null;
	private String merchantId = null;
	private Date refundTime = null;
	private long refundAmount = 0l;
	private long refundPoint = 0l;
	private String pointRate = null;
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
	 * @return the refundId
	 */
	public String getRefundId() {
		return refundId;
	}
	/**
	 * @param refundId the refundId to set
	 */
	public void setRefundId(String refundId) {
		this.refundId = refundId;
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
	 * @return the refundTime
	 */
	public Date getRefundTime() {
		return refundTime;
	}
	/**
	 * @param refundTime the refundTime to set
	 */
	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}
	/**
	 * @return the refundAmount
	 */
	public long getRefundAmount() {
		return refundAmount;
	}
	/**
	 * @param refundAmount the refundAmount to set
	 */
	public void setRefundAmount(long refundAmount) {
		this.refundAmount = refundAmount;
	}
	/**
	 * @return the refundPoint
	 */
	public long getRefundPoint() {
		return refundPoint;
	}
	/**
	 * @param refundPoint the refundPoint to set
	 */
	public void setRefundPoint(long refundPoint) {
		this.refundPoint = refundPoint;
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
}
