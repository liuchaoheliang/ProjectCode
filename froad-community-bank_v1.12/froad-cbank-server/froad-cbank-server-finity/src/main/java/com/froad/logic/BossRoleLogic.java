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
 * @Title: RoleLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.exceptions.FroadServerException;
import com.froad.po.Role;

/**
 * 
 * <p>@Title: RoleLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface BossRoleLogic {


    /**
     * 增加 Role
     * @param role
     * @return Long    主键ID
     */
	public Long addRole(Role role, List<Long> resourceIds)  throws FroadServerException, Exception;



    /**
     * 删除 Role
     * @param role
     * @return Boolean    是否成功
     */
	public Boolean deleteRole(Role role)  throws FroadServerException, Exception;



    /**
     * 修改 Role
     * @param role
     * @return Boolean    是否成功
     */
	public Boolean updateRole(Role role, List<Long> resourceIds)  throws FroadServerException, Exception;


	/**
     * 根据id查询 单个Role
     * @param Role
     * @return Role    结果 
     */
	public Role findRoleById(Long roleId) throws Exception;
	
	
    /**
     * 查询 Role
     * @param role
     * @return List<Role>    结果集合 
     */
	public List<Role> findRole(Role role) throws Exception;
	
	/**
	 * 根据UserId、platform获取所能查看范围的角色列表
	 * @param userId 用户Id
	 * @param platform 平台
	 * @return
	 */
	public List<Role> findRoleList(Long userId,String platform) throws Exception;

	/**
	 * 用户登录时获取的所属角色
	 * @param userId 用户Id
	 * @return List<Role>
	 */
	public List<Role> findRoleByUserIdLogin(Long userId) throws Exception;
	

    /**
     * 分页查询 Role
     * @param page
     * @param role
     * @return Page<Role>    结果集合 
     */
	public Page<Role> findRoleByPage(Page<Role> page, Role role) throws Exception;



}