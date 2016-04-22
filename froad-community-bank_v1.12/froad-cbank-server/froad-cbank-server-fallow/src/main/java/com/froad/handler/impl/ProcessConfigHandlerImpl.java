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
 * @Title: ProcessConfigHandlerImpl.java
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
import com.froad.db.mysql.mapper.ProcessConfigMapper;
import com.froad.logback.LogCvt;
import com.froad.handler.ProcessConfigHandler;
import com.froad.po.mysql.ProcessConfig;

/**
 * 
 * <p>@Title: ProcessConfigHandler.java</p>
 * <p>Description: 实现对MySQL对应的实体ProcessConfig的CURD操作 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class ProcessConfigHandlerImpl implements ProcessConfigHandler {


    /**
     * 增加 ProcessConfig
     * @param processConfig
     * @return Long    主键ID
     * @throws Exception
     */
	@Override
	public Long addProcessConfig(ProcessConfig processConfig)  throws Exception {

		Long result = null; 
		SqlSession sqlSession = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.addProcessConfig(sqlSession, processConfig);

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
     * 增加 ProcessConfig
     * @param sqlSession
     * @param processConfig
     * @return Long    主键ID
     * @throws Exception
     */
	@Override
	public Long addProcessConfig(SqlSession sqlSession, ProcessConfig processConfig) throws Exception {

		Long result = null; 
		ProcessConfigMapper processConfigMapper = sqlSession.getMapper(ProcessConfigMapper.class);
		if (processConfigMapper.addProcessConfig(processConfig) > -1) { // 添加成功
			result = processConfig.getId(); 
		}
		return result; 

	}


    /**
     * 删除 ProcessConfig
     * @param processConfig
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteProcessConfig(ProcessConfig processConfig) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteProcessConfig(sqlSession, processConfig); 
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
     * 删除 ProcessConfig
     * @param sqlSession
     * @param processConfig
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteProcessConfig(SqlSession sqlSession, ProcessConfig processConfig) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(ProcessConfigMapper.class).deleteProcessConfig(processConfig); 
	}


    /**
     * 根据id删除 ProcessConfig
     * @param id
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteProcessConfigById(Long id) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteProcessConfigById(sqlSession, id); 
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
     * 根据id删除 ProcessConfig
     * @param id
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteProcessConfigById(SqlSession sqlSession, Long id) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(ProcessConfigMapper.class).deleteProcessConfigById(id); 
	}


    /**
     * 修改 ProcessConfig
     * @param processConfig
     * @return Boolean    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateProcessConfig(ProcessConfig processConfig) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateProcessConfig(sqlSession, processConfig); 
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
     * 修改 ProcessConfig
     * @param sqlSession
     * @param processConfig
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateProcessConfig(SqlSession sqlSession, ProcessConfig processConfig) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(ProcessConfigMapper.class).updateProcessConfig(processConfig); 
	}


    /**
     * 根据id修改 ProcessConfig
     * @param ProcessConfig
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateProcessConfigById(ProcessConfig processConfig) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateProcessConfigById(sqlSession, processConfig); 
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
     * 根据id修改 ProcessConfig
     * @param sqlSession
     * @param processConfig
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateProcessConfigById(SqlSession sqlSession, ProcessConfig processConfig) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(ProcessConfigMapper.class).updateProcessConfigById(processConfig); 
	}


    /**
     * 根据id查询单个 ProcessConfig
     * @param id
     * @return ProcessConfig    单个ProcessConfig
     * @throws Exception
     */
	@Override
	public ProcessConfig findProcessConfigById(Long id) throws Exception {

		ProcessConfig result = null; 
		SqlSession sqlSession = null;
		ProcessConfigMapper processConfigMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			processConfigMapper = sqlSession.getMapper(ProcessConfigMapper.class);

			result = processConfigMapper.findProcessConfigById(id); 
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
     * 查询一个 ProcessConfig
     * @param processConfig
     * @return ProcessConfig    单个ProcessConfig
     * @throws Exception
     */
	@Override
	public ProcessConfig findOneProcessConfig(ProcessConfig processConfig) throws Exception {

		ProcessConfig result = null; 
		SqlSession sqlSession = null;
		ProcessConfigMapper processConfigMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			processConfigMapper = sqlSession.getMapper(ProcessConfigMapper.class);

			result = processConfigMapper.findOneProcessConfig(processConfig); 
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
     * 查询统计 ProcessConfig
     * @param processConfig
     * @return Integer    返回记录数ProcessConfig
     * @throws Exception
     */
	@Override
	public Integer countProcessConfig(ProcessConfig processConfig) throws Exception {

		Integer result = null; 
		SqlSession sqlSession = null;
		ProcessConfigMapper processConfigMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			processConfigMapper = sqlSession.getMapper(ProcessConfigMapper.class);

			result = processConfigMapper.countProcessConfig(processConfig); 
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
     * 查询 ProcessConfig
     * @param processConfig
     * @return List<ProcessConfig>    ProcessConfig集合 
     * @throws Exception
     */
	@Override
	public List<ProcessConfig> findProcessConfig(ProcessConfig processConfig) throws Exception {

		return this.findProcessConfig(processConfig, null); 

	}


    /**
     * 查询 ProcessConfig
     * @param processConfig
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<ProcessConfig>    ProcessConfig集合 
     * @throws Exception
     */
	@Override
	public List<ProcessConfig> findProcessConfig(ProcessConfig processConfig, String order) throws Exception {

		List<ProcessConfig> result = null; 
		SqlSession sqlSession = null;
		ProcessConfigMapper processConfigMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			processConfigMapper = sqlSession.getMapper(ProcessConfigMapper.class);

			result = processConfigMapper.findProcessConfig(processConfig, order); 
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
     * 分页查询 ProcessConfig
     * @param page
     * @param processConfig
     * @return Page<ProcessConfig>    ProcessConfig分页结果 
     * @throws Exception 
     */
	@Override
	public Page<ProcessConfig> findProcessConfigByPage(Page<ProcessConfig> page, ProcessConfig processConfig) throws Exception {

		List<ProcessConfig> result = new ArrayList<ProcessConfig>(); 
		SqlSession sqlSession = null;
		ProcessConfigMapper processConfigMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			processConfigMapper = sqlSession.getMapper(ProcessConfigMapper.class);

			result = processConfigMapper.findByPage(page, processConfig); 
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