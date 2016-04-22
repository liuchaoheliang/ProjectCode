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
 * @Title: RoleResourceMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.RoleResource;

/**
 * 
 * <p>
 * @Title: RoleResourceMapper.java
 * </p>
 * <p>
 * Description: 描述
 * </p>
 * 
 * @author f-road
 * @version 1.0
 * @created 2015年3月17日
 */
public interface RoleResourceMapper {

	/**
	 * *****************基础方法-tools自动生产 begin*******************
	 */
	int deleteByPrimaryKey(RoleResource key);

	int insert(RoleResource record);

	int insertSelective(RoleResource record);
	/**
	 * *****************基础方法-tools自动生产 end*******************
	 */

	/**
	 * 增加 RoleResource
	 * 
	 * @param roleResource
	 * @return Long 主键ID
	 */
	public Long addRoleResource(RoleResource roleResource);

	/**
	 * 批量增加 RoleResource
	 * 
	 * @param List<RoleResource>
	 * @return Boolean 是否成功
	 */
	public Boolean addRoleResourceByBatch(@Param("roleResourceList") List<RoleResource> roleResourceList);

	/**
	 * 删除 RoleResource
	 * 
	 * @param roleResource
	 * @return Boolean 是否成功
	 */
	public Boolean deleteRoleResource(RoleResource roleResource);

	/**
	 * 修改 RoleResource
	 * 
	 * @param roleResource
	 * @return Boolean 是否成功
	 */
	public Boolean updateRoleResource(RoleResource roleResource);

	/**
	 * 查询 RoleResource
	 * 
	 * @param roleResource
	 * @return List<RoleResource> 返回结果集
	 */
	public List<RoleResource> findRoleResource(RoleResource roleResource);
	
	
	public Boolean deleteRoleResourceChildren(@Param("resourceIds") List<Long> resourceIds); 

	/**
	 * 分页查询 RoleResource
	 * 
	 * @param page
	 * @param roleResource
	 * @return List<RoleResource> 返回分页查询结果集
	 */
	// public List<RoleResource> findByPage(Page page,
	// @Param("roleResource")RoleResource roleResource);

}