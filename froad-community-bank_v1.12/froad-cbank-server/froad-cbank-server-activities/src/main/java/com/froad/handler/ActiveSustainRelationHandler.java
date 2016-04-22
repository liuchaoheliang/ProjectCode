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
 * @Title: ActiveSustainRelationHandler.java
 * @Package com.froad.handler
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.handler;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.bean.Page;
import com.froad.po.ActiveSustainRelation;

/**
 * 
 * <p>@Title: ActiveSustainRelationHandler.java</p>
 * <p>Description: 封装对MySQL对应的实体ActiveSustainRelation的CURD操作接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface ActiveSustainRelationHandler {


    /**
     * 增加 ActiveSustainRelation
     * @param activeSustainRelation
     * @return Long    主键ID
     * @throws Exception
     */
	public Long addActiveSustainRelation(ActiveSustainRelation activeSustainRelation)  throws Exception;



    /**
     * 增加 ActiveSustainRelation
     * @param sqlSession
     * @param activeSustainRelation
     * @return Long    主键ID
     * @throws Exception
     */
	public Long addActiveSustainRelation(SqlSession sqlSession, ActiveSustainRelation activeSustainRelation) throws Exception;



    /**
     * 删除 ActiveSustainRelation
     * @param activeSustainRelation
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteActiveSustainRelation(ActiveSustainRelation activeSustainRelation) throws Exception;



    /**
     * 删除 ActiveSustainRelation
     * @param sqlSession
     * @param activeSustainRelation
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteActiveSustainRelation(SqlSession sqlSession, ActiveSustainRelation activeSustainRelation) throws Exception;



    /**
     * 根据id删除 ActiveSustainRelation
     * @param id
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteActiveSustainRelationById(Long id) throws Exception;



    /**
     * 根据id删除 ActiveSustainRelation
     * @param id
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteActiveSustainRelationById(SqlSession sqlSession, Long id) throws Exception;



    /**
     * 修改 ActiveSustainRelation
     * @param activeSustainRelation
     * @return Boolean    受影响行数
     * @throws Exception
     */
	public Integer updateActiveSustainRelation(ActiveSustainRelation activeSustainRelation) throws Exception;



    /**
     * 修改 ActiveSustainRelation
     * @param sqlSession
     * @param activeSustainRelation
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateActiveSustainRelation(SqlSession sqlSession, ActiveSustainRelation activeSustainRelation) throws Exception;



    /**
     * 根据id修改 ActiveSustainRelation
     * @param ActiveSustainRelation
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateActiveSustainRelationById(ActiveSustainRelation activeSustainRelation) throws Exception;



    /**
     * 根据id修改 ActiveSustainRelation
     * @param sqlSession
     * @param activeSustainRelation
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateActiveSustainRelationById(SqlSession sqlSession, ActiveSustainRelation activeSustainRelation) throws Exception;



    /**
     * 根据id查询单个 ActiveSustainRelation
     * @param id
     * @return ActiveSustainRelation    单个ActiveSustainRelation
     * @throws Exception
     */
	public ActiveSustainRelation findActiveSustainRelationById(Long id) throws Exception;



    /**
     * 查询一个 ActiveSustainRelation
     * @param activeSustainRelation
     * @return ActiveSustainRelation    单个ActiveSustainRelation
     * @throws Exception
     */
	public ActiveSustainRelation findOneActiveSustainRelation(ActiveSustainRelation activeSustainRelation) throws Exception;



    /**
     * 查询统计 ActiveSustainRelation
     * @param activeSustainRelation
     * @return Integer    返回记录数ActiveSustainRelation
     * @throws Exception
     */
	public Integer countActiveSustainRelation(ActiveSustainRelation activeSustainRelation) throws Exception;



    /**
     * 查询 ActiveSustainRelation
     * @param activeSustainRelation
     * @return List<ActiveSustainRelation>    ActiveSustainRelation集合 
     * @throws Exception
     */
	public List<ActiveSustainRelation> findActiveSustainRelation(ActiveSustainRelation activeSustainRelation) throws Exception;



    /**
     * 查询 ActiveSustainRelation
     * @param activeSustainRelation
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<ActiveSustainRelation>    ActiveSustainRelation集合 
     * @throws Exception
     */
	public List<ActiveSustainRelation> findActiveSustainRelation(ActiveSustainRelation activeSustainRelation, String order) throws Exception;



    /**
     * 分页查询 ActiveSustainRelation
     * @param page
     * @param activeSustainRelation
     * @return Page<ActiveSustainRelation>    ActiveSustainRelation分页结果 
     * @throws Exception 
     */
	public List<ActiveSustainRelation> findActiveSustainRelationByPage(Page<ActiveSustainRelation> page, ActiveSustainRelation activeSustainRelation) throws Exception;



}