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
 * @Title: BossUserLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.bean.ResultBean;
import com.froad.po.BossUser;
import com.froad.po.BossUserCheckRes;
import com.froad.po.BossUserLoginRes;

/**
 * 
 * <p>@Title: BossUserLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface BossUserLogic {


    /**
     * 增加 BossUser
     * @param bossUser
     * @return Long    主键ID
     */
	public ResultBean addBossUser(BossUser bossUser);



    /**
     * 删除 BossUser
     * @param bossUser
     * @return Boolean    是否成功
     */
	public ResultBean deleteBossUser(BossUser bossUser);



    /**
     * 修改 BossUser
     * @param bossUser
     * @return Boolean    是否成功
     */
	public ResultBean updateBossUser(BossUser bossUser);



    /**
     * 查询 BossUser
     * @param bossUser
     * @return List<BossUser>    结果集合 
     */
	public List<BossUser> findBossUser(BossUser bossUser);



    /**
     * 分页查询 BossUser
     * @param page
     * @param bossUser
     * @return Page<BossUser>    结果集合 
     */
	public Page<BossUser> findBossUserByPage(Page<BossUser> page, BossUser bossUser);

	/**
     * 是否存在username
     * @return bool
     * 
     * @param username
     */
	public boolean isExistUsername(String username);
	
	/**
	 * 登录login
	 * @param username
	 * @param password
	 * @param operatorIp
	 * @return BossUserLoginRes
	 * */
	public BossUserLoginRes login(String username, String password, String operatorIp);

	/**
     * 用户登出
     * @param token
     * @return boolean
     * 
     * @param token
     */
    public Boolean logout(String token);
	
	/**
     * 校验 token
     * @param token值
     * @param userId
     * @return bossUserCheckRes
     */
	public BossUserCheckRes tokenCheck(String token, long userId);
}