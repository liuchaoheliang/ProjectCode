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
 * @Title: VouchersDetailRuleLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.po.VouchersDetailRule;

/**
 * 
 * <p>@Title: VouchersDetailRuleLogic.java</p>
 * <p>Description: 封装对VouchersDetailRule所有业务逻辑处理的接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface VouchersDetailRuleLogic {


    /**
     * 增加 VouchersDetailRule
     * @param vouchersDetailRule
     * @return Long    主键ID
     */
	public Long addVouchersDetailRule(VouchersDetailRule vouchersDetailRule) ;



    /**
     * 删除 VouchersDetailRule
     * @param vouchersDetailRule
     * @return Boolean    是否成功
     */
	public Boolean deleteVouchersDetailRule(VouchersDetailRule vouchersDetailRule) ;



    /**
     * 根据id删除 VouchersDetailRule
     * @param id
     * @return Boolean    是否成功
     */
	public Boolean deleteVouchersDetailRuleById(Long id) ;



    /**
     * 修改 VouchersDetailRule
     * @param vouchersDetailRule
     * @return Boolean    是否成功
     */
	public Boolean updateVouchersDetailRule(VouchersDetailRule vouchersDetailRule) ;



    /**
     * 根据id修改 VouchersDetailRule
     * @param vouchersDetailRule
     * @return Boolean    是否成功
     */
	public Boolean updateVouchersDetailRuleById(VouchersDetailRule vouchersDetailRule) ;



    /**
     * 根据id查询单个 VouchersDetailRule
     * @param id
     * @return VouchersDetailRule    单个 VouchersDetailRule
     */
	public VouchersDetailRule findVouchersDetailRuleById(Long id) ;



    /**
     * 查询一个 VouchersDetailRule
     * @param vouchersDetailRule
     * @return VouchersDetailRule    单个 VouchersDetailRule
     */
	public VouchersDetailRule findOneVouchersDetailRule(VouchersDetailRule vouchersDetailRule) ;



    /**
     * 统计 VouchersDetailRule
     * @param vouchersDetailRule
     * @return Integer    返回记录数 VouchersDetailRule
     */
	public Integer countVouchersDetailRule(VouchersDetailRule vouchersDetailRule) ;



    /**
     * 查询 VouchersDetailRule
     * @param vouchersDetailRule
     * @return List<VouchersDetailRule>    VouchersDetailRule集合 
     */
	public List<VouchersDetailRule> findVouchersDetailRule(VouchersDetailRule vouchersDetailRule) ;



    /**
     * 分页查询 VouchersDetailRule
     * @param page
     * @param vouchersDetailRule
     * @return Page<VouchersDetailRule>    VouchersDetailRule分页结果 
     */
	public Page<VouchersDetailRule> findVouchersDetailRuleByPage(Page<VouchersDetailRule> page, VouchersDetailRule vouchersDetailRule) ;



}