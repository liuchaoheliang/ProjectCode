package com.froad.fft.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 角色资源表
 * 
 * @author FQ
 * 
 */
public class SysRoleResourceDto implements Serializable{

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
