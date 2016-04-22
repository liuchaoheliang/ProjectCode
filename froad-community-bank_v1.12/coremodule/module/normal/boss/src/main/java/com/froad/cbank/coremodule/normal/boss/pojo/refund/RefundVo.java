package com.froad.cbank.coremodule.normal.boss.pojo.refund;

import java.util.List;

/**
 * 类描述：退款订单类
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road
 * @time:  2015-5-1下午2:36:46
 */
public class RefundVo {
	private String refundId;//退款ID
	private String orderId;//订单号
	private String requestTime;//退款申请时间
	private String refundStatus;//退款状态
	private Double refundAmount;//退款金额
	private Double refundPoints;//退还积分
	private String reason;//退款原因
	private String subOrderId;//原子订单号
	private String merchantId;//商户ID
	private String merchantName;//商户名
	private Long refundTime;//退款时间
	private String clientId;//客户端ID
	private List<RefGood> refGoodsList; 
	private List<RefRecord> refRecordList;
	
	public String getRefundId() {
		return refundId;
	}
	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}
	public String getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}
	public Double getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}
	public Double getRefundPoints() {
		return refundPoints;
	}
	public void setRefundPoints(Double refundPoints) {
		this.refundPoints = refundPoints;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getSubOrderId() {
		return subOrderId;
	}
	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
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
	public Long getRefundTime() {
		return refundTime;
	}
	public void setRefundTime(Long refundTime) {
		this.refundTime = refundTime;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public List<RefGood> getRefGoodsList() {
		return refGoodsList;
	}
	public void setRefGoodsList(List<RefGood> refGoodsList) {
		this.refGoodsList = refGoodsList;
	}
	public List<RefRecord> getRefRecordList() {
		return refRecordList;
	}
	public void setRefRecordList(List<RefRecord> refRecordList) {
		this.refRecordList = refRecordList;
	}
}
