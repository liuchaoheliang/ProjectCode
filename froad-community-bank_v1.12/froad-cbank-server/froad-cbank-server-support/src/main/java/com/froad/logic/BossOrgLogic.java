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
 * @Title: BossOrgLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.bean.ResultBean;
import com.froad.po.BossOrg;

/**
 * 
 * <p>@Title: BossOrgLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface BossOrgLogic {


    /**
     * 增加 BossOrg
     * @param bossOrg
     * @return Long    主键ID
     */
	public ResultBean addBossOrg(BossOrg bossOrg);



    /**
     * 删除 BossOrg
     * @param bossOrg
     * @return Boolean    是否成功
     */
	public ResultBean deleteBossOrg(BossOrg bossOrg);



    /**
     * 修改 BossOrg
     * @param bossOrg
     * @return Boolean    是否成功
     */
	public ResultBean updateBossOrg(BossOrg bossOrg);



    /**
     * 查询 BossOrg
     * @param bossOrg
     * @return List<BossOrg>    结果集合 
     */
	public List<BossOrg> findBossOrg(BossOrg bossOrg);



    /**
     * 分页查询 BossOrg
     * @param page
     * @param bossOrg
     * @return Page<BossOrg>    结果集合 
     */
	public Page<BossOrg> findBossOrgByPage(Page<BossOrg> page, BossOrg bossOrg);



}