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
 * @Title: HistoryTaskActorHandler.java
 * @Package com.froad.handler
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.handler;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.bean.Page;
import com.froad.po.mysql.HistoryTaskActor;

/**
 * 
 * <p>@Title: HistoryTaskActorHandler.java</p>
 * <p>Description: 封装对MySQL对应的实体HistoryTaskActor的CURD操作接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface HistoryTaskActorHandler {


    /**
     * 增加 HistoryTaskActor
     * @param historyTaskActor
     * @return String    主键ID
     * @throws Exception
     */
	public String addHistoryTaskActor(HistoryTaskActor historyTaskActor)  throws Exception;



    /**
     * 增加 HistoryTaskActor
     * @param sqlSession
     * @param historyTaskActor
     * @return String    主键ID
     * @throws Exception
     */
	public String addHistoryTaskActor(SqlSession sqlSession, HistoryTaskActor historyTaskActor) throws Exception;



    /**
     * 删除 HistoryTaskActor
     * @param historyTaskActor
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteHistoryTaskActor(HistoryTaskActor historyTaskActor) throws Exception;



    /**
     * 删除 HistoryTaskActor
     * @param sqlSession
     * @param historyTaskActor
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteHistoryTaskActor(SqlSession sqlSession, HistoryTaskActor historyTaskActor) throws Exception;



    /**
     * 根据taskId删除 HistoryTaskActor
     * @param taskId
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteHistoryTaskActorByTaskId(String taskId) throws Exception;



    /**
     * 根据taskId删除 HistoryTaskActor
     * @param taskId
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteHistoryTaskActorByTaskId(SqlSession sqlSession, String taskId) throws Exception;



    /**
     * 修改 HistoryTaskActor
     * @param historyTaskActor
     * @return Boolean    受影响行数
     * @throws Exception
     */
	public Integer updateHistoryTaskActor(HistoryTaskActor historyTaskActor) throws Exception;



    /**
     * 修改 HistoryTaskActor
     * @param sqlSession
     * @param historyTaskActor
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateHistoryTaskActor(SqlSession sqlSession, HistoryTaskActor historyTaskActor) throws Exception;



    /**
     * 根据taskId修改 HistoryTaskActor
     * @param HistoryTaskActor
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateHistoryTaskActorByTaskId(HistoryTaskActor historyTaskActor) throws Exception;



    /**
     * 根据taskId修改 HistoryTaskActor
     * @param sqlSession
     * @param historyTaskActor
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateHistoryTaskActorByTaskId(SqlSession sqlSession, HistoryTaskActor historyTaskActor) throws Exception;



    /**
     * 根据taskId查询单个 HistoryTaskActor
     * @param taskId
     * @return HistoryTaskActor    单个HistoryTaskActor
     * @throws Exception
     */
	public HistoryTaskActor findHistoryTaskActorByTaskId(String taskId) throws Exception;



    /**
     * 查询一个 HistoryTaskActor
     * @param historyTaskActor
     * @return HistoryTaskActor    单个HistoryTaskActor
     * @throws Exception
     */
	public HistoryTaskActor findOneHistoryTaskActor(HistoryTaskActor historyTaskActor) throws Exception;



    /**
     * 查询统计 HistoryTaskActor
     * @param historyTaskActor
     * @return Integer    返回记录数HistoryTaskActor
     * @throws Exception
     */
	public Integer countHistoryTaskActor(HistoryTaskActor historyTaskActor) throws Exception;



    /**
     * 查询 HistoryTaskActor
     * @param historyTaskActor
     * @return List<HistoryTaskActor>    HistoryTaskActor集合 
     * @throws Exception
     */
	public List<HistoryTaskActor> findHistoryTaskActor(HistoryTaskActor historyTaskActor) throws Exception;



    /**
     * 查询 HistoryTaskActor
     * @param historyTaskActor
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<HistoryTaskActor>    HistoryTaskActor集合 
     * @throws Exception
     */
	public List<HistoryTaskActor> findHistoryTaskActor(HistoryTaskActor historyTaskActor, String order) throws Exception;



    /**
     * 分页查询 HistoryTaskActor
     * @param page
     * @param historyTaskActor
     * @return Page<HistoryTaskActor>    HistoryTaskActor分页结果 
     * @throws Exception 
     */
	public Page<HistoryTaskActor> findHistoryTaskActorByPage(Page<HistoryTaskActor> page, HistoryTaskActor historyTaskActor) throws Exception;



}