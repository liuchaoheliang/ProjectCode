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
 * @Title: MerchantTypeMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.chonggou.mappers;

import java.util.List;

import com.froad.db.chonggou.entity.MerchantTypeCG;

/**
 * 
 * <p>@Title: MerchantTypeMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface MerchantTypeMapperCG {


    /**
     * 增加 MerchantType
     * @param merchantType
     * @return Long    主键ID
     */
	public Long addMerchantType(MerchantTypeCG merchantTypeCG);



    /**
     * 批量增加 MerchantType
     * @param List<MerchantType>
     * @return Boolean    是否成功
     */
	public Boolean addMerchantTypeByBatch(List<MerchantTypeCG> merchantTypeCGList);
	
	
    /**
     * 查询一个 MerchantType
     * @param merchantType
     * @return MerchantType    返回结果
     */
	public MerchantTypeCG findMerchantTypeById(Long id);


	public MerchantTypeCG findMerchantTypeByTypeName(String typeName);
	
    /**
     * 查询 MerchantType
     * @param merchantType
     * @return List<MerchantType>    返回结果集
     */
	public List<MerchantTypeCG> findAll();
	
	
	public List<MerchantTypeCG> findMerchantAll();


}