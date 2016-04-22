package com.froad.fft.persistent.api.impl;

import java.util.List;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.SysResourceMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.SysResource;

public class SysResourceMapperImpl implements SysResourceMapper {

	@Resource
	private SysResourceMapper sysResourceMapper;
	
	@Override
	public Long saveSysResource(SysResource sysResource) {
		sysResourceMapper.saveSysResource(sysResource);
		return sysResource.getId();
	}

	@Override
	public Boolean updateSysResourceById(SysResource sysResource) {
		return sysResourceMapper.updateSysResourceById(sysResource);
	}

	@Override
	public SysResource selectSysResourceById(Long id) {
		return sysResourceMapper.selectSysResourceById(id);
	}

	@Override
	public List<SysResource> selectAllSysResource() {
		return sysResourceMapper.selectAllSysResource();
	}

	@Override
	public List<SysResource> selectRootSysResourceList() {
		return sysResourceMapper.selectRootSysResourceList();
	}

	@Override
	public List<SysResource> selectSysResourceByClientId(Long clientId) {
		return sysResourceMapper.selectSysResourceByClientId(clientId);
	}

	@Override
	public List<SysResource> selectSysResourceByPage(Page page) {
		return sysResourceMapper.selectSysResourceByPage(page);
	}

	@Override
	public Integer selectSysResourceByPageCount(Page page) {
		return sysResourceMapper.selectSysResourceByPageCount(page);
	}

}
