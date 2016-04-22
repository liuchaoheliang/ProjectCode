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
 * @Title: HistoryInstanceHandler.java
 * @Package com.froad.handler
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.handler;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.bean.Page;
import com.froad.po.mysql.HistoryInstance;

/**
 * 
 * <p>@Title: HistoryInstanceHandler.java</p>
 * <p>Description: 封装对MySQL对应的实体HistoryInstance的CURD操作接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface HistoryInstanceHandler {


    /**
     * 增加 HistoryInstance
     * @param historyInstance
     * @return Long    主键ID
     * @throws Exception
     */
	public Long addHistoryInstance(HistoryInstance historyInstance)  throws Exception;



    /**
     * 增加 HistoryInstance
     * @param sqlSession
     * @param historyInstance
     * @return Long    主键ID
     * @throws Exception
     */
	public Long addHistoryInstance(SqlSession sqlSession, HistoryInstance historyInstance) throws Exception;



    /**
     * 删除 HistoryInstance
     * @param historyInstance
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteHistoryInstance(HistoryInstance historyInstance) throws Exception;



    /**
     * 删除 HistoryInstance
     * @param sqlSession
     * @param historyInstance
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteHistoryInstance(SqlSession sqlSession, HistoryInstance historyInstance) throws Exception;



    /**
     * 根据id删除 HistoryInstance
     * @param id
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteHistoryInstanceById(Long id) throws Exception;



    /**
     * 根据id删除 HistoryInstance
     * @param id
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteHistoryInstanceById(SqlSession sqlSession, Long id) throws Exception;
	/**
	 * 
	 * deleteHistoryInstanceByInstanceId:根据instanceId删除 HistoryInstance
	 *
	 * @author vania
	 * 2015年11月3日 下午4:40:33
	 * @param instanceId
	 * @return
	 * @throws Exception
	 *
	 */
	public Integer deleteHistoryInstanceByInstanceId(String instanceId) throws Exception;
	
	/**
	 * 
	 * deleteHistoryInstanceByInstanceId:根据instanceId删除 HistoryInstance
	 *
	 * @author vania
	 * 2015年11月3日 下午4:40:53
	 * @param sqlSession
	 * @param instanceId
	 * @return
	 * @throws Exception
	 *
	 */
	public Integer deleteHistoryInstanceByInstanceId(SqlSession sqlSession, String instanceId) throws Exception;



    /**
     * 修改 HistoryInstance
     * @param historyInstance
     * @return Boolean    受影响行数
     * @throws Exception
     */
	public Integer updateHistoryInstance(HistoryInstance historyInstance) throws Exception;



    /**
     * 修改 HistoryInstance
     * @param sqlSession
     * @param historyInstance
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateHistoryInstance(SqlSession sqlSession, HistoryInstance historyInstance) throws Exception;



    /**
     * 根据id修改 HistoryInstance
     * @param HistoryInstance
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateHistoryInstanceById(HistoryInstance historyInstance) throws Exception;

	
	/**
     * 根据InstanceId修改 HistoryInstance
     * @param HistoryInstance
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateHistoryInstanceByInstanceId(HistoryInstance historyInstance) throws Exception ;
	
	
	/**
	 * 根据InstanceId修改 HistoryInstance
	 * @param sqlSession
     * @param historyInstance
     * @return Integer    受影响行数
	 * @throws Exception
	 */
	public Integer updateHistoryInstanceByInstanceId(SqlSession sqlSession, HistoryInstance historyInstance) throws Exception ;

    /**
     * 根据id修改 HistoryInstance
     * @param sqlSession
     * @param historyInstance
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateHistoryInstanceById(SqlSession sqlSession, HistoryInstance historyInstance) throws Exception;



    /**
     * 根据id查询单个 HistoryInstance
     * @param id
     * @return HistoryInstance    单个HistoryInstance
     * @throws Exception
     */
	public HistoryInstance findHistoryInstanceById(Long id) throws Exception;
	
	/**
     * 根据instanceId查询单个 HistoryInstance
     * @param instanceId
     * @return HistoryInstance    单个HistoryInstance
     * @throws Exception
     */
	public HistoryInstance findHistoryInstanceByInstanceId(String instanceId) throws Exception;



    /**
     * 查询一个 HistoryInstance
     * @param historyInstance
     * @return HistoryInstance    单个HistoryInstance
     * @throws Exception
     */
	public HistoryInstance findOneHistoryInstance(HistoryInstance historyInstance) throws Exception;



    /**
     * 查询统计 HistoryInstance
     * @param historyInstance
     * @return Integer    返回记录数HistoryInstance
     * @throws Exception
     */
	public Integer countHistoryInstance(HistoryInstance historyInstance) throws Exception;



    /**
     * 查询 HistoryInstance
     * @param historyInstance
     * @return List<HistoryInstance>    HistoryInstance集合 
     * @throws Exception
     */
	public List<HistoryInstance> findHistoryInstance(HistoryInstance historyInstance) throws Exception;



    /**
     * 查询 HistoryInstance
     * @param historyInstance
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<HistoryInstance>    HistoryInstance集合 
     * @throws Exception
     */
	public List<HistoryInstance> findHistoryInstance(HistoryInstance historyInstance, String order) throws Exception;



    /**
     * 分页查询 HistoryInstance
     * @param page
     * @param historyInstance
     * @return Page<HistoryInstance>    HistoryInstance分页结果 
     * @throws Exception 
     */
	public Page<HistoryInstance> findHistoryInstanceByPage(Page<HistoryInstance> page, HistoryInstance historyInstance) throws Exception;



}