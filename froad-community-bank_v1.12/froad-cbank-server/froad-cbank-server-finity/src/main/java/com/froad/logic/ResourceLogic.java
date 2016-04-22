/*   
* Copyright © 2008 F-Road All Rights Reserved.
*  
* This software is the confidential and proprietary information of   
* Founder. You shall not disclose such Confidential Information   
* and shall use it only in accordance with the terms of the agreements   
* you entered into with Founder.   
*   
*/

/**
 * 
 * @Title: ResourceLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.Resource;

/**
 * 
 * <p>@Title: ResourceLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface ResourceLogic {

	public Long insertSelective(Resource resource);
	

    /**
     * 增加 Resource
     * @param resource
     * @return Long    主键ID
     */
	public Long addResource(Resource resource);



    /**
     * 删除 Resource
     * @param resource
     * @return Boolean    是否成功
     */
	public Boolean deleteResource(Resource resource);



    /**
     * 修改 Resource
     * @param resource
     * @return Boolean    是否成功
     */
	public Boolean updateResource(Resource resource);



    /**
     * 查询 Resource
     * @param resource
     * @return List<Resource>    结果集合 
     */
	public List<Resource> findResource(Resource resource);



    /**
     * 分页查询 Resource
     * @param page
     * @param resource
     * @return Page<Resource>    结果集合 
     */
	public Page<Resource> findResourceByPage(Page<Resource> page, Resource resource);


	/**
	 * 根据角色获取资源信息
	 * @param roleId
	 * @return
	 */
	public List<Resource> findResourceByRole(Resource resource,Long roleId);

	
	/**
	 * 根据角色获取资源信息
	 * @param roleId
	 * @return
	 */
	public List<Resource> findResourceByRoles(Resource resource,List<Long> roles);
	
	/**
	 * 根据用户获取资源信息
	 * 
	 * @param userId
	 * @return
	 */
	public List<Resource> findResourceByUser(Resource resource,  Long userId,Integer userType);



}