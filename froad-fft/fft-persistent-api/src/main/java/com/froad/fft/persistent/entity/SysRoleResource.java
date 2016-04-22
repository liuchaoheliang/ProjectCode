package com.froad.fft.persistent.entity;

import java.io.Serializable;


/**
 * 角色资源表
 * 
 * @author FQ
 * 
 */
public class SysRoleResource implements Serializable{

	private Long roleId;// 角色ID
	private Long resourceId;// 资源ID

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

}
