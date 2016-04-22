/**
 * Project Name:Froad Cbank Server Order
 * File Name:ThirdpartyPayLogicImpl.java
 * Package Name:com.froad.logic.impl
 * Date:2015-9-22上午10:00:24
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.froad.common.beans.ResultBean;
import com.froad.common.beans.payment.RefundTempBean;
import com.froad.enums.RefundState;
import com.froad.enums.ResultCode;
import com.froad.exceptions.PaymentException;
import com.froad.logback.LogCvt;
import com.froad.logic.ThirdpartyPayLogic;
import com.froad.logic.impl.payment.PaymentCoreLogicImpl;
import com.froad.logic.payment.PaymentCoreLogic;
import com.froad.logic.payment.RefundLogic;
import com.froad.po.Payment;
import com.froad.po.refund.RefundHistory;
import com.froad.po.refund.RefundPaymentInfo;
import com.froad.support.RefundSupport;
import com.froad.support.impl.RefundSupportImpl;
import com.froad.support.impl.payment.AWIPSThirdpartyImpl;
import com.froad.support.impl.payment.DataPersistentImpl;
import com.froad.support.payment.AWIPSThirdparty;
import com.froad.support.payment.DataPersistent;
import com.froad.thirdparty.common.OpenApiCommand;
import com.froad.thirdparty.dto.request.openapi.OpenApiOrderDetail;
import com.froad.thirdparty.dto.request.openapi.OpenApiReq;
import com.froad.thirdparty.dto.request.openapi.OpenApiReq.BillOrderType;
import com.froad.thirdparty.dto.request.openapi.OpenApiReq.Client;
import com.froad.thirdparty.dto.request.openapi.OpenApiReq.PayDirect;
import com.froad.thirdparty.dto.request.openapi.OpenApiReq.PayType;
import com.froad.thirdparty.dto.request.openapi.OpenApiRes;
import com.froad.thirdparty.dto.request.points.PointsReq;
import com.froad.thirdparty.dto.response.points.PointsRes;
import com.froad.thrift.vo.payment.CombineSettleReq;
import com.froad.util.Arith;
import com.froad.util.DateUtil;
import com.froad.util.payment.BaseSubassembly;
import com.froad.util.payment.Const;
import com.froad.util.payment.EsyT;

/**
 * ClassName:ThirdpartyPayLogicImpl
 * Reason:	 TODO ADD REASON.
 * Date:     2015-9-22 上午10:00:24
 * @author   Zxy
 * @version  
 * @see 	 
 */
public class ThirdpartyPayLogicImpl implements ThirdpartyPayLogic {

	private AWIPSThirdparty awipsThirdparty = new AWIPSThirdpartyImpl();
	RefundSupport refundSupport = new RefundSupportImpl();
	DataPersistent dataPersistent = new DataPersistentImpl();
	private PaymentCoreLogic paymentCoreLogic = new PaymentCoreLogicImpl();
	RefundLogic reundLogic = new com.froad.logic.impl.payment.RefundLogicImpl();
	
