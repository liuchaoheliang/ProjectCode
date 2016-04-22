package com.froad.fft.api.service;

import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.dto.SysRoleDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

/**
 * 角色
 * @author FQ
 *
 */
public interface SysRoleExportService {
	
	/**
	 * 保存
	 * @param sysRoleDto
	 * @return
	 */
	public Long saveSysRole(ClientAccessType clientAccessType,ClientVersion clientVersion,SysRoleDto sysRole)throws FroadException;
	
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	public SysRoleDto findSysRoleById(ClientAccessType clientAccessType,ClientVersion clientVersion,Long id)throws FroadException;

	/**
	 * @author larry
	 * @param management
	 * @param version10
	 * @param pageDto
	 * <p>分页查询系统角色</p>
	 * @return
	 */
	public PageDto<SysRoleDto> findSysRoleByPage(ClientAccessType management,
			ClientVersion version10, PageDto<SysRoleDto> pageDto)throws FroadException;

	/**
	 * @author larry
	 * @param management
	 * @param version10
	 * @param sysRoleDto
	 * <p>更新系统角色</p>
	 * @return
	 */
	public Boolean updateSysRole(ClientAccessType management,
			ClientVersion version10, SysRoleDto sysRoleDto)throws FroadException;
}
