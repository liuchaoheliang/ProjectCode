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
 * @Title: BossUserMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.BossUser;

/**
 * 
 * <p>@Title: BossUserMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface BossUserMapper {


    /**
     * 增加 BossUser
     * @param bossUser
     * @return Long    主键ID
     */
	public Long addBossUser(BossUser bossUser);



    /**
     * 批量增加 BossUser
     * @param List<BossUser>
     * @return Boolean    是否成功
     */
	public Boolean addBossUserByBatch(List<BossUser> bossUserList);



    /**
     * 删除 BossUser
     * @param bossUser
     * @return Boolean    是否成功
     */
	public Boolean deleteBossUser(BossUser bossUser);



    /**
     * 修改 BossUser
     * @param bossUser
     * @return Boolean    是否成功
     */
	public Boolean updateBossUser(BossUser bossUser);



    /**
     * 查询一个 BossUser
     * @param bossUser
     * @return BossUser    返回结果
     */
	public BossUser findBossUserById(Long id);



    /**
     * 查询 BossUser
     * @param bossUser
     * @return List<BossUser>    返回结果集
     */
	public List<BossUser> findBossUser(BossUser bossUser);



    /**
     * 分页查询 BossUser
     * @param page 
     * @param bossUser
     * @return List<BossUser>    返回分页查询结果集
     */
	public List<BossUser> findByPage(Page page, @Param("bossUser")BossUser bossUser);


	/**
     * 查询单个 BossUser
     * @param username
     * @return BossUser    返回结果集
     */
	public BossUser findBossUserByUsername(String username);
}