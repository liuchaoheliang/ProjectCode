/*   
* Copyright © 2008 F-Road All Rights Reserved.
*  
* This software is the confidential and proprietary information of   
* Founder. You shall not disclose such Confidential Information   
* and shall use it only in accordance with the terms of the agreements   
* you entered into with Founder.   
*   
*/


package com.froad.db.mysql.mapper;



import com.froad.po.MerchantMonthCount;

/**
 * 
 * <p>@Title: MerchantMonthCountMapper.java</p>
 * <p>Description: 商户月度统计操作 </p> 
 * @author lf 
 * @version 1.0
 * @created 2015年4月11日
 */
public interface MerchantMonthCountMapper {

    /**
     * 查询一个 MerchantMonthCount
     * @param merchantMonthCount
     * @return MerchantMonthCount    返回结果
     */
	public MerchantMonthCount findMerchantMonthCount(MerchantMonthCount merchantMonthCount);

}