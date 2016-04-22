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
 * @Title: HistoryInstanceHandlerImpl.java
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
import com.froad.db.mysql.mapper.HistoryInstanceMapper;
import com.froad.logback.LogCvt;
import com.froad.handler.HistoryInstanceHandler;
import com.froad.po.mysql.HistoryInstance;

/**
 * 
 * <p>@Title: HistoryInstanceHandler.java</p>
 * <p>Description: 实现对MySQL对应的实体HistoryInstance的CURD操作 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class HistoryInstanceHandlerImpl implements HistoryInstanceHandler {


    /**
     * 增加 HistoryInstance
     * @param historyInstance
     * @return Long    主键ID
     * @throws Exception
     */
	@Override
	public Long addHistoryInstance(HistoryInstance historyInstance)  throws Exception {

		Long result = null; 
		SqlSession sqlSession = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.addHistoryInstance(sqlSession, historyInstance);

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
     * 增加 HistoryInstance
     * @param sqlSession
     * @param historyInstance
     * @return Long    主键ID
     * @throws Exception
     */
	@Override
	public Long addHistoryInstance(SqlSession sqlSession, HistoryInstance historyInstance) throws Exception {

		Long result = null; 
		HistoryInstanceMapper historyInstanceMapper = sqlSession.getMapper(HistoryInstanceMapper.class);
		if (historyInstanceMapper.addHistoryInstance(historyInstance) > -1) { // 添加成功
			result = historyInstance.getId(); 
		}
		return result; 

	}


    /**
     * 删除 HistoryInstance
     * @param historyInstance
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteHistoryInstance(HistoryInstance historyInstance) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteHistoryInstance(sqlSession, historyInstance); 
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
     * 删除 HistoryInstance
     * @param sqlSession
     * @param historyInstance
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteHistoryInstance(SqlSession sqlSession, HistoryInstance historyInstance) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(HistoryInstanceMapper.class).deleteHistoryInstance(historyInstance); 
	}


    /**
     * 根据id删除 HistoryInstance
     * @param id
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteHistoryInstanceById(Long id) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteHistoryInstanceById(sqlSession, id); 
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
     * 根据id删除 HistoryInstance
     * @param id
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteHistoryInstanceById(SqlSession sqlSession, Long id) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(HistoryInstanceMapper.class).deleteHistoryInstanceById(id); 
	}
	
	/**
	 * 根据instanceId删除 HistoryInstance
	 * @param instanceId
	 * @return Integer    受影响行数
	 * @throws Exception
	 */
	@Override
	public Integer deleteHistoryInstanceByInstanceId(String instanceId) throws Exception {


		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteHistoryInstanceByInstanceId(sqlSession, instanceId); 
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
	 * 根据instanceId删除 HistoryInstance
	 * @param sqlSession
	 * @param instanceId
	 * @return Integer    受影响行数
	 * @throws Exception
	 */
	@Override
	public Integer deleteHistoryInstanceByInstanceId(SqlSession sqlSession, String instanceId) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(HistoryInstanceMapper.class).deleteHistoryInstanceByInstanceId(instanceId); 
	}


    /**
     * 修改 HistoryInstance
     * @param historyInstance
     * @return Boolean    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateHistoryInstance(HistoryInstance historyInstance) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateHistoryInstance(sqlSession, historyInstance); 
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
     * 修改 HistoryInstance
     * @param sqlSession
     * @param historyInstance
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateHistoryInstance(SqlSession sqlSession, HistoryInstance historyInstance) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(HistoryInstanceMapper.class).updateHistoryInstance(historyInstance); 
	}


    /**
     * 根据id修改 HistoryInstance
     * @param HistoryInstance
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateHistoryInstanceById(HistoryInstance historyInstance) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateHistoryInstanceById(sqlSession, historyInstance); 
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
     * 根据id修改 HistoryInstance
     * @param sqlSession
     * @param historyInstance
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateHistoryInstanceById(SqlSession sqlSession, HistoryInstance historyInstance) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(HistoryInstanceMapper.class).updateHistoryInstanceById(historyInstance); 
	}
	
	/**
     * 根据InstanceId修改 HistoryInstance
     * @param HistoryInstance
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateHistoryInstanceByInstanceId(HistoryInstance historyInstance) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateHistoryInstanceByInstanceId(sqlSession, historyInstance); 
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
     * 根据instanceId修改 HistoryInstance
     * @param sqlSession
     * @param historyInstance
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateHistoryInstanceByInstanceId(SqlSession sqlSession, HistoryInstance historyInstance) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(HistoryInstanceMapper.class).updateHistoryInstanceByInstanceId(historyInstance); 
	}


    /**
     * 根据id查询单个 HistoryInstance
     * @param id
     * @return HistoryInstance    单个HistoryInstance
     * @throws Exception
     */
	@Override
	public HistoryInstance findHistoryInstanceById(Long id) throws Exception {

		HistoryInstance result = null; 
		SqlSession sqlSession = null;
		HistoryInstanceMapper historyInstanceMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			historyInstanceMapper = sqlSession.getMapper(HistoryInstanceMapper.class);

			result = historyInstanceMapper.findHistoryInstanceById(id); 
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
     * 根据instanceId查询单个 HistoryInstance
     * @param instanceId
     * @return HistoryInstance    单个HistoryInstance
     * @throws Exception
     */
	@Override
	public HistoryInstance findHistoryInstanceByInstanceId(String instanceId) throws Exception {
		HistoryInstance result = null;
		SqlSession sqlSession = null;
		HistoryInstanceMapper historyInstanceMapper = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			historyInstanceMapper = sqlSession.getMapper(HistoryInstanceMapper.class);

			result = historyInstanceMapper.findHistoryInstanceByInstanceId(instanceId);
		} catch (Exception e) {
			result = null;
			LogCvt.error("根据instanceId查询HistoryInstance失败，原因:" + e.getMessage(), e);
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return result;
	}

    /**
     * 查询一个 HistoryInstance
     * @param historyInstance
     * @return HistoryInstance    单个HistoryInstance
     * @throws Exception
     */
	@Override
	public HistoryInstance findOneHistoryInstance(HistoryInstance historyInstance) throws Exception {

		HistoryInstance result = null; 
		SqlSession sqlSession = null;
		HistoryInstanceMapper historyInstanceMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			historyInstanceMapper = sqlSession.getMapper(HistoryInstanceMapper.class);

			result = historyInstanceMapper.findOneHistoryInstance(historyInstance); 
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
     * 查询统计 HistoryInstance
     * @param historyInstance
     * @return Integer    返回记录数HistoryInstance
     * @throws Exception
     */
	@Override
	public Integer countHistoryInstance(HistoryInstance historyInstance) throws Exception {

		Integer result = null; 
		SqlSession sqlSession = null;
		HistoryInstanceMapper historyInstanceMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			historyInstanceMapper = sqlSession.getMapper(HistoryInstanceMapper.class);

			result = historyInstanceMapper.countHistoryInstance(historyInstance); 
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
     * 查询 HistoryInstance
     * @param historyInstance
     * @return List<HistoryInstance>    HistoryInstance集合 
     * @throws Exception
     */
	@Override
	public List<HistoryInstance> findHistoryInstance(HistoryInstance historyInstance) throws Exception {

		return this.findHistoryInstance(historyInstance, null); 

	}


    /**
     * 查询 HistoryInstance
     * @param historyInstance
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<HistoryInstance>    HistoryInstance集合 
     * @throws Exception
     */
	@Override
	public List<HistoryInstance> findHistoryInstance(HistoryInstance historyInstance, String order) throws Exception {

		List<HistoryInstance> result = null; 
		SqlSession sqlSession = null;
		HistoryInstanceMapper historyInstanceMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			historyInstanceMapper = sqlSession.getMapper(HistoryInstanceMapper.class);

			result = historyInstanceMapper.findHistoryInstance(historyInstance, order); 
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
     * 分页查询 HistoryInstance
     * @param page
     * @param historyInstance
     * @return Page<HistoryInstance>    HistoryInstance分页结果 
     * @throws Exception 
     */
	@Override
	public Page<HistoryInstance> findHistoryInstanceByPage(Page<HistoryInstance> page, HistoryInstance historyInstance) throws Exception {

		List<HistoryInstance> result = new ArrayList<HistoryInstance>(); 
		SqlSession sqlSession = null;
		HistoryInstanceMapper historyInstanceMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			historyInstanceMapper = sqlSession.getMapper(HistoryInstanceMapper.class);

			result = historyInstanceMapper.findByPage(page, historyInstance); 
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