/**
 * Project Name:Froad Cbank Server Boss
 * File Name:ThirdpartyPay.java
 * Package Name:com.froad.support.payment
 * Date:2015-9-22下午1:54:17
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.support.payment;

import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.payment.CombineSettleReq;

/**
 * ClassName:ThirdpartyPay
 * Reason:	 TODO ADD REASON.
 * Date:     2015-9-22 下午1:54:17
 * @author   Zxy
 * @version  
 * @see 	 
 */
public interface ThirdpartyPay {

	/**
	 * 连接order模块 结算
	 * cashCombineSettle:(这里用一句话描述这个方法的作用).
	 *
	 * @author Zxy
	 * 2015-9-22 下午2:09:25
	 * @param req
	 * @return
	 *
	 */
	public ResultVo cashCombineSettle(CombineSettleReq req);
	
	public ResultVo retryRefund(String refundId);
	
	public ResultVo refundPayCash(String paymentId);
	
	public ResultVo refundPayPoint(String paymentId);
}
