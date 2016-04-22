/**
 * Project Name:froad-cbank-server-common
 * File Name:Waybill.java
 * Package Name:com.froad.logic
 * Date:2015年11月26日下午5:48:27
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.logic;

import com.froad.po.Waybill;


/**
 * ClassName:Waybill
 * Reason:	 TODO ADD REASON.
 * Date:     2015年11月26日 下午5:48:27
 * @author   liuyanyun
 * @version  
 * @see 	 
 */
public interface WaybillCommonLogic {

	/**
	 * 根据子订单号查询物流信息
	 */
	public Waybill findBySubOrderId(String subOrderId);
}
