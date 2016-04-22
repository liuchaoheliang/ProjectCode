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
import com.froad.po.ProductMonthCount;

/**
 * 
 * <p>@Title: ProductMonthCountMapper.java</p>
 * <p>Description: 商品月度统计操作 </p> 
 * @author lf 
 * @version 1.0
 * @created 2015年4月11日
 */
public interface ProductMonthCountMapper {

    /**
     * 查询一个 ProductMonthCount 最大月销量的信息
     * @param merchantMonthCount
     * @return ProductMonthCount    返回结果
     */
	public ProductMonthCount findProductMonthCountOfSellCountMax(MerchantMonthCount merchantMonthCount);

}