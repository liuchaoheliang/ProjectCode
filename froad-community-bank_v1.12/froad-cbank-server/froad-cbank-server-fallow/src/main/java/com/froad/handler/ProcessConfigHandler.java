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
 * @Title: ProcessConfigHandler.java
 * @Package com.froad.handler
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.handler;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.bean.Page;
import com.froad.po.mysql.ProcessConfig;

/**
 * 
 * <p>@Title: ProcessConfigHandler.java</p>
 * <p>Description: 封装对MySQL对应的实体ProcessConfig的CURD操作接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface ProcessConfigHandler {


    /**
     * 增加 ProcessConfig
     * @param processConfig
     * @return Long    主键ID
     * @throws Exception
     */
	public Long addProcessConfig(ProcessConfig processConfig)  throws Exception;



    /**
     * 增加 ProcessConfig
     * @param sqlSession
     * @param processConfig
     * @return Long    主键ID
     * @throws Exception
     */
	public Long addProcessConfig(SqlSession sqlSession, ProcessConfig processConfig) throws Exception;



    /**
     * 删除 ProcessConfig
     * @param processConfig
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteProcessConfig(ProcessConfig processConfig) throws Exception;



    /**
     * 删除 ProcessConfig
     * @param sqlSession
     * @param processConfig
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteProcessConfig(SqlSession sqlSession, ProcessConfig processConfig) throws Exception;



    /**
     * 根据id删除 ProcessConfig
     * @param id
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteProcessConfigById(Long id) throws Exception;



    /**
     * 根据id删除 ProcessConfig
     * @param id
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteProcessConfigById(SqlSession sqlSession, Long id) throws Exception;



    /**
     * 修改 ProcessConfig
     * @param processConfig
     * @return Boolean    受影响行数
     * @throws Exception
     */
	public Integer updateProcessConfig(ProcessConfig processConfig) throws Exception;



    /**
     * 修改 ProcessConfig
     * @param sqlSession
     * @param processConfig
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateProcessConfig(SqlSession sqlSession, ProcessConfig processConfig) throws Exception;



    /**
     * 根据id修改 ProcessConfig
     * @param ProcessConfig
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateProcessConfigById(ProcessConfig processConfig) throws Exception;



    /**
     * 根据id修改 ProcessConfig
     * @param sqlSession
     * @param processConfig
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateProcessConfigById(SqlSession sqlSession, ProcessConfig processConfig) throws Exception;



    /**
     * 根据id查询单个 ProcessConfig
     * @param id
     * @return ProcessConfig    单个ProcessConfig
     * @throws Exception
     */
	public ProcessConfig findProcessConfigById(Long id) throws Exception;



    /**
     * 查询一个 ProcessConfig
     * @param processConfig
     * @return ProcessConfig    单个ProcessConfig
     * @throws Exception
     */
	public ProcessConfig findOneProcessConfig(ProcessConfig processConfig) throws Exception;



    /**
     * 查询统计 ProcessConfig
     * @param processConfig
     * @return Integer    返回记录数ProcessConfig
     * @throws Exception
     */
	public Integer countProcessConfig(ProcessConfig processConfig) throws Exception;



    /**
     * 查询 ProcessConfig
     * @param processConfig
     * @return List<ProcessConfig>    ProcessConfig集合 
     * @throws Exception
     */
	public List<ProcessConfig> findProcessConfig(ProcessConfig processConfig) throws Exception;



    /**
     * 查询 ProcessConfig
     * @param processConfig
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<ProcessConfig>    ProcessConfig集合 
     * @throws Exception
     */
	public List<ProcessConfig> findProcessConfig(ProcessConfig processConfig, String order) throws Exception;



    /**
     * 分页查询 ProcessConfig
     * @param page
     * @param processConfig
     * @return Page<ProcessConfig>    ProcessConfig分页结果 
     * @throws Exception 
     */
	public Page<ProcessConfig> findProcessConfigByPage(Page<ProcessConfig> page, ProcessConfig processConfig) throws Exception;



}