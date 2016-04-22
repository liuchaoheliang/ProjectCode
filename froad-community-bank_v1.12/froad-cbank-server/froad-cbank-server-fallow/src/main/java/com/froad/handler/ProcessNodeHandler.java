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
 * @Title: ProcessNodeHandler.java
 * @Package com.froad.handler
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.handler;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.bean.Page;
import com.froad.po.mysql.ProcessNode;

/**
 * 
 * <p>@Title: ProcessNodeHandler.java</p>
 * <p>Description: 封装对MySQL对应的实体ProcessNode的CURD操作接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface ProcessNodeHandler {


    /**
     * 增加 ProcessNode
     * @param processNode
     * @return Long    主键ID
     * @throws Exception
     */
	public Long addProcessNode(ProcessNode processNode)  throws Exception;



    /**
     * 增加 ProcessNode
     * @param sqlSession
     * @param processNode
     * @return Long    主键ID
     * @throws Exception
     */
	public Long addProcessNode(SqlSession sqlSession, ProcessNode processNode) throws Exception;



    /**
     * 删除 ProcessNode
     * @param processNode
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteProcessNode(ProcessNode processNode) throws Exception;



    /**
     * 删除 ProcessNode
     * @param sqlSession
     * @param processNode
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteProcessNode(SqlSession sqlSession, ProcessNode processNode) throws Exception;



    /**
     * 根据id删除 ProcessNode
     * @param id
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteProcessNodeById(Long id) throws Exception;



    /**
     * 根据id删除 ProcessNode
     * @param id
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteProcessNodeById(SqlSession sqlSession, Long id) throws Exception;



    /**
     * 修改 ProcessNode
     * @param processNode
     * @return Boolean    受影响行数
     * @throws Exception
     */
	public Integer updateProcessNode(ProcessNode processNode) throws Exception;



    /**
     * 修改 ProcessNode
     * @param sqlSession
     * @param processNode
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateProcessNode(SqlSession sqlSession, ProcessNode processNode) throws Exception;



    /**
     * 根据id修改 ProcessNode
     * @param ProcessNode
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateProcessNodeById(ProcessNode processNode) throws Exception;



    /**
     * 根据id修改 ProcessNode
     * @param sqlSession
     * @param processNode
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateProcessNodeById(SqlSession sqlSession, ProcessNode processNode) throws Exception;



    /**
     * 根据id查询单个 ProcessNode
     * @param id
     * @return ProcessNode    单个ProcessNode
     * @throws Exception
     */
	public ProcessNode findProcessNodeById(Long id) throws Exception;



    /**
     * 查询一个 ProcessNode
     * @param processNode
     * @return ProcessNode    单个ProcessNode
     * @throws Exception
     */
	public ProcessNode findOneProcessNode(ProcessNode processNode) throws Exception;


	
	/**
     * 查询一个 ProcessNode
     * @param clientId 客户端id
     * @param processId 审核流程id
     * @param nodeId 流程节点id
     * @return ProcessNode    单个ProcessNode
     * @throws Exception
     */
	public ProcessNode findOneProcessNode(String clientId, String processId, String nodeId) throws Exception;

    /**
     * 查询统计 ProcessNode
     * @param processNode
     * @return Integer    返回记录数ProcessNode
     * @throws Exception
     */
	public Integer countProcessNode(ProcessNode processNode) throws Exception;



    /**
     * 查询 ProcessNode
     * @param processNode
     * @return List<ProcessNode>    ProcessNode集合 
     * @throws Exception
     */
	public List<ProcessNode> findProcessNode(ProcessNode processNode) throws Exception;



    /**
     * 查询 ProcessNode
     * @param processNode
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<ProcessNode>    ProcessNode集合 
     * @throws Exception
     */
	public List<ProcessNode> findProcessNode(ProcessNode processNode, String order) throws Exception;



    /**
     * 分页查询 ProcessNode
     * @param page
     * @param processNode
     * @return Page<ProcessNode>    ProcessNode分页结果 
     * @throws Exception 
     */
	public Page<ProcessNode> findProcessNodeByPage(Page<ProcessNode> page, ProcessNode processNode) throws Exception;



}