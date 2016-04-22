package com.froad.fft.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.froad.fft.api.service.SysUserRoleExportService;
import com.froad.fft.api.support.BeanToDtoSupport;
import com.froad.fft.api.support.DtoToBeanSupport;
import com.froad.fft.bean.FroadException;
import com.froad.fft.dto.SysUserRoleDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.persistent.entity.SysUserRole;
import com.froad.fft.service.SysUserRoleService;

public class SysUserRoleExportServiceImpl implements SysUserRoleExportService{

	final static Logger logger = Logger.getLogger(SysUserRoleExportServiceImpl.class);
	
	@Resource(name="sysUserRoleServiceImpl")
	private SysUserRoleService sysUserRoleService;

	@Override
	public Boolean saveSysUserRole(ClientAccessType clientAccessType,
			ClientVersion clientVersion, SysUserRoleDto sysUserRole)
			throws FroadException {
		SysUserRole userRole = DtoToBeanSupport.loadBySysUserRole(sysUserRole);
		return sysUserRoleService.saveSysUserRole(userRole);
	}

	@Override
	public List<SysUserRoleDto> findSysUserRoleByUserId(
			ClientAccessType clientAccessType, ClientVersion clientVersion,
			Long userId) throws FroadException {
		List<SysUserRoleDto> userRoleDtos  = new ArrayList<SysUserRoleDto>();
		List<SysUserRole> userRoles  = sysUserRoleService.findSysUserRoleByUserId(userId);
		for (SysUserRole sysUserRole : userRoles) {
			userRoleDtos.add(BeanToDtoSupport.loadBySysUserRoleDto(sysUserRole));
		}
		return userRoleDtos;
	}

	@Override
	public Boolean deleteUserRole(SysUserRoleDto sysUserRoleDto) throws FroadException{
		SysUserRole sysUserRole = DtoToBeanSupport.loadBySysUserRole(sysUserRoleDto);
		return sysUserRoleService.deleteUserRole(sysUserRole);
	}
	
	
}
