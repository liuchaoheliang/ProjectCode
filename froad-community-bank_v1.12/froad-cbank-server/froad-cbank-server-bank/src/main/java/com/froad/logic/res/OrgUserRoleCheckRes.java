package com.froad.logic.res;

import com.froad.po.OrgUserRole;
import com.froad.po.Result;

/**
 * 
 * @ClassName: BankOperatorCheckRes 
 * @Description: 银行用户token检验Res
 * @author: ll
 * @date: 2015年5月14日
 */
public class OrgUserRoleCheckRes {
	private Result result;//结果集
	private OrgUserRole orgUserRole;//银行联合登录帐号对象
	
	
	public OrgUserRoleCheckRes() {
		
	}
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	public OrgUserRole getOrgUserRole() {
		return orgUserRole;
	}
	public void setOrgUserRole(OrgUserRole orgUserRole) {
		this.orgUserRole = orgUserRole;
	}
	
	
	
	
	
}
