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
 * @Title: BossUserOrgLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.bean.ResultBean;
import com.froad.po.BossUserOrg;

/**
 * 
 * <p>@Title: BossUserOrgLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface BossUserOrgLogic {


    /**
     * 增加 BossUserOrg
     * @param bossUserOrg
     * @return Long    主键ID
     */
	public ResultBean addBossUserOrg(BossUserOrg bossUserOrg);



    /**
     * 删除 BossUserOrg
     * @param bossUserOrg
     * @return Boolean    是否成功
     */
	public ResultBean deleteBossUserOrg(BossUserOrg bossUserOrg);



    /**
     * 修改 BossUserOrg
     * @param bossUserOrg
     * @return Boolean    是否成功
     */
	public ResultBean updateBossUserOrg(BossUserOrg bossUserOrg);



    /**
     * 查询 BossUserOrg
     * @param bossUserOrg
     * @return List<BossUserOrg>    结果集合 
     */
	public List<BossUserOrg> findBossUserOrg(BossUserOrg bossUserOrg);



    /**
     * 分页查询 BossUserOrg
     * @param page
     * @param bossUserOrg
     * @return Page<BossUserOrg>    结果集合 
     */
	public Page<BossUserOrg> findBossUserOrgByPage(Page<BossUserOrg> page, BossUserOrg bossUserOrg);



}