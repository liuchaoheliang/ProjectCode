package com.froad.fft.persistent.api.impl;

import java.util.List;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.SysRoleResourceMapper;
import com.froad.fft.persistent.entity.SysRoleResource;

public class SysRoleResourceMapperImpl implements SysRoleResourceMapper {

	@Resource
	private SysRoleResourceMapper sysRoleResourceMapper;
	
	@Override
	public Long saveSysRoleResource(SysRoleResource sysRoleResource) {
		return sysRoleResourceMapper.saveSysRoleResource(sysRoleResource);
	}

	@Override
	public List<SysRoleResource> selectSysRoleResourceByRoleId(Long roleId) {
		return sysRoleResourceMapper.selectSysRoleResourceByRoleId(roleId);
	}

	@Override
	public Integer deleteSysRoleResource(SysRoleResource sysRoleResource) {
		return sysRoleResourceMapper.deleteSysRoleResource(sysRoleResource);
	}

}
