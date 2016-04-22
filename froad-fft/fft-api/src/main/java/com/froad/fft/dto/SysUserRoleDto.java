package com.froad.fft.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户角色
 * @author FQ
 *
 */
public class SysUserRoleDto implements Serializable{

	private Long userId;//用户ID
	private Long roleId;//角色ID
	
	private SysRoleDto sysRoleDto;
	
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
	public SysRoleDto getSysRoleDto() {
		return sysRoleDto;
	}
	public void setSysRoleDto(SysRoleDto sysRoleDto) {
		this.sysRoleDto = sysRoleDto;
	}
	
	
}
