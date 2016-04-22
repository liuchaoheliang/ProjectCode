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
 * @Title: ActiveSustainRelationLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.po.ActiveSustainRelation;

/**
 * 
 * <p>@Title: ActiveSustainRelationLogic.java</p>
 * <p>Description: 封装对ActiveSustainRelation所有业务逻辑处理的接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface ActiveSustainRelationLogic {


    /**
     * 增加 ActiveSustainRelation
     * @param activeSustainRelation
     * @return Long    主键ID
     */
	public Long addActiveSustainRelation(ActiveSustainRelation activeSustainRelation) ;



    /**
     * 删除 ActiveSustainRelation
     * @param activeSustainRelation
     * @return Boolean    是否成功
     */
	public Boolean deleteActiveSustainRelation(ActiveSustainRelation activeSustainRelation) ;



    /**
     * 根据id删除 ActiveSustainRelation
     * @param id
     * @return Boolean    是否成功
     */
	public Boolean deleteActiveSustainRelationById(Long id) ;



    /**
     * 修改 ActiveSustainRelation
     * @param activeSustainRelation
     * @return Boolean    是否成功
     */
	public Boolean updateActiveSustainRelation(ActiveSustainRelation activeSustainRelation) ;



    /**
     * 根据id修改 ActiveSustainRelation
     * @param activeSustainRelation
     * @return Boolean    是否成功
     */
	public Boolean updateActiveSustainRelationById(ActiveSustainRelation activeSustainRelation) ;



    /**
     * 根据id查询单个 ActiveSustainRelation
     * @param id
     * @return ActiveSustainRelation    单个 ActiveSustainRelation
     */
	public ActiveSustainRelation findActiveSustainRelationById(Long id) ;



    /**
     * 查询一个 ActiveSustainRelation
     * @param activeSustainRelation
     * @return ActiveSustainRelation    单个 ActiveSustainRelation
     */
	public ActiveSustainRelation findOneActiveSustainRelation(ActiveSustainRelation activeSustainRelation) ;



    /**
     * 统计 ActiveSustainRelation
     * @param activeSustainRelation
     * @return Integer    返回记录数 ActiveSustainRelation
     */
	public Integer countActiveSustainRelation(ActiveSustainRelation activeSustainRelation) ;



    /**
     * 查询 ActiveSustainRelation
     * @param activeSustainRelation
     * @return List<ActiveSustainRelation>    ActiveSustainRelation集合 
     */
	public List<ActiveSustainRelation> findActiveSustainRelation(ActiveSustainRelation activeSustainRelation) ;



    /**
     * 分页查询 ActiveSustainRelation
     * @param page
     * @param activeSustainRelation
     * @return Page<ActiveSustainRelation>    ActiveSustainRelation分页结果 
     */
	public Page<ActiveSustainRelation> findActiveSustainRelationByPage(Page<ActiveSustainRelation> page, ActiveSustainRelation activeSustainRelation) ;



}