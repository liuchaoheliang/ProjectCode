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
 * @Title: ActivitiesHandler.java
 * @Package com.froad.handler
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.handler;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.bean.Page;
import com.froad.po.Activities;

/**
 * 
 * <p>@Title: ActivitiesHandler.java</p>
 * <p>Description: 封装对MySQL对应的实体Activities的CURD操作接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface ActivitiesHandler {


    /**
     * 增加 Activities
     * @param activities
     * @return Long    主键ID
     * @throws Exception
     */
	public Long addActivities(Activities activities)  throws Exception;



    /**
     * 增加 Activities
     * @param sqlSession
     * @param activities
     * @return Long    主键ID
     * @throws Exception
     */
	public Long addActivities(SqlSession sqlSession, Activities activities) throws Exception;



    /**
     * 删除 Activities
     * @param activities
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteActivities(Activities activities) throws Exception;



    /**
     * 删除 Activities
     * @param sqlSession
     * @param activities
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteActivities(SqlSession sqlSession, Activities activities) throws Exception;



    /**
     * 根据id删除 Activities
     * @param id
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteActivitiesById(Long id) throws Exception;



    /**
     * 根据id删除 Activities
     * @param id
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteActivitiesById(SqlSession sqlSession, Long id) throws Exception;



    /**
     * 修改 Activities
     * @param activities
     * @return Boolean    受影响行数
     * @throws Exception
     */
	public Integer updateActivities(Activities activities) throws Exception;



    /**
     * 修改 Activities
     * @param sqlSession
     * @param activities
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateActivities(SqlSession sqlSession, Activities activities) throws Exception;



    /**
     * 根据id修改 Activities
     * @param Activities
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateActivitiesById(Activities activities) throws Exception;



    /**
     * 根据id修改 Activities
     * @param sqlSession
     * @param activities
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateActivitiesById(SqlSession sqlSession, Activities activities) throws Exception;



    /**
     * 根据id查询单个 Activities
     * @param id
     * @return Activities    单个Activities
     * @throws Exception
     */
	public Activities findActivitiesById(Long id) throws Exception;



    /**
     * 查询一个 Activities
     * @param activities
     * @return Activities    单个Activities
     * @throws Exception
     */
	public Activities findOneActivities(Activities activities) throws Exception;



    /**
     * 查询统计 Activities
     * @param activities
     * @return Integer    返回记录数Activities
     * @throws Exception
     */
	public Integer countActivities(Activities activities) throws Exception;



    /**
     * 查询 Activities
     * @param activities
     * @return List<Activities>    Activities集合 
     * @throws Exception
     */
	public List<Activities> findActivities(Activities activities) throws Exception;



    /**
     * 查询 Activities
     * @param activities
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<Activities>    Activities集合 
     * @throws Exception
     */
	public List<Activities> findActivities(Activities activities, String order) throws Exception;



    /**
     * 分页查询 Activities
     * @param page
     * @param activities
     * @return Page<Activities>    Activities分页结果 
     * @throws Exception 
     */
	public Page<Activities> findActivitiesByPage(Page<Activities> page, Activities activities) throws Exception;



}