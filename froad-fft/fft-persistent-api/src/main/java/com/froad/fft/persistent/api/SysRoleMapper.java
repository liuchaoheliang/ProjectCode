package com.froad.fft.persistent.api;

import java.util.List;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.SysRole;

public interface SysRoleMapper {

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>保存SysRole</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月9日 上午10:03:11 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Long saveSysRole(SysRole sysRole);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键更新数据</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月9日 上午10:03:47 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Boolean updateSysRoleById(SysRole sysRole);

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键获取对象</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月9日 上午10:37:08 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public SysRole selectSysRoleById(Long id);

	public List<SysRole> selectSysRoleByPage(Page page);

	public Integer selectSysRoleByPageCount(Page page);

}
