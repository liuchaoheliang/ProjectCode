package com.froad.po;

/**
 * CbBossRole entity. @author MyEclipse Persistence Tools
 */
public class BossRole implements java.io.Serializable {

	// Fields

	private Long id;
	private String roleName;
	private Boolean isEnable;
	private String remark;

	// Constructors

	/** default constructor */
	public BossRole() {
	}

	/** minimal constructor */
	public BossRole(String roleName, Boolean isEnable) {
		this.roleName = roleName;
		this.isEnable = isEnable;
	}

	/** full constructor */
	public BossRole(String roleName, Boolean isEnable, String remark) {
		this.roleName = roleName;
		this.isEnable = isEnable;
		this.remark = remark;
	}

	// Property accessors
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Boolean getIsEnable() {
		return this.isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}