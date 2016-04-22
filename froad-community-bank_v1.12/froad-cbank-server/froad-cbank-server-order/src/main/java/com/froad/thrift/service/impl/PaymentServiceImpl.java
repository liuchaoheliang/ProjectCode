package com.froad.thrift.service.impl;


import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;

import com.froad.common.beans.PayTempBean;
import com.froad.common.beans.ResultBean;
import com.froad.enums.CashType;
import com.froad.enums.PaymentMethod;
import com.froad.enums.ResultCode;
import com.froad.exceptions.PaymentException;
import com.froad.handler.PaymentQueryHandler;
import com.froad.handler.impl.PaymentQueryHandlerImpl;
import com.froad.logback.LogCvt;
import com.froad.logic.OrderLogic;
import com.froad.logic.PaymentLogic;
import com.froad.logic.impl.OrderLogicImpl;
import com.froad.logic.impl.PaymentLogicImpl;
import com.froad.logic.impl.payment.PaymentCoreLogicImpl;
import com.froad.logic.payment.PaymentCoreLogic;
import com.froad.po.Payment;
import com.froad.po.mongo.OrderMongo;
import com.froad.support.impl.payment.AWIPSThirdpartyImpl;
import com.froad.support.payment.AWIPSThirdparty;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.PaymentService;
import com.froad.thrift.service.PaymentService.Iface;
import com.froad.thrift.service.impl.validation.PaymentValidation;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.payment.DoPayOrdersVoReq;
import com.froad.thrift.vo.payment.DoPayOrdersVoRes;
import com.froad.thrift.vo.payment.PaymentQueryExcetionVo;
import com.froad.thrift.vo.payment.PaymentQueryPageVo;
import com.froad.thrift.vo.payment.PaymentQueryVo;
import com.froad.util.payment.BaseSubassembly;
import com.froad.util.payment.Logger;

/**
 * Copyright © 2015 F-Road. All rights reserved.
 * @ClassName: PaymentServiceImpl
 * @Description: 支付外部接口实现
 * @Author: zhaoxiaoyao@f-road.com.cn
 * @Date: 2015年3月17日 上午10:00:49
 */
public class PaymentServiceImpl extends BizMonitorBaseService implements PaymentService.Iface {
	public PaymentServiceImpl() {}
	public PaymentServiceImpl(String name, String version) {
		super(name, version);
	}
	
