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
 * @Title: VouchersDetailRuleMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.VouchersDetailRule;

/**
 * 
 * <p>@Title: VouchersDetailRuleMapper.java</p>
 * <p>Description: 封装mybatis对MySQL映射的实体VouchersDetailRule的CURD操作Mapper </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface VouchersDetailRuleMapper {


    /**
     * 增加 VouchersDetailRule
     * @param vouchersDetailRule
     * @return Long    受影响行数
     */
	public Long addVouchersDetailRule(VouchersDetailRule vouchersDetailRule);



    /**
     * 批量增加 VouchersDetailRule
     * @param vouchersDetailRuleList
     * @return Boolean    是否成功
     */
	public Boolean addVouchersDetailRuleByBatch(@Param("vouchersDetailRuleList") List<VouchersDetailRule> vouchersDetailRuleList);



    /**
     * 删除 VouchersDetailRule
     * @param vouchersDetailRule
     * @return Integer    受影响行数
     */
	public Integer deleteVouchersDetailRule(VouchersDetailRule vouchersDetailRule);



    /**
     * 根据id删除一个 VouchersDetailRule
     * @param id
     * @return Integer    受影响行数
     */
	public Integer deleteVouchersDetailRuleById(Long id);



    /**
     * 修改 VouchersDetailRule
     * @param vouchersDetailRule
     * @return Integer    受影响行数
     */
	public Integer updateVouchersDetailRule(VouchersDetailRule vouchersDetailRule);



    /**
     * 根据id修改一个 VouchersDetailRule
     * @param vouchersDetailRule
     * @return Integer    受影响行数
     */
	public Integer updateVouchersDetailRuleById(VouchersDetailRule vouchersDetailRule);



    /**
     * 根据id查询一个 VouchersDetailRule
     * @param id
     * @return VouchersDetailRule    返回结果
     */
	public VouchersDetailRule findVouchersDetailRuleById(Long id);



    /**
     * 查询一个 VouchersDetailRule
     * @param vouchersDetailRule
     * @return VouchersDetailRule    返回结果集
     */
	public VouchersDetailRule findOneVouchersDetailRule(VouchersDetailRule vouchersDetailRule);



    /**
     * 统计 VouchersDetailRule
     * @param vouchersDetailRule
     * @return Integer    返回记录数
     */
	public Integer countVouchersDetailRule(VouchersDetailRule vouchersDetailRule);



    /**
     * 查询 VouchersDetailRule
     * @param vouchersDetailRule
     * @return List<VouchersDetailRule>    返回结果集
     */
	public List<VouchersDetailRule> findVouchersDetailRule(@Param("vouchersDetailRule")VouchersDetailRule vouchersDetailRule, @Param("order")String order);



    /**
     * 分页查询 VouchersDetailRule
     * @param page 
     * @param vouchersDetailRule
     * @return List<VouchersDetailRule>    返回分页查询结果集
     */
	public List<VouchersDetailRule> findByPage(Page page, @Param("vouchersDetailRule")VouchersDetailRule vouchersDetailRule);



}