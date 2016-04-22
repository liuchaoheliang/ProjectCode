package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 银行机构信息
 * 
 * @author Administrator
 *
 */
public class BankOrgVoReq extends BaseVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1234567890L;

	private String orgId; // 机构ID
	private String myOrgCode; // 当前机构号
	private String orgCode; // 机构号
	private String orgName; // 机构名
	private String orgLevel; // 机构级别
	private Boolean state; // 禁/启用状态
	private Long areaId; // 地区
	private String areaCode;
	private Boolean orgType;// 0-部门机构，1-业务机构

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getMyOrgCode() {
		return myOrgCode;
	}

	public void setMyOrgCode(String myOrgCode) {
		this.myOrgCode = myOrgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgLevel() {
		return orgLevel;
	}

	public void setOrgLevel(String orgLevel) {
		this.orgLevel = orgLevel;
	}

	public Boolean getState() {
		return state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public Boolean getOrgType() {
		return orgType;
	}

	public void setOrgType(Boolean orgType) {
		this.orgType = orgType;
	}

}
