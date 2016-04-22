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
 * @Title: RegistDetailRuleMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.RegistDetailRule;


/**
 * 
 * <p>@Title: RegistDetailRuleMapper.java</p>
 * <p>Description: 封装mybatis对MySQL映射的实体RegistDetailRule的CURD操作Mapper </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface RegistDetailRuleMapper {


    /**
     * 增加 RegistDetailRule
     * @param registDetailRule
     * @return Long    受影响行数
     */
	public Long addRegistDetailRule(RegistDetailRule registDetailRule);



    /**
     * 批量增加 RegistDetailRule
     * @param registDetailRuleList
     * @return Boolean    是否成功
     */
	public Boolean addRegistDetailRuleByBatch(@Param("registDetailRuleList") List<RegistDetailRule> registDetailRuleList);



    /**
     * 删除 RegistDetailRule
     * @param registDetailRule
     * @return Integer    受影响行数
     */
	public Integer deleteRegistDetailRule(RegistDetailRule registDetailRule);



    /**
     * 根据id删除一个 RegistDetailRule
     * @param id
     * @return Integer    受影响行数
     */
	public Integer deleteRegistDetailRuleById(Long id);



    /**
     * 修改 RegistDetailRule
     * @param registDetailRule
     * @return Integer    受影响行数
     */
	public Integer updateRegistDetailRule(RegistDetailRule registDetailRule);



    /**
     * 根据id修改一个 RegistDetailRule
     * @param registDetailRule
     * @return Integer    受影响行数
     */
	public Integer updateRegistDetailRuleById(RegistDetailRule registDetailRule);



    /**
     * 根据id查询一个 RegistDetailRule
     * @param id
     * @return RegistDetailRule    返回结果
     */
	public RegistDetailRule findRegistDetailRuleById(Long id);



    /**
     * 查询一个 RegistDetailRule
     * @param registDetailRule
     * @return RegistDetailRule    返回结果集
     */
	public RegistDetailRule findOneRegistDetailRule(RegistDetailRule registDetailRule);



    /**
     * 统计 RegistDetailRule
     * @param registDetailRule
     * @return Integer    返回记录数
     */
	public Integer countRegistDetailRule(RegistDetailRule registDetailRule);



    /**
     * 查询 RegistDetailRule
     * @param registDetailRule
     * @return List<RegistDetailRule>    返回结果集
     */
	public List<RegistDetailRule> findRegistDetailRule(@Param("registDetailRule")RegistDetailRule registDetailRule, @Param("order")String order);



    /**
     * 分页查询 RegistDetailRule
     * @param page 
     * @param registDetailRule
     * @return List<RegistDetailRule>    返回分页查询结果集
     */
	public List<RegistDetailRule> findByPage(Page page, @Param("registDetailRule")RegistDetailRule registDetailRule);

	/**
	 * 查询当前客户端有效的注册送规则
	 * @param clientId
	 * */
	public RegistDetailRule findNowEffectiveRegisteredHandsel(String clientId);
}