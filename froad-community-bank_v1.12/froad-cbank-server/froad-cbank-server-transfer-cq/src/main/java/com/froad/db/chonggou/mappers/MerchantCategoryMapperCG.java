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
package com.froad.db.chonggou.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.chonggou.entity.MerchantCategoryCG;

/**
 * 
 * <p>@Title: MerchantCategoryMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface MerchantCategoryMapperCG {


    /**
     * 增加 MerchantCategory
     * @param merchantCategory
     * @return Long    主键ID
     */
	public Long addMerchantCategory(MerchantCategoryCG merchantCategoryCG);



    /**
     * 批量增加 MerchantCategory
     * @param List<MerchantCategory>
     * @return Boolean    是否成功
     */
	public Boolean addMerchantCategoryByBatch(List<MerchantCategoryCG> merchantCategoryCGList);
	
	
	/**
     * 修改 MerchantCategory
     * @param merchantCategory
     * @return Boolean    是否成功
     */
	public Boolean updateMerchantCategory(MerchantCategoryCG merchantCategoryCG);
	
	
    /**
     * 查询一个 MerchantCategory
     * @param merchantCategory
     * @return MerchantCategory    返回结果
     */
	public MerchantCategoryCG findMerchantCategoryById(Long id);



    /**
     * 查询 MerchantCategory
     * @param merchantCategory
     * @return List<MerchantCategory>    返回结果集
     */
	public List<MerchantCategoryCG> findMerchantCategory(MerchantCategoryCG merchantCategory);
	
	
	public boolean removeMerchantCategory(@Param("clientId") String clientId);


}