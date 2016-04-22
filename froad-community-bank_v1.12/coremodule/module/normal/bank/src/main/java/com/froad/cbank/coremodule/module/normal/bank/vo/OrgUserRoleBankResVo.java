package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

public class OrgUserRoleBankResVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	private Long id;
	/**
	 * 客户端id
	 */
	private String clientId;
	/**
	 * 角色名称
	 */
	private String roleName;
	/**
	 * 角色id
	 */
	private Long roleId;
	/**
	 * 机构编号
	 */
	private String orgCode;
	/**
	 * 机构名称
	 */
	private String orgName;
	/**
	 * 登录名
	 */
	private String username;
	/**
	 * 机构级别
	 */
	private String orgLevel;
	 
	private String proinceAgency; //一级机构
	private String proinceAgencyName;//一级机构名称
	private String cityAgency;  //二级机构
	private String cityAgencyName;  //二级机构名称
	private String countyAgency;//三级机构
	private String countyAgencyName;//三级机构名称
	
	private String parentOrgCode;//父级机构编号
	 
	public String getProinceAgency() {
		return proinceAgency;
	}

	public void setProinceAgency(String proinceAgency) {
		this.proinceAgency = proinceAgency;
	}

	public String getProinceAgencyName() {
		return proinceAgencyName;
	}

	public void setProinceAgencyName(String proinceAgencyName) {
		this.proinceAgencyName = proinceAgencyName;
	}

	public String getCityAgency() {
		return cityAgency;
	}

	public void setCityAgency(String cityAgency) {
		this.cityAgency = cityAgency;
	}

	public String getCityAgencyName() {
		return cityAgencyName;
	}

	public void setCityAgencyName(String cityAgencyName) {
		this.cityAgencyName = cityAgencyName;
	}

	public String getCountyAgency() {
		return countyAgency;
	}

	public void setCountyAgency(String countyAgency) {
		this.countyAgency = countyAgency;
	}

	public String getCountyAgencyName() {
		return countyAgencyName;
	}

	public void setCountyAgencyName(String countyAgencyName) {
		this.countyAgencyName = countyAgencyName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOrgLevel() {
		return orgLevel;
	}

	public void setOrgLevel(String orgLevel) {
		this.orgLevel = orgLevel;
	}

	public String getParentOrgCode() {
		return parentOrgCode;
	}

	public void setParentOrgCode(String parentOrgCode) {
		this.parentOrgCode = parentOrgCode;
	}

 
}
