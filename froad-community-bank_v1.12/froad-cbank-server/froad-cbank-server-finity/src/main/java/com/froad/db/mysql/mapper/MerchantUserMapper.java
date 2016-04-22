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
 * @Title: MerchantUserMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.MerchantUser;

/**
 * 
 * <p>@Title: MerchantUserMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface MerchantUserMapper {


    /**
     * 增加 MerchantUser
     * @param merchantUser
     * @return Long    主键ID
     */
	public Long addMerchantUser(MerchantUser merchantUser);



    /**
     * 批量增加 MerchantUser
     * @param List<MerchantUser>
     * @return Boolean    是否成功
     */
	public Boolean addMerchantUserByBatch(List<MerchantUser> merchantUserList);



    /**
     * 删除 MerchantUser
     * @param merchantUser
     * @return Boolean    是否成功
     */
	public Boolean deleteMerchantUser(MerchantUser merchantUser);



    /**
     * 修改 MerchantUser
     * @param merchantUser
     * @return Boolean    是否成功
     */
	public Boolean updateMerchantUser(MerchantUser merchantUser);


    /**
     * 查询一个 MerchantUser
     * @param merchantUser
     * @return MerchantUser    返回结果
     */
	public MerchantUser findMerchantUserById(Long id);



    /**
     * 查询 MerchantUser
     * @param merchantUser
     * @return List<MerchantUser>    返回结果集
     */
	public List<MerchantUser> findMerchantUser(@Param("page")Page page,@Param("merchantUser")MerchantUser merchantUser);



    /**
     * 分页查询 MerchantUser
     * @param page 
     * @param merchantUser
     * @return List<MerchantUser>    返回分页查询结果集
     */
	public List<MerchantUser> findByPage(Page page, @Param("merchantUser")MerchantUser merchantUser);


	 /**
     * 查询 MerchantUser - 根据 username
     * @param username 
     * @return MerchantUser
     */
	public MerchantUser findMerchantUserByUsername(@Param("username")String username,@Param("clientId")String clientId);
	
	/**
     * 查询 MerchantUser - 根据 phone
     * @param phone 
     * @return MerchantUser
     */
	public MerchantUser findMerchantUserByPhone(String phone);

	/**
     * 查询 MerchantUser
     * @param roleId
     * @param merchantIdList
     * @return List<MerchantUser>    返回结果集
     */
	public List<MerchantUser> getMerchantUserByRoleIdAndMerchantIds(@Param("roleId") long roleId, @Param("merchantIdList") List<String> merchantIdList);
}