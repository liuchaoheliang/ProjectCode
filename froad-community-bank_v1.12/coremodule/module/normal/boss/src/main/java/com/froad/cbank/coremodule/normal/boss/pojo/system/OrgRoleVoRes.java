package com.froad.cbank.coremodule.normal.boss.pojo.system;

/**
 * 组织角色响应参数
 * @author yfy
 */
public class OrgRoleVoRes {

	/**
	 * 主键ID
	 */
	private Long id;
	/**
	 * 组织ID
	 */
	private String orgId;
	/**
	 * 角色Id
	 */
	private String roleId;
	/**
	 * 角色名称
	 */
	private String roleName;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
