package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 日志
 * @author ylchu
 *
 */
public class OperatorLogVo extends BaseVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -542288488587611674L;

	private String loginName;	//用户名
	private String orgCode;	//网点机构号
	private String orgName;	//网点机构号
	
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
}
