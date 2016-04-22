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
 * @Title: ProcessHandlerImpl.java
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
import com.froad.db.mysql.mapper.ProcessMapper;
import com.froad.db.redis.ProcessRedis;
import com.froad.logback.LogCvt;
import com.froad.handler.ProcessHandler;
import com.froad.po.mysql.Process;
import com.froad.util.RedisKeyUtil;

/**
 * 
 * <p>
 * 
 * @Title: ProcessHandler.java
 *         </p>
 *         <p>
 *         Description: 实现对MySQL对应的实体Process的CURD操作
 *         </p>
 * 
 * @author f-road
 * @version 1.0
 * @created 2015年3月17日
 */
public class ProcessHandlerImpl implements ProcessHandler {

	/**
	 * 增加 Process
	 * 
	 * @param process
	 * @return Long 主键ID
	 * @throws Exception
	 */
	@Override
	public Long addProcess(Process process) throws Exception {

		Long result = null;
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.addProcess(sqlSession, process);

			if (null != result) { // 添加成功

				sqlSession.commit(true);

			} else { // 添加失败
				sqlSession.rollback(true);
			}
		} catch (Exception e) {
			result = null;
			if (null != sqlSession)
				sqlSession.rollback(true);
			throw e;
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return result;

	}

	/**
	 * 增加 Process
	 * 
	 * @param sqlSession
	 * @param process
	 * @return Long 主键ID
	 * @throws Exception
	 */
	@Override
	public Long addProcess(SqlSession sqlSession, Process process) throws Exception {

		Long result = null;
		ProcessMapper processMapper = sqlSession.getMapper(ProcessMapper.class);
		if (processMapper.addProcess(process) > -1) { // 添加成功
			result = process.getId();
		}
		return result;

	}

