package com.froad.fft.api.service;

import java.util.List;

import com.froad.fft.bean.FroadException;
import com.froad.fft.dto.SysUserRoleDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

public interface SysUserRoleExportService {
	
	/**
	 * 保存
	 * @param sysUserRole
	 * @return
	 */
	public Boolean saveSysUserRole(ClientAccessType clientAccessType,ClientVersion clientVersion,SysUserRoleDto sysUserRole)throws FroadException;
	
	/**
	 * 根据用户ID查询
	 * @param userId
	 * @return
	 */
	public List<SysUserRoleDto> findSysUserRoleByUserId(ClientAccessType clientAccessType,ClientVersion clientVersion,Long userId)throws FroadException;

	/**
	*<p>删除用户角色</p>
	* @author larry
	* @datetime 2014-4-2下午08:18:43
	* @return Boolean
	* @throws 
	* example<br/>
	*
	 */
	public Boolean deleteUserRole(SysUserRoleDto sysUserRoleDto)throws FroadException;
}
