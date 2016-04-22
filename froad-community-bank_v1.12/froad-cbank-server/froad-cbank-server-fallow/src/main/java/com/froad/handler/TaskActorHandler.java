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
 * @Title: TaskActorHandler.java
 * @Package com.froad.handler
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.handler;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.bean.Page;
import com.froad.po.mysql.TaskActor;

/**
 * 
 * <p>@Title: TaskActorHandler.java</p>
 * <p>Description: 封装对MySQL对应的实体TaskActor的CURD操作接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface TaskActorHandler {


    /**
     * 增加 TaskActor
     * @param taskActor
     * @return String    主键ID
     * @throws Exception
     */
	public String addTaskActor(TaskActor taskActor)  throws Exception;



    /**
     * 增加 TaskActor
     * @param sqlSession
     * @param taskActor
     * @return String    主键ID
     * @throws Exception
     */
	public String addTaskActor(SqlSession sqlSession, TaskActor taskActor) throws Exception;



    /**
     * 删除 TaskActor
     * @param taskActor
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteTaskActor(TaskActor taskActor) throws Exception;



    /**
     * 删除 TaskActor
     * @param sqlSession
     * @param taskActor
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteTaskActor(SqlSession sqlSession, TaskActor taskActor) throws Exception;



    /**
     * 根据taskId删除 TaskActor
     * @param taskId
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteTaskActorByTaskId(String taskId) throws Exception;



    /**
     * 根据taskId删除 TaskActor
     * @param taskId
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteTaskActorByTaskId(SqlSession sqlSession, String taskId) throws Exception;



    /**
     * 修改 TaskActor
     * @param taskActor
     * @return Boolean    受影响行数
     * @throws Exception
     */
	public Integer updateTaskActor(TaskActor taskActor) throws Exception;



    /**
     * 修改 TaskActor
     * @param sqlSession
     * @param taskActor
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateTaskActor(SqlSession sqlSession, TaskActor taskActor) throws Exception;



    /**
     * 根据taskId修改 TaskActor
     * @param TaskActor
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateTaskActorByTaskId(TaskActor taskActor) throws Exception;



    /**
     * 根据taskId修改 TaskActor
     * @param sqlSession
     * @param taskActor
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateTaskActorByTaskId(SqlSession sqlSession, TaskActor taskActor) throws Exception;



    /**
     * 根据taskId查询单个 TaskActor
     * @param taskId
     * @return TaskActor    单个TaskActor
     * @throws Exception
     */
	public TaskActor findTaskActorByTaskId(String taskId) throws Exception;



    /**
     * 查询一个 TaskActor
     * @param taskActor
     * @return TaskActor    单个TaskActor
     * @throws Exception
     */
	public TaskActor findOneTaskActor(TaskActor taskActor) throws Exception;



    /**
     * 查询统计 TaskActor
     * @param taskActor
     * @return Integer    返回记录数TaskActor
     * @throws Exception
     */
	public Integer countTaskActor(TaskActor taskActor) throws Exception;



    /**
     * 查询 TaskActor
     * @param taskActor
     * @return List<TaskActor>    TaskActor集合 
     * @throws Exception
     */
	public List<TaskActor> findTaskActor(TaskActor taskActor) throws Exception;



    /**
     * 查询 TaskActor
     * @param taskActor
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<TaskActor>    TaskActor集合 
     * @throws Exception
     */
	public List<TaskActor> findTaskActor(TaskActor taskActor, String order) throws Exception;



    /**
     * 分页查询 TaskActor
     * @param page
     * @param taskActor
     * @return Page<TaskActor>    TaskActor分页结果 
     * @throws Exception 
     */
	public Page<TaskActor> findTaskActorByPage(Page<TaskActor> page, TaskActor taskActor) throws Exception;



}