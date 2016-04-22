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
 * @Title: ActiveDetailRuleHandler.java
 * @Package com.froad.handler
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.handler;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.bean.Page;
import com.froad.po.ActiveDetailRule;

/**
 * 
 * <p>@Title: ActiveDetailRuleHandler.java</p>
 * <p>Description: 封装对MySQL对应的实体ActiveDetailRule的CURD操作接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface ActiveDetailRuleHandler {


    /**
     * 增加 ActiveDetailRule
     * @param activeDetailRule
     * @return Long    主键ID
     * @throws Exception
     */
	public Long addActiveDetailRule(ActiveDetailRule activeDetailRule)  throws Exception;



    /**
     * 增加 ActiveDetailRule
     * @param sqlSession
     * @param activeDetailRule
     * @return Long    主键ID
     * @throws Exception
     */
	public Long addActiveDetailRule(SqlSession sqlSession, ActiveDetailRule activeDetailRule) throws Exception;



    /**
     * 删除 ActiveDetailRule
     * @param activeDetailRule
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteActiveDetailRule(ActiveDetailRule activeDetailRule) throws Exception;



    /**
     * 删除 ActiveDetailRule
     * @param sqlSession
     * @param activeDetailRule
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteActiveDetailRule(SqlSession sqlSession, ActiveDetailRule activeDetailRule) throws Exception;



    /**
     * 根据id删除 ActiveDetailRule
     * @param id
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteActiveDetailRuleById(Long id) throws Exception;



    /**
     * 根据id删除 ActiveDetailRule
     * @param id
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteActiveDetailRuleById(SqlSession sqlSession, Long id) throws Exception;



    /**
     * 修改 ActiveDetailRule
     * @param activeDetailRule
     * @return Boolean    受影响行数
     * @throws Exception
     */
	public Integer updateActiveDetailRule(ActiveDetailRule activeDetailRule) throws Exception;



    /**
     * 修改 ActiveDetailRule
     * @param sqlSession
     * @param activeDetailRule
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateActiveDetailRule(SqlSession sqlSession, ActiveDetailRule activeDetailRule) throws Exception;



    /**
     * 根据id修改 ActiveDetailRule
     * @param ActiveDetailRule
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateActiveDetailRuleById(ActiveDetailRule activeDetailRule) throws Exception;



    /**
     * 根据id修改 ActiveDetailRule
     * @param sqlSession
     * @param activeDetailRule
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateActiveDetailRuleById(SqlSession sqlSession, ActiveDetailRule activeDetailRule) throws Exception;



    /**
     * 根据id查询单个 ActiveDetailRule
     * @param id
     * @return ActiveDetailRule    单个ActiveDetailRule
     * @throws Exception
     */
	public ActiveDetailRule findActiveDetailRuleById(Long id) throws Exception;



    /**
     * 查询一个 ActiveDetailRule
     * @param activeDetailRule
     * @return ActiveDetailRule    单个ActiveDetailRule
     * @throws Exception
     */
	public ActiveDetailRule findOneActiveDetailRule(ActiveDetailRule activeDetailRule) throws Exception;



    /**
     * 查询统计 ActiveDetailRule
     * @param activeDetailRule
     * @return Integer    返回记录数ActiveDetailRule
     * @throws Exception
     */
	public Integer countActiveDetailRule(ActiveDetailRule activeDetailRule) throws Exception;



    /**
     * 查询 ActiveDetailRule
     * @param activeDetailRule
     * @return List<ActiveDetailRule>    ActiveDetailRule集合 
     * @throws Exception
     */
	public List<ActiveDetailRule> findActiveDetailRule(ActiveDetailRule activeDetailRule) throws Exception;



    /**
     * 查询 ActiveDetailRule
     * @param activeDetailRule
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<ActiveDetailRule>    ActiveDetailRule集合 
     * @throws Exception
     */
	public List<ActiveDetailRule> findActiveDetailRule(ActiveDetailRule activeDetailRule, String order) throws Exception;



    /**
     * 分页查询 ActiveDetailRule
     * @param page
     * @param activeDetailRule
     * @return Page<ActiveDetailRule>    ActiveDetailRule分页结果 
     * @throws Exception 
     */
	public Page<ActiveDetailRule> findActiveDetailRuleByPage(Page<ActiveDetailRule> page, ActiveDetailRule activeDetailRule) throws Exception;

	public Integer deleteActiveBaseRuleByActiveId(String activeId) throws Exception;

}