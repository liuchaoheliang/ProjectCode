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
 * @Title: TaskActorLogicImpl.java
 * @Package com.froad.logic.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.handler.TaskActorHandler;
import com.froad.handler.impl.TaskActorHandlerImpl;
import com.froad.logback.LogCvt;
import com.froad.logic.TaskActorLogic;
import com.froad.po.mysql.TaskActor;

/**
 * 
 * <p>@Title: TaskActorLogic.java</p>
 * <p>Description: 实现对TaskActor所有业务逻辑处理 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class TaskActorLogicImpl implements TaskActorLogic {
	private TaskActorHandler taskActorHandler = new TaskActorHandlerImpl();

    /**
     * 增加 TaskActor
     * @param taskActor
     * @return String    主键ID
     */
	@Override
	public String addTaskActor(TaskActor taskActor) {

		String result = null; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = taskActorHandler.addTaskActor(taskActor);

			if (null != result) { // 添加成功
				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 添加失败

			}
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("添加TaskActor失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 删除 TaskActor
     * @param taskActor
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteTaskActor(TaskActor taskActor) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = taskActorHandler.deleteTaskActor(taskActor) > 0;

			if (result) { // 删除成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 删除失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("删除TaskActor失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据taskId删除 TaskActor
     * @param taskId
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteTaskActorByTaskId(String taskId) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = taskActorHandler.deleteTaskActorByTaskId(taskId) > 0;

			if (result) { // 删除成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 删除失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("根据taskId删除TaskActor失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 修改 TaskActor
     * @param taskActor
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateTaskActor(TaskActor taskActor) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = taskActorHandler.updateTaskActor(taskActor) > 0;
			if (result) { // 修改成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
				result = true;

			} else { // 修改失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("修改TaskActor失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据taskId修改 TaskActor
     * @param taskActor
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateTaskActorByTaskId(TaskActor taskActor) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = taskActorHandler.updateTaskActorByTaskId(taskActor) > 0;

			if (result) { // 修改成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 修改失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("根据taskId修改TaskActor失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据taskId查询单个 TaskActor
     * @param taskId
     * @return TaskActor    单个 TaskActor
     */
	@Override
	public TaskActor findTaskActorByTaskId(String taskId) {

		TaskActor result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = taskActorHandler.findTaskActorByTaskId(taskId);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据id查询TaskActor失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 查询一个 TaskActor
     * @param taskActor
     * @return TaskActor    单个 TaskActor
     */
	@Override
	public TaskActor findOneTaskActor(TaskActor taskActor) {

		TaskActor result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = taskActorHandler.findOneTaskActor(taskActor);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件查询一个TaskActor失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 统计 TaskActor
     * @param taskActor
     * @return Integer    返回记录数 TaskActor
     */
	@Override
	public Integer countTaskActor(TaskActor taskActor) {

		Integer result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = taskActorHandler.countTaskActor(taskActor);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件统计TaskActor失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 查询 TaskActor
     * @param taskActor
     * @return List<TaskActor>    TaskActor集合 
     */
	@Override
	public List<TaskActor> findTaskActor(TaskActor taskActor) {

		List<TaskActor> result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = taskActorHandler.findTaskActor(taskActor);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件查询TaskActor失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 分页查询 TaskActor
     * @param page
     * @param taskActor
     * @return Page<TaskActor>    TaskActor分页结果 
     */
	@Override
	public Page<TaskActor> findTaskActorByPage(Page<TaskActor> page, TaskActor taskActor) {

		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			page = taskActorHandler.findTaskActorByPage(page, taskActor);
		} catch (Exception e) { 
			LogCvt.error("分页查询TaskActor失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return page; 

	}


}