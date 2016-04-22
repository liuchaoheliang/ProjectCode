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
 * @Title: ActivitiesMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.Activities;

/**
 * 
 * <p>@Title: ActivitiesMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface ActivitiesMapper {


    /**
     * 增加 Activities
     * @param activities
     * @return Long    主键ID
     */
	public Long addActivities(Activities activities);



    /**
     * 批量增加 Activities
     * @param List<Activities>
     * @return Boolean    是否成功
     */
	public Boolean addActivitiesByBatch(List<Activities> activitiesList);



    /**
     * 删除 Activities
     * @param activities
     * @return Boolean    是否成功
     */
	public Boolean deleteActivities(Long activitiesId);



    /**
     * 修改 Activities
     * @param activities
     * @return Boolean    是否成功
     */
	public Boolean updateActivities(Activities activities);


    /**
     * 查询一个 Activities
     * @param activities
     * @return Activities    返回结果
     */
	public List<Activities> findActivitiesById(Activities activities);
	
    /**
     * 查询 Activities
     * @param activities
     * @return List<Activities>    返回结果集
     */
	public List<Activities> findActivities(Activities activities);



    /**
     * 分页查询 Activities
     * @param page 
     * @param activities
     * @return List<Activities>    返回分页查询结果集
     */
	public List<Activities> findByPage(Page page, @Param("activities")Activities activities);

	/**
     * 查询一个 Activities
     * @param id
     * @return Activities    返回结果
     */
	public Activities getActivitiesById(long id);
}