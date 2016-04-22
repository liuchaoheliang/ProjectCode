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
 * @Title: BankResourceMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.BankResource;

/**
 * 
 * <p>@Title: BankResourceMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface BankResourceMapper {


    /**
     * 增加 BankResource
     * @param bankResource
     * @return Long    主键ID
     */
	public Long addBankResource(BankResource bankResource);



    /**
     * 批量增加 BankResource
     * @param List<BankResource>
     * @return Boolean    是否成功
     */
	public Boolean addBankResourceByBatch(List<BankResource> bankResourceList);



    /**
     * 删除 BankResource
     * @param bankResource
     * @return Boolean    是否成功
     */
	public Boolean deleteBankResource(Long id);



    /**
     * 修改 BankResource
     * @param bankResource
     * @return Boolean    是否成功
     */
	public Boolean updateBankResource(BankResource bankResource);



	/**
     * 根据资源id集合查询资源对象集合
     * @param resourceIdList 资源id集合
     * @return List<BankResource> 返回资源对象集合
     */
	public List<BankResource> findBankResourceByList(@Param("resourceIdList")List<Long> resourceIdList);

	
    /**
     * 查询 BankResource
     * @param bankResource
     * @return List<BankResource>    返回结果集
     */
	public List<BankResource> findBankResource(BankResource bankResource);



    /**
     * 分页查询 BankResource
     * @param page 
     * @param bankResource
     * @return List<BankResource>    返回分页查询结果集
     */
	public List<BankResource> findByPage(Page page, @Param("bankResource")BankResource bankResource);

	/**
     * 删除子资源
     * @param resourceId
     * @return Boolean    是否成功
     */
	public Boolean deleteSubResource(Long resourceId);

}