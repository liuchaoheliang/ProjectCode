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
 * @Title: OrgLevelMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.OrgLevel;

/**
 * 
 * <p>@Title: OrgLevelMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface OrgLevelMapper {


    /**
     * 增加 OrgLevel
     * @param orgLevel
     * @return Long    主键ID
     */
	public Long addOrgLevel(OrgLevel orgLevel);



    /**
     * 批量增加 OrgLevel
     * @param List<OrgLevel>
     * @return Boolean    是否成功
     */
	public Boolean addOrgLevelByBatch(List<OrgLevel> orgLevelList);



    /**
     * 删除 OrgLevel
     * @param orgLevel
     * @return Boolean    是否成功
     */
	public Boolean deleteOrgLevel(OrgLevel orgLevel);



    /**
     * 修改 OrgLevel
     * @param orgLevel
     * @return Boolean    是否成功
     */
	public Boolean updateOrgLevel(OrgLevel orgLevel);



    /**
     * 查询 OrgLevel
     * @param orgLevel
     * @return List<OrgLevel>    返回结果集
     */
	public List<OrgLevel> findOrgLevel(OrgLevel orgLevel);



    /**
     * 分页查询 OrgLevel
     * @param page 
     * @param orgLevel
     * @return List<OrgLevel>    返回分页查询结果集
     */
	public List<OrgLevel> findByPage(Page page, @Param("orgLevel")OrgLevel orgLevel);



}