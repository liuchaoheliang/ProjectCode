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
 * @Title: AdMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.Ad;

/**
 * 
 * <p>@Title: AdMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface AdMapper {


    /**
     * 增加 Ad
     * @param ad
     * @return Long    主键ID
     */
	public Long addAd(Ad ad);



    /**
     * 批量增加 Ad
     * @param List<Ad>
     * @return Boolean    是否成功
     */
	public Boolean addAdByBatch(List<Ad> adList);



    /**
     * 删除 Ad
     * @param ad
     * @return Boolean    是否成功
     */
	public Boolean deleteAd(Ad ad);



    /**
     * 查询一个 Ad
     * @param ad
     * @return Ad    返回结果
     */
	public Ad findAdById(Long id);

    /**
     * 修改 Ad
     * @param ad
     * @return Boolean    是否成功
     */
	public Boolean updateAd(Ad ad);



    /**
     * 查询 Ad
     * @param ad
     * @return List<Ad>    返回结果集
     */
	public List<Ad> findAd(Ad ad);



    /**
     * 分页查询 Ad
     * @param page 
     * @param ad
     * @return List<Ad>    返回分页查询结果集
     */
	public List<Ad> findByPage(Page page, @Param("ad")Ad ad);



}