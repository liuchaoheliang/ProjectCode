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
 * @Title: ActiveTagRelationHandler.java
 * @Package com.froad.handler
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.handler;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.bean.Page;
import com.froad.po.ActiveTagRelation;

/**
 * 
 * <p>@Title: ActiveTagRelationHandler.java</p>
 * <p>Description: 封装对MySQL对应的实体ActiveTagRelation的CURD操作接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface ActiveTagRelationHandler {


    /**
     * 增加 ActiveTagRelation
     * @param activeTagRelation
     * @return Long    主键ID
     * @throws Exception
     */
	public Long addActiveTagRelation(ActiveTagRelation activeTagRelation)  throws Exception;



    /**
     * 增加 ActiveTagRelation
     * @param sqlSession
     * @param activeTagRelation
     * @return Long    主键ID
     * @throws Exception
     */
	public Long addActiveTagRelation(SqlSession sqlSession, ActiveTagRelation activeTagRelation) throws Exception;



    /**
     * 删除 ActiveTagRelation
     * @param activeTagRelation
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteActiveTagRelation(ActiveTagRelation activeTagRelation) throws Exception;



    /**
     * 删除 ActiveTagRelation
     * @param sqlSession
     * @param activeTagRelation
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteActiveTagRelation(SqlSession sqlSession, ActiveTagRelation activeTagRelation) throws Exception;



    /**
     * 根据id删除 ActiveTagRelation
     * @param id
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteActiveTagRelationById(Long id) throws Exception;



    /**
     * 根据id删除 ActiveTagRelation
     * @param id
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteActiveTagRelationById(SqlSession sqlSession, Long id) throws Exception;



    /**
     * 修改 ActiveTagRelation
     * @param activeTagRelation
     * @return Boolean    受影响行数
     * @throws Exception
     */
	public Integer updateActiveTagRelation(ActiveTagRelation activeTagRelation) throws Exception;



    /**
     * 修改 ActiveTagRelation
     * @param sqlSession
     * @param activeTagRelation
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateActiveTagRelation(SqlSession sqlSession, ActiveTagRelation activeTagRelation) throws Exception;



    /**
     * 根据id修改 ActiveTagRelation
     * @param ActiveTagRelation
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateActiveTagRelationById(ActiveTagRelation activeTagRelation) throws Exception;



    /**
     * 根据id修改 ActiveTagRelation
     * @param sqlSession
     * @param activeTagRelation
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateActiveTagRelationById(SqlSession sqlSession, ActiveTagRelation activeTagRelation) throws Exception;



    /**
     * 根据id查询单个 ActiveTagRelation
     * @param id
     * @return ActiveTagRelation    单个ActiveTagRelation
     * @throws Exception
     */
	public ActiveTagRelation findActiveTagRelationById(Long id) throws Exception;



    /**
     * 查询一个 ActiveTagRelation
     * @param activeTagRelation
     * @return ActiveTagRelation    单个ActiveTagRelation
     * @throws Exception
     */
	public ActiveTagRelation findOneActiveTagRelation(ActiveTagRelation activeTagRelation) throws Exception;



    /**
     * 查询统计 ActiveTagRelation
     * @param activeTagRelation
     * @return Integer    返回记录数ActiveTagRelation
     * @throws Exception
     */
	public Integer countActiveTagRelation(ActiveTagRelation activeTagRelation) throws Exception;



    /**
     * 查询 ActiveTagRelation
     * @param activeTagRelation
     * @return List<ActiveTagRelation>    ActiveTagRelation集合 
     * @throws Exception
     */
	public List<ActiveTagRelation> findActiveTagRelation(ActiveTagRelation activeTagRelation) throws Exception;



    /**
     * 查询 ActiveTagRelation
     * @param activeTagRelation
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<ActiveTagRelation>    ActiveTagRelation集合 
     * @throws Exception
     */
	public List<ActiveTagRelation> findActiveTagRelation(ActiveTagRelation activeTagRelation, String order) throws Exception;



    /**
     * 分页查询 ActiveTagRelation
     * @param page
     * @param activeTagRelation
     * @return Page<ActiveTagRelation>    ActiveTagRelation分页结果 
     * @throws Exception 
     */
	public Page<ActiveTagRelation> findActiveTagRelationByPage(Page<ActiveTagRelation> page, ActiveTagRelation activeTagRelation) throws Exception;
	
	public int countLimitProductActivityTag(String clientId, String activeType);
	
	public ActiveTagRelation getAvailableActive(String clientId, String itemType, String itemId, String activeId, String type);
	
	

}