	private OrderLogic orderLogic = new OrderLogicImpl();
	private PaymentLogic paymentLogic = new PaymentLogicImpl();
	private PaymentQueryHandler paymentQueryHandler = new PaymentQueryHandlerImpl();
	private PaymentCoreLogic paymentCoreLogic = new PaymentCoreLogicImpl();
	private AWIPSThirdparty awipsThirdparty = new AWIPSThirdpartyImpl();
    
	
	@Override
	public DoPayOrdersVoRes doPayOrders(DoPayOrdersVoReq doPayOrdersVoReq) throws TException {
		
		Logger.logInfo("收到支付请求", "doPayOrdersVoReq", doPayOrdersVoReq);

		if(doPayOrdersVoReq == null){
			Logger.logInfo("无效的支付请求: 请求参数为空");
			return new DoPayOrdersVoRes(ResultCode.payment_params_error.getCode(), "请求参数为空", null);
		}
		
//		long stBase = System.currentTimeMillis();
//		
//		String orderId = doPayOrdersVoReq.getOrderId();
//		
//		ResultBean resultBean = orderLogic.getOrderByOrderId(doPayOrdersVoReq.getClientId(),orderId);
//		
//        if (!isSuccess(resultBean)) {
//        	Logger.logInfo("支付接口获取订单失败,错误原因:" + resultBean.getMsg(),"orderId",orderId);
//            return makeResultBean(resultBean);
//        }
//
//        OrderMongo order = (OrderMongo) resultBean.getData();
//
//        if(order == null){
//        	Logger.logInfo("获取指定订单数据为空","orderId",orderId);
//        	return makeResultBean(new ResultBean(ResultCode.payment_order_none));
//        }
//        
//        String currOrderStatus = order.getOrderStatus();
//        Logger.logInfo("支付前订单数据为: ","order",order);
//		boolean flag = orderLogic.updateOrderStatusByOrderId(doPayOrdersVoReq.getOrderId());
//		
//		if(!flag){
//			Logger.logInfo(
//					"欲付订单的订单状态不允许支付",
//					new String[]{"orderId","orderStatus"},
//					new Object[]{orderId,currOrderStatus});
//			return makeResultBean(new ResultBean(ResultCode.payment_order_status_unsupport, "当前订单状态不能支付"));
//		}
//        
//        //2：校验支付参数和订单参数
//		//开始基本参数校验---------------------
//		Logger.logInfo("step:1-开始基础参数校验 : ","orderId",orderId);
//		resultBean = PaymentValidation.baseParamVerifyBeforPay(doPayOrdersVoReq);
//		if(!isSuccess(resultBean)){ // 如果校验基础参数失败，逻辑结束，接口跳出
//			rollbackOrderStatus(orderId, currOrderStatus);
//			return makeResultBean(resultBean);
//		}
//		
//		long st = System.currentTimeMillis();
//		//----------------------------------逻辑校验---------------------------支付前
//		Logger.logInfo("step:2-基础参数校验通过,开始验证支付前逻辑组参校验");
//		PayTempBean tempBean = new PayTempBean(doPayOrdersVoReq);
//		resultBean = paymentLogic.validationAllBeforePayOrder(tempBean,order);//禁用之前的支付流水并创建新的支付流水
//		if(!isSuccess(resultBean)){ //支付前校验不通过
//			rollbackOrderStatus(orderId, currOrderStatus);
//			return makeResultBean(resultBean);
//		}
//		Logger.logInfo("基础参数校验耗时：ms " + (System.currentTimeMillis() - st));
//		@SuppressWarnings("unchecked")
//		List<Payment> payList = (List<Payment>) resultBean.getData();
//		//----------------------------------逻辑校验---------------------------支付前
//
//		
//		//3：检查订单库存是否被定时任务释放，如果释放，则重新扣除库存
//		Logger.logInfo("step:3-检查库存状态，如果被定时任务释放，则重新扣除");
//		resultBean = paymentLogic.checkAndOperateStore(order,false);
//		if(!isSuccess(resultBean)){
//			rollbackOrderStatus(orderId, currOrderStatus);
//		    return makeResultBean(resultBean);
//		}
//		
//		st = System.currentTimeMillis();
//		//4：补全订单支付参数
//		// 补全订单支付相关信息
//		Logger.logInfo("step:4-补全订单支付相关信息");
//		resultBean = paymentLogic.completeOrderPaymentInfo(tempBean,order);
//        if (!isSuccess(resultBean)) {
//        	rollbackOrderStatus(orderId, currOrderStatus);
//            return makeResultBean(resultBean);
//        }
//        Logger.logInfo("补全订单耗时：ms " + (System.currentTimeMillis() - st));
//        
//        
//        //5：开始支付操作
//        Logger.logInfo("step:5-校验并补全订单数据完成，开始支付处理");
//		try {
//			resultBean = paymentLogic.doPay(payList,doPayOrdersVoReq.getToken());
//		} catch (PaymentException e) {
//			Logger.logError("支付处理请求发送发生异常,将认为该笔订单请求发送成功,用定时任务处理支付结果,向用户展示支付处理中");
//			return makeResultBean(new ResultBean(ResultCode.success,"支付请求处理中"));
//		}
//		Logger.logInfo("核心支付完成耗时：ms " + (System.currentTimeMillis() - st));
//		
//		st = System.currentTimeMillis();
//		if(!isSuccess(resultBean)){ //支付失败
//			// 支付失败 - 释放库存，修改orderState为已退库，从cb_bank:time_order中移除order
//			String resultStr = paymentLogic.doPayResressFailed(order).toString();
//			Logger.logInfo("支付失败，执行失败逻辑处理","resultStr",resultStr);
//			return makeResultBean(resultBean);
//		}
//		Logger.logInfo("库存操作用时：ms " + (System.currentTimeMillis() - st));
//		
//		
//		//6：根据支付类型和结果做支付善后处理 
//		//如果是不含现金支付则可以直接调用支付后的逻辑处理，否则要等通知处理
//		if(BaseSubassembly.isPurePointsPayment(tempBean.getPayType())){
//			st = System.currentTimeMillis();
//			Logger.logInfo("step:6-纯积分支付成功，直接执行附属逻辑");
//			resultBean = paymentLogic.doPayRedress(order);
//            if (!isSuccess(resultBean)) {
//            	Logger.logError("已成功完成支付,但在处理支付完毕后的逻辑发生异常","resultBean",resultBean);
//            }
//            Logger.logInfo("执行后续逻辑用时：ms " + (System.currentTimeMillis() - st));
//            Logger.logInfo("支付完成耗时：ms " + (System.currentTimeMillis() - stBase));
//			return makeResultBean(resultBean);
//		}else{
//			//包含现金支付 - openapi受理完成 1、第三方支付表单 2、等待通知结果
//			Logger.logInfo("step:6-包含现金支付，OpenAPI受理已成功,等待调用方处理第三方支付页面或者等待OpenAPI通知支付结果");
//			return new DoPayOrdersVoRes(resultBean.getCode(), resultBean.getMsg(), resultBean.getData() != null ? resultBean.getData().toString() : "");
//		}
		
		
		
		//支付代码结构改造v 1.0.2
		ResultBean resultBean = PaymentValidation.baseParamVerifyBeforPay(doPayOrdersVoReq);
		if(!isSuccess(resultBean)){
			return makeResultBean(resultBean);
		}
		resultBean = paymentCoreLogic.doPayCoreForGeneralTrade(BaseSubassembly.loadyToPayThriftVoBean(doPayOrdersVoReq));
		return new DoPayOrdersVoRes(resultBean.getCode(), resultBean.getMsg(), resultBean.getData() == null ? null : (String)resultBean.getData());
		
		
	}
	
