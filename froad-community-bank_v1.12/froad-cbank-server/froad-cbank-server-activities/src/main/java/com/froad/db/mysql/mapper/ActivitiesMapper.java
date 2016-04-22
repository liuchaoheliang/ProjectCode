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
 * <p>Description: 封装mybatis对MySQL映射的实体Activities的CURD操作Mapper </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface ActivitiesMapper {


    /**
     * 增加 Activities
     * @param activities
     * @return Long    受影响行数
     */
	public Long addActivities(Activities activities);



    /**
     * 批量增加 Activities
     * @param activitiesList
     * @return Boolean    是否成功
     */
	public Boolean addActivitiesByBatch(List<Activities> activitiesList);



    /**
     * 删除 Activities
     * @param activities
     * @return Integer    受影响行数
     */
	public Integer deleteActivities(Activities activities);



    /**
     * 根据id删除一个 Activities
     * @param id
     * @return Integer    受影响行数
     */
	public Integer deleteActivitiesById(Long id);



    /**
     * 修改 Activities
     * @param activities
     * @return Integer    受影响行数
     */
	public Integer updateActivities(Activities activities);



    /**
     * 根据id修改一个 Activities
     * @param activities
     * @return Integer    受影响行数
     */
	public Integer updateActivitiesById(Activities activities);



    /**
     * 根据id查询一个 Activities
     * @param id
     * @return Activities    返回结果
     */
	public Activities findActivitiesById(Long id);



    /**
     * 查询一个 Activities
     * @param activities
     * @return Activities    返回结果集
     */
	public Activities findOneActivities(Activities activities);



    /**
     * 统计 Activities
     * @param activities
     * @return Integer    返回记录数
     */
	public Integer countActivities(Activities activities);



    /**
     * 查询 Activities
     * @param activities
     * @return List<Activities>    返回结果集
     */
	public List<Activities> findActivities(@Param("activities")Activities activities, @Param("order")String order);



    /**
     * 分页查询 Activities
     * @param page 
     * @param activities
     * @return List<Activities>    返回分页查询结果集
     */
	public List<Activities> findByPage(Page page, @Param("activities")Activities activities);



}