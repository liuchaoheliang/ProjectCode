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
 * @Title: HistoryTaskMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.mysql.HistoryTask;

/**
 * 
 * <p>@Title: HistoryTaskMapper.java</p>
 * <p>Description: 封装mybatis对MySQL映射的实体HistoryTask的CURD操作Mapper </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface HistoryTaskMapper {


    /**
     * 增加 HistoryTask
     * @param historyTask
     * @return Long    受影响行数
     */
	public Long addHistoryTask(HistoryTask historyTask);



    /**
     * 批量增加 HistoryTask
     * @param historyTaskList
     * @return Boolean    是否成功
     */
	public Boolean addHistoryTaskByBatch(@Param("historyTaskList")List<HistoryTask> historyTaskList);



    /**
     * 删除 HistoryTask
     * @param historyTask
     * @return Integer    受影响行数
     */
	public Integer deleteHistoryTask(HistoryTask historyTask);



    /**
     * 根据id删除一个 HistoryTask
     * @param id
     * @return Integer    受影响行数
     */
	public Integer deleteHistoryTaskById(Long id);
	
	
	/**
	 * 根据taskId删除一个 HistoryTask
	 * @param taskId
	 * @return Integer    受影响行数
	 */
	public Integer deleteHistoryTaskByTaskId(String taskId);
	
	
	/**
     * 根据instanceId删除一个 HistoryTask
     * @param instanceId
     * @return Integer    受影响行数
     */
	public Integer deleteHistoryTaskByInstanceId(String instanceId);

    /**
     * 修改 HistoryTask
     * @param historyTask
     * @return Integer    受影响行数
     */
	public Integer updateHistoryTask(HistoryTask historyTask);



    /**
     * 根据id修改一个 HistoryTask
     * @param historyTask
     * @return Integer    受影响行数
     */
	public Integer updateHistoryTaskById(HistoryTask historyTask);
	
	
	/**
	 * 根据taskId修改一个 HistoryTask
	 * @param historyTask
	 * @return Integer    受影响行数
	 */
	public Integer updateHistoryTaskByTaskId(HistoryTask historyTask);
	
	
	/**
	 * 根据instanceId修改一个 HistoryTask
	 * @param historyTask
	 * @return Integer    受影响行数
	 */
	public Integer updateHistoryTaskByInstanceId(HistoryTask historyTask);



    /**
     * 根据id查询一个 HistoryTask
     * @param id
     * @return HistoryTask    返回结果
     */
	public HistoryTask findHistoryTaskById(Long id);



    /**
     * 查询一个 HistoryTask
     * @param historyTask
     * @return HistoryTask    返回结果集
     */
	public HistoryTask findOneHistoryTask(HistoryTask historyTask);



    /**
     * 统计 HistoryTask
     * @param historyTask
     * @return Integer    返回记录数
     */
	public Integer countHistoryTask(HistoryTask historyTask);



    /**
     * 查询 HistoryTask
     * @param historyTask
     * @return List<HistoryTask>    返回结果集
     */
	public List<HistoryTask> findHistoryTask(@Param("historyTask")HistoryTask historyTask, @Param("order")String order);
	
	/**
     * 查询 执行节点HistoryTask
     * @param historyTask
     * @return List<HistoryTask>    返回结果集
     */
	public List<HistoryTask> findHistoryTaskOfExecute(@Param("historyTask")HistoryTask historyTask);



    /**
     * 分页查询 HistoryTask
     * @param page 
     * @param historyTask
     * @return List<HistoryTask>    返回分页查询结果集
     */
	public List<HistoryTask> findByPage(Page page, @Param("historyTask")HistoryTask historyTask);



}