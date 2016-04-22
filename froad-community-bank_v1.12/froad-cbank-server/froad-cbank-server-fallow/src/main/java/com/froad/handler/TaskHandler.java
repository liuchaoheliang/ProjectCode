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
 * @Title: TaskHandler.java
 * @Package com.froad.handler
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.handler;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.bean.Page;
import com.froad.po.mysql.Task;

/**
 * 
 * <p>@Title: TaskHandler.java</p>
 * <p>Description: 封装对MySQL对应的实体Task的CURD操作接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface TaskHandler {


    /**
     * 增加 Task
     * @param task
     * @return Long    主键ID
     * @throws Exception
     */
	public Long addTask(Task task)  throws Exception;



    /**
     * 增加 Task
     * @param sqlSession
     * @param task
     * @return Long    主键ID
     * @throws Exception
     */
	public Long addTask(SqlSession sqlSession, Task task) throws Exception;



    /**
     * 删除 Task
     * @param task
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteTask(Task task) throws Exception;



    /**
     * 删除 Task
     * @param sqlSession
     * @param task
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteTask(SqlSession sqlSession, Task task) throws Exception;



    /**
     * 根据id删除 Task
     * @param id
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteTaskById(Long id) throws Exception;



    /**
     * 根据id删除 Task
     * @param id
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteTaskById(SqlSession sqlSession, Long id) throws Exception;



    /**
     * 根据taskId删除 Task
     * @param taskId
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteTaskByTaskId(String taskId) throws Exception;



    /**
     * 根据taskId删除 Task
     * @param taskId
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteTaskByTaskId(SqlSession sqlSession, String taskId) throws Exception;
	
	/**
	 * 根据instanceId删除 Task
	 * @param sqlSession
	 * @return Integer    受影响行数
	 * @throws Exception
	 */
	public Integer deleteTaskByInstanceId(String instanceId) throws Exception;
	
	/**
	 * 根据instanceId删除 Task
	 * @param instanceId
	 * @param sqlSession
	 * @return Integer    受影响行数
	 * @throws Exception
	 */
	public Integer deleteTaskByInstanceId(SqlSession sqlSession, String instanceId) throws Exception;



    /**
     * 修改 Task
     * @param task
     * @return Boolean    受影响行数
     * @throws Exception
     */
	public Integer updateTask(Task task) throws Exception;



    /**
     * 修改 Task
     * @param sqlSession
     * @param task
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateTask(SqlSession sqlSession, Task task) throws Exception;



    /**
     * 根据id修改 Task
     * @param Task
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateTaskById(Task task) throws Exception;



    /**
     * 根据id修改 Task
     * @param sqlSession
     * @param task
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateTaskById(SqlSession sqlSession, Task task) throws Exception;



    /**
     * 根据taskId修改 Task
     * @param Task
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateTaskByTaskId(Task task) throws Exception;



    /**
     * 根据taskId修改 Task
     * @param sqlSession
     * @param Task
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateTaskByTaskId(SqlSession sqlSession, Task task) throws Exception;



    /**
     * 根据id查询单个 Task
     * @param id
     * @return Task    单个Task
     * @throws Exception
     */
	public Task findTaskById(Long id) throws Exception;



    /**
     * 根据taskId查询单个 Task
     * @param taskId
     * @return Task    单个Task
     * @throws Exception
     */
	public Task findTaskByTaskId(String taskId) throws Exception;



    /**
     * 查询一个 Task
     * @param task
     * @return Task    单个Task
     * @throws Exception
     */
	public Task findOneTask(Task task) throws Exception;



    /**
     * 查询统计 Task
     * @param task
     * @return Integer    返回记录数Task
     * @throws Exception
     */
	public Integer countTask(Task task) throws Exception;



    /**
     * 查询 Task
     * @param task
     * @return List<Task>    Task集合 
     * @throws Exception
     */
	public List<Task> findTask(Task task) throws Exception;



    /**
     * 查询 Task
     * @param task
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<Task>    Task集合 
     * @throws Exception
     */
	public List<Task> findTask(Task task, String order) throws Exception;



    /**
     * 分页查询 Task
     * @param page
     * @param task
     * @return Page<Task>    Task分页结果 
     * @throws Exception 
     */
	public Page<Task> findTaskByPage(Page<Task> page, Task task) throws Exception;



}