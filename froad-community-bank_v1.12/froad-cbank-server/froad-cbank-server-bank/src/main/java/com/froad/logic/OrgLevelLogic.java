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
 * @Title: OrgLevelLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.exceptions.FroadServerException;
import com.froad.po.OrgLevel;

/**
 * 
 * <p>@Title: OrgLevelLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface OrgLevelLogic {


    /**
     * 增加 OrgLevel
     * @param orgLevel
     * @return Long    主键ID
     */
	public Long addOrgLevel(OrgLevel orgLevel) throws FroadServerException, Exception;



    /**
     * 删除 OrgLevel
     * @param orgLevel
     * @return Boolean    是否成功
     */
	public Boolean deleteOrgLevel(OrgLevel orgLevel) throws FroadServerException, Exception;



    /**
     * 修改 OrgLevel
     * @param orgLevel
     * @return Boolean    是否成功
     */
	public Boolean updateOrgLevel(OrgLevel orgLevel) throws FroadServerException, Exception;


	/**
     * 查询 OrgLevel缓存数据
     * @param orgLevel
     * @return role_id
     */
	public Long findOrgLevelByRedis(String clientId,String orgLevel);
	

    /**
     * 查询 OrgLevel
     * @param orgLevel
     * @return List<OrgLevel>    结果集合 
     */
	public List<OrgLevel> findOrgLevel(OrgLevel orgLevel);



    /**
     * 分页查询 OrgLevel
     * @param page
     * @param orgLevel
     * @return Page    结果集合 
     */
	public Page<OrgLevel> findOrgLevelByPage(Page<OrgLevel> page, OrgLevel orgLevel);



}