package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 角色Response
 * 
 * @author ylchu
 *
 */
public class RoleRes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -19230186978677955L;
	private String roleName; // 角色名
	private String remark; // 备注
	private Long roleId;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
