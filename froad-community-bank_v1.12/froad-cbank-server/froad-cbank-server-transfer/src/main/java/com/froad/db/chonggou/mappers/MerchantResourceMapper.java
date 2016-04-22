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
 * @Title: MerchantResourceMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.chonggou.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.chonggou.entity.MerchantResource;
import com.froad.db.mysql.bean.Page;

/**
 * 
 * <p>@Title: MerchantResourceMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface MerchantResourceMapper {


    /**
     * 增加 MerchantResource
     * @param merchantResource
     * @return Long    主键ID
     */
	public Long addMerchantResource(MerchantResource merchantResource);



    /**
     * 批量增加 MerchantResource
     * @param List<MerchantResource>
     * @return Boolean    是否成功
     */
	public Boolean addMerchantResourceByBatch(List<MerchantResource> merchantResourceList);



    /**
     * 删除 MerchantResource
     * @param merchantResource
     * @return Boolean    是否成功
     */
	public Boolean deleteMerchantResource(MerchantResource merchantResource);



    /**
     * 修改 MerchantResource
     * @param merchantResource
     * @return Boolean    是否成功
     */
	public Boolean updateMerchantResource(MerchantResource merchantResource);



    /**
     * 查询一个 MerchantResource
     * @param merchantResource
     * @return MerchantResource    返回结果
     */
	public MerchantResource findMerchantResourceById(Long id);



    /**
     * 查询 MerchantResource
     * @param merchantResource
     * @return List<MerchantResource>    返回结果集
     */
	public List<MerchantResource> findMerchantResource(MerchantResource merchantResource);



    /**
     * 分页查询 MerchantResource
     * @param page 
     * @param merchantResource
     * @return List<MerchantResource>    返回分页查询结果集
     */
	public List<MerchantResource> findByPage(Page page, @Param("merchantResource")MerchantResource merchantResource);



}