package com.froad.cbank.coremodule.normal.boss.pojo.system;

public class FinityRoleVoRes {
	private Long id;
	/**
	 * Boss超级管理员(全系统只有一个)1-代表系统管理员0-代表其他
	 */
	private Boolean isAdmin;
	/**
	 * 平台名称（boss、bank、merchant）
	 */
	private String platform;
	/**
	 * 角色名称
	 */
	private String roleName;
	/**
	 * 是否删除(0-未删除 1-删除)
	 */
	private Boolean isDelete;
	/**
	 * 添加角色的用户ID
	 */
	private String userId;
	
	/**
	 * 描述
	 */
	private String remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
