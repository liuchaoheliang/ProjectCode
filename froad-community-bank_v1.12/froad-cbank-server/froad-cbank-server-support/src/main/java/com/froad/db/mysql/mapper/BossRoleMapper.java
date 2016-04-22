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
 * @Title: BossRoleMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.BossRole;

/**
 * 
 * <p>@Title: BossRoleMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface BossRoleMapper {


    /**
     * 增加 BossRole
     * @param bossRole
     * @return Long    主键ID
     */
	public Long addBossRole(BossRole bossRole);



    /**
     * 批量增加 BossRole
     * @param List<BossRole>
     * @return Boolean    是否成功
     */
	public Boolean addBossRoleByBatch(List<BossRole> bossRoleList);



    /**
     * 删除 BossRole
     * @param bossRole
     * @return Boolean    是否成功
     */
	public Boolean deleteBossRole(BossRole bossRole);



    /**
     * 修改 BossRole
     * @param bossRole
     * @return Boolean    是否成功
     */
	public Boolean updateBossRole(BossRole bossRole);



    /**
     * 查询一个 BossRole
     * @param bossRole
     * @return BossRole    返回结果
     */
	public BossRole findBossRoleById(Long id);



    /**
     * 查询 BossRole
     * @param bossRole
     * @return List<BossRole>    返回结果集
     */
	public List<BossRole> findBossRole(BossRole bossRole);



    /**
     * 分页查询 BossRole
     * @param page 
     * @param bossRole
     * @return List<BossRole>    返回分页查询结果集
     */
	public List<BossRole> findByPage(Page page, @Param("bossRole")BossRole bossRole);



}