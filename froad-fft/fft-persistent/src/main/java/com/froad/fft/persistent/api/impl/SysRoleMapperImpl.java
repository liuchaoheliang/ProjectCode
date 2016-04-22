package com.froad.fft.persistent.api.impl;

import java.util.List;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.SysRoleMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.SysRole;

public class SysRoleMapperImpl implements SysRoleMapper {

	@Resource
	private SysRoleMapper sysRoleMapper;
	
	@Override
	public Long saveSysRole(SysRole sysRole) {
		sysRoleMapper.saveSysRole(sysRole);
		return sysRole.getId();
	}

	@Override
	public Boolean updateSysRoleById(SysRole sysRole) {
		return sysRoleMapper.updateSysRoleById(sysRole);
	}

	@Override
	public SysRole selectSysRoleById(Long id) {
		return sysRoleMapper.selectSysRoleById(id);
	}

	@Override
	public List<SysRole> selectSysRoleByPage(Page page) {
		return sysRoleMapper.selectSysRoleByPage(page);
	}

	@Override
	public Integer selectSysRoleByPageCount(Page page) {
		return sysRoleMapper.selectSysRoleByPageCount(page);
	}

}
