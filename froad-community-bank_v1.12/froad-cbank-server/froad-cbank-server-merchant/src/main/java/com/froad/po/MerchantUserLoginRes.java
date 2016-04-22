package com.froad.po;

import java.io.Serializable;



/**
 * 商户用户登录结果
 * 
 * */
public class MerchantUserLoginRes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Result result;
	
	private MerchantUser merchantUser;
	
	private int loginFailureCount;
	
	public String token;
	
	public Boolean isAdmin;

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public MerchantUser getMerchantUser() {
		return merchantUser;
	}

	public void setMerchantUser(MerchantUser merchantUser) {
		this.merchantUser = merchantUser;
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

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}


	
}
