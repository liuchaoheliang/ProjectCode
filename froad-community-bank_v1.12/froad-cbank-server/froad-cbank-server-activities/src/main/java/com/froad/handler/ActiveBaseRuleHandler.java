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
 * @Title: ActiveBaseRuleHandler.java
 * @Package com.froad.handler
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.handler;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.bean.ResultBean;
import com.froad.po.ActiveBaseRule;

/**
 * 
 * <p>@Title: ActiveBaseRuleHandler.java</p>
 * <p>Description: 封装对MySQL对应的实体ActiveBaseRule的CURD操作接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface ActiveBaseRuleHandler {


    /**
     * 增加 ActiveBaseRule
     * @param activeBaseRule
     * @return Long    主键ID
     * @throws Exception
     */
	public Long addActiveBaseRule(ActiveBaseRule activeBaseRule)  throws Exception;



    /**
     * 增加 ActiveBaseRule
     * @param sqlSession
     * @param activeBaseRule
     * @return Long    主键ID
     * @throws Exception
     */
	public Long addActiveBaseRule(SqlSession sqlSession, ActiveBaseRule activeBaseRule) throws Exception;



    /**
     * 删除 ActiveBaseRule
     * @param activeBaseRule
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteActiveBaseRule(ActiveBaseRule activeBaseRule) throws Exception;



    /**
     * 删除 ActiveBaseRule
     * @param sqlSession
     * @param activeBaseRule
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteActiveBaseRule(SqlSession sqlSession, ActiveBaseRule activeBaseRule) throws Exception;



    /**
     * 根据id删除 ActiveBaseRule
     * @param id
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteActiveBaseRuleById(Long id) throws Exception;

    /**
     * 根据id禁止 ActiveBaseRule
     * @param id
     * @return Integer    受影响行数
     * @throws Exception
     */
	public ResultBean disableActiveBaseRuleByActiveId(String clientId, String activeId, String operator) throws Exception;


    /**
     * 根据id删除 ActiveBaseRule
     * @param id
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteActiveBaseRuleById(SqlSession sqlSession, Long id) throws Exception;

    /**
     * 根据id禁用 ActiveBaseRule
     * @param id
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer disableActiveBaseRuleByActiveId(SqlSession sqlSession, String clientId, String activeId, String operator) throws Exception;


    /**
     * 修改 ActiveBaseRule
     * @param activeBaseRule
     * @return Boolean    受影响行数
     * @throws Exception
     */
	public Integer updateActiveBaseRule(ActiveBaseRule activeBaseRule) throws Exception;



    /**
     * 修改 ActiveBaseRule
     * @param sqlSession
     * @param activeBaseRule
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateActiveBaseRule(SqlSession sqlSession, ActiveBaseRule activeBaseRule) throws Exception;



    /**
     * 根据id修改 ActiveBaseRule
     * @param ActiveBaseRule
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateActiveBaseRuleById(ActiveBaseRule activeBaseRule) throws Exception;



    /**
     * 根据id修改 ActiveBaseRule
     * @param sqlSession
     * @param activeBaseRule
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateActiveBaseRuleById(SqlSession sqlSession, ActiveBaseRule activeBaseRule) throws Exception;



    /**
     * 根据id查询单个 ActiveBaseRule
     * @param id
     * @return ActiveBaseRule    单个ActiveBaseRule
     * @throws Exception
     */
	public ActiveBaseRule findActiveBaseRuleById(Long id) throws Exception;



    /**
     * 查询一个 ActiveBaseRule
     * @param activeBaseRule
     * @return ActiveBaseRule    单个ActiveBaseRule
     * @throws Exception
     */
	public ActiveBaseRule findOneActiveBaseRule(ActiveBaseRule activeBaseRule) throws Exception;

	public ActiveBaseRule findOneActiveBaseRuleByActiveNameAndClientId(ActiveBaseRule activeBaseRule);

	 /**
	  * @Title: findOneActiveBaseRuleByActiveId
	  * @Description: 根据活动ID获取基础信息
	  * @author: shenshaocheng 2015年12月15日
	  * @modify: shenshaocheng 2015年12月15日
	  * @param activeBaseRule 查询条件
	  * @return
	 */	
	public ActiveBaseRule findOneActiveBaseRuleByActiveId(ActiveBaseRule activeBaseRule);
	
    /**
     * 查询统计 ActiveBaseRule
     * @param activeBaseRule
     * @return Integer    返回记录数ActiveBaseRule
     * @throws Exception
     */
	public Integer countActiveBaseRule(ActiveBaseRule activeBaseRule) throws Exception;



    /**
     * 查询 ActiveBaseRule
     * @param activeBaseRule
     * @return List<ActiveBaseRule>    ActiveBaseRule集合 
     * @throws Exception
     */
	public List<ActiveBaseRule> findActiveBaseRule(ActiveBaseRule activeBaseRule) throws Exception;

	 /**
	  * @Title: findSuntainActiveBaseRule
	  * @Description: 获取支持的活动（满额优惠、打折）
	  * @author: shenshaocheng 2015年12月7日
	  * @modify: shenshaocheng 2015年12月7日
	  * @param activeBaseRule  查询条件
	  * @return
	 */	
	public List<ActiveBaseRule> findSuntainActiveBaseRule(ActiveBaseRule activeBaseRule);

    /**
     * 查询 ActiveBaseRule
     * @param activeBaseRule
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<ActiveBaseRule>    ActiveBaseRule集合 
     * @throws Exception
     */
	public List<ActiveBaseRule> findActiveBaseRule(ActiveBaseRule activeBaseRule, String order) throws Exception;



    /**
     * 分页查询 ActiveBaseRule
     * @param page
     * @param activeBaseRule
     * @return Page<ActiveBaseRule>    ActiveBaseRule分页结果 
     * @throws Exception 
     */
	public Page<ActiveBaseRule> findActiveBaseRuleByPage(Page<ActiveBaseRule> page, ActiveBaseRule activeBaseRule) throws Exception;


}