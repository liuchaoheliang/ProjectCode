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
 * @Title: BossRoleLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.bean.ResultBean;
import com.froad.po.BossRole;

/**
 * 
 * <p>@Title: BossRoleLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface BossRoleLogic {


    /**
     * 增加 BossRole
     * @param bossRole
     * @return Long    主键ID
     */
	public ResultBean addBossRole(BossRole bossRole);



    /**
     * 删除 BossRole
     * @param bossRole
     * @return Boolean    是否成功
     */
	public ResultBean deleteBossRole(BossRole bossRole);



    /**
     * 修改 BossRole
     * @param bossRole
     * @return Boolean    是否成功
     */
	public ResultBean updateBossRole(BossRole bossRole);



    /**
     * 查询 BossRole
     * @param bossRole
     * @return List<BossRole>    结果集合 
     */
	public List<BossRole> findBossRole(BossRole bossRole);



    /**
     * 分页查询 BossRole
     * @param page
     * @param bossRole
     * @return Page<BossRole>    结果集合 
     */
	public Page<BossRole> findBossRoleByPage(Page<BossRole> page, BossRole bossRole);



}