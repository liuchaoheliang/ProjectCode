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
 * @Title: TaskMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.mysql.Task;

/**
 * 
 * <p>@Title: TaskMapper.java</p>
 * <p>Description: 封装mybatis对MySQL映射的实体Task的CURD操作Mapper </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface TaskMapper {


    /**
     * 增加 Task
     * @param task
     * @return Long    受影响行数
     */
	public Long addTask(Task task);



    /**
     * 批量增加 Task
     * @param taskList
     * @return Boolean    是否成功
     */
	public Boolean addTaskByBatch(@Param("taskList")List<Task> taskList);



    /**
     * 删除 Task
     * @param task
     * @return Integer    受影响行数
     */
	public Integer deleteTask(Task task);



    /**
     * 根据id删除一个 Task
     * @param id
     * @return Integer    受影响行数
     */
	public Integer deleteTaskById(Long id);



    /**
     * 根据taskId删除一个 Task
     * @param taskId
     * @return Integer    受影响行数
     */
	public Integer deleteTaskByTaskId(String taskId);

	
	/**
     * 根据instanceId删除一个 Task
     * @param instanceId
     * @return Integer    受影响行数
     */
	public Integer deleteTaskByInstanceId(String instanceId);

    /**
     * 修改 Task
     * @param task
     * @return Integer    受影响行数
     */
	public Integer updateTask(Task task);



    /**
     * 根据id修改一个 Task
     * @param task
     * @return Integer    受影响行数
     */
	public Integer updateTaskById(Task task);



    /**
     * 根据taskId修改一个 Task
     * @param task
     * @return Integer    受影响行数
     */
	public Integer updateTaskByTaskId(Task task);



    /**
     * 根据id查询一个 Task
     * @param id
     * @return Task    返回结果
     */
	public Task findTaskById(Long id);



    /**
     * 根据taskId查询一个 Task
     * @param taskId
     * @return Task    返回结果
     */
	public Task findTaskByTaskId(String taskId);



    /**
     * 查询一个 Task
     * @param task
     * @return Task    返回结果集
     */
	public Task findOneTask(Task task);



    /**
     * 统计 Task
     * @param task
     * @return Integer    返回记录数
     */
	public Integer countTask(Task task);



    /**
     * 查询 Task
     * @param task
     * @return List<Task>    返回结果集
     */
	public List<Task> findTask(@Param("task")Task task, @Param("order")String order);



    /**
     * 分页查询 Task
     * @param page 
     * @param task
     * @return List<Task>    返回分页查询结果集
     */
	public List<Task> findByPage(Page page, @Param("task")Task task);



}