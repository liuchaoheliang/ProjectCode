package com.froad.po;

/**
 * CbBossUserOrg entity. @author MyEclipse Persistence Tools
 */
public class BossUserOrg implements java.io.Serializable {

	// Fields

	private Long id;
	private Long userId;
	private String orgCode;

	// Constructors

	/** default constructor */
	public BossUserOrg() {
	}

	/** full constructor */
	public BossUserOrg(Long userId, String orgCode) {
		this.userId = userId;
		this.orgCode = orgCode;
	}

	// Property accessors
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getOrgCode() {
		return this.orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

}