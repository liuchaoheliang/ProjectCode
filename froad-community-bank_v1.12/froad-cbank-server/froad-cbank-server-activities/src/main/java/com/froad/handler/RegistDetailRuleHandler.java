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
 * @Title: RegistDetailRuleHandler.java
 * @Package com.froad.handler
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.handler;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.bean.Page;
import com.froad.po.RegistDetailRule;

/**
 * 
 * <p>@Title: RegistDetailRuleHandler.java</p>
 * <p>Description: 封装对MySQL对应的实体RegistDetailRule的CURD操作接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface RegistDetailRuleHandler {


    /**
     * 增加 RegistDetailRule
     * @param registDetailRule
     * @return Long    主键ID
     * @throws Exception
     */
	public Long addRegistDetailRule(RegistDetailRule registDetailRule)  throws Exception;



    /**
     * 增加 RegistDetailRule
     * @param sqlSession
     * @param registDetailRule
     * @return Long    主键ID
     * @throws Exception
     */
	public Long addRegistDetailRule(SqlSession sqlSession, RegistDetailRule registDetailRule) throws Exception;



    /**
     * 删除 RegistDetailRule
     * @param registDetailRule
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteRegistDetailRule(RegistDetailRule registDetailRule) throws Exception;



    /**
     * 删除 RegistDetailRule
     * @param sqlSession
     * @param registDetailRule
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteRegistDetailRule(SqlSession sqlSession, RegistDetailRule registDetailRule) throws Exception;



    /**
     * 根据id删除 RegistDetailRule
     * @param id
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteRegistDetailRuleById(Long id) throws Exception;



    /**
     * 根据id删除 RegistDetailRule
     * @param id
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteRegistDetailRuleById(SqlSession sqlSession, Long id) throws Exception;



    /**
     * 修改 RegistDetailRule
     * @param registDetailRule
     * @return Boolean    受影响行数
     * @throws Exception
     */
	public Integer updateRegistDetailRule(RegistDetailRule registDetailRule) throws Exception;



    /**
     * 修改 RegistDetailRule
     * @param sqlSession
     * @param registDetailRule
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateRegistDetailRule(SqlSession sqlSession, RegistDetailRule registDetailRule) throws Exception;



    /**
     * 根据id修改 RegistDetailRule
     * @param RegistDetailRule
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateRegistDetailRuleById(RegistDetailRule registDetailRule) throws Exception;



    /**
     * 根据id修改 RegistDetailRule
     * @param sqlSession
     * @param registDetailRule
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateRegistDetailRuleById(SqlSession sqlSession, RegistDetailRule registDetailRule) throws Exception;



    /**
     * 根据id查询单个 RegistDetailRule
     * @param id
     * @return RegistDetailRule    单个RegistDetailRule
     * @throws Exception
     */
	public RegistDetailRule findRegistDetailRuleById(Long id) throws Exception;



    /**
     * 查询一个 RegistDetailRule
     * @param registDetailRule
     * @return RegistDetailRule    单个RegistDetailRule
     * @throws Exception
     */
	public RegistDetailRule findOneRegistDetailRule(RegistDetailRule registDetailRule) throws Exception;



    /**
     * 查询统计 RegistDetailRule
     * @param registDetailRule
     * @return Integer    返回记录数RegistDetailRule
     * @throws Exception
     */
	public Integer countRegistDetailRule(RegistDetailRule registDetailRule) throws Exception;



    /**
     * 查询 RegistDetailRule
     * @param registDetailRule
     * @return List<RegistDetailRule>    RegistDetailRule集合 
     * @throws Exception
     */
	public List<RegistDetailRule> findRegistDetailRule(RegistDetailRule registDetailRule) throws Exception;



    /**
     * 查询 RegistDetailRule
     * @param registDetailRule
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<RegistDetailRule>    RegistDetailRule集合 
     * @throws Exception
     */
	public List<RegistDetailRule> findRegistDetailRule(RegistDetailRule registDetailRule, String order) throws Exception;



    /**
     * 分页查询 RegistDetailRule
     * @param page
     * @param registDetailRule
     * @return Page<RegistDetailRule>    RegistDetailRule分页结果 
     * @throws Exception 
     */
	public Page<RegistDetailRule> findRegistDetailRuleByPage(Page<RegistDetailRule> page, RegistDetailRule registDetailRule) throws Exception;


	/**
	 * 查询当前客户端有效的注册送规则
	 * @param clientId
	 * */
	RegistDetailRule findNowEffectiveRegisteredHandsel(String clientId) throws Exception;
	
}