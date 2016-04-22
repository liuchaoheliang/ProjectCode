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
 * @Title: ActiveSustainRelationMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.ActiveSustainRelation;

/**
 * 
 * <p>@Title: ActiveSustainRelationMapper.java</p>
 * <p>Description: 封装mybatis对MySQL映射的实体ActiveSustainRelation的CURD操作Mapper </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface ActiveSustainRelationMapper {


    /**
     * 增加 ActiveSustainRelation
     * @param activeSustainRelation
     * @return Long    受影响行数
     */
	public Long addActiveSustainRelation(ActiveSustainRelation activeSustainRelation);



    /**
     * 批量增加 ActiveSustainRelation
     * @param activeSustainRelationList
     * @return Boolean    是否成功
     */
	public Boolean addActiveSustainRelationByBatch(@Param("activeSustainRelationList") List<ActiveSustainRelation> activeSustainRelationList);



    /**
     * 删除 ActiveSustainRelation
     * @param activeSustainRelation
     * @return Integer    受影响行数
     */
	public Integer deleteActiveSustainRelation(ActiveSustainRelation activeSustainRelation);



    /**
     * 根据id删除一个 ActiveSustainRelation
     * @param id
     * @return Integer    受影响行数
     */
	public Integer deleteActiveSustainRelationById(Long id);

	  /**
     * 根据id删除 ActiveSustainRelation
     * @param id
     * @return Integer    受影响行数
     */
	public Integer deleteActiveSustainRelationByActiveId(String activeId);

    /**
     * 修改 ActiveSustainRelation
     * @param activeSustainRelation
     * @return Integer    受影响行数
     */
	public Integer updateActiveSustainRelation(ActiveSustainRelation activeSustainRelation);



    /**
     * 根据id修改一个 ActiveSustainRelation
     * @param activeSustainRelation
     * @return Integer    受影响行数
     */
	public Integer updateActiveSustainRelationById(ActiveSustainRelation activeSustainRelation);



    /**
     * 根据id查询一个 ActiveSustainRelation
     * @param id
     * @return ActiveSustainRelation    返回结果
     */
	public ActiveSustainRelation findActiveSustainRelationById(Long id);



    /**
     * 查询一个 ActiveSustainRelation
     * @param activeSustainRelation
     * @return ActiveSustainRelation    返回结果集
     */
	public ActiveSustainRelation findOneActiveSustainRelation(ActiveSustainRelation activeSustainRelation);



    /**
     * 统计 ActiveSustainRelation
     * @param activeSustainRelation
     * @return Integer    返回记录数
     */
	public Integer countActiveSustainRelation(ActiveSustainRelation activeSustainRelation);



    /**
     * 查询 ActiveSustainRelation
     * @param activeSustainRelation
     * @return List<ActiveSustainRelation>    返回结果集
     */
	public List<ActiveSustainRelation> findActiveSustainRelation(@Param("activeSustainRelation")ActiveSustainRelation activeSustainRelation, @Param("order")String order);



    /**
     * 分页查询 ActiveSustainRelation
     * @param page 
     * @param activeSustainRelation
     * @return List<ActiveSustainRelation>    返回分页查询结果集
     */
	public List<ActiveSustainRelation> findByPage(Page page, @Param("activeSustainRelation")ActiveSustainRelation activeSustainRelation);



}