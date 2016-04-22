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
 * @Title: ProcessHandler.java
 * @Package com.froad.handler
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.handler;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.bean.Page;
import com.froad.po.mysql.Process;

/**
 * 
 * <p>@Title: ProcessHandler.java</p>
 * <p>Description: 封装对MySQL对应的实体Process的CURD操作接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface ProcessHandler {


    /**
     * 增加 Process
     * @param process
     * @return Long    主键ID
     * @throws Exception
     */
	public Long addProcess(Process process)  throws Exception;



    /**
     * 增加 Process
     * @param sqlSession
     * @param process
     * @return Long    主键ID
     * @throws Exception
     */
	public Long addProcess(SqlSession sqlSession, Process process) throws Exception;



    /**
     * 删除 Process
     * @param process
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteProcess(Process process) throws Exception;



    /**
     * 删除 Process
     * @param sqlSession
     * @param process
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteProcess(SqlSession sqlSession, Process process) throws Exception;



    /**
     * 根据id删除 Process
     * @param id
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteProcessById(Long id) throws Exception;



    /**
     * 根据id删除 Process
     * @param id
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteProcessById(SqlSession sqlSession, Long id) throws Exception;



    /**
     * 根据processId删除 Process
     * @param processId
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteProcessByProcessId(String processId) throws Exception;



    /**
     * 根据processId删除 Process
     * @param processId
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteProcessByProcessId(SqlSession sqlSession, String processId) throws Exception;



    /**
     * 修改 Process
     * @param process
     * @return Boolean    受影响行数
     * @throws Exception
     */
	public Integer updateProcess(Process process) throws Exception;



    /**
     * 修改 Process
     * @param sqlSession
     * @param process
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateProcess(SqlSession sqlSession, Process process) throws Exception;



    /**
     * 根据id修改 Process
     * @param Process
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateProcessById(Process process) throws Exception;



    /**
     * 根据id修改 Process
     * @param sqlSession
     * @param process
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateProcessById(SqlSession sqlSession, Process process) throws Exception;



    /**
     * 根据processId修改 Process
     * @param Process
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateProcessByProcessId(Process process) throws Exception;



    /**
     * 根据processId修改 Process
     * @param sqlSession
     * @param Process
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateProcessByProcessId(SqlSession sqlSession, Process process) throws Exception;



    /**
     * 根据id查询单个 Process
     * @param id
     * @return Process    单个Process
     * @throws Exception
     */
	public Process findProcessById(Long id) throws Exception;



    /**
     * 根据processId查询单个 Process
     * @param processId
     * @return Process    单个Process
     * @throws Exception
     */
	public Process findProcessByProcessId(String processId) throws Exception;
	
	/**
	 * 根据clientId,processId查询单个 Process
	 * @param clientId
	 * @param processId
	 * @return Process    单个Process
	 * @throws Exception
	 */
	public Process findOneProcess(String clientId, String processId) throws Exception;



    /**
     * 查询一个 Process
     * @param process
     * @return Process    单个Process
     * @throws Exception
     */
	public Process findOneProcess(Process process) throws Exception;



    /**
     * 查询统计 Process
     * @param process
     * @return Integer    返回记录数Process
     * @throws Exception
     */
	public Integer countProcess(Process process) throws Exception;



    /**
     * 查询 Process
     * @param process
     * @return List<Process>    Process集合 
     * @throws Exception
     */
	public List<Process> findProcess(Process process) throws Exception;



    /**
     * 查询 Process
     * @param process
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<Process>    Process集合 
     * @throws Exception
     */
	public List<Process> findProcess(Process process, String order) throws Exception;



    /**
     * 分页查询 Process
     * @param page
     * @param process
     * @return Page<Process>    Process分页结果 
     * @throws Exception 
     */
	public Page<Process> findProcessByPage(Page<Process> page, Process process) throws Exception;



}