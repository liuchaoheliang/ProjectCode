package com.froad.cbank.coremodule.module.normal.bank.vo;

/**
 * 银行机构信息
 * 
 * @author Administrator
 *
 */
public class BankOrgRes {

	private String orgCode; // 机构号
	private String orgName; // 机构名
	private String partenOrgName;// 上级机构
	private String orgLevel;
	private Boolean orgType;// 0:部门机构;1:业务机构

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

	public String getPartenOrgName() {
		return partenOrgName;
	}

	public void setPartenOrgName(String partenOrgName) {
		this.partenOrgName = partenOrgName;
	}

	public String getOrgLevel() {
		return orgLevel;
	}

	public void setOrgLevel(String orgLevel) {
		this.orgLevel = orgLevel;
	}

	public Boolean getOrgType() {
		return orgType;
	}

	public void setOrgType(Boolean orgType) {
		this.orgType = orgType;
	}

}
