package com.froad.fft.api.service;

import java.util.List;

import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.dto.SysResourceDto;
import com.froad.fft.dto.SysUserDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

/**
 * 用户
 * @author FQ
 *
 */
public interface SysUserExportService {
	
	/**
	 * 保存
	 * @param sysUser
	 * @return
	 */
	public Long saveSysUser(ClientAccessType clientAccessType,ClientVersion clientVersion,SysUserDto sysUserDto) throws FroadException;
	
	/**
	 * 更新
	 * @param sysUser
	 * @return
	 */
	public Boolean updateSysUserById(ClientAccessType clientAccessType,ClientVersion clientVersion,SysUserDto sysUserDto)throws FroadException;
	
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	public SysUserDto findSysUserById(ClientAccessType clientAccessType,ClientVersion clientVersion,Long id) throws FroadException;
	
	/**
	 * 根据用户名查找用户
	 * @param username
	 * @return
	 */
	public SysUserDto findSysUserByUsername(ClientAccessType clientAccessType,ClientVersion clientVersion,String username) throws FroadException;
	
	/**
	 * 根据用户ID 查找所有资源
	 * @param id
	 * @return
	 */
	public List<SysResourceDto> findSysResourceListByUserId(ClientAccessType clientAccessType,ClientVersion clientVersion,Long id) throws FroadException;

	/**
	*<p>分页查询用户</p>
	* @author larry
	* @datetime 2014-4-1下午07:50:29
	* @return PageDto<SysUserDto>
	* @throws 
	* example<br/>
	*
	 */
	public PageDto<SysUserDto> findSysUserByPage(PageDto<SysUserDto> pageDto);

}
