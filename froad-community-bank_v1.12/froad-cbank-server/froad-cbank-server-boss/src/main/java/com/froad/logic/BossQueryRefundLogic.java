/**
 * Project Name:froad-cbank-server-boss
 * File Name:BossQueryRefundLogic.java
 * Package Name:com.froad.logic
 * Date:2015年9月1日下午4:41:35
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.logic;

import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.refund.BossQueryRefundDetailRes;
import com.froad.thrift.vo.refund.BossQueryRefundOrderListReq;
import com.froad.thrift.vo.refund.BossQueryRefundOrderListRes;

/**
 * ClassName:BossQueryRefundLogic
 * Reason:	 TODO ADD REASON.
 * Date:     2015年9月1日 下午4:41:35
 * @author   kevin
 * @version  
 * @see 	 
 */
public interface BossQueryRefundLogic {
	
	
	public BossQueryRefundOrderListRes getRefundList(BossQueryRefundOrderListReq req, String flag);
	
	public ExportResultRes exportRefundOrderList(BossQueryRefundOrderListReq req);
	
	
	public BossQueryRefundDetailRes getRefundDetail(String refundId);
	

}
