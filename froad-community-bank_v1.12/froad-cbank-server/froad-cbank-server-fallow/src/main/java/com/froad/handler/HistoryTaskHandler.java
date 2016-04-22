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
 * @Title: HistoryTaskHandler.java
 * @Package com.froad.handler
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.handler;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.bean.Page;
import com.froad.po.mysql.HistoryTask;

/**
 * 
 * <p>
 * @Title: HistoryTaskHandler.java
 * </p>
 * <p>
 * Description: 封装对MySQL对应的实体HistoryTask的CURD操作接口
 * </p>
 * 
 * @author f-road
 * @version 1.0
 * @created 2015年3月17日
 */
public interface HistoryTaskHandler {

	/**
	 * 增加 HistoryTask
	 * 
	 * @param historyTask
	 * @return Long 主键ID
	 * @throws Exception
	 */
	public Long addHistoryTask(HistoryTask historyTask) throws Exception;

	/**
	 * 增加 HistoryTask
	 * 
	 * @param sqlSession
	 * @param historyTask
	 * @return Long 主键ID
	 * @throws Exception
	 */
	public Long addHistoryTask(SqlSession sqlSession, HistoryTask historyTask) throws Exception;

	/**
	 * 删除 HistoryTask
	 * 
	 * @param historyTask
	 * @return Integer 受影响行数
	 * @throws Exception
	 */
	public Integer deleteHistoryTask(HistoryTask historyTask) throws Exception;

	/**
	 * 删除 HistoryTask
	 * 
	 * @param sqlSession
	 * @param historyTask
	 * @return Integer 受影响行数
	 * @throws Exception
	 */
	public Integer deleteHistoryTask(SqlSession sqlSession, HistoryTask historyTask) throws Exception;

	/**
	 * 根据id删除 HistoryTask
	 * 
	 * @param id
	 * @return Integer 受影响行数
	 * @throws Exception
	 */
	public Integer deleteHistoryTaskById(Long id) throws Exception;

	/**
	 * 根据id删除 HistoryTask
	 * 
	 * @param id
	 * @param sqlSession
	 * @return Integer 受影响行数
	 * @throws Exception
	 */
	public Integer deleteHistoryTaskById(SqlSession sqlSession, Long id) throws Exception;

	/**
	 * 根据taskId删除 HistoryTask
	 * 
	 * @param taskId
	 * @return Integer 受影响行数
	 * @throws Exception
	 */
	public Integer deleteHistoryTaskByTaskId(String taskId) throws Exception;

	/**
	 * 根据taskId删除 HistoryTask
	 * 
	 * @param taskId
	 * @param sqlSession
	 * @return Integer 受影响行数
	 * @throws Exception
	 */
	public Integer deleteHistoryTaskByTaskId(SqlSession sqlSession, String taskId) throws Exception;

	/**
	 * 根据taskId删除 HistoryTask
	 * 
	 * @param instanceId
	 * @return Integer 受影响行数
	 * @throws Exception
	 */
	public Integer deleteHistoryTaskByInstanceId(String instanceId) throws Exception;

	/**
	 * 根据taskId删除 HistoryTask
	 * 
	 * @param instanceId
	 * @param sqlSession
	 * @return Integer 受影响行数
	 * @throws Exception
	 */
	public Integer deleteHistoryTaskByInstanceId(SqlSession sqlSession, String instanceId) throws Exception;

	/**
	 * 修改 HistoryTask
	 * 
	 * @param historyTask
	 * @return Boolean 受影响行数
	 * @throws Exception
	 */
	public Integer updateHistoryTask(HistoryTask historyTask) throws Exception;

	/**
	 * 修改 HistoryTask
	 * 
	 * @param sqlSession
	 * @param historyTask
	 * @return Integer 受影响行数
	 * @throws Exception
	 */
	public Integer updateHistoryTask(SqlSession sqlSession, HistoryTask historyTask) throws Exception;

	/**
	 * 根据id修改 HistoryTask
	 * 
	 * @param HistoryTask
	 * @return Integer 受影响行数
	 * @throws Exception
	 */
	public Integer updateHistoryTaskById(HistoryTask historyTask) throws Exception;

	/**
	 * 根据taskId修改 HistoryTask
	 * 
	 * @param HistoryTask
	 * @return Integer 受影响行数
	 * @throws Exception
	 */
	public Integer updateHistoryTaskByTaskId(HistoryTask historyTask) throws Exception;

	/**
	 * 根据taskId修改 HistoryTask
	 * 
	 * @param SqlSession
	 * @param HistoryTask
	 * @return Integer 受影响行数
	 * @throws Exception
	 */
	public Integer updateHistoryTaskByTaskId(SqlSession sqlSession, HistoryTask historyTask) throws Exception;

	/**
	 * 根据instanceId修改 HistoryTask
	 * 
	 * @param HistoryTask
	 * @return Integer 受影响行数
	 * @throws Exception
	 */
	public Integer updateHistoryTaskByInstanceId(HistoryTask historyTask) throws Exception;

	/**
	 * 根据instanceId修改 HistoryTask
	 * 
	 * @param SqlSession
	 * @param HistoryTask
	 * @return Integer 受影响行数
	 * @throws Exception
	 */
	public Integer updateHistoryTaskByInstanceId(SqlSession sqlSession, HistoryTask historyTask) throws Exception;

	/**
	 * 根据id修改 HistoryTask
	 * 
	 * @param sqlSession
	 * @param historyTask
	 * @return Integer 受影响行数
	 * @throws Exception
	 */
	public Integer updateHistoryTaskById(SqlSession sqlSession, HistoryTask historyTask) throws Exception;

	/**
	 * 根据id查询单个 HistoryTask
	 * 
	 * @param id
	 * @return HistoryTask 单个HistoryTask
	 * @throws Exception
	 */
	public HistoryTask findHistoryTaskById(Long id) throws Exception;

	/**
	 * 查询一个 HistoryTask
	 * 
	 * @param historyTask
	 * @return HistoryTask 单个HistoryTask
	 * @throws Exception
	 */
	public HistoryTask findOneHistoryTask(HistoryTask historyTask) throws Exception;

	/**
	 * 查询统计 HistoryTask
	 * 
	 * @param historyTask
	 * @return Integer 返回记录数HistoryTask
	 * @throws Exception
	 */
	public Integer countHistoryTask(HistoryTask historyTask) throws Exception;

	/**
	 * 查询 HistoryTask
	 * 
	 * @param historyTask
	 * @return List<HistoryTask> HistoryTask集合
	 * @throws Exception
	 */
	public List<HistoryTask> findHistoryTask(HistoryTask historyTask) throws Exception;

	/**
	 * 查询 HistoryTask
	 * 
	 * @param historyTask
	 * @param order
	 *            排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
	 * @return List<HistoryTask> HistoryTask集合
	 * @throws Exception
	 */
	public List<HistoryTask> findHistoryTask(HistoryTask historyTask, String order) throws Exception;
	
	/**
     * 查询 执行节点HistoryTask
     * @param historyTask
     * @return List<HistoryTask>    返回结果集
     */
	public List<HistoryTask> findHistoryTaskOfExecute(HistoryTask historyTask) throws Exception;

	/**
	 * 分页查询 HistoryTask
	 * 
	 * @param page
	 * @param historyTask
	 * @return Page<HistoryTask> HistoryTask分页结果
	 * @throws Exception
	 */
	public Page<HistoryTask> findHistoryTaskByPage(Page<HistoryTask> page, HistoryTask historyTask) throws Exception;

}