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
 * @Title: MerchantRoleMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.chonggou.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.chonggou.entity.MerchantRole;
import com.froad.db.mysql.bean.Page;

/**
 * 
 * <p>@Title: MerchantRoleMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface MerchantRoleMapper {


    /**
     * 增加 MerchantRole
     * @param merchantRole
     * @return Long    主键ID
     */
	public Long addMerchantRole(MerchantRole merchantRole);



    /**
     * 批量增加 MerchantRole
     * @param List<MerchantRole>
     * @return Boolean    是否成功
     */
	public Boolean addMerchantRoleByBatch(List<MerchantRole> merchantRoleList);



    /**
     * 删除 MerchantRole
     * @param merchantRole
     * @return Boolean    是否成功
     */
	public Boolean deleteMerchantRole(MerchantRole merchantRole);



    /**
     * 修改 MerchantRole
     * @param merchantRole
     * @return Boolean    是否成功
     */
	public Boolean updateMerchantRole(MerchantRole merchantRole);



    /**
     * 查询一个 MerchantRole
     * @param merchantRole
     * @return MerchantRole    返回结果
     */
	public MerchantRole findMerchantRoleById(Long id);



    /**
     * 查询 MerchantRole
     * @param merchantRole
     * @return List<MerchantRole>    返回结果集
     */
	public List<MerchantRole> findMerchantRole(MerchantRole merchantRole);



    /**
     * 分页查询 MerchantRole
     * @param page 
     * @param merchantRole
     * @return List<MerchantRole>    返回分页查询结果集
     */
	public List<MerchantRole> findByPage(Page page, @Param("merchantRole")MerchantRole merchantRole);



}