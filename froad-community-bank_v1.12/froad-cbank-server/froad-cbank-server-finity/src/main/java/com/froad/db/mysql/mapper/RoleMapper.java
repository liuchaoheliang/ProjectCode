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
 * @Title: RoleMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.Role;

/**
 * 
 * <p>@Title: RoleMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface RoleMapper {


    /**
     * 增加 Role
     * @param role
     * @return Long    主键ID
     */
	public Long addRole(Role role);



    /**
     * 批量增加 Role
     * @param List<Role>
     * @return Boolean    是否成功
     */
	public Boolean addRoleByBatch(List<Role> roleList);



    /**
     * 删除 Role
     * @param role
     * @return Boolean    是否成功
     */
	public Boolean deleteRole(Role role);



    /**
     * 修改 Role
     * @param role
     * @return Boolean    是否成功
     */
	public Boolean updateRole(Role role);



    /**
     * 查询一个 Role
     * @param role
     * @return Role    返回结果
     */
	public Role findRoleById(Long id);



    /**
     * 查询 Role
     * @param role
     * @return List<Role>    返回结果集
     */
	public List<Role> findRole(Role role);



    /**
     * 分页查询 Role
     * @param page 
     * @param role
     * @return List<Role>    返回分页查询结果集
     */
	public List<Role> findByPage(Page page, @Param("role")Role role);



}