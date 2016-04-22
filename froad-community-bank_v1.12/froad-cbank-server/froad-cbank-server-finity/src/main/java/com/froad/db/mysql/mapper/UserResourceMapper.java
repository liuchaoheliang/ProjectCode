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
 * @Title: UserResourceMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.UserResource;

/**
 * 
 * <p>@Title: UserResourceMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface UserResourceMapper {


    /**
     * 增加 UserResource
     * @param userResource
     * @return Long    主键ID
     */
	public Long addUserResource(UserResource userResource);



    /**
     * 批量增加 UserResource
     * @param List<UserResource>
     * @return Boolean    是否成功
     */
	public Boolean addUserResourceByBatch(@Param("userResourceList")List<UserResource> userResourceList);



    /**
     * 删除 UserResource
     * @param userResource
     * @return Boolean    是否成功
     */
	public Boolean deleteUserResource(UserResource userResource);



    /**
     * 修改 UserResource
     * @param userResource
     * @return Boolean    是否成功
     */
	public Boolean updateUserResource(UserResource userResource);



    /**
     * 查询一个 UserResource
     * @param userResource
     * @return UserResource    返回结果
     */
	public UserResource findUserResourceByUser_id(Long user_id);



    /**
     * 查询 UserResource
     * @param userResource
     * @return List<UserResource>    返回结果集
     */
	public List<UserResource> findUserResource(UserResource userResource);



    /**
     * 分页查询 UserResource
     * @param page 
     * @param userResource
     * @return List<UserResource>    返回分页查询结果集
     */
	public List<UserResource> findByPage(Page page, @Param("userResource")UserResource userResource);



}