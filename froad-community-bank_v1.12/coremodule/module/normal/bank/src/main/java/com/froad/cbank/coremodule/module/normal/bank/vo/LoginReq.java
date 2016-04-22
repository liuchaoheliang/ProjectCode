package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 登录
 * 
 * @author ylchu
 *
 */
public class LoginReq implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1492956176251832137L;

	private String userName;
	private String password;
	private String userId;
	private String oldPassword;
	private String token;
	private String orgCode;
	private Integer loginFailureCount;
	private String code;// 验证码
	private String clientId;
	private String loginChannelFlag;//银行用户登录标识
	
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getLoginFailureCount() {
		return loginFailureCount;
	}

	public void setLoginFailureCount(Integer loginFailureCount) {
		this.loginFailureCount = loginFailureCount;
	}

	public String getLoginChannelFlag() {
		return loginChannelFlag;
	}

	public void setLoginChannelFlag(String loginChannelFlag) {
		this.loginChannelFlag = loginChannelFlag;
	}
	
}
