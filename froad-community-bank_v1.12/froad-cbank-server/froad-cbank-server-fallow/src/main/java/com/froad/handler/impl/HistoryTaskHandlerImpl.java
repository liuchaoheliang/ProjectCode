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
 * @Title: HistoryTaskHandlerImpl.java
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
import com.froad.db.mysql.mapper.HistoryTaskMapper;
import com.froad.logback.LogCvt;
import com.froad.handler.HistoryTaskHandler;
import com.froad.po.mysql.HistoryTask;

/**
 * 
 * <p>@Title: HistoryTaskHandler.java</p>
 * <p>Description: 实现对MySQL对应的实体HistoryTask的CURD操作 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class HistoryTaskHandlerImpl implements HistoryTaskHandler {


    /**
     * 增加 HistoryTask
     * @param historyTask
     * @return Long    主键ID
     * @throws Exception
     */
	@Override
	public Long addHistoryTask(HistoryTask historyTask)  throws Exception {

		Long result = null; 
		SqlSession sqlSession = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.addHistoryTask(sqlSession, historyTask);

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
     * 增加 HistoryTask
     * @param sqlSession
     * @param historyTask
     * @return Long    主键ID
     * @throws Exception
     */
	@Override
	public Long addHistoryTask(SqlSession sqlSession, HistoryTask historyTask) throws Exception {

		Long result = null; 
		HistoryTaskMapper historyTaskMapper = sqlSession.getMapper(HistoryTaskMapper.class);
		if (historyTaskMapper.addHistoryTask(historyTask) > -1) { // 添加成功
			result = historyTask.getId(); 
		}
		return result; 

	}


    /**
     * 删除 HistoryTask
     * @param historyTask
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteHistoryTask(HistoryTask historyTask) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteHistoryTask(sqlSession, historyTask); 
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
     * 删除 HistoryTask
     * @param sqlSession
     * @param historyTask
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteHistoryTask(SqlSession sqlSession, HistoryTask historyTask) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(HistoryTaskMapper.class).deleteHistoryTask(historyTask); 
	}


    /**
     * 根据id删除 HistoryTask
     * @param id
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteHistoryTaskById(Long id) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteHistoryTaskById(sqlSession, id); 
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
     * 根据id删除 HistoryTask
     * @param id
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteHistoryTaskById(SqlSession sqlSession, Long id) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(HistoryTaskMapper.class).deleteHistoryTaskById(id); 
	}
	
	/**
	 * 根据taskId删除 HistoryTask
	 * @param taskId
	 * @return Integer    受影响行数
	 * @throws Exception
	 */
	@Override
	public Integer deleteHistoryTaskByTaskId(String taskId) throws Exception {
		
		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			
			result = this.deleteHistoryTaskByTaskId(sqlSession, taskId); 
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
	 * 根据taskId删除 HistoryTask
	 * @param taskId
	 * @param sqlSession
	 * @return Integer    受影响行数
	 * @throws Exception
	 */
	@Override
	public Integer deleteHistoryTaskByTaskId(SqlSession sqlSession, String taskId) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(HistoryTaskMapper.class).deleteHistoryTaskByTaskId(taskId); 
	}
	
	/**
	 * 根据instanceId删除 HistoryTask
	 * @param instanceId
	 * @return Integer    受影响行数
	 * @throws Exception
	 */
	@Override
	public Integer deleteHistoryTaskByInstanceId(String instanceId) throws Exception {
		
		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			
			result = this.deleteHistoryTaskByInstanceId(sqlSession, instanceId); 
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
	 * 根据instanceId删除 HistoryTask
	 * @param instanceId
	 * @param sqlSession
	 * @return Integer    受影响行数
	 * @throws Exception
	 */
	@Override
	public Integer deleteHistoryTaskByInstanceId(SqlSession sqlSession, String instanceId) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(HistoryTaskMapper.class).deleteHistoryTaskByInstanceId(instanceId); 
	}


    /**
     * 修改 HistoryTask
     * @param historyTask
     * @return Boolean    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateHistoryTask(HistoryTask historyTask) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateHistoryTask(sqlSession, historyTask); 
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
     * 修改 HistoryTask
     * @param sqlSession
     * @param historyTask
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateHistoryTask(SqlSession sqlSession, HistoryTask historyTask) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(HistoryTaskMapper.class).updateHistoryTask(historyTask); 
	}


    /**
     * 根据id修改 HistoryTask
     * @param HistoryTask
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateHistoryTaskById(HistoryTask historyTask) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateHistoryTaskById(sqlSession, historyTask); 
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
     * 根据id修改 HistoryTask
     * @param sqlSession
     * @param historyTask
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateHistoryTaskById(SqlSession sqlSession, HistoryTask historyTask) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(HistoryTaskMapper.class).updateHistoryTaskById(historyTask); 
	}
	
	
	/**
	 * 根据taskId修改 HistoryTask
	 * @param HistoryTask
	 * @return Integer    受影响行数
	 * @throws Exception
	 */
	@Override
	public Integer updateHistoryTaskByTaskId(HistoryTask historyTask) throws Exception {
		
		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			
			result = this.updateHistoryTaskByTaskId(sqlSession, historyTask); 
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
	 * 根据taskId修改 HistoryTask
	 * @param sqlSession
	 * @param historyTask
	 * @return Integer    受影响行数
	 * @throws Exception
	 */
	@Override
	public Integer updateHistoryTaskByTaskId(SqlSession sqlSession, HistoryTask historyTask) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(HistoryTaskMapper.class).updateHistoryTaskByTaskId(historyTask); 
	}
	
	
	/**
	 * 根据instanceId修改 HistoryTask
	 * @param HistoryTask
	 * @return Integer    受影响行数
	 * @throws Exception
	 */
	@Override
	public Integer updateHistoryTaskByInstanceId(HistoryTask historyTask) throws Exception {
		
		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			
			result = this.updateHistoryTaskByInstanceId(sqlSession, historyTask); 
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
	 * 根据instanceId修改 HistoryTask
	 * @param sqlSession
	 * @param historyTask
	 * @return Integer    受影响行数
	 * @throws Exception
	 */
	@Override
	public Integer updateHistoryTaskByInstanceId(SqlSession sqlSession, HistoryTask historyTask) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(HistoryTaskMapper.class).updateHistoryTaskByInstanceId(historyTask); 
	}


    /**
     * 根据id查询单个 HistoryTask
     * @param id
     * @return HistoryTask    单个HistoryTask
     * @throws Exception
     */
	@Override
	public HistoryTask findHistoryTaskById(Long id) throws Exception {

		HistoryTask result = null; 
		SqlSession sqlSession = null;
		HistoryTaskMapper historyTaskMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			historyTaskMapper = sqlSession.getMapper(HistoryTaskMapper.class);

			result = historyTaskMapper.findHistoryTaskById(id); 
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
     * 查询一个 HistoryTask
     * @param historyTask
     * @return HistoryTask    单个HistoryTask
     * @throws Exception
     */
	@Override
	public HistoryTask findOneHistoryTask(HistoryTask historyTask) throws Exception {

		HistoryTask result = null; 
		SqlSession sqlSession = null;
		HistoryTaskMapper historyTaskMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			historyTaskMapper = sqlSession.getMapper(HistoryTaskMapper.class);

			result = historyTaskMapper.findOneHistoryTask(historyTask); 
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
     * 查询统计 HistoryTask
     * @param historyTask
     * @return Integer    返回记录数HistoryTask
     * @throws Exception
     */
	@Override
	public Integer countHistoryTask(HistoryTask historyTask) throws Exception {

		Integer result = null; 
		SqlSession sqlSession = null;
		HistoryTaskMapper historyTaskMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			historyTaskMapper = sqlSession.getMapper(HistoryTaskMapper.class);

			result = historyTaskMapper.countHistoryTask(historyTask); 
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
     * 查询 HistoryTask
     * @param historyTask
     * @return List<HistoryTask>    HistoryTask集合 
     * @throws Exception
     */
	@Override
	public List<HistoryTask> findHistoryTask(HistoryTask historyTask) throws Exception {

		return this.findHistoryTask(historyTask, null); 

	}


    /**
     * 查询 HistoryTask
     * @param historyTask
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<HistoryTask>    HistoryTask集合 
     * @throws Exception
     */
	@Override
	public List<HistoryTask> findHistoryTask(HistoryTask historyTask, String order) throws Exception {

		List<HistoryTask> result = null; 
		SqlSession sqlSession = null;
		HistoryTaskMapper historyTaskMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			historyTaskMapper = sqlSession.getMapper(HistoryTaskMapper.class);

			result = historyTaskMapper.findHistoryTask(historyTask, order); 
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
     * 查询 执行节点HistoryTask
     * @param historyTask
     * @return List<HistoryTask>    返回结果集
     */
	public List<HistoryTask> findHistoryTaskOfExecute(HistoryTask historyTask) throws Exception{

		List<HistoryTask> result = null; 
		SqlSession sqlSession = null;
		HistoryTaskMapper historyTaskMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			historyTaskMapper = sqlSession.getMapper(HistoryTaskMapper.class);

			result = historyTaskMapper.findHistoryTaskOfExecute(historyTask); 
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
     * 分页查询 HistoryTask
     * @param page
     * @param historyTask
     * @return Page<HistoryTask>    HistoryTask分页结果 
     * @throws Exception 
     */
	@Override
	public Page<HistoryTask> findHistoryTaskByPage(Page<HistoryTask> page, HistoryTask historyTask) throws Exception {

		List<HistoryTask> result = new ArrayList<HistoryTask>(); 
		SqlSession sqlSession = null;
		HistoryTaskMapper historyTaskMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			historyTaskMapper = sqlSession.getMapper(HistoryTaskMapper.class);

			result = historyTaskMapper.findByPage(page, historyTask); 
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