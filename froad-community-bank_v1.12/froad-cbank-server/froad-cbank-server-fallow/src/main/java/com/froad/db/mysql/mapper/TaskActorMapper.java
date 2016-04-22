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
 * @Title: TaskActorMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.mysql.TaskActor;

/**
 * 
 * <p>@Title: TaskActorMapper.java</p>
 * <p>Description: 封装mybatis对MySQL映射的实体TaskActor的CURD操作Mapper </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface TaskActorMapper {


    /**
     * 增加 TaskActor
     * @param taskActor
     * @return Long    受影响行数
     */
	public Long addTaskActor(TaskActor taskActor);



    /**
     * 批量增加 TaskActor
     * @param taskActorList
     * @return Boolean    是否成功
     */
	public Boolean addTaskActorByBatch(@Param("taskActorList")List<TaskActor> taskActorList);



    /**
     * 删除 TaskActor
     * @param taskActor
     * @return Integer    受影响行数
     */
	public Integer deleteTaskActor(TaskActor taskActor);



    /**
     * 根据taskId删除一个 TaskActor
     * @param taskId
     * @return Integer    受影响行数
     */
	public Integer deleteTaskActorByTaskId(String taskId);



    /**
     * 修改 TaskActor
     * @param taskActor
     * @return Integer    受影响行数
     */
	public Integer updateTaskActor(TaskActor taskActor);



    /**
     * 根据taskId修改一个 TaskActor
     * @param taskActor
     * @return Integer    受影响行数
     */
	public Integer updateTaskActorByTaskId(TaskActor taskActor);



    /**
     * 根据taskId查询一个 TaskActor
     * @param taskId
     * @return TaskActor    返回结果
     */
	public TaskActor findTaskActorByTaskId(String taskId);



    /**
     * 查询一个 TaskActor
     * @param taskActor
     * @return TaskActor    返回结果集
     */
	public TaskActor findOneTaskActor(TaskActor taskActor);



    /**
     * 统计 TaskActor
     * @param taskActor
     * @return Integer    返回记录数
     */
	public Integer countTaskActor(TaskActor taskActor);



    /**
     * 查询 TaskActor
     * @param taskActor
     * @return List<TaskActor>    返回结果集
     */
	public List<TaskActor> findTaskActor(@Param("taskActor")TaskActor taskActor, @Param("order")String order);



    /**
     * 分页查询 TaskActor
     * @param page 
     * @param taskActor
     * @return List<TaskActor>    返回分页查询结果集
     */
	public List<TaskActor> findByPage(Page page, @Param("taskActor")TaskActor taskActor);



}