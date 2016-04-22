package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 银行机构信息
 * @author Administrator
 *
 */
public class BankOrgVoRes implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1234567890L;
	
	private Long orgId;        //机构ID
	private String orgCode;    //机构号
	private String orgName;    //机构名
	private String partenOrgCode; //上级机构
	private Boolean state;       //机构状态
	private String orgLevel;    //机构级别
	 /**
	   * 机构类型 0-部门机构，1-业务机构
	   */
	private Boolean orgType;
	
	
	
	public Boolean getOrgType() {
		return orgType;
	}
	public void setOrgType(Boolean orgType) {
		this.orgType = orgType;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
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
	public String getPartenOrgCode() {
		return partenOrgCode;
	}
	public void setPartenOrgCode(String partenOrgCode) {
		this.partenOrgCode = partenOrgCode;
	}
	public Boolean getState() {
		return state;
	}
	public void setState(Boolean state) {
		this.state = state;
	}
	public String getOrgLevel() {
		return orgLevel;
	}
	public void setOrgLevel(String orgLevel) {
		this.orgLevel = orgLevel;
	}
	
}
