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
 * @Title: RegistDetailRuleLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.po.RegistDetailRule;

/**
 * 
 * <p>@Title: RegistDetailRuleLogic.java</p>
 * <p>Description: 封装对RegistDetailRule所有业务逻辑处理的接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface RegistDetailRuleLogic {


    /**
     * 增加 RegistDetailRule
     * @param registDetailRule
     * @return Long    主键ID
     */
	public Long addRegistDetailRule(RegistDetailRule registDetailRule) ;



    /**
     * 删除 RegistDetailRule
     * @param registDetailRule
     * @return Boolean    是否成功
     */
	public Boolean deleteRegistDetailRule(RegistDetailRule registDetailRule) ;



    /**
     * 根据id删除 RegistDetailRule
     * @param id
     * @return Boolean    是否成功
     */
	public Boolean deleteRegistDetailRuleById(Long id) ;



    /**
     * 修改 RegistDetailRule
     * @param registDetailRule
     * @return Boolean    是否成功
     */
	public Boolean updateRegistDetailRule(RegistDetailRule registDetailRule) ;



    /**
     * 根据id修改 RegistDetailRule
     * @param registDetailRule
     * @return Boolean    是否成功
     */
	public Boolean updateRegistDetailRuleById(RegistDetailRule registDetailRule) ;



    /**
     * 根据id查询单个 RegistDetailRule
     * @param id
     * @return RegistDetailRule    单个 RegistDetailRule
     */
	public RegistDetailRule findRegistDetailRuleById(Long id) ;



    /**
     * 查询一个 RegistDetailRule
     * @param registDetailRule
     * @return RegistDetailRule    单个 RegistDetailRule
     */
	public RegistDetailRule findOneRegistDetailRule(RegistDetailRule registDetailRule) ;



    /**
     * 统计 RegistDetailRule
     * @param registDetailRule
     * @return Integer    返回记录数 RegistDetailRule
     */
	public Integer countRegistDetailRule(RegistDetailRule registDetailRule) ;



    /**
     * 查询 RegistDetailRule
     * @param registDetailRule
     * @return List<RegistDetailRule>    RegistDetailRule集合 
     */
	public List<RegistDetailRule> findRegistDetailRule(RegistDetailRule registDetailRule) ;



    /**
     * 分页查询 RegistDetailRule
     * @param page
     * @param registDetailRule
     * @return Page<RegistDetailRule>    RegistDetailRule分页结果 
     */
	public Page<RegistDetailRule> findRegistDetailRuleByPage(Page<RegistDetailRule> page, RegistDetailRule registDetailRule) ;



}