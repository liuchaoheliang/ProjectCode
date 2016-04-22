package com.froad.fft.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.froad.fft.dto.SysUserDto;
import com.froad.fft.persistent.api.SysResourceMapper;
import com.froad.fft.persistent.api.SysRoleResourceMapper;
import com.froad.fft.persistent.api.SysUserMapper;
import com.froad.fft.persistent.api.SysUserRoleMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.SysResource;
import com.froad.fft.persistent.entity.SysRoleResource;
import com.froad.fft.persistent.entity.SysUser;
import com.froad.fft.persistent.entity.SysUserRole;
import com.froad.fft.service.SysUserService;

@Service("sysUserServiceImpl")
public class SysUserServiceImpl implements SysUserService {

	private static Logger logger = Logger.getLogger(SysUserServiceImpl.class);
	
	@Resource
	private SysUserMapper sysUserMapper;
	
	@Resource
	private SysUserRoleMapper sysUserRoleMapper;
	
	@Resource
	private SysRoleResourceMapper sysRoleResourceMapper;
	
	@Resource
	private SysResourceMapper sysResourceMapper;
	
	
	@Override
	public Long saveSysUser(SysUser sysUser) {
		return sysUserMapper.saveSysUser(sysUser);
	}

	@Override
	public Boolean updateSysUserById(SysUser sysUser) {
		if(sysUser.getId() == null){
			logger.error("更新对象缺少必要Id字段值");
			return false;
		}
		return sysUserMapper.updateSysUserById(sysUser);
	}

	@Override
	public SysUser findSysUserById(Long id) {
		if(id == null){
			logger.error("查询数据id为空");
			return null;
		}
		return sysUserMapper.selectSysUserById(id);
	}

	@Override
	public SysUser findSysUserByUsername(String username) {
		
		if(username == null){
			logger.error("查询数据username为空");
			return null;
		}
		return sysUserMapper.selectSysUserByUsername(username);
	}

	@Override
	public List<SysResource> findSysResourceListByUserId(Long id) {
		
		if(id == null){
			logger.error("查询数据id为空");
			return null;
		}
		
		SysUser sysUser=sysUserMapper.selectSysUserById(id);
		List<SysUserRole> sysUserRoleList=sysUserRoleMapper.selectSysUserRoleByUserId(id);
				
		List<SysResource> sysResourceList=new ArrayList<SysResource>();
		//用户角色集合
		if(sysUserRoleList !=null && sysUserRoleList.size() > 0){
			for(SysUserRole sysUserRole:sysUserRoleList){
				List<SysRoleResource> sysRoleResourceList=sysRoleResourceMapper.selectSysRoleResourceByRoleId(sysUserRole.getRoleId());
				//角色资源集合
				for(SysRoleResource sysRoleResource:sysRoleResourceList){
					SysResource sysResource=sysResourceMapper.selectSysResourceById(sysRoleResource.getResourceId());
					//用户对应启用的资源 
					if(sysUser.getClientId().longValue()==sysResource.getClientId().longValue()&& sysResource.getIsEnabled()){
						sysResourceList.add(sysResource);
					}
				}
			}
		}
		return sysResourceList;
	}

	@Override
	public Page<SysUserDto> findSysUserByPage(Page page) {
		if(page == null){
			page=new Page();
		}
		page.setResultsContent(sysUserMapper.selectSysUserByPage(page));
		page.setTotalCount(sysUserMapper.selectSysUserByPageCount(page));
		return page;
	}

}
