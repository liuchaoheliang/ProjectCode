package com.froad.thirdparty.dto.request.openapi;

import java.util.List;

import com.froad.thirdparty.dto.response.openapi.Order;

/**
 * *******************************************************
 *<p> 工程: fft-server </p>
 *<p> 类名: OpenApiRes.java </p>
 *<p> 描述: *-- <b>OpenApi响应结果</b> --* </p>
 *<p> 作者: 赵肖瑶 </p>
 *<p> 时间: 2014年1月15日 下午2:58:34 </p>
 ********************************************************
 */
public class OpenApiRes {

	private String resultCode;//结果
	private String resultDesc;//结果描述
	
	private String checkResultCode;//校验结果码   00: 存在/成功|01: 不存在/失败
	private String checkResultContent;//校验结果信息
	private String paymentURL; //支付宝返回的表单内容
	private String froadBillNo;
	private String signNo;//快捷签约协议号
	
	private String singlePenLimit;//单笔限额
	private String dailyLimit;//每日限额
	private String monthlyLimit;//每月限额
	private String xml;//报文
	
	private String verifyResultCode;

	private String verifyResultContent;
	
	private List<Order> orders;
	
	private String queryResultCode;
	private String queryResultContent;
	
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultDesc() {
		return resultDesc;
	}
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
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
	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}
	public List<Order> getOrders() {
		return orders;
	}
	public void setOrders(List<Order> orders) {
		this.orders = orders;
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
	
	
}
