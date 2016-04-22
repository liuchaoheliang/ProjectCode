package com.froad.fft.persistent.api.impl;

import java.util.List;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.SysUserMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.SysUser;


public class SysUserMapperImpl implements SysUserMapper {
	
	@Resource
	private SysUserMapper sysUserMapper;

	@Override
	public Long saveSysUser(SysUser sysUser) {
		sysUserMapper.saveSysUser(sysUser);
		return sysUser.getId();
	}

	@Override
	public Boolean updateSysUserById(SysUser sysUser) {
		return sysUserMapper.updateSysUserById(sysUser);
	}

	@Override
	public SysUser selectSysUserById(Long id) {
		return sysUserMapper.selectSysUserById(id);
	}

	@Override
	public SysUser selectSysUserByUsername(String username) {
		return sysUserMapper.selectSysUserByUsername(username);
	}

	@Override
	public List selectSysUserByPage(Page page) {
		return sysUserMapper.selectSysUserByPage(page);
	}

	@Override
	public Integer selectSysUserByPageCount(Page page) {
		return sysUserMapper.selectSysUserByPageCount(page);
	}

}
