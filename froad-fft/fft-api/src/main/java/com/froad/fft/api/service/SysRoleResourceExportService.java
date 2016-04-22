package com.froad.fft.api.service;

import java.util.List;

import com.froad.fft.bean.FroadException;
import com.froad.fft.dto.SysRoleResourceDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

public interface SysRoleResourceExportService {
	
	/**
	 * 保存
	 * @param sysRoleResource
	 * @return
	 */
	public Boolean saveSysRoleResource(ClientAccessType clientAccessType,ClientVersion clientVersion,SysRoleResourceDto sysRoleResource) throws FroadException;
	
	/**
	 * 根据角色ID查询
	 * @param roleId
	 * @return
	 */
	public List<SysRoleResourceDto> findSysRoleResourceByRoleId(ClientAccessType clientAccessType,ClientVersion clientVersion,Long roleId) throws FroadException;

	/**
	 * @author larry
	 * @param management
	 * @param version10
	 * @param sysRoleResourceDto
	 * <p>删除角色资源</p>
	 * @return
	 */
	public Boolean deleteSysRoleResource(ClientAccessType management,
			ClientVersion version10, SysRoleResourceDto sysRoleResourceDto)throws FroadException;
}
