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
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.MerchantType;

/**
 * 
 * <p>@Title: MerchantTypeMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface MerchantTypeMapper {


    /**
     * 增加 MerchantType
     * @param merchantType
     * @return Long    主键ID
     */
	public Long addMerchantType(MerchantType merchantType);



    /**
     * 批量增加 MerchantType
     * @param List<MerchantType>
     * @return Boolean    是否成功
     */
	public Boolean addMerchantTypeByBatch(List<MerchantType> merchantTypeList);



    /**
     * 删除 MerchantType
     * @param merchantType
     * @return Boolean    是否成功
     */
	public Boolean deleteMerchantType(MerchantType merchantType);



    /**
     * 修改 MerchantType
     * @param merchantType
     * @return Boolean    是否成功
     */
	public Boolean updateMerchantType(MerchantType merchantType);



    /**
     * 查询一个 MerchantType
     * @param merchantType
     * @return MerchantType    返回结果
     */
	public MerchantType findMerchantTypeById(@Param("id")Long id,@Param("clientId")String clientId);



    /**
     * 查询 MerchantType
     * @param merchantType
     * @return List<MerchantType>    返回结果集
     */
	public List<MerchantType> findMerchantType(MerchantType merchantType);



    /**
     * 分页查询 MerchantType
     * @param page 
     * @param merchantType
     * @return List<MerchantType>    返回分页查询结果集
     */
	public List<MerchantType> findByPage(Page page, @Param("merchantType")MerchantType merchantType);
	
	

    /**
     * 查询 MerchantType
     * @param merchantType
     * @return List<MerchantType>    返回结果集
     */
	public List<MerchantType> findMerchantTypeByMerchantTypeIdList(@Param("clientId")String clientId,@Param("merchantTypeIdList")List<Long> merchantTypeIdList);

}