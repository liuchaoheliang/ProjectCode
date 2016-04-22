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
 * @Title: ProcessNodeHandlerImpl.java
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
import com.froad.db.mysql.mapper.ProcessNodeMapper;
import com.froad.db.redis.ProcessNodeRedis;
import com.froad.handler.ProcessNodeHandler;
import com.froad.logback.LogCvt;
import com.froad.po.mysql.ProcessNode;

/**
 * 
 * <p>@Title: ProcessNodeHandler.java</p>
 * <p>Description: 实现对MySQL对应的实体ProcessNode的CURD操作 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class ProcessNodeHandlerImpl implements ProcessNodeHandler {


    /**
     * 增加 ProcessNode
     * @param processNode
     * @return Long    主键ID
     * @throws Exception
     */
	@Override
	public Long addProcessNode(ProcessNode processNode)  throws Exception {

		Long result = null; 
		SqlSession sqlSession = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.addProcessNode(sqlSession, processNode);

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
     * 增加 ProcessNode
     * @param sqlSession
     * @param processNode
     * @return Long    主键ID
     * @throws Exception
     */
	@Override
	public Long addProcessNode(SqlSession sqlSession, ProcessNode processNode) throws Exception {

		Long result = null; 
		ProcessNodeMapper processNodeMapper = sqlSession.getMapper(ProcessNodeMapper.class);
		if (processNodeMapper.addProcessNode(processNode) > -1) { // 添加成功
			result = processNode.getId(); 
		}
		return result; 

	}


    /**
     * 删除 ProcessNode
     * @param processNode
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteProcessNode(ProcessNode processNode) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteProcessNode(sqlSession, processNode); 
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
     * 删除 ProcessNode
     * @param sqlSession
     * @param processNode
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteProcessNode(SqlSession sqlSession, ProcessNode processNode) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(ProcessNodeMapper.class).deleteProcessNode(processNode); 
	}


    /**
     * 根据id删除 ProcessNode
     * @param id
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteProcessNodeById(Long id) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteProcessNodeById(sqlSession, id); 
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
     * 根据id删除 ProcessNode
     * @param id
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteProcessNodeById(SqlSession sqlSession, Long id) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(ProcessNodeMapper.class).deleteProcessNodeById(id); 
	}


    /**
     * 修改 ProcessNode
     * @param processNode
     * @return Boolean    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateProcessNode(ProcessNode processNode) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateProcessNode(sqlSession, processNode); 
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
     * 修改 ProcessNode
     * @param sqlSession
     * @param processNode
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateProcessNode(SqlSession sqlSession, ProcessNode processNode) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(ProcessNodeMapper.class).updateProcessNode(processNode); 
	}


    /**
     * 根据id修改 ProcessNode
     * @param ProcessNode
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateProcessNodeById(ProcessNode processNode) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateProcessNodeById(sqlSession, processNode); 
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
     * 根据id修改 ProcessNode
     * @param sqlSession
     * @param processNode
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateProcessNodeById(SqlSession sqlSession, ProcessNode processNode) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(ProcessNodeMapper.class).updateProcessNodeById(processNode); 
	}


    /**
     * 根据id查询单个 ProcessNode
     * @param id
     * @return ProcessNode    单个ProcessNode
     * @throws Exception
     */
	@Override
	public ProcessNode findProcessNodeById(Long id) throws Exception {

		ProcessNode result = null; 
		SqlSession sqlSession = null;
		ProcessNodeMapper processNodeMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			processNodeMapper = sqlSession.getMapper(ProcessNodeMapper.class);

			result = processNodeMapper.findProcessNodeById(id); 
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
     * 查询一个 ProcessNode
     * @param processNode
     * @return ProcessNode    单个ProcessNode
     * @throws Exception
     */
	@Override
	public ProcessNode findOneProcessNode(ProcessNode processNode) throws Exception {

		ProcessNode result = null; 
		SqlSession sqlSession = null;
		ProcessNodeMapper processNodeMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			processNodeMapper = sqlSession.getMapper(ProcessNodeMapper.class);

			result = processNodeMapper.findOneProcessNode(processNode); 
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
     * 查询一个 ProcessNode
     * @param clientId 客户端id
     * @param processId 审核流程id
     * @param nodeId 流程节点id
     * @return ProcessNode    单个ProcessNode
     * @throws Exception
     */
	@Override
	public ProcessNode findOneProcessNode(String clientId, String processId, String nodeId) throws Exception {
		ProcessNode processNode = ProcessNodeRedis.get_cbbank_process_node(clientId, processId, nodeId);
		if (null == processNode) {
			LogCvt.warn("客户端id:" + clientId + ", 审核流程id:" + processId + ", 流程节点id:" + nodeId + "缓存为空!");

			ProcessNode processNodeQuery = new ProcessNode();
			processNodeQuery.setClientId(clientId); // 客户端ID
			processNodeQuery.setProcessId(processId);
			processNodeQuery.setNodeId(nodeId);

			processNode = this.findOneProcessNode(processNodeQuery);
			if (null != processNode)
				if (ProcessNodeRedis.set_cbbank_process_node(clientId, processId, nodeId, processNode))
					LogCvt.info("客户端id:" + clientId + ", 审核流程id:" + processId + ", 流程节点id:" + nodeId + "重建缓存成功!");
		}

		return processNode;

	}


    /**
     * 查询统计 ProcessNode
     * @param processNode
     * @return Integer    返回记录数ProcessNode
     * @throws Exception
     */
	@Override
	public Integer countProcessNode(ProcessNode processNode) throws Exception {

		Integer result = null; 
		SqlSession sqlSession = null;
		ProcessNodeMapper processNodeMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			processNodeMapper = sqlSession.getMapper(ProcessNodeMapper.class);

			result = processNodeMapper.countProcessNode(processNode); 
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
     * 查询 ProcessNode
     * @param processNode
     * @return List<ProcessNode>    ProcessNode集合 
     * @throws Exception
     */
	@Override
	public List<ProcessNode> findProcessNode(ProcessNode processNode) throws Exception {

		return this.findProcessNode(processNode, null); 

	}


    /**
     * 查询 ProcessNode
     * @param processNode
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<ProcessNode>    ProcessNode集合 
     * @throws Exception
     */
	@Override
	public List<ProcessNode> findProcessNode(ProcessNode processNode, String order) throws Exception {

		List<ProcessNode> result = null; 
		SqlSession sqlSession = null;
		ProcessNodeMapper processNodeMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			processNodeMapper = sqlSession.getMapper(ProcessNodeMapper.class);

			result = processNodeMapper.findProcessNode(processNode, order); 
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
     * 分页查询 ProcessNode
     * @param page
     * @param processNode
     * @return Page<ProcessNode>    ProcessNode分页结果 
     * @throws Exception 
     */
	@Override
	public Page<ProcessNode> findProcessNodeByPage(Page<ProcessNode> page, ProcessNode processNode) throws Exception {

		List<ProcessNode> result = new ArrayList<ProcessNode>(); 
		SqlSession sqlSession = null;
		ProcessNodeMapper processNodeMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			processNodeMapper = sqlSession.getMapper(ProcessNodeMapper.class);

			result = processNodeMapper.findByPage(page, processNode); 
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