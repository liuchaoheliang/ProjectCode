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
 * @Title: RoleResourceLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.po.RoleResource;

/**
 * 
 * <p>@Title: RoleResourceLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface RoleResourceLogic {


    /**
     * 增加 RoleResource
     * @param roleResource
     * @return Long    主键ID
     */
	public Long addRoleResource(RoleResource roleResource);



    /**
     * 删除 roleId 的资源树
     * @param roleResource
     * @return Boolean    是否成功
     */
	public Boolean deleteRoleResource(Long roleId);



    /**
     * 修改 roleId的Resource资源，先做清除操作，再做新增操作 
     * @param roleResource
     * @return Boolean    是否成功
     */
	public Boolean updateRoleResource(Long roleId ,List<Long> resourceIds);
	
	
	  /**
     * 修改 roleId的Resource资源，先做清除操作，再做新增操作 
     * @param roleResource
     * @return Boolean    是否成功
     */
	public Boolean updateBossRoleResource(Long roleId ,String platform,List<Long> resourceIds);
	
	
	/**
	 * 导入银行端 角色资源关系TASK
	 * @param roleId
	 * @param resourceIds
	 * @return
	 */
	public Boolean updateRoleResourceByBankTask(Long roleId, List<Long> resourceIds);

    /**
     * 查询 RoleResource 根据roleId
     * @param roleResource
     * @return List<RoleResource>    结果集合 
     */
	public List<RoleResource> findRoleResource(Long roleId);



    /**
     * 分页查询 RoleResource
     * @param page
     * @param roleResource
     * @return Page<RoleResource>    结果集合 
     */
//	public Page<RoleResource> findRoleResourceByPage(Page<RoleResource> page, RoleResource roleResource);



}