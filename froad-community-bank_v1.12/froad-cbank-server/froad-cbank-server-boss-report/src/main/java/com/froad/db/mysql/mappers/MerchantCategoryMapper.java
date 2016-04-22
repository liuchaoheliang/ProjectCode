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
package com.froad.db.mysql.mappers;

import java.util.List;

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
     * 查询 MerchantCategory
     * @param merchantCategory
     * @return List<MerchantCategory>    返回结果集
     */
	public List<MerchantCategory> findMerchantCategory(MerchantCategory merchantCategory);
	
}