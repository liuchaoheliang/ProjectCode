package com.froad.db.ahui.mappers;

import org.apache.ibatis.annotations.Param;

import com.froad.fft.persistent.entity.SysUser;

public interface SysUserMapper extends com.froad.fft.persistent.api.SysUserMapper{
	
	public SysUser selectSysUserById(@Param("id")Long id);
	
	public SysUser selectSysUserByName(@Param("username")String name);
	
	
}

