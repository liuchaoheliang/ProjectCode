package com.froad.fft.persistent.api;

import java.util.List;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.SysResource;

public interface SysResourceMapper {

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>保存SysResource</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月8日 下午5:24:11 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Long saveSysResource(SysResource sysResource);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键更新数据</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月8日 下午5:25:00 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Boolean updateSysResourceById(SysResource sysResource);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键查询数据</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月8日 下午5:25:33 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public SysResource selectSysResourceById(Long id);
	
	/**
	 * 查询所有资源
	 * @return
	 */
	public List<SysResource> selectAllSysResource();
	
	/**
	 * 查询所有顶级资源集合
	 * @return
	 */
	public List<SysResource> selectRootSysResourceList();
	
	/**
	 * 根据客户端ID 查找资源
	 * @param clientId
	 * @return
	 */
	public List<SysResource> selectSysResourceByClientId(Long clientId);
	
	/**
	 * 分页查找资源
	 * @param page
	 * @return
	 */
	public List<SysResource> selectSysResourceByPage(Page page);
	
	/**
	 * @author larry
	 * 分页查找资源COUNT
	 * @param page
	 * @return
	 */
	public Integer selectSysResourceByPageCount(Page page);
	
}
