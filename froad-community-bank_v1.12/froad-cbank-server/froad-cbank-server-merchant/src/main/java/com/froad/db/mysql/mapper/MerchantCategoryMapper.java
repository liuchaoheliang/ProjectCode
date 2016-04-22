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
 * @Title: MerchantCategoryMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.MerchantCategory;

/**
 * 
 * <p>@Title: MerchantCategoryMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface MerchantCategoryMapper {


    /**
     * 增加 MerchantCategory
     * @param merchantCategory
     * @return Long    主键ID
     */
	public Long addMerchantCategory(MerchantCategory merchantCategory);



    /**
     * 批量增加 MerchantCategory
     * @param List<MerchantCategory>
     * @return Boolean    是否成功
     */
	public Boolean addMerchantCategoryByBatch(List<MerchantCategory> merchantCategoryList);



    /**
     * 删除 MerchantCategory
     * @param merchantCategory
     * @return Boolean    是否成功
     */
	public Boolean deleteMerchantCategory(MerchantCategory merchantCategory);



    /**
     * 修改 MerchantCategory
     * @param merchantCategory
     * @return Boolean    是否成功
     */
	public Boolean updateMerchantCategory(MerchantCategory merchantCategory);



    /**
     * 查询一个 MerchantCategory
     * @param merchantCategory
     * @return MerchantCategory    返回结果
     */
	public MerchantCategory findMerchantCategoryById(Long id);



    /**
     * 查询 MerchantCategory
     * @param merchantCategory
     * @return List<MerchantCategory>    返回结果集
     */
	public List<MerchantCategory> findMerchantCategory(MerchantCategory merchantCategory);

	
	   /**
     * 查询 MerchantCategory
     * @param merchantCategory
     * @return List<MerchantCategory>    返回结果集
     */
	public List<MerchantCategory> findMerchantCategoryByCategoryIdList(@Param("clientId")String clientId ,@Param("categoryIdList")List<Long> categoryIdList);


    /**
     * 分页查询 MerchantCategory
     * @param page 
     * @param merchantCategory
     * @return List<MerchantCategory>    返回分页查询结果集
     */
	public List<MerchantCategory> findByPage(Page page, @Param("merchantCategory")MerchantCategory merchantCategory);



}