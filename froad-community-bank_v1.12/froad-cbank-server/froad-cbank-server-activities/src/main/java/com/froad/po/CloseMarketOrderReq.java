package com.froad.po;

import java.io.Serializable;

public class CloseMarketOrderReq implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 请求id */
	private String reqId;
    /** 客户端id */
	private String clientId;
    /** 订单id */
	private String orderId;
    /** 用户编号 */
	private Long memberCode;
    /** 原因  0-系统关单/客户取消订单  1-客户退货关单*/
	private Integer reason;
	
	public String getReqId() {
		return reqId;
	}
	public void setReqId(String reqId) {
		this.reqId = reqId;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	public Integer getReason() {
		return reason;
	}
	public void setReason(Integer reason) {
		this.reason = reason;
	}

}
