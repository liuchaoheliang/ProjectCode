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


import com.froad.db.chonggou.entity.AdCG;

/**
 *  转移类型ID中间表
  * @ClassName: TransferMapper
  * @Description: TODO
  * @author share 2015年4月30日
  * @modify share 2015年4月30日
 */
public interface AdMapperCG {
	

    /**
     * 增加 Ad
     * @param adPosition
     * @return Long    主键ID
     */
	public Long addAd(AdCG ad);
	
	
	
    /**
     * 查询 Ad
     * @param adPosition
     * @return List<Ad>    返回结果集
     */
	public List<AdCG> findAd(AdCG ad);
	
	


}