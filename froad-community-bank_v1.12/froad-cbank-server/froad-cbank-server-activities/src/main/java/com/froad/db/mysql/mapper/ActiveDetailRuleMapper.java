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
 * @Title: ActiveDetailRuleMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.ActiveDetailRule;

/**
 * 
 * <p>@Title: ActiveDetailRuleMapper.java</p>
 * <p>Description: 封装mybatis对MySQL映射的实体ActiveDetailRule的CURD操作Mapper </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface ActiveDetailRuleMapper {


    /**
     * 增加 ActiveDetailRule
     * @param activeDetailRule
     * @return Long    受影响行数
     */
	public Long addActiveDetailRule(ActiveDetailRule activeDetailRule);



    /**
     * 批量增加 ActiveDetailRule
     * @param activeDetailRuleList
     * @return Boolean    是否成功
     */
	public Boolean addActiveDetailRuleByBatch(List<ActiveDetailRule> activeDetailRuleList);



    /**
     * 删除 ActiveDetailRule
     * @param activeDetailRule
     * @return Integer    受影响行数
     */
	public Integer deleteActiveDetailRule(ActiveDetailRule activeDetailRule);



    /**
     * 根据id删除一个 ActiveDetailRule
     * @param id
     * @return Integer    受影响行数
     */
	public Integer deleteActiveDetailRuleById(Long id);



    /**
     * 修改 ActiveDetailRule
     * @param activeDetailRule
     * @return Integer    受影响行数
     */
	public Integer updateActiveDetailRule(ActiveDetailRule activeDetailRule);



    /**
     * 根据id修改一个 ActiveDetailRule
     * @param activeDetailRule
     * @return Integer    受影响行数
     */
	public Integer updateActiveDetailRuleById(ActiveDetailRule activeDetailRule);

    /**
     * 根据id修改一个 ActiveDetailRule
     * @param activeDetailRule
     * @return Integer    受影响行数
     */
	public Integer updateActiveDetailRuleByActiveId(ActiveDetailRule activeDetailRule);


    /**
     * 根据id查询一个 ActiveDetailRule
     * @param id
     * @return ActiveDetailRule    返回结果
     */
	public ActiveDetailRule findActiveDetailRuleById(Long id);

    /**
     * 根据id查询一个 ActiveDetailRule
     * @param activeId
     * @return ActiveBaseRule    返回结果
     */	
	public ActiveDetailRule findActiveDetailRuleByActiveId(@Param("activeId") String activeId);


    /**
     * 查询一个 ActiveDetailRule
     * @param activeDetailRule
     * @return ActiveDetailRule    返回结果集
     */
	public ActiveDetailRule findOneActiveDetailRule(ActiveDetailRule activeDetailRule);



    /**
     * 统计 ActiveDetailRule
     * @param activeDetailRule
     * @return Integer    返回记录数
     */
	public Integer countActiveDetailRule(ActiveDetailRule activeDetailRule);



    /**
     * 查询 ActiveDetailRule
     * @param activeDetailRule
     * @return List<ActiveDetailRule>    返回结果集
     */
	public List<ActiveDetailRule> findActiveDetailRule(@Param("activeDetailRule")ActiveDetailRule activeDetailRule, @Param("order")String order);



    /**
     * 分页查询 ActiveDetailRule
     * @param page 
     * @param activeDetailRule
     * @return List<ActiveDetailRule>    返回分页查询结果集
     */
	public List<ActiveDetailRule> findByPage(Page page, @Param("activeDetailRule")ActiveDetailRule activeDetailRule);



}