/**
 * Project Name:Froad Cbank Server Order
 * File Name:ThirdpartyPayLogic.java
 * Package Name:com.froad.logic
 * Date:2015-9-22上午10:00:10
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.logic;

import com.froad.common.beans.ResultBean;
import com.froad.thrift.vo.payment.CombineSettleReq;

/**
 * ClassName:ThirdpartyPayLogic
 * Reason:	 TODO ADD REASON.
 * Date:     2015-9-22 上午10:00:10
 * @author   Zxy
 * @version  
 * @see 	 
 */
public interface ThirdpartyPayLogic {

	public ResultBean cashCombineSettle(CombineSettleReq req);
	
	public ResultBean retryRefund(String refundId);
	
	public ResultBean refundPayCash(String paymentId);
	
	public ResultBean refundPayPoint(String paymentId);
	
	public ResultBean pointPresent(String clientId,String loginID,double value);
}
