package com.froad.cbank.coremodule.normal.boss.pojo.refund;

import com.froad.cbank.coremodule.normal.boss.pojo.Page;


/**
 * 类描述：
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015
 * @author: f-road
 * @time: 2015-5-1下午1:53:56
 */
public class RefundListReq extends Page {
	private String clientId;//客户端ID
	private String source;//查找来源
	private String memberCode;//会员编号
	private String status;//退款状态
	private String startDate;//开始时间
	private String endDate;//结束时间
	private String orderId;//订单号
	private String subOrderId;//子订单号
	private String refundId;//退款ID
	
	private String orgCodes;
	
	public String getOrgCodes() {
		return orgCodes;
	}
	public void setOrgCodes(String orgCodes) {
		this.orgCodes = orgCodes;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getSubOrderId() {
		return subOrderId;
	}
	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}
	public String getRefundId() {
		return refundId;
	}
	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}
}
