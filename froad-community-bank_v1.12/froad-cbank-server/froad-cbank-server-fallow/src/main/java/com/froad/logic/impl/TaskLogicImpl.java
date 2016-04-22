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
 * @Title: TaskLogicImpl.java
 * @Package com.froad.logic.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.handler.TaskHandler;
import com.froad.handler.impl.TaskHandlerImpl;
import com.froad.logback.LogCvt;
import com.froad.logic.TaskLogic;
import com.froad.po.mysql.Task;

/**
 * 
 * <p>@Title: TaskLogic.java</p>
 * <p>Description: 实现对Task所有业务逻辑处理 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class TaskLogicImpl implements TaskLogic {
	private TaskHandler taskHandler = new TaskHandlerImpl();

    /**
     * 增加 Task
     * @param task
     * @return Long    主键ID
     */
	@Override
	public Long addTask(Task task) {

		Long result = null; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = taskHandler.addTask(task);

			if (null != result) { // 添加成功
				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 添加失败

			}
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("添加Task失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 删除 Task
     * @param task
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteTask(Task task) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = taskHandler.deleteTask(task) > 0;

			if (result) { // 删除成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 删除失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("删除Task失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据id删除 Task
     * @param id
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteTaskById(Long id) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = taskHandler.deleteTaskById(id) > 0;

			if (result) { // 删除成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 删除失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("根据id删除Task失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据taskId删除 Task
     * @param taskId
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteTaskByTaskId(String taskId) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = taskHandler.deleteTaskByTaskId(taskId) > 0;

			if (result) { // 删除成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 删除失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("根据taskId删除Task失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 修改 Task
     * @param task
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateTask(Task task) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = taskHandler.updateTask(task) > 0;
			if (result) { // 修改成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
				result = true;

			} else { // 修改失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("修改Task失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据id修改 Task
     * @param task
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateTaskById(Task task) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = taskHandler.updateTaskById(task) > 0;

			if (result) { // 修改成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 修改失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("根据id修改Task失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据taskId修改 Task
     * @param task
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateTaskByTaskId(Task task) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = taskHandler.updateTaskByTaskId(task) > 0;

			if (result) { // 修改成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 修改失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("根据taskId修改Task失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据id查询单个 Task
     * @param id
     * @return Task    单个 Task
     */
	@Override
	public Task findTaskById(Long id) {

		Task result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = taskHandler.findTaskById(id);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据id查询Task失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据taskId查询单个 Task
     * @param taskId
     * @return Task    单个 Task
     */
	@Override
	public Task findTaskByTaskId(String taskId) {

		Task result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			result = taskHandler.findTaskByTaskId(taskId);
			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据taskId查询Task失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 查询一个 Task
     * @param task
     * @return Task    单个 Task
     */
	@Override
	public Task findOneTask(Task task) {

		Task result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = taskHandler.findOneTask(task);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件查询一个Task失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 统计 Task
     * @param task
     * @return Integer    返回记录数 Task
     */
	@Override
	public Integer countTask(Task task) {

		Integer result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = taskHandler.countTask(task);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件统计Task失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 查询 Task
     * @param task
     * @return List<Task>    Task集合 
     */
	@Override
	public List<Task> findTask(Task task) {

		List<Task> result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = taskHandler.findTask(task);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件查询Task失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 分页查询 Task
     * @param page
     * @param task
     * @return Page<Task>    Task分页结果 
     */
	@Override
	public Page<Task> findTaskByPage(Page<Task> page, Task task) {

		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			page = taskHandler.findTaskByPage(page, task);
		} catch (Exception e) { 
			LogCvt.error("分页查询Task失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return page; 

	}


}