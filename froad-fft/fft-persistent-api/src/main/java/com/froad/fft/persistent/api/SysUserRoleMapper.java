package com.froad.fft.persistent.api;

import java.util.List;

import com.froad.fft.persistent.entity.SysUserRole;

public interface SysUserRoleMapper {
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>保存SysUserRole</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月8日 下午3:52:06 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public void saveSysUserRole(SysUserRole sysUserRole);
	
	/**
	 * 根据用户ID查询
	 * @param userId
	 * @return
	 */
	public List<SysUserRole> selectSysUserRoleByUserId(Long userId);

	/**
	*<p>删除用户角色</p>
	* @author larry
	* @datetime 2014-4-2下午08:21:45
	* @return Boolean
	* @throws 
	* example<br/>
	*
	 */
	public Boolean deleteUserRole(SysUserRole sysUserRole);

}
