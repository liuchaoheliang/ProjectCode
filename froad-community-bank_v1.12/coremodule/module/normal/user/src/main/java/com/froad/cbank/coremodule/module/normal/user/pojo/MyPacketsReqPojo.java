package com.froad.cbank.coremodule.module.normal.user.pojo;


/**
 * 红包
 * 
 * @author artPing
 *
 */
public class MyPacketsReqPojo extends PagePojo {
	private String reqId;
	private String clientId;
	private long memberCode;
	private String status;

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

	public long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(long memberCode) {
		this.memberCode = memberCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
