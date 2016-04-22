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
 * @Title: AdPositionLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.bean.ResultBean;
import com.froad.po.AdPosition;

/**
 * 
 * <p>@Title: AdPositionLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface AdPositionLogic {


    /**
     * 增加 AdPosition
     * @param adPosition
     * @return Long    主键ID
     */
	public ResultBean addAdPosition(AdPosition adPosition);



    /**
     * 删除 AdPosition
     * @param adPosition
     * @return Boolean    是否成功
     */
	public ResultBean deleteAdPosition(AdPosition adPosition);



    /**
     * 修改 AdPosition
     * @param adPosition
     * @return Boolean    是否成功
     */
	public ResultBean updateAdPosition(AdPosition adPosition);



    /**
     * 查询 AdPosition
     * @param adPosition
     * @return List<AdPosition>    结果集合 
     */
	public List<AdPosition> findAdPosition(AdPosition adPosition);



    /**
     * 分页查询 AdPosition
     * @param page
     * @param adPosition
     * @return Page<AdPosition>    结果集合 
     */
	public Page<AdPosition> findAdPositionByPage(Page<AdPosition> page, AdPosition adPosition);

	/**
     * 查询 AdPosition
     * @return AdPositionVo
     * 
     * @param clientId
     * @param id
     */
	public AdPosition getAdPositionById(String clientId, long id);

}