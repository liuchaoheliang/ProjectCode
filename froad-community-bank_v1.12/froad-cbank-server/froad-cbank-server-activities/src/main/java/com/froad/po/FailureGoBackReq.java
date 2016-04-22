package com.froad.po;

import java.io.Serializable;
import java.util.List;

public class FailureGoBackReq implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 请求id */
	private String reqId;
    /** 客户端id */
	private String clientId;
    /** 用户编号 */
	private Long memberCode;
	/** 订单id */
	private String orderId;
    /** 满减活动id - 列表 */
	private List<String> activeIdList;
    /** 代金券/红包id - 列表 */
	private List<String> vouchersIdList;
	
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
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	public List<String> getActiveIdList() {
		return activeIdList;
	}
	public void setActiveIdList(List<String> activeIdList) {
		this.activeIdList = activeIdList;
	}
	public List<String> getVouchersIdList() {
		return vouchersIdList;
	}
	public void setVouchersIdList(List<String> vouchersIdList) {
		this.vouchersIdList = vouchersIdList;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
}
