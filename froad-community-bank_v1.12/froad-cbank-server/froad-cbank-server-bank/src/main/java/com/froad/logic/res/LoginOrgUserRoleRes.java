package com.froad.logic.res;

import com.froad.po.OrgUserRole;
import com.froad.po.Result;

/**
 * 
 * @ClassName: LoginOrgUserRoleRes 
 * @Description: 银行联合登录Res
 * @author: ll
 * @date: 2015年3月28日
 */
public class LoginOrgUserRoleRes {
	
	private Result result;//结果集
	private int loginFailureCount;//登录失败次数
	private String token;//token值
	private OrgUserRole orgUserRole;//银行联合登录帐号对象
	
	
	
	public String getToken() {
		return token;
	}



	public void setToken(String token) {
		this.token = token;
	}



	public OrgUserRole getOrgUserRole() {
		return orgUserRole;
	}



	public void setOrgUserRole(OrgUserRole orgUserRole) {
		this.orgUserRole = orgUserRole;
	}

	
	
	

	public Result getResult() {
		return result;
	}



	public void setResult(Result result) {
		this.result = result;
	}



	public int getLoginFailureCount() {
		return loginFailureCount;
	}



	public void setLoginFailureCount(int loginFailureCount) {
		this.loginFailureCount = loginFailureCount;
	}



	public LoginOrgUserRoleRes(){}
	
}
