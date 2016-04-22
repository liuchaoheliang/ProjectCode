package com.froad.po;

import java.io.Serializable;

public class VouchersToRedPackReq implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String clientId;

	private String vouchersId;

	private Long memberCode;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getVouchersId() {
		return vouchersId;
	}

	public void setVouchersId(String vouchersId) {
		this.vouchersId = vouchersId;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

}
