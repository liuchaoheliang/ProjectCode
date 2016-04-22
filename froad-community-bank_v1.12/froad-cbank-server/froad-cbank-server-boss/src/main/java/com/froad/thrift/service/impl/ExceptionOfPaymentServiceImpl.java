/**
 * Project Name:Froad Cbank Server Boss
 * File Name:ExceptionOfPaymentService.java
 * Package Name:com.froad.thrift.service.impl
 * Date:2015-9-18上午11:21:00
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.thrift.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;

import com.froad.common.beans.ResultBean;
import com.froad.logback.LogCvt;
import com.froad.logic.impl.payment.ExceptionOfPaymentImpl;
import com.froad.logic.payment.ExceptionOfPayment;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.ExceptionOfPaymentService;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.payment.BossPaymentDetialVo;
import com.froad.thrift.vo.payment.BossPaymentPage;
import com.froad.thrift.vo.payment.BossQueryConditionVo;
import com.froad.util.JSonUtil;

/**
 * ClassName:ExceptionOfPaymentService
 * Reason:	 TODO ADD REASON.
 * Date:     2015-9-18 上午11:21:00
 * @author   Zxy
 * @version  
 * @see 	 
 */
public class ExceptionOfPaymentServiceImpl extends BizMonitorBaseService implements ExceptionOfPaymentService.Iface{

	public ExceptionOfPaymentServiceImpl() {}
	
	public ExceptionOfPaymentServiceImpl(String name, String version) {
		super(name, version);
	}
	
	private ExceptionOfPayment exceptionOfPayment = new ExceptionOfPaymentImpl();
	
	@Override
	public BossPaymentPage queryExceptionPaymentByPage(BossQueryConditionVo condition) {
		LogCvt.info("异常订单列表查询请求参数:" + JSonUtil.toJSonString(condition));
		BossPaymentPage paymentPage = new BossPaymentPage();
		PageVo pageVo = condition.getPageVo();
		if (pageVo == null) {
			paymentPage.setResultVo(new ResultVo("9999", "pageVo参数为空"));
			return paymentPage;
		}
		
		String exceptionType = condition.getExceptionType();// 1-支付异常 - 自动退积分失败;2-结算异常;3-退款异常;4-支付异常-退现金失败
		if (StringUtils.equals(exceptionType, "1")) { // 1-支付异常 - 自动退积分失败
			paymentPage = exceptionOfPayment.queryOfPayRefundPointFailed(condition);
		} else if (StringUtils.equals(exceptionType, "2")) {// 2-结算异常
			paymentPage = exceptionOfPayment.queryOfSettleFailed(condition);
		} else if (StringUtils.equals(exceptionType, "3")) {// 3-退款异常
			paymentPage = exceptionOfPayment.queryOfRefundFailed(condition);
		} else if(StringUtils.equals(exceptionType, "4")){
			paymentPage = exceptionOfPayment.queryOfPayRefundCashFailed(condition);
		} else {
			paymentPage.setResultVo(new ResultVo("9999", "exceptionType参数不合法"));
		}
		return paymentPage;
	}
	
	private ResultVo toResult(ResultBean resultBean){
		ResultVo resultVo = new ResultVo();
		resultVo.setResultCode(resultBean.getCode());
		resultVo.setResultDesc(resultBean.getMsg());
		return resultVo;
	}

	@Override
	public BossPaymentDetialVo queryOfSettleDetial(String paymentId)throws TException {
		LogCvt.info("查询paymentId为:" + paymentId + " 结算异常订单详情");
		return exceptionOfPayment.queryOfSettleDetial(paymentId);
	}

	@Override
	public ResultVo retryOfSettle(String paymentId) throws TException {
		return toResult(exceptionOfPayment.retryOfSettle(paymentId));
	}

	@Override
	public BossPaymentDetialVo queryOfRefundDetial(String paymentId) throws TException {
		return exceptionOfPayment.queryOfRefundDetial(paymentId);
	}

	@Override
	public ResultVo retryOfReturn(String refundId) throws TException {
		return exceptionOfPayment.retryOfRefund(refundId);
	}

	@Override
	public ResultVo returnCash(String paymentId) throws TException {
		return exceptionOfPayment.refundOfCash(paymentId);
	}

	@Override
	public ResultVo returnPoint(String paymentId) throws TException {
		return exceptionOfPayment.refundOfPoint(paymentId);
	}

	@Override
	public ResultVo returnPointAndCash(String paymentId) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BossPaymentDetialVo queryOfPaymentRefundPointDetial(String paymentId)throws TException {
		return exceptionOfPayment.queryOfPayRefundPointFailedDetial(paymentId);
	}

	@Override
	public BossPaymentDetialVo queryOfPaymentRefundCashDetial(String paymentId)throws TException {
		return exceptionOfPayment.queryOfPayRefundCashFailedDetial(paymentId);
	}

}
