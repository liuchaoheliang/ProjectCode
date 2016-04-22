package com.froad.fft.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.froad.fft.api.service.SysRoleResourceExportService;
import com.froad.fft.api.support.BeanToDtoSupport;
import com.froad.fft.api.support.DtoToBeanSupport;
import com.froad.fft.bean.FroadException;
import com.froad.fft.dto.SysRoleResourceDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.persistent.entity.SysRoleResource;
import com.froad.fft.service.SysRoleResourceService;

public class SysRoleResourceExportServiceImpl implements SysRoleResourceExportService{

	final static Logger logger = Logger.getLogger(SysRoleResourceExportServiceImpl.class);
	
	@Resource(name="sysRoleResourceServiceImpl")
	private SysRoleResourceService sysRoleResourceService;

	@Override
	public Boolean saveSysRoleResource(ClientAccessType clientAccessType,
			ClientVersion clientVersion, SysRoleResourceDto sysRoleResource)
			throws FroadException {
		SysRoleResource roleResource =DtoToBeanSupport.loadBySysRoleResource(sysRoleResource);
		return sysRoleResourceService.saveSysRoleResource(roleResource);
	}

	@Override
	public List<SysRoleResourceDto> findSysRoleResourceByRoleId(
			ClientAccessType clientAccessType, ClientVersion clientVersion,
			Long roleId) throws FroadException {
		List<SysRoleResource>  sysRoleResources =sysRoleResourceService.findSysRoleResourceByRoleId(roleId);
		List<SysRoleResourceDto> sysRoleResourceDtos = new ArrayList<SysRoleResourceDto>();
		if(sysRoleResources!=null){
			for (SysRoleResource sysRoleResource : sysRoleResources) {
				sysRoleResourceDtos.add(BeanToDtoSupport.loadBySysRoleResourceDto(sysRoleResource));
			}
		}
		return sysRoleResourceDtos;
	}

	@Override
	public Boolean deleteSysRoleResource(ClientAccessType management,
			ClientVersion version10, SysRoleResourceDto sysRoleResourceDto) {
		SysRoleResource sysRoleResource = DtoToBeanSupport.loadBySysRoleResource(sysRoleResourceDto);
		return sysRoleResourceService.deleteSysRoleResource(sysRoleResource);
	}
	
	
}
