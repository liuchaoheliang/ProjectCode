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
 * @Title: HistoryTaskActorHandlerImpl.java
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
import com.froad.db.mysql.mapper.HistoryTaskActorMapper;
import com.froad.logback.LogCvt;
import com.froad.handler.HistoryTaskActorHandler;
import com.froad.po.mysql.HistoryTaskActor;

/**
 * 
 * <p>@Title: HistoryTaskActorHandler.java</p>
 * <p>Description: 实现对MySQL对应的实体HistoryTaskActor的CURD操作 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class HistoryTaskActorHandlerImpl implements HistoryTaskActorHandler {


    /**
     * 增加 HistoryTaskActor
     * @param historyTaskActor
     * @return String    主键ID
     * @throws Exception
     */
	@Override
	public String addHistoryTaskActor(HistoryTaskActor historyTaskActor)  throws Exception {

		String result = null; 
		SqlSession sqlSession = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.addHistoryTaskActor(sqlSession, historyTaskActor);

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
     * 增加 HistoryTaskActor
     * @param sqlSession
     * @param historyTaskActor
     * @return String    主键ID
     * @throws Exception
     */
	@Override
	public String addHistoryTaskActor(SqlSession sqlSession, HistoryTaskActor historyTaskActor) throws Exception {

		String result = null; 
		HistoryTaskActorMapper historyTaskActorMapper = sqlSession.getMapper(HistoryTaskActorMapper.class);
		if (historyTaskActorMapper.addHistoryTaskActor(historyTaskActor) > -1) { // 添加成功
			result = historyTaskActor.getTaskId(); 
		}
		return result; 

	}


    /**
     * 删除 HistoryTaskActor
     * @param historyTaskActor
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteHistoryTaskActor(HistoryTaskActor historyTaskActor) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteHistoryTaskActor(sqlSession, historyTaskActor); 
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
     * 删除 HistoryTaskActor
     * @param sqlSession
     * @param historyTaskActor
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteHistoryTaskActor(SqlSession sqlSession, HistoryTaskActor historyTaskActor) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(HistoryTaskActorMapper.class).deleteHistoryTaskActor(historyTaskActor); 
	}


    /**
     * 根据taskId删除 HistoryTaskActor
     * @param taskId
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteHistoryTaskActorByTaskId(String taskId) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteHistoryTaskActorByTaskId(sqlSession, taskId); 
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
     * 根据taskId删除 HistoryTaskActor
     * @param taskId
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteHistoryTaskActorByTaskId(SqlSession sqlSession, String taskId) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(HistoryTaskActorMapper.class).deleteHistoryTaskActorByTaskId(taskId); 
	}


    /**
     * 修改 HistoryTaskActor
     * @param historyTaskActor
     * @return Boolean    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateHistoryTaskActor(HistoryTaskActor historyTaskActor) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateHistoryTaskActor(sqlSession, historyTaskActor); 
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
     * 修改 HistoryTaskActor
     * @param sqlSession
     * @param historyTaskActor
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateHistoryTaskActor(SqlSession sqlSession, HistoryTaskActor historyTaskActor) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(HistoryTaskActorMapper.class).updateHistoryTaskActor(historyTaskActor); 
	}


    /**
     * 根据taskId修改 HistoryTaskActor
     * @param HistoryTaskActor
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateHistoryTaskActorByTaskId(HistoryTaskActor historyTaskActor) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateHistoryTaskActorByTaskId(sqlSession, historyTaskActor); 
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
     * 根据taskId修改 HistoryTaskActor
     * @param sqlSession
     * @param historyTaskActor
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateHistoryTaskActorByTaskId(SqlSession sqlSession, HistoryTaskActor historyTaskActor) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(HistoryTaskActorMapper.class).updateHistoryTaskActorByTaskId(historyTaskActor); 
	}


    /**
     * 根据taskId查询单个 HistoryTaskActor
     * @param taskId
     * @return HistoryTaskActor    单个HistoryTaskActor
     * @throws Exception
     */
	@Override
	public HistoryTaskActor findHistoryTaskActorByTaskId(String taskId) throws Exception {

		HistoryTaskActor result = null; 
		SqlSession sqlSession = null;
		HistoryTaskActorMapper historyTaskActorMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			historyTaskActorMapper = sqlSession.getMapper(HistoryTaskActorMapper.class);

			result = historyTaskActorMapper.findHistoryTaskActorByTaskId(taskId); 
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
     * 查询一个 HistoryTaskActor
     * @param historyTaskActor
     * @return HistoryTaskActor    单个HistoryTaskActor
     * @throws Exception
     */
	@Override
	public HistoryTaskActor findOneHistoryTaskActor(HistoryTaskActor historyTaskActor) throws Exception {

		HistoryTaskActor result = null; 
		SqlSession sqlSession = null;
		HistoryTaskActorMapper historyTaskActorMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			historyTaskActorMapper = sqlSession.getMapper(HistoryTaskActorMapper.class);

			result = historyTaskActorMapper.findOneHistoryTaskActor(historyTaskActor); 
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
     * 查询统计 HistoryTaskActor
     * @param historyTaskActor
     * @return Integer    返回记录数HistoryTaskActor
     * @throws Exception
     */
	@Override
	public Integer countHistoryTaskActor(HistoryTaskActor historyTaskActor) throws Exception {

		Integer result = null; 
		SqlSession sqlSession = null;
		HistoryTaskActorMapper historyTaskActorMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			historyTaskActorMapper = sqlSession.getMapper(HistoryTaskActorMapper.class);

			result = historyTaskActorMapper.countHistoryTaskActor(historyTaskActor); 
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
     * 查询 HistoryTaskActor
     * @param historyTaskActor
     * @return List<HistoryTaskActor>    HistoryTaskActor集合 
     * @throws Exception
     */
	@Override
	public List<HistoryTaskActor> findHistoryTaskActor(HistoryTaskActor historyTaskActor) throws Exception {

		return this.findHistoryTaskActor(historyTaskActor, null); 

	}


    /**
     * 查询 HistoryTaskActor
     * @param historyTaskActor
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<HistoryTaskActor>    HistoryTaskActor集合 
     * @throws Exception
     */
	@Override
	public List<HistoryTaskActor> findHistoryTaskActor(HistoryTaskActor historyTaskActor, String order) throws Exception {

		List<HistoryTaskActor> result = null; 
		SqlSession sqlSession = null;
		HistoryTaskActorMapper historyTaskActorMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			historyTaskActorMapper = sqlSession.getMapper(HistoryTaskActorMapper.class);

			result = historyTaskActorMapper.findHistoryTaskActor(historyTaskActor, order); 
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
     * 分页查询 HistoryTaskActor
     * @param page
     * @param historyTaskActor
     * @return Page<HistoryTaskActor>    HistoryTaskActor分页结果 
     * @throws Exception 
     */
	@Override
	public Page<HistoryTaskActor> findHistoryTaskActorByPage(Page<HistoryTaskActor> page, HistoryTaskActor historyTaskActor) throws Exception {

		List<HistoryTaskActor> result = new ArrayList<HistoryTaskActor>(); 
		SqlSession sqlSession = null;
		HistoryTaskActorMapper historyTaskActorMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			historyTaskActorMapper = sqlSession.getMapper(HistoryTaskActorMapper.class);

			result = historyTaskActorMapper.findByPage(page, historyTaskActor); 
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