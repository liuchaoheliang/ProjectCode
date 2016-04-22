package com.froad.cbank.coremodule.module.normal.user.pojo;

public class UserLoginPojo {

	private String loginId;
	
	private String loginPwd;
	
	private String mobile;
	
	private String createChannel;
	
	private String registerIP;
	
	private String bankOrgNo;
	
	private String authToken; 
	
	
	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCreateChannel() {
		return createChannel;
	}

	public void setCreateChannel(String createChannel) {
		this.createChannel = createChannel;
	}

	public String getRegisterIP() {
		return registerIP;
	}

	public void setRegisterIP(String registerIP) {
		this.registerIP = registerIP;
	}

	public String getBankOrgNo() {
		return bankOrgNo;
	}

	public void setBankOrgNo(String bankOrgNo) {
		this.bankOrgNo = bankOrgNo;
	}
	
}
