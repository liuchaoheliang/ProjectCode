package com.froad.po;

import java.io.Serializable;

import com.froad.db.mysql.bean.Page;

public class FindVouchersOfCenterReq implements Serializable {

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
    /** 1未过期2已过期3已使用 */
	private String status;
    /** 分页参数 */
    private Page page;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
    
    
}