	/**
	 * 结算请求体
	 * builderSettleReq:(这里用一句话描述这个方法的作用).
	 *
	 * @author Zxy
	 * 2015-9-22 上午10:15:35
	 * @param req
	 * @return
	 *
	 */
	private OpenApiReq builderSettleReq(CombineSettleReq req){
		List<OpenApiOrderDetail> orderDetails = new ArrayList<OpenApiOrderDetail>();
		String openAPIPartnerNo = BaseSubassembly.acquireOpenAPIPartnerNo(req.getClientId());
		OpenApiOrderDetail detail = new OpenApiOrderDetail(
				req.getPaymentId() //支付流水号
				,
				req.getPayValue()//支付金额
				, openAPIPartnerNo, 
				"社区银行结算订单"
				);
		orderDetails.add(detail);
		
		OpenApiReq apiReq = new OpenApiReq(
				orderDetails, 
				BillOrderType.COMBINE,//指定消费现金模式
				req.getPayValue(), //支付金额
				PayType.TIMELY_PAY, //及时结算
				Client.PC,
				req.getPaymentOrgNo(), 
				null, 
				PayDirect.F_MERCHANT, //直连方式
				"社区银行结算订单", 
				openAPIPartnerNo, 
				"社区银行结算订单", 
				"",
				"",
				DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT4, new Date()), 
				req.getMerchantAndOutletId().replace("|", "_") + "|" + OpenApiCommand.MERCHANT_ACCT_QUERY_URL,
				"20"
				);
		return apiReq;
	}
	
	@Override
	public ResultBean cashCombineSettle(CombineSettleReq req) {
		OpenApiReq apiReq = builderSettleReq(req);
		OpenApiRes res = null;
		try {
			res = awipsThirdparty.cashConsume(apiReq);
		} catch (PaymentException e) {
			throw e;
		}
		if(Const.SUCCESS_CODE.equals(res.getResultCode())){
			return new ResultBean(ResultCode.success,res.getFroadBillNo());
		}
		return new ResultBean(ResultCode.failed,res.getResultDesc());
	}

	@Override
	public ResultBean retryRefund(String refundId) {
		RefundHistory refund = refundSupport.getByRefundId(refundId);
		List<RefundPaymentInfo> refundPaymentInfo = refund.getPaymentInfo();
		
		
		if(refundPaymentInfo != null && refundPaymentInfo.size() != 0){
			double cash = 0d;
			double point = 0d;
			double present = 0d;
			Payment original;
			Payment target;
			for (RefundPaymentInfo paymentInfo : refundPaymentInfo) {
				if("1".equals(paymentInfo.getType())){
					cash = Arith.div(paymentInfo.getRefundValue(), 1000);
				}else if ("2".equals(paymentInfo.getType())){
					Integer froadRate = BaseSubassembly.acquireFroadPointPointRate(refund.getClientId());
					if(froadRate == null){
						return new ResultBean(ResultCode.failed,"获取方付通积分比例失败");
					}
					point = Arith.div(Arith.div(paymentInfo.getRefundValue(), 1000),froadRate);
				}else if ("3".equals(paymentInfo.getType())){
					Integer pointRate = BaseSubassembly.acquireBankPointPointRate(refund.getClientId());
					if(pointRate == null){
						return new ResultBean(ResultCode.failed,"获取银行积分比例失败");
					}
					point = Arith.div(Arith.div(paymentInfo.getRefundValue(), 1000),pointRate);
				}else if ("6".equals(paymentInfo.getType())){
					present = Arith.div(paymentInfo.getRefundValue(), 1000);
				}
				original = new Payment();
				original.setPaymentId(paymentInfo.getPaymentId());
				original.setIsDisposeException("0");
				target = new Payment();
				target.setIsDisposeException("1");
				if(dataPersistent.findAndModifyPaymentFromMongoDB(original, target) == null){
					return new ResultBean(ResultCode.failed, "该数据已处理");
				}
			}
			refund.setRefundState("2");//退款中
			refundSupport.updateRefundState(refundId, "2", null);
			ResultBean resultBean = paymentCoreLogic.refundUserPaymentCapital(refund.getOrderId(), present, point, cash);
			
			if(EsyT.isResultBeanSuccess(resultBean)){
				RefundTempBean refundTemp = (RefundTempBean)resultBean.getData();
				for (RefundPaymentInfo paymentInfo : refundPaymentInfo) {
					if("1".equals(paymentInfo.getType())){
						paymentInfo.setPaymentId(refundTemp.getPaymentCashPaymentId());
						paymentInfo.setResultCode(resultBean.getCode());
						paymentInfo.setResultDesc(resultBean.getMsg());
					}else if ("2".equals(paymentInfo.getType()) || "3".equals(paymentInfo.getType())){
						paymentInfo.setPaymentId(refundTemp.getPaymentPointPaymentId());
						paymentInfo.setResultCode(resultBean.getCode());
						paymentInfo.setResultDesc(resultBean.getMsg());
					}else if ("6".equals(paymentInfo.getType())){
						paymentInfo.setPaymentId(refundTemp.getPresentPointPaymentId());
						paymentInfo.setResultCode(resultBean.getCode());
						paymentInfo.setResultDesc(resultBean.getMsg());
					}
				}
				String refundState = "2";
				if(refundPaymentInfo.size() == 1){ //单一类型退款
					if("1".equals(refundPaymentInfo.get(0).getType())){
						if(!RefundState.REFUND_FAILED.equals(refundTemp.getRefundCashState())){
							refundSupport.updateRefundState(refundId, refundState, refundPaymentInfo);
							return new ResultBean(ResultCode.success,"现金退款重发成功，等待财务处理");
						}else{
							refundState = "4";
							refundSupport.updateRefundState(refundId, refundState, refundPaymentInfo);
							return new ResultBean(ResultCode.failed,resultBean.getMsg());
						}
					}else if ("2".equals(refundPaymentInfo.get(0).getType()) || "3".equals(refundPaymentInfo.get(0).getType())){
						if(RefundState.REFUND_SUCCESS.equals(refundTemp.getRefundPointState())){
							refundState = "3";
							refundSupport.updateRefundState(refundId, refundState, refundPaymentInfo);
							return new ResultBean(ResultCode.success,"退款完成");
						}else{
							refundState = "4";
							refundSupport.updateRefundState(refundId, refundState, refundPaymentInfo);
							return new ResultBean(ResultCode.failed,resultBean.getMsg());
						}
					}
				}else if(refundPaymentInfo.size() == 2){ //退款流水两条，如果有赠送积分的则认为是1条
					boolean isExitPresent = false;
					RefundPaymentInfo refundPayment = null;
					for (RefundPaymentInfo paymentInfo : refundPaymentInfo) {
						if("6".equals(paymentInfo.getType())){
							isExitPresent = true;
						}else{
							refundPayment = paymentInfo;
						}
					}
					
					if(isExitPresent){ //存在赠送积分记录 //算单一退款记录
						if("1".equals(refundPayment.getType())){
							if(!RefundState.REFUND_FAILED.equals(refundTemp.getRefundCashState())){
								refundSupport.updateRefundState(refundId, refundState, refundPaymentInfo);
								return new ResultBean(ResultCode.success,"现金退款重发成功，等待财务处理");
							}else{
								refundState = "4";
								refundSupport.updateRefundState(refundId, refundState, refundPaymentInfo);
								return new ResultBean(ResultCode.failed,resultBean.getMsg());
							}
						}else if ("2".equals(refundPayment.getType()) || "3".equals(refundPayment.getType())){
							if(RefundState.REFUND_SUCCESS.equals(refundTemp.getRefundPointState())){
								refundState = "3";
								refundSupport.updateRefundState(refundId, refundState, refundPaymentInfo);
								return new ResultBean(ResultCode.success,"退款完成");
							}else{
								refundState = "4";
								refundSupport.updateRefundState(refundId, refundState, refundPaymentInfo);
								return new ResultBean(ResultCode.failed,resultBean.getMsg());
							}
						}
					}else{
						refundState = "2";
						refundSupport.updateRefundState(refundId, refundState, refundPaymentInfo);
						return new ResultBean(ResultCode.success,"退款重发成功，等待财务处理");
					}
				}else{
					refundState = "2";
					refundSupport.updateRefundState(refundId, refundState, refundPaymentInfo);
					return new ResultBean(ResultCode.success,"退款重发成功，等待财务处理");
				}
				//修改refund数据完毕
			}
			
			RefundTempBean refundTemp = (RefundTempBean)resultBean.getData();
			for (RefundPaymentInfo paymentInfo : refundPaymentInfo) {
				if("1".equals(paymentInfo.getType())){
					paymentInfo.setPaymentId(refundTemp.getPaymentCashPaymentId());
					paymentInfo.setResultCode(resultBean.getCode());
					paymentInfo.setResultDesc(resultBean.getMsg());
				}else if ("2".equals(paymentInfo.getType()) || "3".equals(paymentInfo.getType())){
					paymentInfo.setPaymentId(refundTemp.getPaymentPointPaymentId());
					paymentInfo.setResultCode(resultBean.getCode());
					paymentInfo.setResultDesc(resultBean.getMsg());
				}else if ("6".equals(paymentInfo.getType())){
					paymentInfo.setPaymentId(refundTemp.getPresentPointPaymentId());
					paymentInfo.setResultCode(resultBean.getCode());
					paymentInfo.setResultDesc(resultBean.getMsg());
				}
			}
			
			refundSupport.updateRefundState(refundId, "4", refundPaymentInfo);
			return resultBean;
		}else{
			return new ResultBean(ResultCode.failed,"退款数据不正确，无法正常退款");
		}
	}

	@Override
	public ResultBean refundPayCash(String paymentId) {
		Payment payment = dataPersistent.findByPaymentIdFromMongoDB(paymentId);
		if(payment == null){
			return new ResultBean(ResultCode.failed,"指定流水不存在");
		}
		
		if(!"5".equals(payment.getPaymentReason()) || !"5".equals(payment.getPaymentStatus())){
			return new ResultBean(ResultCode.failed,"该流水不符合处理条件");
		}
		
		Payment temp = new Payment();
		temp.setPaymentId(payment.getPaymentId());
		temp.setIsDisposeException("0");
		Payment target = new Payment();
		target.setIsDisposeException("1");
		
		if(dataPersistent.findAndModifyPaymentFromMongoDB(temp, target) == null){
			return new ResultBean(ResultCode.failed,"该数据已处理");
		}
		
		temp = new Payment();
		temp.setIsEnable(true);
		temp.setPaymentType(2);
		temp.setPaymentReason("2");
		temp.setOrderId(payment.getOrderId());
		temp.setPaymentStatus("4");
		List<Payment> payments = dataPersistent.findByPaymentConditionFromMongoDB(temp);
		if(payments == null || payments.size() != 1){
			return new ResultBean(ResultCode.failed,"未找到支付相关信息");
		}
		return reundLogic.autoRefundCashOfUserPay(payments.get(0).getPaymentId());
	}

	@Override
	public ResultBean refundPayPoint(String paymentId) {
		Payment payment = dataPersistent.findByPaymentIdFromMongoDB(paymentId);
		if(payment == null){
			return new ResultBean(ResultCode.failed,"指定流水不存在");
		}
		
		if(!"5".equals(payment.getPaymentReason()) || !"5".equals(payment.getPaymentStatus())){
			return new ResultBean(ResultCode.failed,"该流水不符合处理条件");
		}
		Payment temp = new Payment();
		temp.setPaymentId(payment.getPaymentId());
		temp.setIsDisposeException("0");
		Payment target = new Payment();
		target.setIsDisposeException("1");
		
		if(dataPersistent.findAndModifyPaymentFromMongoDB(temp, target) == null){
			return new ResultBean(ResultCode.failed,"该数据已处理");
		}
		
		temp = new Payment();
		temp.setIsEnable(true);
		temp.setPaymentTypeDetails(0);
		temp.setPaymentReason("2");
		temp.setOrderId(payment.getOrderId());
		temp.setPaymentStatus("4");
		List<Payment> payments = dataPersistent.findByPaymentConditionFromMongoDB(temp);
		if(payments == null || payments.size() != 1){
			return new ResultBean(ResultCode.failed,"未找到支付相关信息");
		}
		return reundLogic.autoRefundPointOfUserPay(payments.get(0).getPaymentId());
	}

	@Override
	public ResultBean pointPresent(String clientId,String loginID,double value) {
		String objectId = EsyT.simpleID();
		LogCvt.info("收到外部赠送积分请求 objectId: " + objectId);
		PointsReq req = new PointsReq(Const.FROAD_POINT_ORG_NO,loginID,
				"1",
				objectId, 
				"社区银行自动赠送积分订单"
				, 
				"", 
				"社区银行自动赠送积分订单", String.valueOf(value), BaseSubassembly.acquirePointPartnerNo(clientId), "");
		PointsRes res;
		try {
			res = awipsThirdparty.pointPresent(req);
		} catch (Exception e) {
			LogCvt.error("外部接口赠送积分请求异常");
			return new ResultBean(ResultCode.payment_thirted_request_faild,"请求发发送异常",objectId);
		}
		if("0000".equals(res.getResultCode())){
			return new ResultBean(ResultCode.success,"赠送积分成功",objectId);
		}else{
			return new ResultBean(ResultCode.failed,res.getResultDesc(),objectId);
		}
	}
	
}
