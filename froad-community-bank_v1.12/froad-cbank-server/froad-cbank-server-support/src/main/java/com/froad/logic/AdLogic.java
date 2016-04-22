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
 * @Title: AdLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;
import java.util.Map;

import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.bean.ResultBean;
import com.froad.po.Ad;
import com.froad.po.AdPosition;
import com.froad.thrift.vo.AdVo;

/**
 * 
 * <p>@Title: AdLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface AdLogic {


    /**
     * 增加 Ad
     * @param ad
     * @return Long    主键ID
     */
	public ResultBean addAd(Ad ad);



    /**
     * 删除 Ad
     * @param ad
     * @return Boolean    是否成功
     */
	public ResultBean deleteAd(Ad ad);



    /**
     * 修改 Ad
     * @param ad
     * @return Boolean    是否成功
     */
	public ResultBean updateAd(Ad ad);



    /**
     * 查询 Ad
     * @param ad
     * @return List<Ad>    结果集合 
     */
	public List<Ad> findAd(Ad ad,AdPosition adPosition);



    /**
     * 分页查询 Ad
     * @param page
     * @param ad
     * @return Page<Ad>    结果集合 
     */
	public Page<Ad> findAdByPage(Page<Ad> page, Ad ad);

	/**
     * 查询 Ad
     * @return AdVo
     * 
     * @param id
     */
	public Ad findAdById(long id);
	
	
	/**
     * 查询 Ad
     * @return Ad
     * 
     * @param positionIds
     */
	public Map<String, List<Ad>> getAdByPositionIds(List<Long> positionIds);
}