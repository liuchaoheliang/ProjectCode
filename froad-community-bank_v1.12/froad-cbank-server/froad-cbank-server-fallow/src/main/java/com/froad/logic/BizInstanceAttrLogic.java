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
 * @Title: BizInstanceAttrLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.po.mysql.BizInstanceAttr;

/**
 * 
 * <p>@Title: BizInstanceAttrLogic.java</p>
 * <p>Description: 封装对BizInstanceAttr所有业务逻辑处理的接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface BizInstanceAttrLogic {


    /**
     * 增加 BizInstanceAttr
     * @param bizInstanceAttr
     * @return Long    主键ID
     */
	public Long addBizInstanceAttr(BizInstanceAttr bizInstanceAttr) ;



    /**
     * 删除 BizInstanceAttr
     * @param bizInstanceAttr
     * @return Boolean    是否成功
     */
	public Boolean deleteBizInstanceAttr(BizInstanceAttr bizInstanceAttr) ;



    /**
     * 根据id删除 BizInstanceAttr
     * @param id
     * @return Boolean    是否成功
     */
	public Boolean deleteBizInstanceAttrById(Long id) ;



    /**
     * 修改 BizInstanceAttr
     * @param bizInstanceAttr
     * @return Boolean    是否成功
     */
	public Boolean updateBizInstanceAttr(BizInstanceAttr bizInstanceAttr) ;



    /**
     * 根据id修改 BizInstanceAttr
     * @param bizInstanceAttr
     * @return Boolean    是否成功
     */
	public Boolean updateBizInstanceAttrById(BizInstanceAttr bizInstanceAttr) ;



    /**
     * 根据id查询单个 BizInstanceAttr
     * @param id
     * @return BizInstanceAttr    单个 BizInstanceAttr
     */
	public BizInstanceAttr findBizInstanceAttrById(Long id) ;



    /**
     * 查询一个 BizInstanceAttr
     * @param bizInstanceAttr
     * @return BizInstanceAttr    单个 BizInstanceAttr
     */
	public BizInstanceAttr findOneBizInstanceAttr(BizInstanceAttr bizInstanceAttr) ;



    /**
     * 统计 BizInstanceAttr
     * @param bizInstanceAttr
     * @return Integer    返回记录数 BizInstanceAttr
     */
	public Integer countBizInstanceAttr(BizInstanceAttr bizInstanceAttr) ;



    /**
     * 查询 BizInstanceAttr
     * @param bizInstanceAttr
     * @return List<BizInstanceAttr>    BizInstanceAttr集合 
     */
	public List<BizInstanceAttr> findBizInstanceAttr(BizInstanceAttr bizInstanceAttr) ;



    /**
     * 分页查询 BizInstanceAttr
     * @param page
     * @param bizInstanceAttr
     * @return Page<BizInstanceAttr>    BizInstanceAttr分页结果 
     */
	public Page<BizInstanceAttr> findBizInstanceAttrByPage(Page<BizInstanceAttr> page, BizInstanceAttr bizInstanceAttr) ;



}