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
 * @Title: TaskHandlerImpl.java
 * @Package com.froad.handler.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.handler.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.TaskMapper;
import com.froad.logback.LogCvt;
import com.froad.handler.TaskHandler;
import com.froad.po.mysql.Task;

/**
 * 
 * <p>@Title: TaskHandler.java</p>
 * <p>Description: 实现对MySQL对应的实体Task的CURD操作 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class TaskHandlerImpl implements TaskHandler {


    /**
     * 增加 Task
     * @param task
     * @return Long    主键ID
     * @throws Exception
     */
	@Override
	public Long addTask(Task task)  throws Exception {

		Long result = null; 
		SqlSession sqlSession = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.addTask(sqlSession, task);

			if (null != result) { // 添加成功

				sqlSession.commit(true); 

			} else { // 添加失败
				sqlSession.rollback(true); 
			}
		} catch (Exception e) { 
			result = null; 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			 throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 增加 Task
     * @param sqlSession
     * @param task
     * @return Long    主键ID
     * @throws Exception
     */
	@Override
	public Long addTask(SqlSession sqlSession, Task task) throws Exception {

		Long result = null; 
		TaskMapper taskMapper = sqlSession.getMapper(TaskMapper.class);
		if (taskMapper.addTask(task) > -1) { // 添加成功
			result = task.getId(); 
		}
		return result; 

	}


    /**
     * 删除 Task
     * @param task
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteTask(Task task) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteTask(sqlSession, task); 
			if (result > -1) { // 删除成功

				sqlSession.commit(true);

			} else { // 删除失败
				sqlSession.rollback(true); 
			}
		} catch (Exception e) { 
			result = -1; 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			 throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 删除 Task
     * @param sqlSession
     * @param task
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteTask(SqlSession sqlSession, Task task) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(TaskMapper.class).deleteTask(task); 
	}


    /**
     * 根据id删除 Task
     * @param id
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteTaskById(Long id) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteTaskById(sqlSession, id); 
			if (result > -1) { // 删除成功

				sqlSession.commit(true);

			} else { // 删除失败
				sqlSession.rollback(true); 
			}
		} catch (Exception e) { 
			result = -1; 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 根据id删除 Task
     * @param id
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteTaskById(SqlSession sqlSession, Long id) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(TaskMapper.class).deleteTaskById(id); 
	}


    /**
     * 根据taskId删除 Task
     * @param taskId
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteTaskByTaskId(String taskId) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteTaskByTaskId(sqlSession, taskId); 
			if (result > -1) { // 删除成功

				sqlSession.commit(true);

			} else { // 删除失败
				sqlSession.rollback(true); 
			}
		} catch (Exception e) { 
			result = -1; 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 根据taskId删除 Task
     * @param taskId
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteTaskByTaskId(SqlSession sqlSession, String taskId) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(TaskMapper.class).deleteTaskByTaskId(taskId); 
	}
	
	
	/**
	 * 根据instanceId删除 Task
	 * @param instanceId
	 * @return Integer    受影响行数
	 * @throws Exception
	 */
	@Override
	public Integer deleteTaskByInstanceId(String instanceId) throws Exception {
		
		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			
			result = this.deleteTaskByInstanceId(sqlSession, instanceId); 
			if (result > -1) { // 删除成功
				
				sqlSession.commit(true);
				
			} else { // 删除失败
				sqlSession.rollback(true); 
			}
		} catch (Exception e) { 
			result = -1; 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 
		
	}
	
	
	/**
	 * 根据instanceId删除 Task
	 * @param instanceId
	 * @param sqlSession
	 * @return Integer    受影响行数
	 * @throws Exception
	 */
	@Override
	public Integer deleteTaskByInstanceId(SqlSession sqlSession, String instanceId) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(TaskMapper.class).deleteTaskByInstanceId(instanceId); 
	}


    /**
     * 修改 Task
     * @param task
     * @return Boolean    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateTask(Task task) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateTask(sqlSession, task); 
			if (result > -1) { // 修改成功

				sqlSession.commit(true);

			} else { // 修改失败
				sqlSession.rollback(true); 
			}
		} catch (Exception e) { 
			result = -1; 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 修改 Task
     * @param sqlSession
     * @param task
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateTask(SqlSession sqlSession, Task task) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(TaskMapper.class).updateTask(task); 
	}


    /**
     * 根据id修改 Task
     * @param Task
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateTaskById(Task task) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateTaskById(sqlSession, task); 
			if (result > -1) { // 修改成功

				sqlSession.commit(true);

			} else { // 修改失败
				sqlSession.rollback(true); 
			}
		} catch (Exception e) { 
			result = -1; 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 根据id修改 Task
     * @param sqlSession
     * @param task
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateTaskById(SqlSession sqlSession, Task task) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(TaskMapper.class).updateTaskById(task); 
	}


    /**
     * 根据taskId修改 Task
     * @param Task
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateTaskByTaskId(Task task) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateTaskByTaskId(sqlSession, task); 
			if (result > -1) { // 修改成功

				sqlSession.commit(true);

			} else { // 修改失败
				sqlSession.rollback(true); 
			}
		} catch (Exception e) { 
			result = -1; 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 根据taskId修改 Task
     * @param sqlSession
     * @param Task
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateTaskByTaskId(SqlSession sqlSession, Task task) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(TaskMapper.class).updateTaskByTaskId(task); 
	}


    /**
     * 根据id查询单个 Task
     * @param id
     * @return Task    单个Task
     * @throws Exception
     */
	@Override
	public Task findTaskById(Long id) throws Exception {

		Task result = null; 
		SqlSession sqlSession = null;
		TaskMapper taskMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			taskMapper = sqlSession.getMapper(TaskMapper.class);

			result = taskMapper.findTaskById(id); 
		} catch (Exception e) { 
			result = null; 
			throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 根据taskId查询单个 Task
     * @param taskId
     * @return Task    单个Task
     * @throws Exception
     */
	@Override
	public Task findTaskByTaskId(String taskId) throws Exception {

		Task result = null; 
		SqlSession sqlSession = null;
		TaskMapper taskMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			taskMapper = sqlSession.getMapper(TaskMapper.class);

			result = taskMapper.findTaskByTaskId(taskId); 
		} catch (Exception e) { 
			result = null; 
			throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 查询一个 Task
     * @param task
     * @return Task    单个Task
     * @throws Exception
     */
	@Override
	public Task findOneTask(Task task) throws Exception {

		Task result = null; 
		SqlSession sqlSession = null;
		TaskMapper taskMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			taskMapper = sqlSession.getMapper(TaskMapper.class);

			result = taskMapper.findOneTask(task); 
		} catch (Exception e) { 
			result = null; 
			throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 查询统计 Task
     * @param task
     * @return Integer    返回记录数Task
     * @throws Exception
     */
	@Override
	public Integer countTask(Task task) throws Exception {

		Integer result = null; 
		SqlSession sqlSession = null;
		TaskMapper taskMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			taskMapper = sqlSession.getMapper(TaskMapper.class);

			result = taskMapper.countTask(task); 
		} catch (Exception e) { 
			result = null; 
			throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 查询 Task
     * @param task
     * @return List<Task>    Task集合 
     * @throws Exception
     */
	@Override
	public List<Task> findTask(Task task) throws Exception {

		return this.findTask(task, null); 

	}


    /**
     * 查询 Task
     * @param task
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<Task>    Task集合 
     * @throws Exception
     */
	@Override
	public List<Task> findTask(Task task, String order) throws Exception {

		List<Task> result = null; 
		SqlSession sqlSession = null;
		TaskMapper taskMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			taskMapper = sqlSession.getMapper(TaskMapper.class);

			result = taskMapper.findTask(task, order); 
		} catch (Exception e) { 
			result = null; 
			throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 分页查询 Task
     * @param page
     * @param task
     * @return Page<Task>    Task分页结果 
     * @throws Exception 
     */
	@Override
	public Page<Task> findTaskByPage(Page<Task> page, Task task) throws Exception {

		List<Task> result = new ArrayList<Task>(); 
		SqlSession sqlSession = null;
		TaskMapper taskMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			taskMapper = sqlSession.getMapper(TaskMapper.class);

			result = taskMapper.findByPage(page, task); 
			page.setResultsContent(result);
			// sqlSession.commit(true);
		} catch (Exception e) { 
			// if(null != sqlSession)  
				// sqlSession.rollback(true);  
			throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}


}