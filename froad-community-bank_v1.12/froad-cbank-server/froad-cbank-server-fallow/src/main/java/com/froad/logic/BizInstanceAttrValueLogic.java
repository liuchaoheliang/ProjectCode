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
 * @Title: BizInstanceAttrValueLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.po.mysql.BizInstanceAttrValue;

/**
 * 
 * <p>@Title: BizInstanceAttrValueLogic.java</p>
 * <p>Description: 封装对BizInstanceAttrValue所有业务逻辑处理的接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface BizInstanceAttrValueLogic {


    /**
     * 增加 BizInstanceAttrValue
     * @param bizInstanceAttrValue
     * @return String    主键ID
     */
	public String addBizInstanceAttrValue(BizInstanceAttrValue bizInstanceAttrValue) ;



    /**
     * 删除 BizInstanceAttrValue
     * @param bizInstanceAttrValue
     * @return Boolean    是否成功
     */
	public Boolean deleteBizInstanceAttrValue(BizInstanceAttrValue bizInstanceAttrValue) ;



    /**
     * 根据instanceId删除 BizInstanceAttrValue
     * @param instanceId
     * @return Boolean    是否成功
     */
	public Boolean deleteBizInstanceAttrValueByInstanceId(String instanceId) ;



    /**
     * 修改 BizInstanceAttrValue
     * @param bizInstanceAttrValue
     * @return Boolean    是否成功
     */
	public Boolean updateBizInstanceAttrValue(BizInstanceAttrValue bizInstanceAttrValue) ;



    /**
     * 根据instanceId修改 BizInstanceAttrValue
     * @param bizInstanceAttrValue
     * @return Boolean    是否成功
     */
	public Boolean updateBizInstanceAttrValueByInstanceId(BizInstanceAttrValue bizInstanceAttrValue) ;



    /**
     * 根据instanceId查询单个 BizInstanceAttrValue
     * @param instanceId
     * @return BizInstanceAttrValue    单个 BizInstanceAttrValue
     */
	public BizInstanceAttrValue findBizInstanceAttrValueByInstanceId(String instanceId) ;



    /**
     * 查询一个 BizInstanceAttrValue
     * @param bizInstanceAttrValue
     * @return BizInstanceAttrValue    单个 BizInstanceAttrValue
     */
	public BizInstanceAttrValue findOneBizInstanceAttrValue(BizInstanceAttrValue bizInstanceAttrValue) ;



    /**
     * 统计 BizInstanceAttrValue
     * @param bizInstanceAttrValue
     * @return Integer    返回记录数 BizInstanceAttrValue
     */
	public Integer countBizInstanceAttrValue(BizInstanceAttrValue bizInstanceAttrValue) ;



    /**
     * 查询 BizInstanceAttrValue
     * @param bizInstanceAttrValue
     * @return List<BizInstanceAttrValue>    BizInstanceAttrValue集合 
     */
	public List<BizInstanceAttrValue> findBizInstanceAttrValue(BizInstanceAttrValue bizInstanceAttrValue) ;



    /**
     * 分页查询 BizInstanceAttrValue
     * @param page
     * @param bizInstanceAttrValue
     * @return Page<BizInstanceAttrValue>    BizInstanceAttrValue分页结果 
     */
	public Page<BizInstanceAttrValue> findBizInstanceAttrValueByPage(Page<BizInstanceAttrValue> page, BizInstanceAttrValue bizInstanceAttrValue) ;



}