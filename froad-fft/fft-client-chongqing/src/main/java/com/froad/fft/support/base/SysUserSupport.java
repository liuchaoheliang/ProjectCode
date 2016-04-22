package com.froad.fft.support.base;

import java.util.List;

import com.froad.fft.dto.SysResourceDto;
import com.froad.fft.dto.SysUserDto;

/**
 * 用户
 * @author FQ
 *
 */
public interface SysUserSupport {
	
	/**
	 * 获取当前登录的用户 
	 * @return  登录的用户 ,若不存在则返回null
	 */
	public SysUserDto getCurrentSysUser();
	
	/**
	 * 获取当前登录用户名
	 * @return 登录的用户名 ,若不存在则返回null
	 */
	public String getCurrentUsername();
	
	/**
	 * 根据ID 查找
	 * @param id
	 * @return
	 */
	public SysUserDto findSysUserById(Long id);
	
	/**
	 * 根据用户名查找
	 * @param username
	 * @return
	 */
	public SysUserDto findSysUserByUsername(String username);
	
	/**
	 * 根据ID 更新
	 * @param sysUserDto
	 * @return
	 */
	public Boolean updateSysUserById(SysUserDto sysUserDto);
	
	/**
	 * 根据用户ID 查找 到就资源
	 * @param id
	 * @return
	 */
	public List<SysResourceDto> findSysResourceListByUserId(Long id);
	
	
	
	/**
	 * 方法描述：根据传入的系统角色查询其权限下所有系统用户信息
	  * 		（即：查询权限下所有操作员的信息）
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月9日 上午10:53:31
	  */
	public List<SysUserDto> getOperatorsBySysUser(SysUserDto sysUserDto);
}
