package com.froad.logic;

import com.froad.po.MerchantMonthCount;


/**
 * 
 * <p>@Title: MerchantMonthCountLogic.java</p>
 * <p>Description: 商户月度销售统计 </p> 
 * @author lf 
 * @version 1.0
 * @created 2015年4月11日
 */
public interface MerchantMonthCountLogic {

	/**
     * 查询 MerchantMonthCount
     * @param merchantMonthCount
     * @return MerchantMonthCount
     */
	public MerchantMonthCount getOutletCommentById(MerchantMonthCount merchantMonthCount);
	
	
}
