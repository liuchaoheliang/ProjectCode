package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

public class OrgUserRoleReqVo extends BaseVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 主键id
	 */
	public Long id;
	/**
	 * 客户端id
	 */
	public String clientId;
	/**
	 * 角色名称
	 */
	public String roleName;
	/**
	 * 角色id
	 */
	public Long roleId;
	/**
	 * 机构编号
	 */
	public String orgCode;
	/**
	 * 登录名
	 */
	public String username;
	/**
	 * 机构级别
	 */
	public String orgLevel;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
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

	public String getOrgLevel() {
		return orgLevel;
	}

	public void setOrgLevel(String orgLevel) {
		this.orgLevel = orgLevel;
	}

}
