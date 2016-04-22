package com.froad.fft.persistent.entity;

import java.io.Serializable;


/**
 * 用户角色
 * @author FQ
 *
 */
public class SysUserRole implements Serializable{

	private Long userId;//用户ID
	private Long roleId;//角色ID
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
}
