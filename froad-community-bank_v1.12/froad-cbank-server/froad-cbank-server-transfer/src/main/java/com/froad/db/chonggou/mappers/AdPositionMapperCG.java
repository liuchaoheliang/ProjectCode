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
 * @Title: OrgMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.chonggou.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.chonggou.entity.AdPositionCG;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.enums.TransferTypeEnum;
import com.froad.po.Org;

/**
 *  转移类型ID中间表
  * @ClassName: TransferMapper
  * @Description: TODO
  * @author share 2015年4月30日
  * @modify share 2015年4月30日
 */
public interface AdPositionMapperCG {
	

    /**
     * 增加 AdPosition
     * @param adPosition
     * @return Long    主键ID
     */
	public Long addAdPosition(AdPositionCG adPosition);
	
	
	
    /**
     * 查询 AdPosition
     * @param adPosition
     * @return List<AdPosition>    返回结果集
     */
	public List<AdPositionCG> findAdPosition(AdPositionCG adPosition);
	
	


}