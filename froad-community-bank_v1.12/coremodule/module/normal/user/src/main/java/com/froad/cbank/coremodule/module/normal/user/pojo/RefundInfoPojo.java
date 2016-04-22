package com.froad.cbank.coremodule.module.normal.user.pojo;

import java.util.List;

/**
 * 类描述：退款信息转换实体
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2015年4月20日 下午5:46:14
 */
public class RefundInfoPojo {

	private String refundId;
	private String orderId;
	private String requestTime;
	private String refundTime;
	private String refundStatus;
	private String refundAmount;
	private String refundPoints;
	private String reason;
	private String subOrderId;
	/**
	 * 退款选项，1-查询， 2-退款申请
	 */
	private String option;
	
	/**
	 * isVipRefund:是否为Vip资格退款商品  1-是   0- 否 
	 */
	private String isVipRefund;
	

	private List<RefundProduct> productList;

	public RefundInfoPojo() {
	}

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

	public String getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getRefundPoints() {
		return refundPoints;
	}

	public void setRefundPoints(String refundPoints) {
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

	public List<RefundProduct> getProductList() {
		return productList;
	}

	public void setProductList(List<RefundProduct> productList) {
		this.productList = productList;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(String refundTime) {
		this.refundTime = refundTime;
	}

	public String getIsVipRefund() {
		return isVipRefund;
	}

	public void setIsVipRefund(String isVipRefund) {
		this.isVipRefund = isVipRefund;
	}

}
