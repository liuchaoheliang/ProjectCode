package com.froad.fft.service;

import java.util.List;

import com.froad.fft.persistent.entity.SysUserRole;

public interface SysUserRoleService {

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>保存SysUserRole并返回结果</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月8日 下午3:53:41 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Boolean saveSysUserRole(SysUserRole sysUserRole);
	
	/**
	 * 根据用户ID查询
	 * @param userId
	 * @return
	 */
	public List<SysUserRole> findSysUserRoleByUserId(Long userId);

	/**
	*<p>删除用户角色</p>
	* @author larry
	* @datetime 2014-4-2下午08:20:19
	* @return Boolean
	* @throws 
	* example<br/>
	*
	 */
	public Boolean deleteUserRole(SysUserRole sysUserRole);
}
