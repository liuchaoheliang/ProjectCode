package com.froad.fft.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.froad.fft.persistent.api.SysResourceMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.SysResource;
import com.froad.fft.service.SysResourceService;

@Service("sysResourceServiceImpl")
public class SysResourceServiceImpl implements SysResourceService {
	
	private static Logger logger = Logger.getLogger(SysResourceServiceImpl.class);

	@Resource
	private SysResourceMapper sysResourceMapper;	
	
	@Override
	public Long saveSysResource(SysResource sysResource) {
		return sysResourceMapper.saveSysResource(sysResource);
	}

	@Override
	public Boolean updateSysResourceById(SysResource sysResource) {
		if(sysResource.getId() == null){
			logger.error("更新对象缺少必要Id字段值");
			return false;
		}
		return sysResourceMapper.updateSysResourceById(sysResource);
	}

	@Override
	public SysResource findSysResourceById(Long id) {
		if(id == null){
			logger.error("查询数据id为空");
			return null;
		}
		return sysResourceMapper.selectSysResourceById(id);
	}

	@Override
	public List<SysResource> findAllSysResource() {
		return sysResourceMapper.selectAllSysResource();
	}

	@Override
	public List<SysResource> findRootSysResourceList() {
		return sysResourceMapper.selectRootSysResourceList();
	}

	@Override
	public List<SysResource> findSysResourceByClientId(Long clientId) {
		if(clientId == null){
			logger.error("查询数据clientId为空");
			return null;
		}
		return sysResourceMapper.selectSysResourceByClientId(clientId);
	}

	@Override
	public Page findSysResourceByPage(Page page) {
		
		if(page == null){
			page=new Page();
		}
		page.setResultsContent(sysResourceMapper.selectSysResourceByPage(page));
		page.setTotalCount(sysResourceMapper.selectSysResourceByPageCount(page));
		return page;
	}

}
