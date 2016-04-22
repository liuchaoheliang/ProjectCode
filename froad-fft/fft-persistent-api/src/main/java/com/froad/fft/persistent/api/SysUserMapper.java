package com.froad.fft.persistent.api;

import java.util.List;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.SysUser;

public interface SysUserMapper {

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>保存SysUser</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月9日 下午4:17:18 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Long saveSysUser(SysUser sysUser);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键更新数据</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月9日 下午4:18:49 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Boolean updateSysUserById(SysUser sysUser);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>方法说明</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月9日 下午4:19:42 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public SysUser selectSysUserById(Long id);
	
	/**
	 * 根据用户名查找用户
	 * @param username
	 * @return
	 */
	public SysUser selectSysUserByUsername(String username);

	/**
	*<p>分页查询用户</p>
	* @author larry
	* @datetime 2014-4-1下午08:02:11
	* @return List
	* @throws 
	* example<br/>
	*
	 */
	public List selectSysUserByPage(Page page);

	/**
	*<p>分页总数</p>
	* @author larry
	* @datetime 2014-4-1下午08:02:22
	* @return Integer
	* @throws 
	* example<br/>
	*
	 */
	public Integer selectSysUserByPageCount(Page page);
}
