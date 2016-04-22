package com.froad.fft.service;

import java.util.List;

import com.froad.fft.persistent.entity.SysRoleResource;

public interface SysRoleResourceService {
	
	/**
	 * 保存
	 * @param sysRoleResource
	 * @return
	 */
	public Boolean saveSysRoleResource(SysRoleResource sysRoleResource);
	
	/**
	 * 根据角色ID查询
	 * @param roleId
	 * @return
	 */
	public List<SysRoleResource> findSysRoleResourceByRoleId(Long roleId);

	/**
	 * @author larry
	 * @param sysRoleResource
	 * <p>删除用户资源</p>
	 * @return
	 */
	public Boolean deleteSysRoleResource(SysRoleResource sysRoleResource);
}
