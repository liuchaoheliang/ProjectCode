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
 * @Title: TaskActorHandlerImpl.java
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
import com.froad.db.mysql.mapper.TaskActorMapper;
import com.froad.logback.LogCvt;
import com.froad.handler.TaskActorHandler;
import com.froad.po.mysql.TaskActor;

/**
 * 
 * <p>@Title: TaskActorHandler.java</p>
 * <p>Description: 实现对MySQL对应的实体TaskActor的CURD操作 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class TaskActorHandlerImpl implements TaskActorHandler {


    /**
     * 增加 TaskActor
     * @param taskActor
     * @return String    主键ID
     * @throws Exception
     */
	@Override
	public String addTaskActor(TaskActor taskActor)  throws Exception {

		String result = null; 
		SqlSession sqlSession = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.addTaskActor(sqlSession, taskActor);

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
     * 增加 TaskActor
     * @param sqlSession
     * @param taskActor
     * @return String    主键ID
     * @throws Exception
     */
	@Override
	public String addTaskActor(SqlSession sqlSession, TaskActor taskActor) throws Exception {

		String result = null; 
		TaskActorMapper taskActorMapper = sqlSession.getMapper(TaskActorMapper.class);
		if (taskActorMapper.addTaskActor(taskActor) > -1) { // 添加成功
			result = taskActor.getTaskId(); 
		}
		return result; 

	}


    /**
     * 删除 TaskActor
     * @param taskActor
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteTaskActor(TaskActor taskActor) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteTaskActor(sqlSession, taskActor); 
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
     * 删除 TaskActor
     * @param sqlSession
     * @param taskActor
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteTaskActor(SqlSession sqlSession, TaskActor taskActor) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(TaskActorMapper.class).deleteTaskActor(taskActor); 
	}


    /**
     * 根据taskId删除 TaskActor
     * @param taskId
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteTaskActorByTaskId(String taskId) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteTaskActorByTaskId(sqlSession, taskId); 
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
     * 根据taskId删除 TaskActor
     * @param taskId
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteTaskActorByTaskId(SqlSession sqlSession, String taskId) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(TaskActorMapper.class).deleteTaskActorByTaskId(taskId); 
	}


    /**
     * 修改 TaskActor
     * @param taskActor
     * @return Boolean    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateTaskActor(TaskActor taskActor) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateTaskActor(sqlSession, taskActor); 
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
     * 修改 TaskActor
     * @param sqlSession
     * @param taskActor
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateTaskActor(SqlSession sqlSession, TaskActor taskActor) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(TaskActorMapper.class).updateTaskActor(taskActor); 
	}


    /**
     * 根据taskId修改 TaskActor
     * @param TaskActor
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateTaskActorByTaskId(TaskActor taskActor) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateTaskActorByTaskId(sqlSession, taskActor); 
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
     * 根据taskId修改 TaskActor
     * @param sqlSession
     * @param taskActor
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateTaskActorByTaskId(SqlSession sqlSession, TaskActor taskActor) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(TaskActorMapper.class).updateTaskActorByTaskId(taskActor); 
	}


    /**
     * 根据taskId查询单个 TaskActor
     * @param taskId
     * @return TaskActor    单个TaskActor
     * @throws Exception
     */
	@Override
	public TaskActor findTaskActorByTaskId(String taskId) throws Exception {

		TaskActor result = null; 
		SqlSession sqlSession = null;
		TaskActorMapper taskActorMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			taskActorMapper = sqlSession.getMapper(TaskActorMapper.class);

			result = taskActorMapper.findTaskActorByTaskId(taskId); 
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
     * 查询一个 TaskActor
     * @param taskActor
     * @return TaskActor    单个TaskActor
     * @throws Exception
     */
	@Override
	public TaskActor findOneTaskActor(TaskActor taskActor) throws Exception {

		TaskActor result = null; 
		SqlSession sqlSession = null;
		TaskActorMapper taskActorMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			taskActorMapper = sqlSession.getMapper(TaskActorMapper.class);

			result = taskActorMapper.findOneTaskActor(taskActor); 
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
     * 查询统计 TaskActor
     * @param taskActor
     * @return Integer    返回记录数TaskActor
     * @throws Exception
     */
	@Override
	public Integer countTaskActor(TaskActor taskActor) throws Exception {

		Integer result = null; 
		SqlSession sqlSession = null;
		TaskActorMapper taskActorMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			taskActorMapper = sqlSession.getMapper(TaskActorMapper.class);

			result = taskActorMapper.countTaskActor(taskActor); 
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
     * 查询 TaskActor
     * @param taskActor
     * @return List<TaskActor>    TaskActor集合 
     * @throws Exception
     */
	@Override
	public List<TaskActor> findTaskActor(TaskActor taskActor) throws Exception {

		return this.findTaskActor(taskActor, null); 

	}


    /**
     * 查询 TaskActor
     * @param taskActor
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<TaskActor>    TaskActor集合 
     * @throws Exception
     */
	@Override
	public List<TaskActor> findTaskActor(TaskActor taskActor, String order) throws Exception {

		List<TaskActor> result = null; 
		SqlSession sqlSession = null;
		TaskActorMapper taskActorMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			taskActorMapper = sqlSession.getMapper(TaskActorMapper.class);

			result = taskActorMapper.findTaskActor(taskActor, order); 
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
     * 分页查询 TaskActor
     * @param page
     * @param taskActor
     * @return Page<TaskActor>    TaskActor分页结果 
     * @throws Exception 
     */
	@Override
	public Page<TaskActor> findTaskActorByPage(Page<TaskActor> page, TaskActor taskActor) throws Exception {

		List<TaskActor> result = new ArrayList<TaskActor>(); 
		SqlSession sqlSession = null;
		TaskActorMapper taskActorMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			taskActorMapper = sqlSession.getMapper(TaskActorMapper.class);

			result = taskActorMapper.findByPage(page, taskActor); 
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