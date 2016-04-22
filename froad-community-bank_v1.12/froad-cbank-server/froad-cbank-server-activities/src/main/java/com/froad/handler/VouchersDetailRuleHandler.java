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
 * @Title: VouchersDetailRuleHandler.java
 * @Package com.froad.handler
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.handler;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.bean.Page;
import com.froad.po.VouchersDetailRule;

/**
 * 
 * <p>@Title: VouchersDetailRuleHandler.java</p>
 * <p>Description: 封装对MySQL对应的实体VouchersDetailRule的CURD操作接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface VouchersDetailRuleHandler {


    /**
     * 增加 VouchersDetailRule
     * @param vouchersDetailRule
     * @return Long    主键ID
     * @throws Exception
     */
	public Long addVouchersDetailRule(VouchersDetailRule vouchersDetailRule)  throws Exception;



    /**
     * 增加 VouchersDetailRule
     * @param sqlSession
     * @param vouchersDetailRule
     * @return Long    主键ID
     * @throws Exception
     */
	public Long addVouchersDetailRule(SqlSession sqlSession, VouchersDetailRule vouchersDetailRule) throws Exception;



    /**
     * 删除 VouchersDetailRule
     * @param vouchersDetailRule
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteVouchersDetailRule(VouchersDetailRule vouchersDetailRule) throws Exception;



    /**
     * 删除 VouchersDetailRule
     * @param sqlSession
     * @param vouchersDetailRule
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteVouchersDetailRule(SqlSession sqlSession, VouchersDetailRule vouchersDetailRule) throws Exception;



    /**
     * 根据id删除 VouchersDetailRule
     * @param id
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteVouchersDetailRuleById(Long id) throws Exception;



    /**
     * 根据id删除 VouchersDetailRule
     * @param id
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteVouchersDetailRuleById(SqlSession sqlSession, Long id) throws Exception;



    /**
     * 修改 VouchersDetailRule
     * @param vouchersDetailRule
     * @return Boolean    受影响行数
     * @throws Exception
     */
	public Integer updateVouchersDetailRule(VouchersDetailRule vouchersDetailRule) throws Exception;



    /**
     * 修改 VouchersDetailRule
     * @param sqlSession
     * @param vouchersDetailRule
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateVouchersDetailRule(SqlSession sqlSession, VouchersDetailRule vouchersDetailRule) throws Exception;



    /**
     * 根据id修改 VouchersDetailRule
     * @param VouchersDetailRule
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateVouchersDetailRuleById(VouchersDetailRule vouchersDetailRule) throws Exception;



    /**
     * 根据id修改 VouchersDetailRule
     * @param sqlSession
     * @param vouchersDetailRule
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateVouchersDetailRuleById(SqlSession sqlSession, VouchersDetailRule vouchersDetailRule) throws Exception;



    /**
     * 根据id查询单个 VouchersDetailRule
     * @param id
     * @return VouchersDetailRule    单个VouchersDetailRule
     * @throws Exception
     */
	public VouchersDetailRule findVouchersDetailRuleById(Long id) throws Exception;



    /**
     * 查询一个 VouchersDetailRule
     * @param vouchersDetailRule
     * @return VouchersDetailRule    单个VouchersDetailRule
     * @throws Exception
     */
	public VouchersDetailRule findOneVouchersDetailRule(VouchersDetailRule vouchersDetailRule) throws Exception;



    /**
     * 查询统计 VouchersDetailRule
     * @param vouchersDetailRule
     * @return Integer    返回记录数VouchersDetailRule
     * @throws Exception
     */
	public Integer countVouchersDetailRule(VouchersDetailRule vouchersDetailRule) throws Exception;



    /**
     * 查询 VouchersDetailRule
     * @param vouchersDetailRule
     * @return List<VouchersDetailRule>    VouchersDetailRule集合 
     * @throws Exception
     */
	public List<VouchersDetailRule> findVouchersDetailRule(VouchersDetailRule vouchersDetailRule) throws Exception;



    /**
     * 查询 VouchersDetailRule
     * @param vouchersDetailRule
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<VouchersDetailRule>    VouchersDetailRule集合 
     * @throws Exception
     */
	public List<VouchersDetailRule> findVouchersDetailRule(VouchersDetailRule vouchersDetailRule, String order) throws Exception;



    /**
     * 分页查询 VouchersDetailRule
     * @param page
     * @param vouchersDetailRule
     * @return Page<VouchersDetailRule>    VouchersDetailRule分页结果 
     * @throws Exception 
     */
	public Page<VouchersDetailRule> findVouchersDetailRuleByPage(Page<VouchersDetailRule> page, VouchersDetailRule vouchersDetailRule) throws Exception;



}