/**
 * Project Name:Froad Cbank Server Boss
 * File Name:ThirdpartyPayImpl.java
 * Package Name:com.froad.support.impl.payment
 * Date:2015-9-22下午1:54:39
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.support.impl.payment;

import com.froad.logback.LogCvt;
import com.froad.support.payment.ThirdpartyPay;
import com.froad.thrift.client.ThriftClientProxyFactory;
import com.froad.thrift.service.ThirdpartyPayService;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.payment.CombineSettleReq;
import com.froad.util.BossConstants;

/**
 * ClassName:ThirdpartyPayImpl
 * Reason:	 TODO ADD REASON.
 * Date:     2015-9-22 下午1:54:39
 * @author   Zxy
 * @version  
 * @see 	 
 */
public class ThirdpartyPayImpl implements ThirdpartyPay {
	
	@Override
	public ResultVo cashCombineSettle(CombineSettleReq req) {
		try{
			ThirdpartyPayService.Iface client = (ThirdpartyPayService.Iface) ThriftClientProxyFactory.createIface(ThirdpartyPayService.class, BossConstants.THRIFT_ORDER_HOST, Integer.valueOf(BossConstants.THRIFT_ORDER_PORT), 140000);
			return client.cashCombineSettle(req);
		}catch (Exception e) {
			LogCvt.error("调用订单模块 第三方<组合结算> 异常" ,e);
		}
		
		return new ResultVo("9999", "调用订单模块 第三方<组合结算> 异常");
	}

	@Override
	public ResultVo retryRefund(String refundId) {
		try{
			ThirdpartyPayService.Iface client = (ThirdpartyPayService.Iface) ThriftClientProxyFactory.createIface(ThirdpartyPayService.class, BossConstants.THRIFT_ORDER_HOST, Integer.valueOf(BossConstants.THRIFT_ORDER_PORT), 140000);
			return client.retryRefund(refundId);
		}catch (Exception e) {
			LogCvt.error("调用订单模块 第三方<再次退款> 异常" ,e);
		}
		return new ResultVo("9999", "调用订单模块 第三方<再次退款> 异常");
	}

	@Override
	public ResultVo refundPayCash(String paymentId) {
		try{
			ThirdpartyPayService.Iface client = (ThirdpartyPayService.Iface) ThriftClientProxyFactory.createIface(ThirdpartyPayService.class, BossConstants.THRIFT_ORDER_HOST, Integer.valueOf(BossConstants.THRIFT_ORDER_PORT), 140000);
			return client.refundPayCash(paymentId);
		}catch (Exception e) {
			LogCvt.error("调用订单模块异常" ,e);
		}
		return new ResultVo("9999", "调用订单模块异常");
	}

	@Override
	public ResultVo refundPayPoint(String paymentId) {
		try{
			ThirdpartyPayService.Iface client = (ThirdpartyPayService.Iface) ThriftClientProxyFactory.createIface(ThirdpartyPayService.class, BossConstants.THRIFT_ORDER_HOST, Integer.valueOf(BossConstants.THRIFT_ORDER_PORT), 140000);
			return client.refundPayPoint(paymentId);
		}catch (Exception e) {
			LogCvt.error("调用订单模块异常" ,e);
		}
		return new ResultVo("9999", "调用订单模块异常");
	}

}
