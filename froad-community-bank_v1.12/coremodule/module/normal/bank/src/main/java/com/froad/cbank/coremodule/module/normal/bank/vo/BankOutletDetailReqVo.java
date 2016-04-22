package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

public class BankOutletDetailReqVo implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 4784991486670108053L;

	private String auditNumber;// 审核流水号
	private String outletId;// 门店id
	private String clientId;// 客户端
	private String userId;// 用户id
	private String ip;// 用户ip

	public String getAuditNumber() {
		return auditNumber;
	}

	public void setAuditNumber(String auditNumber) {
		this.auditNumber = auditNumber;
	}

	public String getOutletId() {
		return outletId;
	}

	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
