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
 * @Title: AreaCommonMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年6月4日
 */
package com.froad.db.mysql.mapper;

import com.froad.po.Area;

/**
 * 
 * <p>@Title: AreaCommonMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年6月4日
 */
public interface AreaCommonMapper {



    /**
     * 查询一个 Area
     * @param area
     * @return Area    返回结果
     */
	public Area findAreaById(Long id);
	
}