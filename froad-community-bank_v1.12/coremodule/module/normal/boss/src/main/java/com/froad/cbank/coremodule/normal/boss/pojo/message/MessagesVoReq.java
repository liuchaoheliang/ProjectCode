package com.froad.cbank.coremodule.normal.boss.pojo.message;

import java.io.Serializable;

import com.froad.cbank.coremodule.normal.boss.pojo.Page;

public class MessagesVoReq extends Page implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String clientId;// 客户端id
	private String smsType;// 短信类型
	private String mobile;// 手机号码

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getSmsType() {
		return smsType;
	}

	public void setSmsType(String smsType) {
		this.smsType = smsType;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
