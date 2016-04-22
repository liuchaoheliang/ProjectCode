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
package com.froad.db.mysql.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.Area;

/**
 * 
 * <p>@Title: AreaMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface AreaMapper {


    /**
     * 增加 Area
     * @param area
     * @return Long    主键ID
     */
	public Long addArea(Area area);



    /**
     * 批量增加 Area
     * @param List<Area>
     * @return Boolean    是否成功
     */
	public Boolean addAreaByBatch(List<Area> areaList);



    /**
     * 删除 Area
     * @param area
     * @return Boolean    是否成功
     */
	public Boolean deleteArea(Area area);



    /**
     * 修改 Area
     * @param area
     * @return Boolean    是否成功
     */
	public Boolean updateArea(Area area);


    /**
     * 查询一个 Area
     * @param area
     * @return Area    返回结果
     */
	public Area findAreaById(Long id);
	
    /**
     * 查询 Area
     * @param area
     * @return List<Area>    返回结果集
     */
	public List<Area> findArea(Area area);

	
	 /**
     * 查询 Area 下级
     * @param area
     * @return List<Area>    返回结果集
     */
	public List<Area> findChildrenInfoById(Long id);
	
	
//	 /**
//     * 查询 Area 父级
//     * @param area
//     * @return List<Area>    返回结果集
//     */
//	public List<Area> findParentInfoById(Map<String,Long> map);
	


    /**
     * 分页查询 Area
     * @param page 
     * @param area
     * @return List<Area>    返回分页查询结果集
     */
	public List<Area> findByPage(Page page, @Param("area")Area area);



}