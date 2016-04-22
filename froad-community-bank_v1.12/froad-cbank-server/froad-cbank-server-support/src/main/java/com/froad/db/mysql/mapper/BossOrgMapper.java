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
 * @Title: BossOrgMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.BossOrg;

/**
 * 
 * <p>@Title: BossOrgMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface BossOrgMapper {


    /**
     * 增加 BossOrg
     * @param bossOrg
     * @return Long    主键ID
     */
	public Long addBossOrg(BossOrg bossOrg);



    /**
     * 批量增加 BossOrg
     * @param List<BossOrg>
     * @return Boolean    是否成功
     */
	public Boolean addBossOrgByBatch(List<BossOrg> bossOrgList);



    /**
     * 删除 BossOrg
     * @param bossOrg
     * @return Boolean    是否成功
     */
	public Boolean deleteBossOrg(BossOrg bossOrg);



    /**
     * 修改 BossOrg
     * @param bossOrg
     * @return Boolean    是否成功
     */
	public Boolean updateBossOrg(BossOrg bossOrg);



    /**
     * 查询一个 BossOrg
     * @param bossOrg
     * @return BossOrg    返回结果
     */
	public BossOrg findBossOrgById(Long id);



    /**
     * 查询 BossOrg
     * @param bossOrg
     * @return List<BossOrg>    返回结果集
     */
	public List<BossOrg> findBossOrg(BossOrg bossOrg);



    /**
     * 分页查询 BossOrg
     * @param page 
     * @param bossOrg
     * @return List<BossOrg>    返回分页查询结果集
     */
	public List<BossOrg> findByPage(Page page, @Param("bossOrg")BossOrg bossOrg);



}