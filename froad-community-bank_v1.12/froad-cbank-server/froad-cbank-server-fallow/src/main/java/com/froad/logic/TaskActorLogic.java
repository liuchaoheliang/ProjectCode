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
 * @Title: TaskActorLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.po.mysql.TaskActor;

/**
 * 
 * <p>@Title: TaskActorLogic.java</p>
 * <p>Description: 封装对TaskActor所有业务逻辑处理的接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface TaskActorLogic {


    /**
     * 增加 TaskActor
     * @param taskActor
     * @return String    主键ID
     */
	public String addTaskActor(TaskActor taskActor) ;



    /**
     * 删除 TaskActor
     * @param taskActor
     * @return Boolean    是否成功
     */
	public Boolean deleteTaskActor(TaskActor taskActor) ;



    /**
     * 根据taskId删除 TaskActor
     * @param taskId
     * @return Boolean    是否成功
     */
	public Boolean deleteTaskActorByTaskId(String taskId) ;



    /**
     * 修改 TaskActor
     * @param taskActor
     * @return Boolean    是否成功
     */
	public Boolean updateTaskActor(TaskActor taskActor) ;



    /**
     * 根据taskId修改 TaskActor
     * @param taskActor
     * @return Boolean    是否成功
     */
	public Boolean updateTaskActorByTaskId(TaskActor taskActor) ;



    /**
     * 根据taskId查询单个 TaskActor
     * @param taskId
     * @return TaskActor    单个 TaskActor
     */
	public TaskActor findTaskActorByTaskId(String taskId) ;



    /**
     * 查询一个 TaskActor
     * @param taskActor
     * @return TaskActor    单个 TaskActor
     */
	public TaskActor findOneTaskActor(TaskActor taskActor) ;



    /**
     * 统计 TaskActor
     * @param taskActor
     * @return Integer    返回记录数 TaskActor
     */
	public Integer countTaskActor(TaskActor taskActor) ;



    /**
     * 查询 TaskActor
     * @param taskActor
     * @return List<TaskActor>    TaskActor集合 
     */
	public List<TaskActor> findTaskActor(TaskActor taskActor) ;



    /**
     * 分页查询 TaskActor
     * @param page
     * @param taskActor
     * @return Page<TaskActor>    TaskActor分页结果 
     */
	public Page<TaskActor> findTaskActorByPage(Page<TaskActor> page, TaskActor taskActor) ;



}