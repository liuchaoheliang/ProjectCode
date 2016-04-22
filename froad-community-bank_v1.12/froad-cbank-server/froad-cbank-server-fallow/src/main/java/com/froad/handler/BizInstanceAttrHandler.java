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
 * @Title: BizInstanceAttrHandler.java
 * @Package com.froad.handler
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.handler;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.bean.Page;
import com.froad.po.mysql.BizInstanceAttr;

/**
 * 
 * <p>@Title: BizInstanceAttrHandler.java</p>
 * <p>Description: 封装对MySQL对应的实体BizInstanceAttr的CURD操作接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface BizInstanceAttrHandler {


    /**
     * 增加 BizInstanceAttr
     * @param bizInstanceAttr
     * @return Long    主键ID
     * @throws Exception
     */
	public Long addBizInstanceAttr(BizInstanceAttr bizInstanceAttr)  throws Exception;



    /**
     * 增加 BizInstanceAttr
     * @param sqlSession
     * @param bizInstanceAttr
     * @return Long    主键ID
     * @throws Exception
     */
	public Long addBizInstanceAttr(SqlSession sqlSession, BizInstanceAttr bizInstanceAttr) throws Exception;



    /**
     * 删除 BizInstanceAttr
     * @param bizInstanceAttr
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteBizInstanceAttr(BizInstanceAttr bizInstanceAttr) throws Exception;



    /**
     * 删除 BizInstanceAttr
     * @param sqlSession
     * @param bizInstanceAttr
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteBizInstanceAttr(SqlSession sqlSession, BizInstanceAttr bizInstanceAttr) throws Exception;



    /**
     * 根据id删除 BizInstanceAttr
     * @param id
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteBizInstanceAttrById(Long id) throws Exception;



    /**
     * 根据id删除 BizInstanceAttr
     * @param id
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteBizInstanceAttrById(SqlSession sqlSession, Long id) throws Exception;



    /**
     * 修改 BizInstanceAttr
     * @param bizInstanceAttr
     * @return Boolean    受影响行数
     * @throws Exception
     */
	public Integer updateBizInstanceAttr(BizInstanceAttr bizInstanceAttr) throws Exception;



    /**
     * 修改 BizInstanceAttr
     * @param sqlSession
     * @param bizInstanceAttr
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateBizInstanceAttr(SqlSession sqlSession, BizInstanceAttr bizInstanceAttr) throws Exception;



    /**
     * 根据id修改 BizInstanceAttr
     * @param AuditInstanceDetail
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateBizInstanceAttrById(BizInstanceAttr bizInstanceAttr) throws Exception;



    /**
     * 根据id修改 BizInstanceAttr
     * @param sqlSession
     * @param bizInstanceAttr
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateBizInstanceAttrById(SqlSession sqlSession, BizInstanceAttr bizInstanceAttr) throws Exception;



    /**
     * 根据id查询单个 BizInstanceAttr
     * @param id
     * @return BizInstanceAttr    单个BizInstanceAttr
     * @throws Exception
     */
	public BizInstanceAttr findBizInstanceAttrById(Long id) throws Exception;



    /**
     * 查询一个 BizInstanceAttr
     * @param bizInstanceAttr
     * @return BizInstanceAttr    单个BizInstanceAttr
     * @throws Exception
     */
	public BizInstanceAttr findOneBizInstanceAttr(BizInstanceAttr bizInstanceAttr) throws Exception;



    /**
     * 查询统计 BizInstanceAttr
     * @param bizInstanceAttr
     * @return Integer    返回记录数BizInstanceAttr
     * @throws Exception
     */
	public Integer countBizInstanceAttr(BizInstanceAttr bizInstanceAttr) throws Exception;



    /**
     * 查询 BizInstanceAttr
     * @param bizInstanceAttr
     * @return List<BizInstanceAttr>    BizInstanceAttr集合 
     * @throws Exception
     */
	public List<BizInstanceAttr> findBizInstanceAttr(BizInstanceAttr bizInstanceAttr) throws Exception;



    /**
     * 查询 BizInstanceAttr
     * @param bizInstanceAttr
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<BizInstanceAttr>    BizInstanceAttr集合 
     * @throws Exception
     */
	public List<BizInstanceAttr> findBizInstanceAttr(BizInstanceAttr bizInstanceAttr, String order) throws Exception;



    /**
     * 分页查询 BizInstanceAttr
     * @param page
     * @param bizInstanceAttr
     * @return Page<BizInstanceAttr>    BizInstanceAttr分页结果 
     * @throws Exception 
     */
	public Page<BizInstanceAttr> findBizInstanceAttrByPage(Page<BizInstanceAttr> page, BizInstanceAttr bizInstanceAttr) throws Exception;



}