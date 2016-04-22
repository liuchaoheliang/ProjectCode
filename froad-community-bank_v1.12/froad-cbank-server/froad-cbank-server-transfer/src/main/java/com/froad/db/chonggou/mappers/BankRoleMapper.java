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
 * @Title: BankRoleMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.chonggou.mappers;

import com.froad.db.chonggou.entity.BankRole;

/**
 * 
 * <p>@Title: BankRoleMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface BankRoleMapper {


    /**
     * 增加 BankRole
     * @param bankRole
     * @return Long    主键ID
     */
	public Long addBankRole(BankRole bankRole);

	
	public BankRole findById(Long id);
}