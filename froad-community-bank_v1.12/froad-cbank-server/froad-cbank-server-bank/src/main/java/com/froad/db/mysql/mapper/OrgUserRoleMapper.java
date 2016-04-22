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
 * @Title: OrgUserRoleMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.OrgUserRole;

/**
 * 
 * <p>@Title: OrgUserRoleMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface OrgUserRoleMapper {


    /**
     * 增加 OrgUserRole
     * @param orgUserRole
     * @return Long    主键ID
     */
	public Long addOrgUserRole(OrgUserRole orgUserRole);



    /**
     * 批量增加 OrgUserRole
     * @param List<OrgUserRole>
     * @return Boolean    是否成功
     */
	public Boolean addOrgUserRoleByBatch(List<OrgUserRole> orgUserRoleList);



    /**
     * 删除 OrgUserRole
     * @param orgUserRole
     * @return Boolean    是否成功
     */
	public Boolean deleteOrgUserRole(OrgUserRole orgUserRole);



    /**
     * 修改 OrgUserRole
     * @param orgUserRole
     * @return Boolean    是否成功
     */
	public Boolean updateOrgUserRole(OrgUserRole orgUserRole);



    /**
     * 查询 OrgUserRole
     * @param orgUserRole
     * @return List<OrgUserRole>    返回结果集
     */
	public List<OrgUserRole> findOrgUserRole(OrgUserRole orgUserRole);



    /**
     * 分页查询 OrgUserRole
     * @param page 
     * @param orgUserRole
     * @return List<OrgUserRole>    返回分页查询结果集
     */
	public List<OrgUserRole> findByPage(Page page, @Param("orgUserRole")OrgUserRole orgUserRole);



}