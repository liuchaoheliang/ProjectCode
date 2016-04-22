package com.froad.fft.persistent.api;

import java.util.List;

import com.froad.fft.persistent.entity.SysRoleResource;

public interface SysRoleResourceMapper {

	/**
	 * 保存
	 * @param sysRoleResource
	 */
	public Long saveSysRoleResource(SysRoleResource sysRoleResource);
	
	/**
	 * 根据 角色ID 查询
	 * @param sysRoleId 
	 * @return
	 */
	public List<SysRoleResource> selectSysRoleResourceByRoleId(Long roleId);

	/**
	 * @author larry
	 * @param sysRoleResource
	 * <p>删除角色资源</p>
	 * @return
	 */
	public Integer deleteSysRoleResource(SysRoleResource sysRoleResource);
	
}
