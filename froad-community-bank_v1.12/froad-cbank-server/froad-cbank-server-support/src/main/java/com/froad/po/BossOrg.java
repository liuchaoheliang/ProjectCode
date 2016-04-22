package com.froad.po;


/**
 * CbBossOrg entity. @author MyEclipse Persistence Tools
 */
public class BossOrg implements java.io.Serializable {

	// Fields

	private Long id;
	private String parentOrgCode;
	private String orgCode;
	private String orgName;
	private Long roleId;
	private Boolean isEnable;

	// Constructors

	/** default constructor */
	public BossOrg() {
	}

	/** full constructor */
	public BossOrg(String parentOrgCode, String orgCode, String orgName, Long roleId, Boolean isEnable) {
		this.parentOrgCode = parentOrgCode;
		this.orgCode = orgCode;
		this.orgName = orgName;
		this.roleId = roleId;
		this.isEnable = isEnable;
	}

	// Property accessors
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getParentOrgCode() {
		return this.parentOrgCode;
	}

	public void setParentOrgCode(String parentOrgCode) {
		this.parentOrgCode = parentOrgCode;
	}

	public String getOrgCode() {
		return this.orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Boolean getIsEnable() {
		return this.isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

}