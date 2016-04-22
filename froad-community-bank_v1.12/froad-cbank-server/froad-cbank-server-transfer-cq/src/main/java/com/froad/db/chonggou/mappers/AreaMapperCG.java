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
 * @Title: AreaMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.chonggou.mappers;

import java.util.List;

import com.froad.db.chonggou.entity.AreaCG;

/**
 * 
 * <p>@Title: AreaMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface AreaMapperCG {


    /**
     * 增加 Area
     * @param area
     * @return Long    主键ID
     */
	public Long addArea(AreaCG area);

	
    /**
     * 查询 Area
     * @param area
     * @return List<Area>    返回结果集
     */
	public List<AreaCG> findArea(AreaCG area);
	
	

    /**
     * 修改 Area
     * @param area
     * @return Boolean    是否成功
     */
	public Boolean updateArea(AreaCG area);

	
	
	public AreaCG findAreaById(Long id);
	
	public List<AreaCG> findallArea();
	/**
	 * 
	  * @Title: deleteArea
	  * @Description: TODO
	  * @author: Yaren Liang 2015年6月26日
	  * @modify: Yaren Liang 2015年6月26日
	  * @param     
	  * @return void    
	  * @throws
	 */
	public void deleteArea(String id);


}