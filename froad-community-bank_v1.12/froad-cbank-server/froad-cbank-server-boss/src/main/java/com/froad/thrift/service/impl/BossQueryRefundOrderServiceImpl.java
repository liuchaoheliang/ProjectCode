/**
 * Project Name:froad-cbank-server-boss
 * File Name:BossQueryRefundOrderServiceImpl.java
 * Package Name:com.froad.thrift.service.impl
 * Date:2015年9月1日下午4:36:34
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.thrift.service.impl;


import org.apache.thrift.TException;

import com.froad.logback.LogCvt;
import com.froad.logic.BossQueryRefundLogic;
import com.froad.logic.impl.BossQueryRefundLogicImpl;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.BossQueryRefundOrderService;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.refund.BossQueryRefundDetailReq;
import com.froad.thrift.vo.refund.BossQueryRefundDetailRes;
import com.froad.thrift.vo.refund.BossQueryRefundOrderListReq;
import com.froad.thrift.vo.refund.BossQueryRefundOrderListRes;
/**
 * ClassName:BossQueryRefundOrderServiceImpl
 * Reason:	 TODO ADD REASON.
 * Date:     2015年9月1日 下午4:36:34
 * @author   kevin
 * @version  
 * @see 	 
 */
public class BossQueryRefundOrderServiceImpl  extends BizMonitorBaseService implements BossQueryRefundOrderService.Iface{
	
	
	private BossQueryRefundLogic logic = new BossQueryRefundLogicImpl();

	public BossQueryRefundOrderServiceImpl() {
	}

	public BossQueryRefundOrderServiceImpl(String name, String version) {
		super(name, version);
	}

	
	public BossQueryRefundOrderListRes getRefundOrderList(BossQueryRefundOrderListReq req) throws TException {
		BossQueryRefundOrderListRes responseVo = null;
		responseVo = logic.getRefundList(req, "0");
		return responseVo;
	}

	public ExportResultRes exportRefundOrderList(BossQueryRefundOrderListReq req) throws TException {
		return logic.exportRefundOrderList(req);
	}

	

	public BossQueryRefundDetailRes getRefundDetail(BossQueryRefundDetailReq req) throws TException {
		BossQueryRefundDetailRes responseVo = null;
		LogCvt.info(new StringBuffer("查找退款详情：").append(req.toString()).toString());
		responseVo = logic.getRefundDetail(req.getRefundId());
		LogCvt.info(new StringBuffer("查找退款详情响应：").append(responseVo.toString()).toString());
		return responseVo;
	}
	
}
