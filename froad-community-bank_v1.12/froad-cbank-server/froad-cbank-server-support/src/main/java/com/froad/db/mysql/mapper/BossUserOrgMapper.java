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
 * @Title: BossUserOrgMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.BossUserOrg;

/**
 * 
 * <p>@Title: BossUserOrgMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface BossUserOrgMapper {


    /**
     * 增加 BossUserOrg
     * @param bossUserOrg
     * @return Long    主键ID
     */
	public Long addBossUserOrg(BossUserOrg bossUserOrg);



    /**
     * 批量增加 BossUserOrg
     * @param List<BossUserOrg>
     * @return Boolean    是否成功
     */
	public Boolean addBossUserOrgByBatch(List<BossUserOrg> bossUserOrgList);



    /**
     * 删除 BossUserOrg
     * @param bossUserOrg
     * @return Boolean    是否成功
     */
	public Boolean deleteBossUserOrg(BossUserOrg bossUserOrg);



    /**
     * 修改 BossUserOrg
     * @param bossUserOrg
     * @return Boolean    是否成功
     */
	public Boolean updateBossUserOrg(BossUserOrg bossUserOrg);



    /**
     * 查询一个 BossUserOrg
     * @param bossUserOrg
     * @return BossUserOrg    返回结果
     */
	public BossUserOrg findBossUserOrgById(Long id);



    /**
     * 查询 BossUserOrg
     * @param bossUserOrg
     * @return List<BossUserOrg>    返回结果集
     */
	public List<BossUserOrg> findBossUserOrg(BossUserOrg bossUserOrg);



    /**
     * 分页查询 BossUserOrg
     * @param page 
     * @param bossUserOrg
     * @return List<BossUserOrg>    返回分页查询结果集
     */
	public List<BossUserOrg> findByPage(Page page, @Param("bossUserOrg")BossUserOrg bossUserOrg);



}