	private  void rollbackOrderStatus(String orderId,String clientId,String orderStatus){
		OrderMongo mongo = new OrderMongo();
		mongo.setOrderId(orderId);
		mongo.setOrderStatus(orderStatus);
		mongo.setClientId(clientId);
		String resultStr = orderLogic.updateOrderForPay(mongo).toString();
		Logger.logInfo(
				"订单支付失败，需要回滚订单状态 ",
				new String[]{"orderId","resultStr"},
				new Object[]{orderId,resultStr});
	}

    private boolean isSuccess(ResultBean resultBean) {
        return ResultCode.success.getCode().equals(resultBean.getCode());
    }


    private DoPayOrdersVoRes makeResultBean(ResultBean resultBean) {
        Logger.logInfo("支付流程处理完毕","resultBean",resultBean);
        return new DoPayOrdersVoRes(resultBean.getCode(), resultBean.getMsg(),null);
    }
	
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: noticePaymentResult
	 * @Description: 得到OpenApi支付结果通知
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param xmlString
	 * @throws TException
	 * @see com.froad.thrift.service.PaymentService.Iface#noticePaymentResult(java.lang.String)
	 */
	@Override
	public void noticePaymentResult(String xmlString) throws TException {
		if(StringUtils.isEmpty(xmlString)){
			LogCvt.error("收到OpenAPI无效的合并支付空通知内容");
			return;
		}
		
		
//		LogCvt.info("已接收到OpenAPI的合并支付通知内容: " + xmlString);
//		paymentLogic.combinePaymentNotice(xmlString);
	
		
		//支付代码结构改造v 1.0.2
		paymentCoreLogic.noticeOfConsume(xmlString);
	}

	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: noticeRefundResult
	 * @Description: 得到OpenAPI退款结果通知
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param xmlString
	 * @throws TException
	 * @see com.froad.thrift.service.PaymentService.Iface#noticeRefundResult(java.lang.String)
	 */
	@Override
	public void noticeRefundResult(String xmlString) throws TException {
		if(StringUtils.isEmpty(xmlString)){
			LogCvt.error("收到OpenAPI无效的退款空通知内容");
			return;
		}
		
//		LogCvt.info("已接收到OpenAPI的退款通知内容: " + xmlString);
//		paymentLogic.refundPyamentNotice(xmlString);
		
		paymentCoreLogic.noticeOfRefund(xmlString);
	}
	
	@Override
	public ResultVo verifyPaymentResultForTask(String paymentId) throws TException {
		LogCvt.info("收到主动确认OpenAPI支付结果请求 调用方应为定时任务");
//		ResultBean result = paymentLogic.verifyPaymentResultForTask(paymentId);
		ResultBean result = paymentCoreLogic.verifyPaymentResult(paymentId);
        return new ResultVo(result.getCode(),result.getMsg());
	}

	@Override
	public PaymentQueryPageVo queryPaymentForBoss(PaymentQueryVo queryBean)throws TException {
		return paymentQueryHandler.queryPaymentForBoss(queryBean);
	}

	@Override
	public PaymentQueryPageVo queryPaymentForBossOfException(PaymentQueryExcetionVo queryVo) throws TException {
		return paymentQueryHandler.queryPaymentForBossOfException(queryVo);
	}

	@Override
	public ResultVo verifyFoilCardNum(String mobileNum,String clientId) throws TException {
		if(StringUtils.isNotEmpty(mobileNum)){
			ResultBean result = awipsThirdparty.verifyFoilCardNumOfOpenAPI(clientId, mobileNum);
			return new ResultVo(result.getCode(),result.getMsg());
		}
		
		return new ResultVo("9999","参数为空");
	}
}
