package com.froad.cbank.coremodule.normal.boss.pojo.common;

import java.io.Serializable;

/**
 * 银行机构信息
 * @author Administrator
 *
 */
public class BankOrgVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1234567890L;
	
	private String orgCode;    //机构号
	private String orgName;    //机构名
	
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
