
	 /**
  * 文件名：SysUserRoleSupport.java
  * 版本信息：Version 1.0
  * 日期：2014年4月8日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.support.base;

import java.util.List;


import com.froad.fft.dto.SysUserRoleDto;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年4月8日 下午4:34:16 
 */
public interface SysUserRoleSupport {
	
	
	/**
	  * 方法描述：根据系统用户Id查询用户角色信息
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月8日 下午4:42:43
	  */
	public List<SysUserRoleDto> getSysUserRoleByUserId(Long sysUserId);
	
	
	/**
	  * 方法描述：根据用户Id判断是否为超级管理员
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月22日 下午6:20:53
	  */
	public boolean isAdmin(Long sysUserId);
}
