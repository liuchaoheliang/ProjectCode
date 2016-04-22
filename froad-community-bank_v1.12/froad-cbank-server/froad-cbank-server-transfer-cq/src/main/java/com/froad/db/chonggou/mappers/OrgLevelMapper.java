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
 * @Title: OrgLevelMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.chonggou.mappers;

import com.froad.db.chonggou.entity.OrgLevel;

/**
 * 
 * <p>@Title: OrgLevelMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface OrgLevelMapper {


    /**
     * 增加 OrgLevel
     * @param orgLevel
     * @return Long    主键ID
     */
	public Long addOrgLevel(OrgLevel orgLevel);


    /**
     * 删除 OrgLevel
     * @param orgLevel
     * @return Boolean    是否成功
     */
	public Boolean deleteOrgLevel();




}