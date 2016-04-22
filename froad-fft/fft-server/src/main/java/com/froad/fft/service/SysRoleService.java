package com.froad.fft.service;

import com.froad.fft.bean.page.PageDto;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.SysRole;

public interface SysRoleService {

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>保存SysRole并返回数据主键（如果主键为null则插入失败）</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月9日 上午10:43:28 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Long saveSysRole(SysRole sysRole);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键更新数据</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月9日 上午10:43:42 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Boolean updateSysRoleById(SysRole sysRole);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键获取对象</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月9日 上午10:43:59 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public SysRole selectSysRoleById(Long id);

	/**
	 * @author larry
	 * @param page
	 *<p> 分页查找系统角色</p>
	 * @return
	 */
	public Page selectSysResourceByPage(Page page);

}
