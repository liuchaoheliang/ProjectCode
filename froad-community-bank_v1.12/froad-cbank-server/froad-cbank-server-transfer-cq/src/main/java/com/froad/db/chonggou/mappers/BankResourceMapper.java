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
package com.froad.db.chonggou.mappers;

import java.util.List;

import com.froad.db.chonggou.entity.BankResource;

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
     * 修改 BankResource
     * @param bankResource
     * @return Boolean    是否成功
     */
	public Boolean updateBankResource(BankResource bankResource);
	
	
	public BankResource findById(Long id);

	
	public List<BankResource> findAllResources();
	
	
}