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
 * @Title: ResourceMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.Resource;

/**
 * 
 * <p>
 * @Title: ResourceMapper.java
 * </p>
 * <p>
 * Description: 描述
 * </p>
 * 
 * @author f-road
 * @version 1.0
 * @created 2015年3月17日
 */
public interface BossResourceMapper {

	/**
	 * 增加 Resource
	 * 
	 * @param resource
	 * @return Long 主键ID
	 */
	public Long addResource(Resource resource);

	/**
	 * 批量增加 Resource
	 * 
	 * @param List<Resource>
	 * @return Boolean 是否成功
	 */
	public Boolean addResourceByBatch(List<Resource> resourceList);

	/**
	 * 删除 Resource
	 * 
	 * @param resource
	 * @return Boolean 是否成功
	 */
	public Boolean deleteResource(Resource resource);
	
	/**
	 * 删除子资源
	 * @param resource
	 * @return
	 */
	public Boolean deleteResourceByTreePath(Resource resource);
	
	/**
	 * 查询子资源
	 * @param resource
	 * @return
	 */
	public  List<Resource> findResourceByTreePath(@Param("resourceId") Long resourceId);

	/**
	 * 修改 Resource
	 * 
	 * @param resource
	 * @return Boolean 是否成功
	 */
	public Boolean updateResource(Resource resource);

	/**
	 * 查询一个 Resource
	 * 
	 * @param resource
	 * @return Resource 返回结果
	 */
	public Resource findResourceById(Long id);
	
	
	/**
	 * 查询多个 Resource
	 * 
	 * @param resource
	 * @return Resource 返回结果
	 * */
	public List<Resource> findResourceByIds(@Param("resourceIdList") List<Long> resourceIdList);

	/**
	 * 查询 Resource
	 * 
	 * @param resource
	 * @return List<Resource> 返回结果集
	 */
	public List<Resource> findResource(Resource resource);


	/**
	 * 根据角色获取资源信息
	 * 
	 * @param roleId
	 * @return
	 */
	public List<Resource> findResourceByRole(@Param("resource") Resource resource, @Param("roleId") Long roleId);
	
	/**
	 * 根据角色获取资源信息
	 * 
	 * @param roleId
	 * @return
	 */
	public List<Resource> findResourceByRoles(@Param("resource") Resource resource, @Param("roles") List<Long> roles);

	/**
	 * 根据id 获取下级资源
	 * @param resourceId
	 * @return
	 */
	public List<Resource> findChildrenById(Long resourceId);
	
	
	/**
	 * 判断资源Key是否存在
	 * @param resourceKey
	 * @return
	 */
	public Resource IsExistsResourceKey(String resourceKey);
	/**
	 * 根据用户获取资源信息
	 * 
	 * @param userId
	 * @return
	 */
//	public List<Resource> findResourceByUser(@Param("resource") Resource resource, @Param("userId") Long userId);


}