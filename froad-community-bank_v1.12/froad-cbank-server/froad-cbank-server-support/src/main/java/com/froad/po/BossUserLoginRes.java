package com.froad.po;

import java.io.Serializable;

public class BossUserLoginRes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Result result;
	
	private BossUser bossUser;
	
	private int loginFailureCount;
	 
	private String token;

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public BossUser getBossUser() {
		return bossUser;
	}

	public void setBossUser(BossUser bossUser) {
		this.bossUser = bossUser;
	}

	public int getLoginFailureCount() {
		return loginFailureCount;
	}

	public void setLoginFailureCount(int loginFailureCount) {
		this.loginFailureCount = loginFailureCount;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
