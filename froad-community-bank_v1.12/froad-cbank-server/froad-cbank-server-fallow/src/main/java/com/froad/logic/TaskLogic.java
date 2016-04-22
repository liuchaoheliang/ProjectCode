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
 * @Title: TaskLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.po.mysql.Task;

/**
 * 
 * <p>@Title: TaskLogic.java</p>
 * <p>Description: 封装对Task所有业务逻辑处理的接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface TaskLogic {


    /**
     * 增加 Task
     * @param task
     * @return Long    主键ID
     */
	public Long addTask(Task task) ;



    /**
     * 删除 Task
     * @param task
     * @return Boolean    是否成功
     */
	public Boolean deleteTask(Task task) ;



    /**
     * 根据id删除 Task
     * @param id
     * @return Boolean    是否成功
     */
	public Boolean deleteTaskById(Long id) ;



    /**
     * 根据taskId删除 Task
     * @param taskId
     * @return Boolean    是否成功
     */
	public Boolean deleteTaskByTaskId(String taskId) ;



    /**
     * 修改 Task
     * @param task
     * @return Boolean    是否成功
     */
	public Boolean updateTask(Task task) ;



    /**
     * 根据id修改 Task
     * @param task
     * @return Boolean    是否成功
     */
	public Boolean updateTaskById(Task task) ;



    /**
     * 根据taskId修改 Task
     * @param task
     * @return Boolean    是否成功
     */
	public Boolean updateTaskByTaskId(Task task) ;



    /**
     * 根据id查询单个 Task
     * @param id
     * @return Task    单个 Task
     */
	public Task findTaskById(Long id) ;



    /**
     * 根据taskId查询单个 Task
     * @param taskId
     * @return Task    单个 Task
     */
	public Task findTaskByTaskId(String taskId) ;



    /**
     * 查询一个 Task
     * @param task
     * @return Task    单个 Task
     */
	public Task findOneTask(Task task) ;



    /**
     * 统计 Task
     * @param task
     * @return Integer    返回记录数 Task
     */
	public Integer countTask(Task task) ;



    /**
     * 查询 Task
     * @param task
     * @return List<Task>    Task集合 
     */
	public List<Task> findTask(Task task) ;



    /**
     * 分页查询 Task
     * @param page
     * @param task
     * @return Page<Task>    Task分页结果 
     */
	public Page<Task> findTaskByPage(Page<Task> page, Task task) ;



}