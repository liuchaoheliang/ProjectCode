/**
 * Project Name:coremodule-merchant
 * File Name:Query_Order_Detail_PC_Req.java
 * Package Name:com.froad.cbank.coremodule.module.normal.merchant.pojo
 * Date:2015年9月11日下午2:45:43
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.merchant.pojo;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

/**
 * ClassName:Query_Order_Detail_PC_Req
 * Reason:	 TODO ADD REASON.
 * Date:     2015年9月11日 下午2:45:43
 * @author   wm
 * @version  
 * @see 	 
 */
public class Query_Order_Detail_PC_Req extends BasePojo {
	
	@NotEmpty(value = "子订单不能为空")
	private String subOrderId; 
	/**
	 * 订单类型， 0. 面对面，1.团购，2.预售，3.名优特惠，4.积分礼品兑换
	 */
	@NotEmpty(value = "订单类型不能为空")
	private String type;
	
	public String getSubOrderId() {
		return subOrderId;
	}
	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	} 
}
