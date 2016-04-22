package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 商户用户
 * 
 * @author ylchu
 *
 */
public class MerchantManageUserVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5368297780066735644L;

	private String username; // 登录名
	private String phone; // 登录人手机
	private String merchantId; // 商户用户ID
	private String merchantUserId;
	private String userPassword; // 商户用户密码
	private String clientId;
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantUserId() {
		return merchantUserId;
	}

	public void setMerchantUserId(String merchantUserId) {
		this.merchantUserId = merchantUserId;
	}

}
