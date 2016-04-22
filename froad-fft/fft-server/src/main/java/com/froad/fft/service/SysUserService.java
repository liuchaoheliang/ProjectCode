package com.froad.fft.service;

import java.util.List;

import com.froad.fft.bean.page.PageDto;
import com.froad.fft.dto.SysUserDto;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.SysResource;
import com.froad.fft.persistent.entity.SysUser;


public interface SysUserService {

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>保存SysUser并返回数据主键（如果主键为null则插入失败）</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月9日 下午4:24:08 </p>
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
	public SysUser findSysUserById(Long id);
	
	/**
	 * 根据用户名查找用户
	 * @param username
	 * @return
	 */
	public SysUser findSysUserByUsername(String username);
	
	/**
	 * 根据用户ID 查找所有资源
	 * @param id
	 * @return
	 */
	public List<SysResource> findSysResourceListByUserId(Long id);

	public Page<SysUserDto> findSysUserByPage(Page page);
}
