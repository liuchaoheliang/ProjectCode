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
 * @Title: ActiveTagRelationMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.ActiveTagRelation;

/**
 * 
 * <p>@Title: ActiveTagRelationMapper.java</p>
 * <p>Description: 封装mybatis对MySQL映射的实体ActiveTagRelation的CURD操作Mapper </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface ActiveTagRelationMapper {


    /**
     * 增加 ActiveTagRelation
     * @param activeTagRelation
     * @return Long    受影响行数
     */
	public Long addActiveTagRelation(ActiveTagRelation activeTagRelation);



    /**
     * 批量增加 ActiveTagRelation
     * @param activeTagRelationList
     * @return Boolean    是否成功
     */
	public Boolean addActiveTagRelationByBatch(List<ActiveTagRelation> activeTagRelationList);



    /**
     * 删除 ActiveTagRelation
     * @param activeTagRelation
     * @return Integer    受影响行数
     */
	public Integer deleteActiveTagRelation(ActiveTagRelation activeTagRelation);



    /**
     * 根据id删除一个 ActiveTagRelation
     * @param id
     * @return Integer    受影响行数
     */
	public Integer deleteActiveTagRelationById(Long id);



    /**
     * 修改 ActiveTagRelation
     * @param activeTagRelation
     * @return Integer    受影响行数
     */
	public Integer updateActiveTagRelation(ActiveTagRelation activeTagRelation);



    /**
     * 根据id修改一个 ActiveTagRelation
     * @param activeTagRelation
     * @return Integer    受影响行数
     */
	public Integer updateActiveTagRelationById(ActiveTagRelation activeTagRelation);

    /**
     * 根据id修改一个 ActiveTagRelation
     * @param activeTagRelation
     * @return Integer    受影响行数
     */
	public Integer updateActiveTagRelationByActiveId(ActiveTagRelation activeTagRelation);

	

    /**
     * 根据id查询一个 ActiveTagRelation
     * @param id
     * @return ActiveTagRelation    返回结果
     */
	public ActiveTagRelation findActiveTagRelationById(Long id);


    /**
     * 根据id查询一个 ActiveTagRelation
     * @param activeId
     * @return ActiveBaseRule    返回结果
     */	
	public ActiveTagRelation findActiveTagRelationByActiveId(@Param("activeId") String activeId);

	
    /**
     * 查询一个 ActiveTagRelation
     * @param activeTagRelation
     * @return ActiveTagRelation    返回结果集
     */
	public ActiveTagRelation findOneActiveTagRelation(ActiveTagRelation activeTagRelation);



    /**
     * 统计 ActiveTagRelation
     * @param activeTagRelation
     * @return Integer    返回记录数
     */
	public Integer countActiveTagRelation(ActiveTagRelation activeTagRelation);



    /**
     * 查询 ActiveTagRelation
     * @param activeTagRelation
     * @return List<ActiveTagRelation>    返回结果集
     */
	public List<ActiveTagRelation> findActiveTagRelation(@Param("activeTagRelation")ActiveTagRelation activeTagRelation, @Param("order")String order);



    /**
     * 分页查询 ActiveTagRelation
     * @param page 
     * @param activeTagRelation
     * @return List<ActiveTagRelation>    返回分页查询结果集
     */
	public List<ActiveTagRelation> findByPage(Page page, @Param("activeTagRelation")ActiveTagRelation activeTagRelation);

	
	public int countLimitProductActivityTag(@Param("clientId") String clientId, @Param("activeType") String activeType);
	
	public ActiveTagRelation findActive(@Param("clientId")String clientId, @Param("itemType")String itemType, @Param("itemId")String itemId, @Param("activeId")String activeId, @Param("type")String type);
	
	
	
	
	

}