package com.froad.fft.service;

import java.util.List;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.SysResource;

public interface SysResourceService {

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>保存SysResource并返回数据主键（如果主键为null则插入失败）</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月8日 下午5:27:38 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Long saveSysResource(SysResource sysResource);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键更新数据</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月8日 下午5:27:48 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Boolean updateSysResourceById(SysResource sysResource);
	
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键获取对象</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月8日 下午5:28:00 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public SysResource findSysResourceById(Long id);
	
	/**
	 * 查询所有
	 * @return
	 */
	public List<SysResource> findAllSysResource();
	
	/**
	 * 查询所有顶级资源集合
	 * @return
	 */
	public List<SysResource> findRootSysResourceList();
	
	/**
	 * 根据客户端ID 查找资源
	 * @param clientId
	 * @return
	 */
	public List<SysResource> findSysResourceByClientId(Long clientId);
	
	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	public Page findSysResourceByPage(Page page);
}
