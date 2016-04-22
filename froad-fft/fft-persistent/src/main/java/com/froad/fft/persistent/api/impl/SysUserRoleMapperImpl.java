package com.froad.fft.persistent.api.impl;

import java.util.List;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.SysUserRoleMapper;
import com.froad.fft.persistent.entity.SysUserRole;

public class SysUserRoleMapperImpl implements SysUserRoleMapper {

	@Resource
	private SysUserRoleMapper sysUserRoleMapper;
	
	@Override
	public void saveSysUserRole(SysUserRole sysUserRole) {
		sysUserRoleMapper.saveSysUserRole(sysUserRole);
	}

	@Override
	public List<SysUserRole> selectSysUserRoleByUserId(Long userId) {
		return sysUserRoleMapper.selectSysUserRoleByUserId(userId);
	}

	@Override
	public Boolean deleteUserRole(SysUserRole sysUserRole) {
		return sysUserRoleMapper.deleteUserRole(sysUserRole);
	}

}
