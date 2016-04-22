package com.froad.thirdparty.dto.request.openapi;

import java.util.List;

import com.froad.thirdparty.dto.response.openapi.Order;

public class ResponseFroadApi {

	private String refundOrderID;

	private String resultCode;

	private String checkResultCode;

	private String checkResultContent;

	private String trackNo;

	private String remark;

	private String signType;

	private String signMsg;

	private String transferID;

	private String orderID;

	private String froadBillNo;

	private String paymentURL; // 支付宝返回的表单内容

	private String signNo;// 协议号

	private String status;// 签约状态

	private String errorMsg;// 错误描述

	private String msg;// 错误信息（发送短信接口内的）

	private String singlePenLimit;// 单笔限额

	private String dailyLimit;// 每日限额

	private String monthlyLimit;// 每月限额

	private String checkOrg;

	private String cardType;

	private String verifyResultCode;

	private String verifyResultContent;
	
	private QueryParamApiReq queryParam;

	private List<Order> orders;

	private com.froad.thirdparty.dto.response.openapi.System system;
	
	private String queryResultCode;
	private String queryResultContent;
	private String resultRemark;

	public String getRefundOrderID() {
		return refundOrderID;
	}

	public void setRefundOrderID(String refundOrderID) {
		this.refundOrderID = refundOrderID;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getTrackNo() {
		return trackNo;
	}

	public void setTrackNo(String trackNo) {
		this.trackNo = trackNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getSignMsg() {
		return signMsg;
	}

	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
	}

	public String getCheckResultCode() {
		return checkResultCode;
	}

	public void setCheckResultCode(String checkResultCode) {
		this.checkResultCode = checkResultCode;
	}

	public String getCheckResultContent() {
		return checkResultContent;
	}

	public void setCheckResultContent(String checkResultContent) {
		this.checkResultContent = checkResultContent;
	}

	public String getTransferID() {
		return transferID;
	}

	public void setTransferID(String transferID) {
		this.transferID = transferID;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getFroadBillNo() {
		return froadBillNo;
	}

	public void setFroadBillNo(String froadBillNo) {
		this.froadBillNo = froadBillNo;
	}

	public String getPaymentURL() {
		return paymentURL;
	}

	public void setPaymentURL(String paymentURL) {
		this.paymentURL = paymentURL;
	}

	public String getSignNo() {
		return signNo;
	}

	public void setSignNo(String signNo) {
		this.signNo = signNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSinglePenLimit() {
		return singlePenLimit;
	}

	public void setSinglePenLimit(String singlePenLimit) {
		this.singlePenLimit = singlePenLimit;
	}

	public String getDailyLimit() {
		return dailyLimit;
	}

	public void setDailyLimit(String dailyLimit) {
		this.dailyLimit = dailyLimit;
	}

	public String getMonthlyLimit() {
		return monthlyLimit;
	}

	public void setMonthlyLimit(String monthlyLimit) {
		this.monthlyLimit = monthlyLimit;
	}

	public String getCheckOrg() {
		return checkOrg;
	}

	public void setCheckOrg(String checkOrg) {
		this.checkOrg = checkOrg;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public QueryParamApiReq getQueryParam() {
		return queryParam;
	}

	public void setQueryParam(QueryParamApiReq queryParam) {
		this.queryParam = queryParam;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public com.froad.thirdparty.dto.response.openapi.System getSystem() {
		return system;
	}

	public void setSystem(com.froad.thirdparty.dto.response.openapi.System system) {
		this.system = system;
	}

	public String getVerifyResultCode() {
		return verifyResultCode;
	}

	public void setVerifyResultCode(String verifyResultCode) {
		this.verifyResultCode = verifyResultCode;
	}

	public String getVerifyResultContent() {
		return verifyResultContent;
	}

	public void setVerifyResultContent(String verifyResultContent) {
		this.verifyResultContent = verifyResultContent;
	}

	/**
	 * queryResultCode.
	 *
	 * @return  the queryResultCode
	 */
	public String getQueryResultCode() {
		return queryResultCode;
	}

	/**
	 * queryResultCode.
	 *
	 * @param   queryResultCode    the queryResultCode to set
	 */
	public void setQueryResultCode(String queryResultCode) {
		this.queryResultCode = queryResultCode;
	}

	/**
	 * queryResultContent.
	 *
	 * @return  the queryResultContent
	 */
	public String getQueryResultContent() {
		return queryResultContent;
	}

	/**
	 * queryResultContent.
	 *
	 * @param   queryResultContent    the queryResultContent to set
	 */
	public void setQueryResultContent(String queryResultContent) {
		this.queryResultContent = queryResultContent;
	}

	/**
	 * resultRemark.
	 *
	 * @return  the resultRemark
	 */
	public String getResultRemark() {
		return resultRemark;
	}

	/**
	 * resultRemark.
	 *
	 * @param   resultRemark    the resultRemark to set
	 */
	public void setResultRemark(String resultRemark) {
		this.resultRemark = resultRemark;
	}
	

}
