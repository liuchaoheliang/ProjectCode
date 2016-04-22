package com.froad.fft.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.froad.fft.persistent.api.SysUserRoleMapper;
import com.froad.fft.persistent.entity.SysUserRole;
import com.froad.fft.service.SysUserRoleService;

@Service("sysUserRoleServiceImpl")
public class SysUserRoleServiceImpl implements SysUserRoleService {

	private static Logger logger = Logger.getLogger(SysUserRoleServiceImpl.class);

	@Resource
	private SysUserRoleMapper sysUserRoleMapper;
	
	@Override
	public Boolean saveSysUserRole(SysUserRole sysUserRole) {
		try {
			sysUserRoleMapper.saveSysUserRole(sysUserRole);
			return true;
		} catch (Exception e) {
			logger.error("保存对象失败: " + JSONObject.toJSONString(sysUserRole),e);
			return false;
		}
	}

	@Override
	public List<SysUserRole> findSysUserRoleByUserId(Long userId) {
		if(userId == null){
			logger.error("查询数据id为空");
			return null;
		}
		return sysUserRoleMapper.selectSysUserRoleByUserId(userId);
	}

	@Override
	public Boolean deleteUserRole(SysUserRole sysUserRole) {
		if(sysUserRole == null){
			logger.error("用户角色对象为空，删除失败");
			return null;
		}
		return sysUserRoleMapper.deleteUserRole(sysUserRole);
	}

}
