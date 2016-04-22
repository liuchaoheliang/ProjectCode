package com.froad.po;

import java.io.Serializable;



/**
 * 商户用户登录结果
 * 
 * */
public class MerchantUserCheckRes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Result result;
	
	private MerchantUser merchantUser;
	
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

}
