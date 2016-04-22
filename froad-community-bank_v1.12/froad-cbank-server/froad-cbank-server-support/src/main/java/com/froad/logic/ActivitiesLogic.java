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
 * @Title: ActivitiesLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.bean.ResultBean;
import com.froad.po.Activities;

/**
 * 
 * <p>@Title: ActivitiesLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface ActivitiesLogic {


    /**
     * 增加 Activities
     * @param activities
     * @return Long    主键ID
     */
	public ResultBean addActivities(Activities activities);



    /**
     * 删除 Activities
     * @param activities
     * @return Boolean    是否成功
     */
	public ResultBean deleteActivities(String clientId, Long activitiesId);



    /**
     * 修改 Activities
     * @param activities
     * @return Boolean    是否成功
     */
	public ResultBean updateActivities(Activities activities);



    /**
     * 查询 Activities
     * @param activities
     * @return List<Activities>    结果集合 
     */
	public List<Activities> findActivities(Activities activities);



    /**
     * 分页查询 Activities
     * @param page
     * @param activities
     * @return Page<Activities>    结果集合 
     */
	public Page<Activities> findActivitiesByPage(Page<Activities> page, Activities activities);


	/**
     * 查询 Activities
     * @return ActivitiesVo
     * 
     * @param clientId
     * @param id
     */
	public Activities getActivitiesById(String clientId, long id);
}