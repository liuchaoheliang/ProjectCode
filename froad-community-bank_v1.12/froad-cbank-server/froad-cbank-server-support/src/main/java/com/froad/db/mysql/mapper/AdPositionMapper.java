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
 * @Title: AdPositionMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.AdPosition;

/**
 * 
 * <p>@Title: AdPositionMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface AdPositionMapper {


    /**
     * 增加 AdPosition
     * @param adPosition
     * @return Long    主键ID
     */
	public Long addAdPosition(AdPosition adPosition);



    /**
     * 批量增加 AdPosition
     * @param List<AdPosition>
     * @return Boolean    是否成功
     */
	public Boolean addAdPositionByBatch(List<AdPosition> adPositionList);



    /**
     * 删除 AdPosition
     * @param adPosition
     * @return Boolean    是否成功
     */
	public Boolean deleteAdPosition(AdPosition adPosition);



    /**
     * 修改 AdPosition
     * @param adPosition
     * @return Boolean    是否成功
     */
	public Boolean updateAdPosition(AdPosition adPosition);


    /**
     * 查询一个 AdPosition
     * @param adPosition
     * @return AdPosition    返回结果
     */
	public AdPosition findAdPositionById(Long id);
	
	
    /**
     * 查询 AdPosition
     * @param adPosition
     * @return List<AdPosition>    返回结果集
     */
	public List<AdPosition> findAdPosition(AdPosition adPosition);



    /**
     * 分页查询 AdPosition
     * @param page 
     * @param adPosition
     * @return List<AdPosition>    返回分页查询结果集
     */
	public List<AdPosition> findByPage(Page page, @Param("adPosition")AdPosition adPosition);



}