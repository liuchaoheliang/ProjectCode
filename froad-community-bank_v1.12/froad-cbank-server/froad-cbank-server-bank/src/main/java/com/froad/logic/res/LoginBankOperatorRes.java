package com.froad.logic.res;

import com.froad.po.BankOperator;
import com.froad.po.Result;

/**
 * 
 * @ClassName: LoginBankOperatorRes 
 * @Description: 银行用户登录Res
 * @author: ll
 * @date: 2015年3月28日
 */
public class LoginBankOperatorRes {
	
	private Result result;//结果集
	private int loginFailureCount;//登录失败次数
	private String token;//token值
	private BankOperator bankOperator;//银行用户对象
	

	
	public LoginBankOperatorRes(){
		
	}
	
	public int getLoginFailureCount() {
		return loginFailureCount;
	}
	public void setLoginFailureCount(int loginFailureCount) {
		this.loginFailureCount = loginFailureCount;
	}
	public BankOperator getBankOperator() {
		return bankOperator;
	}
	public void setBankOperator(BankOperator bankOperator) {
		this.bankOperator = bankOperator;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public Result getResult() {
		return result;
	}


	public void setResult(Result result) {
		this.result = result;
	}

	
}
