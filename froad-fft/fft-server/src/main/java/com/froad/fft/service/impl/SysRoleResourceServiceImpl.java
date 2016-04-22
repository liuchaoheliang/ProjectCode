package com.froad.fft.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.froad.fft.persistent.api.SysRoleResourceMapper;
import com.froad.fft.persistent.entity.SysRoleResource;
import com.froad.fft.service.SysRoleResourceService;

@Service("sysRoleResourceServiceImpl")
public class SysRoleResourceServiceImpl implements SysRoleResourceService {

	private static Logger logger = Logger
			.getLogger(SysRoleResourceServiceImpl.class);

	@Resource
	private SysRoleResourceMapper sysRoleResourceMapper;

	@Override
	public Boolean saveSysRoleResource(SysRoleResource sysRoleResource) {
		Boolean rs= false;
		try {
			Long result= sysRoleResourceMapper.saveSysRoleResource(sysRoleResource);
			if(result!=null&&result>0){
				rs= true;
			}
		} catch (Exception e) {
			logger.error("保存对象失败: " + JSONObject.toJSONString(sysRoleResource),
					e);
			rs = false;
		}
		return rs;
	}

	@Override
	public List<SysRoleResource> findSysRoleResourceByRoleId(Long roleId) {
		if (roleId == null) {
			logger.error("查询数据id为空");
			return null;
		}
		return sysRoleResourceMapper.selectSysRoleResourceByRoleId(roleId);
	}

	@Override
	public Boolean deleteSysRoleResource(SysRoleResource sysRoleResource) {
		if (sysRoleResource == null || sysRoleResource.getRoleId() == null
				|| sysRoleResource.getRoleId() == null) {
			logger.error("资源ID或角色ID为空,无法删除");
			return false;
		}
		Integer result = sysRoleResourceMapper.deleteSysRoleResource(sysRoleResource);
		if(result!=null&&result>0){
			return true;
		}else{
			return false;
		}
	}

}
