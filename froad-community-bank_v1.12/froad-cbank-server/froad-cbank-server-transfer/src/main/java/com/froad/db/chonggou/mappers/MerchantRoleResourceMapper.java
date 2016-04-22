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
 * @Title: MerchantRoleResourceMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.chonggou.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.chonggou.entity.MerchantRoleResource;
import com.froad.db.mysql.bean.Page;

/**
 * 
 * <p>@Title: MerchantRoleResourceMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface MerchantRoleResourceMapper {


    /**
     * 增加 MerchantRoleResource
     * @param merchantRoleResource
     * @return Long    主键ID
     */
	public Long addMerchantRoleResource(MerchantRoleResource merchantRoleResource);



    /**
     * 批量增加 MerchantRoleResource
     * @param List<MerchantRoleResource>
     * @return Boolean    是否成功
     */
	public Boolean addMerchantRoleResourceByBatch(List<MerchantRoleResource> merchantRoleResourceList);



    /**
     * 删除 MerchantRoleResource
     * @param merchantRoleResource
     * @return Boolean    是否成功
     */
	public Boolean deleteMerchantRoleResource(MerchantRoleResource merchantRoleResource);



    /**
     * 修改 MerchantRoleResource
     * @param merchantRoleResource
     * @return Boolean    是否成功
     */
	public Boolean updateMerchantRoleResource(MerchantRoleResource merchantRoleResource);



    /**
     * 查询一个 MerchantRoleResource
     * @param merchantRoleResource
     * @return MerchantRoleResource    返回结果
     */
	public MerchantRoleResource findMerchantRoleResourceByMerchant_role_id(Long merchant_role_id);



    /**
     * 查询 MerchantRoleResource
     * @param merchantRoleResource
     * @return List<MerchantRoleResource>    返回结果集
     */
	public List<MerchantRoleResource> findMerchantRoleResource(MerchantRoleResource merchantRoleResource);



    /**
     * 分页查询 MerchantRoleResource
     * @param page 
     * @param merchantRoleResource
     * @return List<MerchantRoleResource>    返回分页查询结果集
     */
	public List<MerchantRoleResource> findByPage(Page page, @Param("merchantRoleResource")MerchantRoleResource merchantRoleResource);



}