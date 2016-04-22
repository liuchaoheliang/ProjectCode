package com.froad.po;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class ActiveSearchProductInfo implements Serializable {

	/**
	 * 请求id
	 */
	private String reqId;
	/**
	 * 客户端id
	 */
	private String clientId;
	
	/**
	 * 用户标识
	 */	
	private String memberCode;
	
	/**
	 * 商品id - 列表
	 */
	private List<String> productIdList;

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

	public List<String> getProductIdList() {
		return productIdList;
	}

	public void setProductIdList(List<String> productIdList) {
		this.productIdList = productIdList;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}





}