	/**
	 * 删除 Process
	 * 
	 * @param process
	 * @return Integer 受影响行数
	 * @throws Exception
	 */
	@Override
	public Integer deleteProcess(Process process) throws Exception {

		Integer result = -1;
		SqlSession sqlSession = null;
		try {
			/********************** 操作MySQL数据库 **********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteProcess(sqlSession, process);
			if (result > -1) { // 删除成功

				sqlSession.commit(true);

			} else { // 删除失败
				sqlSession.rollback(true);
			}
		} catch (Exception e) {
			result = -1;
			if (null != sqlSession)
				sqlSession.rollback(true);
			throw e;
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return result;

	}

	/**
	 * 删除 Process
	 * 
	 * @param sqlSession
	 * @param process
	 * @return Integer 受影响行数
	 * @throws Exception
	 */
	@Override
	public Integer deleteProcess(SqlSession sqlSession, Process process) throws Exception {
		/********************** 操作MySQL数据库 **********************/
		return sqlSession.getMapper(ProcessMapper.class).deleteProcess(process);
	}

	/**
	 * 根据id删除 Process
	 * 
	 * @param id
	 * @return Integer 受影响行数
	 * @throws Exception
	 */
	@Override
	public Integer deleteProcessById(Long id) throws Exception {

		Integer result = -1;
		SqlSession sqlSession = null;
		try {
			/********************** 操作MySQL数据库 **********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteProcessById(sqlSession, id);
			if (result > -1) { // 删除成功

				sqlSession.commit(true);

			} else { // 删除失败
				sqlSession.rollback(true);
			}
		} catch (Exception e) {
			result = -1;
			if (null != sqlSession)
				sqlSession.rollback(true);
			throw e;
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return result;

	}

	/**
	 * 根据id删除 Process
	 * 
	 * @param id
	 * @param sqlSession
	 * @return Integer 受影响行数
	 * @throws Exception
	 */
	@Override
	public Integer deleteProcessById(SqlSession sqlSession, Long id) throws Exception {
		/********************** 操作MySQL数据库 **********************/
		return sqlSession.getMapper(ProcessMapper.class).deleteProcessById(id);
	}

	/**
	 * 根据processId删除 Process
	 * 
	 * @param processId
	 * @return Integer 受影响行数
	 * @throws Exception
	 */
	@Override
	public Integer deleteProcessByProcessId(String processId) throws Exception {

		Integer result = -1;
		SqlSession sqlSession = null;
		try {
			/********************** 操作MySQL数据库 **********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteProcessByProcessId(sqlSession, processId);
			if (result > -1) { // 删除成功

				sqlSession.commit(true);

			} else { // 删除失败
				sqlSession.rollback(true);
			}
		} catch (Exception e) {
			result = -1;
			if (null != sqlSession)
				sqlSession.rollback(true);
			throw e;
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return result;

	}

	/**
	 * 根据processId删除 Process
	 * 
	 * @param processId
	 * @param sqlSession
	 * @return Integer 受影响行数
	 * @throws Exception
	 */
	@Override
	public Integer deleteProcessByProcessId(SqlSession sqlSession, String processId) throws Exception {
		/********************** 操作MySQL数据库 **********************/
		return sqlSession.getMapper(ProcessMapper.class).deleteProcessByProcessId(processId);
	}

	/**
	 * 修改 Process
	 * 
	 * @param process
	 * @return Boolean 受影响行数
	 * @throws Exception
	 */
	@Override
	public Integer updateProcess(Process process) throws Exception {

		Integer result = -1;
		SqlSession sqlSession = null;
		try {
			/********************** 操作MySQL数据库 **********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateProcess(sqlSession, process);
			if (result > -1) { // 修改成功

				sqlSession.commit(true);

			} else { // 修改失败
				sqlSession.rollback(true);
			}
		} catch (Exception e) {
			result = -1;
			if (null != sqlSession)
				sqlSession.rollback(true);
			throw e;
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return result;

	}

	/**
	 * 修改 Process
	 * 
	 * @param sqlSession
	 * @param process
	 * @return Integer 受影响行数
	 * @throws Exception
	 */
	@Override
	public Integer updateProcess(SqlSession sqlSession, Process process) throws Exception {
		/********************** 操作MySQL数据库 **********************/
		return sqlSession.getMapper(ProcessMapper.class).updateProcess(process);
	}

	/**
	 * 根据id修改 Process
	 * 
	 * @param Process
	 * @return Integer 受影响行数
	 * @throws Exception
	 */
	@Override
	public Integer updateProcessById(Process process) throws Exception {

		Integer result = -1;
		SqlSession sqlSession = null;
		try {
			/********************** 操作MySQL数据库 **********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateProcessById(sqlSession, process);
			if (result > -1) { // 修改成功

				sqlSession.commit(true);

			} else { // 修改失败
				sqlSession.rollback(true);
			}
		} catch (Exception e) {
			result = -1;
			if (null != sqlSession)
				sqlSession.rollback(true);
			throw e;
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return result;

	}

	/**
	 * 根据id修改 Process
	 * 
	 * @param sqlSession
	 * @param process
	 * @return Integer 受影响行数
	 * @throws Exception
	 */
	@Override
	public Integer updateProcessById(SqlSession sqlSession, Process process) throws Exception {
		/********************** 操作MySQL数据库 **********************/
		return sqlSession.getMapper(ProcessMapper.class).updateProcessById(process);
	}

	/**
	 * 根据processId修改 Process
	 * 
	 * @param Process
	 * @return Integer 受影响行数
	 * @throws Exception
	 */
	@Override
	public Integer updateProcessByProcessId(Process process) throws Exception {

		Integer result = -1;
		SqlSession sqlSession = null;
		try {
			/********************** 操作MySQL数据库 **********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateProcessByProcessId(sqlSession, process);
			if (result > -1) { // 修改成功

				sqlSession.commit(true);

			} else { // 修改失败
				sqlSession.rollback(true);
			}
		} catch (Exception e) {
			result = -1;
			if (null != sqlSession)
				sqlSession.rollback(true);
			throw e;
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return result;

	}

	/**
	 * 根据processId修改 Process
	 * 
	 * @param sqlSession
	 * @param Process
	 * @return Integer 受影响行数
	 * @throws Exception
	 */
	@Override
	public Integer updateProcessByProcessId(SqlSession sqlSession, Process process) throws Exception {
		/********************** 操作MySQL数据库 **********************/
		return sqlSession.getMapper(ProcessMapper.class).updateProcessByProcessId(process);
	}

	/**
	 * 根据id查询单个 Process
	 * 
	 * @param id
	 * @return Process 单个Process
	 * @throws Exception
	 */
	@Override
	public Process findProcessById(Long id) throws Exception {

		Process result = null;
		SqlSession sqlSession = null;
		ProcessMapper processMapper = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			processMapper = sqlSession.getMapper(ProcessMapper.class);

			result = processMapper.findProcessById(id);
		} catch (Exception e) {
			result = null;
			throw e;
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return result;

	}

	/**
	 * 根据processId查询单个 Process
	 * 
	 * @param processId
	 * @return Process 单个Process
	 * @throws Exception
	 */
	@Override
	public Process findProcessByProcessId(String processId) throws Exception {

		Process result = null;
		SqlSession sqlSession = null;
		ProcessMapper processMapper = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			processMapper = sqlSession.getMapper(ProcessMapper.class);

			result = processMapper.findProcessByProcessId(processId);
		} catch (Exception e) {
			result = null;
			throw e;
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return result;

	}

	/**
	 * 根据clientId,processId查询单个 Process
	 * 
	 * @param clientId
	 * @param processId
	 * @return Process 单个Process
	 * @throws Exception
	 */
	@Override
	public Process findOneProcess(String clientId, String processId) throws Exception {
		Process process = ProcessRedis.get_cbbank_process(clientId, processId);
		if (null == process) {
			LogCvt.warn("客户端id:" + clientId + ", 审核流程id:" + processId + "缓存为空!");
			Process processQuery = new Process(); // 查询条件
			processQuery.setClientId(clientId);
			processQuery.setProcessId(processId);
			
			process = this.findOneProcess(processQuery);
			if (null != process)
				if (ProcessRedis.set_cbbank_process(clientId, processId, process))
					LogCvt.info("客户端id:" + clientId + ", 审核流程id:" + processId + "重建缓存成功!");
		}
		return process;
	}

	/**
	 * 查询一个 Process
	 * 
	 * @param process
	 * @return Process 单个Process
	 * @throws Exception
	 */
	@Override
	public Process findOneProcess(Process process) throws Exception {

		Process result = null;
		SqlSession sqlSession = null;
		ProcessMapper processMapper = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			processMapper = sqlSession.getMapper(ProcessMapper.class);

			result = processMapper.findOneProcess(process);
		} catch (Exception e) {
			result = null;
			throw e;
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return result;

	}

	/**
	 * 查询统计 Process
	 * 
	 * @param process
	 * @return Integer 返回记录数Process
	 * @throws Exception
	 */
	@Override
	public Integer countProcess(Process process) throws Exception {

		Integer result = null;
		SqlSession sqlSession = null;
		ProcessMapper processMapper = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			processMapper = sqlSession.getMapper(ProcessMapper.class);

			result = processMapper.countProcess(process);
		} catch (Exception e) {
			result = null;
			throw e;
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return result;

	}

	/**
	 * 查询 Process
	 * 
	 * @param process
	 * @return List<Process> Process集合
	 * @throws Exception
	 */
	@Override
	public List<Process> findProcess(Process process) throws Exception {

		return this.findProcess(process, null);

	}

	/**
	 * 查询 Process
	 * 
	 * @param process
	 * @param order
	 *            排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
	 * @return List<Process> Process集合
	 * @throws Exception
	 */
	@Override
	public List<Process> findProcess(Process process, String order) throws Exception {

		List<Process> result = null;
		SqlSession sqlSession = null;
		ProcessMapper processMapper = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			processMapper = sqlSession.getMapper(ProcessMapper.class);

			result = processMapper.findProcess(process, order);
		} catch (Exception e) {
			result = null;
			throw e;
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return result;

	}

	/**
	 * 分页查询 Process
	 * 
	 * @param page
	 * @param process
	 * @return Page<Process> Process分页结果
	 * @throws Exception
	 */
	@Override
	public Page<Process> findProcessByPage(Page<Process> page, Process process) throws Exception {

		List<Process> result = new ArrayList<Process>();
		SqlSession sqlSession = null;
		ProcessMapper processMapper = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			processMapper = sqlSession.getMapper(ProcessMapper.class);

			result = processMapper.findByPage(page, process);
			page.setResultsContent(result);
			// sqlSession.commit(true);
		} catch (Exception e) {
			// if(null != sqlSession)
			// sqlSession.rollback(true);
			throw e;
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return page;

	}

}