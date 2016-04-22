package com.froad.db.ahui.mappers;


import com.froad.fft.persistent.entity.SysUserRole;

public interface SysUserRoleMapper extends com.froad.fft.persistent.api.SysUserRoleMapper{
	
	public SysUserRole selectSysUserRoleByRoleId(Long roleId);

}

