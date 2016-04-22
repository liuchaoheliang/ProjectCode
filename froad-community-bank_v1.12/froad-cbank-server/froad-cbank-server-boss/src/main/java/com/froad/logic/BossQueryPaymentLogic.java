/**
 * Project Name:froad-cbank-server-boss
 * File Name:BossQueryPaymentLogic.java
 * Package Name:com.froad.logic
 * Date:2015年9月1日下午5:42:53
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.logic;

import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.payment.BossQueryPaymentListRes;
import com.froad.thrift.vo.payment.BossQueryPaymentVo;

/**
 * ClassName:BossQueryPaymentLogic
 * Reason:	 TODO ADD REASON.
 * Date:     2015年9月1日 下午5:42:53
 * @author   kevin
 * @version  
 * @see 	 
 */
public interface BossQueryPaymentLogic {

	
	public BossQueryPaymentListRes getPaymentList(BossQueryPaymentVo req, String flag);
	
	
	public ExportResultRes exportPaymentList(BossQueryPaymentVo req);
	
	
}
