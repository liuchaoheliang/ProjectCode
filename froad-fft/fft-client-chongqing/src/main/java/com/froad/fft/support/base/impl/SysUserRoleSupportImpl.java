
	 /**
  * 文件名：SysUserRoleSupportImpl.java
  * 版本信息：Version 1.0
  * 日期：2014年4月8日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.support.base.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.froad.fft.api.service.SysRoleExportService;
import com.froad.fft.api.service.SysUserRoleExportService;
import com.froad.fft.common.UserRoleType;
import com.froad.fft.dto.SysRoleDto;
import com.froad.fft.dto.SysUserRoleDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.support.base.SysUserRoleSupport;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年4月8日 下午4:39:27 
 */
@Service
public class SysUserRoleSupportImpl implements SysUserRoleSupport {

	@Resource(name="sysUserRoleService")
	private SysUserRoleExportService sysUserRoleService;
	
	@Resource(name="sysRoleService")
	private SysRoleExportService sysRoleService;
	
	final ClientAccessType clientAccessType = ClientAccessType.chongqing;
	

	@Override
	public List<SysUserRoleDto> getSysUserRoleByUserId(Long sysUserId) {
		List<SysUserRoleDto> list=sysUserRoleService.findSysUserRoleByUserId(clientAccessType, ClientVersion.version_1_0, sysUserId);
		for(SysUserRoleDto temp:list){
			Long roleId=temp.getRoleId();
			SysRoleDto roleDto=sysRoleService.findSysRoleById(clientAccessType, ClientVersion.version_1_0, roleId);
			temp.setSysRoleDto(roleDto);
		}
		return list;
	}

	@Override
	public boolean isAdmin(Long sysUserId) {
		boolean flag=false;
		List<SysUserRoleDto> list=sysUserRoleService.findSysUserRoleByUserId(clientAccessType, ClientVersion.version_1_0, sysUserId);
		SysUserRoleDto sysUserRoleDto=list.get(0);
		SysRoleDto roleDto=sysRoleService.findSysRoleById(clientAccessType, ClientVersion.version_1_0, sysUserRoleDto.getRoleId());
		if(UserRoleType.ROLE_ADMINISTRATORS.equals(roleDto.getValue())){
			flag=true;
		}
		return flag;
	}

}
