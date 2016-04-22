/**
 * Project Name:Froad Cbank Server Order
 * File Name:ThirdpartyPayServiceImpl.java
 * Package Name:com.froad.thrift.service.impl
 * Date:2015-9-22上午9:58:25
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.thrift.service.impl;


import org.apache.thrift.TException;

import com.froad.common.beans.ResultBean;
import com.froad.logic.ThirdpartyPayLogic;
import com.froad.logic.impl.ThirdpartyPayLogicImpl;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.ThirdpartyPayService;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.payment.CombineSettleReq;

/**
 * ClassName:ThirdpartyPayServiceImpl
 * Reason:	 TODO ADD REASON.
 * Date:     2015-9-22 上午9:58:25
 * @author   Zxy
 * @version  
 * @see 	 
 */
public class ThirdpartyPayServiceImpl extends BizMonitorBaseService implements ThirdpartyPayService.Iface{

	
	public ThirdpartyPayServiceImpl() {}
	
	public ThirdpartyPayServiceImpl(String name, String version) {
		super(name, version);
	}
	
	private ThirdpartyPayLogic thirdpartyPayLogic = new ThirdpartyPayLogicImpl();
	
	private ResultVo toResult(ResultBean resultBean){
		ResultVo resultVo = new ResultVo();
		resultVo.setResultCode(resultBean.getCode());
		resultVo.setResultDesc(resultBean.getMsg());
		return resultVo;
	}
	
	@Override
	public ResultVo cashCombineSettle(CombineSettleReq req) throws TException {
		ResultBean resultBean = thirdpartyPayLogic.cashCombineSettle(req);
		return toResult(resultBean);
	}

	@Override
	public ResultVo retryRefund(String refundId) throws TException {
		ResultBean resultBean = thirdpartyPayLogic.retryRefund(refundId);
		return toResult(resultBean);
	}

	@Override
	public ResultVo refundPayCash(String paymentId) throws TException {
		ResultBean resultBean = thirdpartyPayLogic.refundPayCash(paymentId);
		return toResult(resultBean);
	}

	@Override
	public ResultVo refundPayPoint(String paymentId) throws TException {
		ResultBean resultBean = thirdpartyPayLogic.refundPayPoint(paymentId);
		return toResult(resultBean);
	}

	@Override
	public com.froad.thrift.vo.payment.ResultVo pointPresent(String clientId, String loginID, double value) throws TException {
		ResultBean resultBean = thirdpartyPayLogic.pointPresent(clientId, loginID, value);
		return new com.froad.thrift.vo.payment.ResultVo(resultBean.getCode(), resultBean.getMsg(), resultBean.getData().toString());
	}

}
