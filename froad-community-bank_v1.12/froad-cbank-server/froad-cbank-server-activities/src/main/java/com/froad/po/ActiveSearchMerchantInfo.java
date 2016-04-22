package com.froad.po;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class ActiveSearchMerchantInfo implements Serializable {

	/**
	 * 请求id
	 */
	private String reqId; 
	/**
	 * 客户端id
	 */
	private String clientId;
	
	/**
	 * 客户标识
	 */	
	private String memberCode;
	
	/**
	 * 商户id - 列表
	 */
	private List<String> merchantIdList;
	
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
	public List<String> getMerchantIdList() {
		return merchantIdList;
	}
	public void setMerchantIdList(List<String> merchantIdList) {
		this.merchantIdList = merchantIdList;
	}
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
}
