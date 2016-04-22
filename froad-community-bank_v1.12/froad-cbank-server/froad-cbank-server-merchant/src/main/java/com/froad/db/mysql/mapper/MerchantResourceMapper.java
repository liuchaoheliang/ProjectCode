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
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.MerchantResource;

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
	 * OrderValue增加某一个数
	 * 
	 * @Title: addOrderValue
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param startOrderValue
	 * @param endOrderValue
	 * @return
	 * @return Boolean 返回类型
	 * @throws
	 */
	public Boolean addOrderValue(@Param("startOrderValue") Integer startOrderValue, @Param("endOrderValue") Integer endOrderValue, @Param("inc") Integer inc);
	/**
	 * OrderValue增加某一个数
	 * 
	 * @Title: addOrderValueByTreePath
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param startOrderValue
	 * @param endOrderValue
	 * @return
	 * @return Boolean 返回类型
	 * @throws
	 */
	public Boolean addOrderValueByTreePath(@Param("id") Long id, @Param("inc") Integer inc);

    /**
     * 查询一个 MerchantResource
     * @param merchantResource
     * @return MerchantResource    返回结果
     */
	public MerchantResource findMerchantResourceById(@Param("id") Long id);

	/**
	 * 统计treePath中有id的有多少个
	 * @Title: countByTreePath 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param id
	 * @return
	 * @return Integer    返回类型 
	 * @throws
	 */
	public Integer countByTreePath(@Param("id") Long id);
	
	/**
	 * 求一个模块的最大的order_value
	 * @Title: maxOrderVallueByTreePath 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param id
	 * @return
	 * @return Integer    返回类型 
	 * @throws
	 */
	public Integer maxOrderVallueByTreePath(@Param("id") Long id);
	
	/**
	 * 统计MerchantResource
	 * @Title: countMerchantResource 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchantResource
	 * @return
	 * @return Integer    返回类型 
	 * @throws
	 */
	public Integer countMerchantResource(MerchantResource merchantResource);
	
	/**
	 * 根据treePath查询id集合
	 * @Title: findMerchantResourceIdByTreePath 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param id
	 * @return
	 * @return List<Long>    返回类型 
	 * @throws
	 */
	public List<Long> findMerchantResourceIdByTreePath(@Param("id")Long id);

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