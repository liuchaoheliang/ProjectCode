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
 * @Title: BizInstanceAttrValueHandler.java
 * @Package com.froad.handler
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.handler;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.bean.Page;
import com.froad.po.mysql.BizInstanceAttrValue;

/**
 * 
 * <p>@Title: BizInstanceAttrValueHandler.java</p>
 * <p>Description: 封装对MySQL对应的实体BizInstanceAttrValue的CURD操作接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface BizInstanceAttrValueHandler {


    /**
     * 增加 BizInstanceAttrValue
     * @param bizInstanceAttrValue
     * @return String    主键ID
     * @throws Exception
     */
	public String addBizInstanceAttrValue(BizInstanceAttrValue bizInstanceAttrValue)  throws Exception;



    /**
     * 增加 BizInstanceAttrValue
     * @param sqlSession
     * @param bizInstanceAttrValue
     * @return String    主键ID
     * @throws Exception
     */
	public String addBizInstanceAttrValue(SqlSession sqlSession, BizInstanceAttrValue bizInstanceAttrValue) throws Exception;



    /**
     * 删除 BizInstanceAttrValue
     * @param bizInstanceAttrValue
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteBizInstanceAttrValue(BizInstanceAttrValue bizInstanceAttrValue) throws Exception;



    /**
     * 删除 BizInstanceAttrValue
     * @param sqlSession
     * @param bizInstanceAttrValue
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteBizInstanceAttrValue(SqlSession sqlSession, BizInstanceAttrValue bizInstanceAttrValue) throws Exception;



    /**
     * 根据instanceId删除 BizInstanceAttrValue
     * @param instanceId
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteBizInstanceAttrValueByInstanceId(String instanceId) throws Exception;



    /**
     * 根据instanceId删除 BizInstanceAttrValue
     * @param instanceId
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteBizInstanceAttrValueByInstanceId(SqlSession sqlSession, String instanceId) throws Exception;



    /**
     * 修改 BizInstanceAttrValue
     * @param bizInstanceAttrValue
     * @return Boolean    受影响行数
     * @throws Exception
     */
	public Integer updateBizInstanceAttrValue(BizInstanceAttrValue bizInstanceAttrValue) throws Exception;



    /**
     * 修改 BizInstanceAttrValue
     * @param sqlSession
     * @param bizInstanceAttrValue
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateBizInstanceAttrValue(SqlSession sqlSession, BizInstanceAttrValue bizInstanceAttrValue) throws Exception;



    /**
     * 根据instanceId修改 BizInstanceAttrValue
     * @param BizInstanceAttr
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateBizInstanceAttrValueByInstanceId(BizInstanceAttrValue bizInstanceAttrValue) throws Exception;



    /**
     * 根据instanceId修改 BizInstanceAttrValue
     * @param sqlSession
     * @param bizInstanceAttrValue
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateBizInstanceAttrValueByInstanceId(SqlSession sqlSession, BizInstanceAttrValue bizInstanceAttrValue) throws Exception;



    /**
     * 根据instanceId查询单个 BizInstanceAttrValue
     * @param instanceId
     * @return BizInstanceAttrValue    单个BizInstanceAttrValue
     * @throws Exception
     */
	public BizInstanceAttrValue findBizInstanceAttrValueByInstanceId(String instanceId) throws Exception;



    /**
     * 查询一个 BizInstanceAttrValue
     * @param bizInstanceAttrValue
     * @return BizInstanceAttrValue    单个BizInstanceAttrValue
     * @throws Exception
     */
	public BizInstanceAttrValue findOneBizInstanceAttrValue(BizInstanceAttrValue bizInstanceAttrValue) throws Exception;



    /**
     * 查询统计 BizInstanceAttrValue
     * @param bizInstanceAttrValue
     * @return Integer    返回记录数BizInstanceAttrValue
     * @throws Exception
     */
	public Integer countBizInstanceAttrValue(BizInstanceAttrValue bizInstanceAttrValue) throws Exception;



    /**
     * 查询 BizInstanceAttrValue
     * @param bizInstanceAttrValue
     * @return List<BizInstanceAttrValue>    BizInstanceAttrValue集合 
     * @throws Exception
     */
	public List<BizInstanceAttrValue> findBizInstanceAttrValue(BizInstanceAttrValue bizInstanceAttrValue) throws Exception;



    /**
     * 查询 BizInstanceAttrValue
     * @param bizInstanceAttrValue
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<BizInstanceAttrValue>    BizInstanceAttrValue集合 
     * @throws Exception
     */
	public List<BizInstanceAttrValue> findBizInstanceAttrValue(BizInstanceAttrValue bizInstanceAttrValue, String order) throws Exception;



    /**
     * 分页查询 BizInstanceAttrValue
     * @param page
     * @param bizInstanceAttrValue
     * @return Page<BizInstanceAttrValue>    BizInstanceAttrValue分页结果 
     * @throws Exception 
     */
	public Page<BizInstanceAttrValue> findBizInstanceAttrValueByPage(Page<BizInstanceAttrValue> page, BizInstanceAttrValue bizInstanceAttrValue) throws Exception;



}