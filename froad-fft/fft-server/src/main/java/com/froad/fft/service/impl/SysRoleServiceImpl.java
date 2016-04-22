package com.froad.fft.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.froad.fft.persistent.api.SysRoleMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.SysRole;
import com.froad.fft.service.SysRoleService;

@Service("sysRoleServiceImpl")
public class SysRoleServiceImpl implements SysRoleService {

	private static Logger logger = Logger.getLogger(SysRoleServiceImpl.class);
	
	@Resource
	private SysRoleMapper sysRoleMapper;
	
	@Override
	public Long saveSysRole(SysRole sysRole) {
		return sysRoleMapper.saveSysRole(sysRole);
	}

	@Override
	public Boolean updateSysRoleById(SysRole sysRole) {
		if(sysRole.getId() == null){
			logger.error("更新对象缺少必要Id字段值");
			return false;
		}
		return sysRoleMapper.updateSysRoleById(sysRole);
	}

	@Override
	public SysRole selectSysRoleById(Long id) {
		if(id == null){
			logger.error("查询数据id为空");
			return null;
		}
		return sysRoleMapper.selectSysRoleById(id);
	}

	@Override
	public Page selectSysResourceByPage(Page page) {
		if(page == null){
			page=new Page();
		}
		page.setResultsContent(sysRoleMapper.selectSysRoleByPage(page));
		page.setTotalCount(sysRoleMapper.selectSysRoleByPageCount(page));
		return page;
	}

}
