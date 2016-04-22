package com.froad.support.impl.payment;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.froad.common.beans.ResultBean;
import com.froad.enums.MonitorPointEnum;
import com.froad.enums.OrderStatus;
import com.froad.enums.ResultCode;
import com.froad.enums.SettlementStatus;
import com.froad.logic.OrderLogic;
import com.froad.logic.SettlementLogic;
import com.froad.logic.impl.OrderLogicImpl;
import com.froad.logic.impl.SettlementLogicImpl;
import com.froad.logic.impl.payment.DoPayTrailingImpl;
import com.froad.logic.impl.payment.RefundLogicImpl;
import com.froad.logic.payment.DoPayTrailing;
import com.froad.logic.payment.RefundLogic;
import com.froad.po.Payment;
import com.froad.po.mongo.OrderMongo;
import com.froad.support.payment.AWIPSThirdparty;
import com.froad.support.payment.VerifyBusiness;
import com.froad.support.payment.DataWrap;
import com.froad.thirdparty.dto.request.openapi.OpenApiRes;
import com.froad.thirdparty.dto.request.points.ResponseQueryOrderStatusApi;
import com.froad.thirdparty.dto.response.points.PointsRes;
import com.froad.thirdparty.dto.response.points.PointsApiQueryParams.StateCode;
import com.froad.util.payment.BaseSubassembly;
import com.froad.util.payment.Const;
import com.froad.util.payment.EsyT;
import com.froad.util.payment.Logger;
import com.froad.util.payment.ThirdpartyRequestBuilder;

public class VerifyBusinessImpl implements VerifyBusiness {

	
	private static final String STATE_CODE_PAY_SUCCESS = "0";
	private static final String STATE_CODE_PAY_FAILED = "1";
	private static final String ORDER_NOT_FOUND_CODE = "0100";
	
	
	private DataWrap dataWrap = new DataWrapImpl();
	private AWIPSThirdparty awipsThirdparty = new AWIPSThirdpartyImpl();
	private SettlementLogic settlementLogic = new SettlementLogicImpl();
	private RefundLogic refundLogic = new RefundLogicImpl();
	private OrderLogic orderLogic = new OrderLogicImpl();
	private DoPayTrailing doPayTrailing = new DoPayTrailingImpl();
	
	
	/**
	 * 通知退款模块退款结果
	* <p>Function: noticeRefundFun</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-28 下午12:13:38
	* @version 1.0
	* @param noticeMap
	* @return
	 */
	private boolean noticeRefundFun(Map<String, Boolean> noticeMap){
		return new com.froad.logic.impl.RefundLogicImpl().updateApprovalResult(noticeMap);
	}
	
	/**
	 * 查询订单信息
	* <p>Function: queryOrderByOrderId</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015年8月5日 下午2:55:23
	* @version 1.0
	* @param clientId
	* @param orderId
	* @return
	 */
	private OrderMongo queryOrderByOrderId(String clientId,String orderId){
		ResultBean resultBean = orderLogic.getOrderByOrderId(clientId, orderId);
		if(EsyT.isResultBeanSuccess(resultBean)){
			return (OrderMongo) resultBean.getData();
		}
		return null;
	}
	
	private void updateOrderByOrderId(OrderMongo order){
		orderLogic.updateOrderForPay(order);
	}
	
	
	/**
	 * 回滚流水步骤
	* <p>Function: rollBackPaymentStep</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-26 下午4:01:49
	* @version 1.0
	* @param paymentId
	* @return
	 */
	private boolean rollBackPaymentStep(String paymentId){
		Payment payment = new Payment();
		payment.setPaymentId(paymentId);
		payment.setStep(3);
		return dataWrap.modifyPaymentById(payment);
	}
	
	@Override
	public ResultBean typeOfCashForSettle(Payment payment) {
		String paymentId = payment.getPaymentId();
		
		OpenApiRes res;
		try {
			res = awipsThirdparty.verifyPayResult(ThirdpartyRequestBuilder.builderCashVerifyReq(payment,Const.OPENAPI_ORDER_TYPE_TRANS));
		} catch (Exception e) {
			Logger.logError("核实结算结果请求异常,默认为处理中不做逻辑变化", e);
			rollBackPaymentStep(paymentId);
			return new ResultBean(ResultCode.success);
		}
		
		String resultCode = res.getResultCode();
		String billNo = StringUtils.isEmpty(payment.getBillNo()) ? res.getFroadBillNo() : null;
		if(!Const.SUCCESS_CODE.equals(resultCode)){
			if(ORDER_NOT_FOUND_CODE.equals(resultCode)){
				//订单号不存在，OpenAPI无该订单
				dataWrap.removePaymentTimeFromRedis(paymentId);
				dataWrap.modifyPaymentToPayFailedOfVerify(paymentId,resultCode,"订单不存在", "主动查询结算结果，订单不存在",billNo);
				settlementLogic.notitySettlement(paymentId, SettlementStatus.settlementfailed, null);
				return new ResultBean(ResultCode.success);
			}
			// Nothing To Do
			rollBackPaymentStep(paymentId);
			return new ResultBean(ResultCode.success);
		}

		String stateCode = res.getOrders().get(0).getStateCode();
		String verifyRemark = res.getOrders().get(0).getRemark();
		
		if(STATE_CODE_PAY_SUCCESS.equals(stateCode)){
			// 支付成功
			dataWrap.modifyPaymentToPaySuccessOfVerify(paymentId,stateCode,verifyRemark, "主动查询结算结果，结算成功",billNo);
			dataWrap.removePaymentTimeFromRedis(paymentId);
			settlementLogic.notitySettlement(paymentId, SettlementStatus.settlementsucc, null);
			return new ResultBean(ResultCode.success);
		} else if (STATE_CODE_PAY_FAILED.equals(stateCode)) {
			// 支付失败
			dataWrap.modifyPaymentToPayFailedOfVerify(paymentId,stateCode,verifyRemark, "主动查询结算结果，结算失败",billNo);
			dataWrap.removePaymentTimeFromRedis(paymentId);
			settlementLogic.notitySettlement(paymentId, SettlementStatus.settlementfailed, null);

			return new ResultBean(ResultCode.success);
		} else {
			// 支付状态未明确
			// Nothing To Do
			rollBackPaymentStep(paymentId);
			return new ResultBean(ResultCode.success);
		}
	}

	@Override
	public ResultBean typeOfCashForUserRefund(Payment payment) {
		String paymentId = payment.getPaymentId();
		
		OpenApiRes res;
		try {
			res = awipsThirdparty.verifyPayResult(ThirdpartyRequestBuilder.builderCashVerifyReq(payment,Const.OPENAPI_ORDER_TYPE_REFUND));
		} catch (Exception e) {
			Logger.logError("核实退款结果请求异常,默认为处理中不做逻辑变化", e);
			rollBackPaymentStep(paymentId);
			return new ResultBean(ResultCode.success);
		}
		
		String resultCode = res.getResultCode();
		Map<String, Boolean> noticeMap = new HashMap<String, Boolean>();
		String billNo = StringUtils.isEmpty(payment.getBillNo()) ? res.getFroadBillNo() : null;
		if(!Const.SUCCESS_CODE.equals(resultCode)){
			
			if(ORDER_NOT_FOUND_CODE.equals(resultCode)){
				//订单号不存在，OpenAPI无该订单
				dataWrap.removePaymentTimeFromRedis(paymentId);
				dataWrap.modifyPaymentToPayFailedOfVerify(paymentId, resultCode,"订单不存在", "主动查询退款结果，订单不存在",billNo);
				noticeMap.put(paymentId, false);
				
				Payment refundPoint = dataWrap.queryToRefundPaymentOfPoint(payment.getClientId(),payment.getPaymentId());
				if (refundPoint == null) {
					Logger.logInfo("此次退款没有连带积分退款，退款已完成");
					noticeRefundFun(noticeMap);
					return new ResultBean(ResultCode.failed);
				}
				dataWrap.modifyPaymentToPayFailed(refundPoint.getPaymentId(), null,null,"现金退款已经失败，积分标记为退款失败");
				noticeMap.put(refundPoint.getPaymentId(), false);
				noticeRefundFun(noticeMap);
				return new ResultBean(ResultCode.failed);
			}
			
			// Nothing To Do
			rollBackPaymentStep(paymentId);
			return new ResultBean(ResultCode.success);
		}
		String stateCode = res.getOrders().get(0).getStateCode();
		String verifyRemark = res.getOrders().get(0).getRemark();
		OrderMongo order = queryOrderByOrderId(payment.getClientId(), payment.getOrderId());
		if(STATE_CODE_PAY_SUCCESS.equals(stateCode)){
			// 支付成功
			dataWrap.modifyPaymentToPaySuccessOfVerify(paymentId, stateCode,verifyRemark, "主动查询退款结果，退款成功",billNo);
			dataWrap.removePaymentTimeFromRedis(paymentId);
			noticeMap.put(paymentId, true);
			// 找出待退积分流水
			Payment refundPoint = dataWrap.queryToRefundPaymentOfPoint(payment.getClientId(), payment.getPaymentId());
			if (refundPoint == null) {
				Logger.logInfo("此次退款没有连带积分退款，退款已完成");
//				if (1 == order.getIsVipOrder()) {
//					OrderMongo orderUpdate = new OrderMongo();
//					orderUpdate.setOrderId(order.getOrderId());
//					orderUpdate.setClientId(order.getClientId());
//					orderUpdate.setRemark("自动退款完成");
//					updateOrderByOrderId(orderUpdate);
//				}else{
//					noticeRefundFun(noticeMap);
//				}
				noticeRefundFun(noticeMap);
				return new ResultBean(ResultCode.success);
			}

			ResultBean resultBean = refundLogic.userRefundGoOnPointOfUserPay(refundPoint);
			if (!EsyT.isResultBeanSuccess(resultBean)) {
				noticeMap.put(refundPoint.getPaymentId(), false);
				Logger.logError("现金退款成功，退款积分失败", "orderId", payment.getOrderId());
				EsyT.sendPoint(MonitorPointEnum.order_pay_user_refund_point_failed);
//				if(1 == order.getIsVipOrder()){
//					OrderMongo orderUpdate = new OrderMongo();
//					orderUpdate.setOrderId(order.getOrderId());
//					orderUpdate.setClientId(order.getClientId());
//					orderUpdate.setRemark("自动退款完成");
//					updateOrderByOrderId(orderUpdate);
//				}else{
//					noticeRefundFun(noticeMap);
//				}
				noticeRefundFun(noticeMap);
				return new ResultBean(ResultCode.failed, "用户退款现金成功，积分失败");
			}
			noticeMap.put(refundPoint.getPaymentId(), true);
//			if(1 == order.getIsVipOrder()){
//				OrderMongo orderUpdate = new OrderMongo();
//				orderUpdate.setOrderId(order.getOrderId());
//				orderUpdate.setClientId(order.getClientId());
//				orderUpdate.setRemark("自动退款完成");
//				updateOrderByOrderId(orderUpdate);
//			}else{
//				noticeRefundFun(noticeMap);
//			}
			noticeRefundFun(noticeMap);
			return new ResultBean(ResultCode.success);
		} else if (STATE_CODE_PAY_FAILED.equals(stateCode)) {

			// 支付失败
			dataWrap.modifyPaymentToPayFailedOfVerify(paymentId, stateCode,verifyRemark, "主动查询退款结果，退款失败",billNo);
			dataWrap.removePaymentTimeFromRedis(paymentId);
			noticeMap.put(paymentId, false);

			Payment refundPoint = dataWrap.queryToRefundPaymentOfPoint(payment.getClientId(), payment.getPaymentId());
			if (refundPoint == null) {
				Logger.logInfo("此次退款没有连带积分退款，退款已完成");
//				if(1 == order.getIsVipOrder()){
//					OrderMongo orderUpdate = new OrderMongo();
//					orderUpdate.setOrderId(order.getOrderId());
//					orderUpdate.setClientId(order.getClientId());
//					orderUpdate.setRemark("自动退款失败: " + verifyRemark);
//					updateOrderByOrderId(orderUpdate);
//				}else{
//					noticeRefundFun(noticeMap);
//				}
				noticeRefundFun(noticeMap);
				return new ResultBean(ResultCode.failed);
			}

			dataWrap.modifyPaymentToPayFailed(refundPoint.getPaymentId(),null,null,"现金退款已经失败，积分标记为退款失败");
			noticeMap.put(refundPoint.getPaymentId(), false);

//			if (1 == order.getIsVipOrder()) {
//				OrderMongo orderUpdate = new OrderMongo();
//				orderUpdate.setOrderId(order.getOrderId());
//				orderUpdate.setClientId(order.getClientId());
//				orderUpdate.setRemark("自动退款失败: " + verifyRemark);
//				updateOrderByOrderId(orderUpdate);
//			}else{
//				noticeRefundFun(noticeMap);
//			}
			noticeRefundFun(noticeMap);
			return new ResultBean(ResultCode.failed);

		} else {
			// 支付状态未明确
			// Nothing To Do
			rollBackPaymentStep(paymentId);
			return new ResultBean(ResultCode.success);
		}
	}

	@Override
	public ResultBean typeOfCashForUserPay(Payment payment) {
		String paymentId = payment.getPaymentId();
		String orderId = payment.getOrderId();
		
		ResultBean resultBean = orderLogic.getOrderByOrderId(payment.getClientId(), orderId);
		if(!EsyT.isResultBeanSuccess(resultBean)){
			Logger.logError("确认流水结果关联订单失败","resultBean",resultBean);
			return new ResultBean(ResultCode.failed);
		}
		
		OpenApiRes res;
		try {
			res = awipsThirdparty.verifyPayResult(ThirdpartyRequestBuilder.builderCashVerifyReq(payment,Const.OPENAPI_ORDER_TYPE_TRANS));
		} catch (Exception e) {
			Logger.logError("核实支付结果请求异常,默认为处理中不做逻辑变化", e);
			rollBackPaymentStep(paymentId);
			return new ResultBean(ResultCode.success);
		}
		
		String resultCode = res.getResultCode();
		boolean isOrderClosed = false;
		String billNo = StringUtils.isEmpty(payment.getBillNo()) ? res.getFroadBillNo() : null;
		OrderMongo order = (OrderMongo) resultBean.getData();
		if (OrderStatus.closed.getCode().equals(order.getOrderStatus()) || OrderStatus.sysclosed.getCode().equals(order.getOrderStatus())) {
			isOrderClosed = true;
		}
		
		if(!Const.SUCCESS_CODE.equals(resultCode)){
			if(ORDER_NOT_FOUND_CODE.equals(resultCode)){
				
				//订单号不存在，OpenAPI无该订单
				dataWrap.removePaymentTimeFromRedis(paymentId);
				dataWrap.modifyPaymentToPayFailedOfVerify(paymentId, resultCode,"订单不存在", "主动查询支付结果，订单不存在",billNo);
				
				if(!isOrderClosed){ //订单未关闭
					doPayTrailing.doPayFailed(order); //支付失败
					
					if(BaseSubassembly.isCombinePayment(order.getPaymentMethod())){
						//如果是合并订单支付，则涉及到需要退还积分
						//查询出用户已经完成支付的积分流水记录
						Payment pointPayment = dataWrap.queryPaymentOfUserPaiedTypePoint(order.getOrderId());
						if(pointPayment == null){
							Logger.logError("组合支付通知失败后应当退还积分，但未能找到成功支付的积分流水");
							return new ResultBean(ResultCode.failed,"组合支付通知失败后应当退还积分，但未能找到成功支付的积分流水");
						}
						resultBean = refundLogic.autoRefundPointOfUserPay(pointPayment.getPaymentId());
						if(!EsyT.isResultBeanSuccess(resultBean)){
							Logger.logError("组合支付通知失败后应当退还积分，但退还积分失败","resultBean",resultBean);
							return new ResultBean(ResultCode.failed,resultBean.getMsg());
						}
					}
				}
				
				return new ResultBean(ResultCode.failed);
			}
			
			// Nothing To Do
			rollBackPaymentStep(paymentId);
			return new ResultBean(ResultCode.success);
		}
		
		String stateCode = res.getOrders().get(0).getStateCode();
		String verifyRemark = res.getOrders().get(0).getRemark();
		if(STATE_CODE_PAY_SUCCESS.equals(stateCode)){
			// 支付成功
			dataWrap.modifyPaymentToPaySuccessOfVerify(paymentId, stateCode,verifyRemark,"主动查询支付结果，支付成功",billNo);
			dataWrap.removePaymentTimeFromRedis(paymentId);
			if (isOrderClosed) {
				// 订单已经关闭
				Logger.logInfo("该支付流水的对应订单已经被关闭,将发起退款");
				resultBean = refundLogic.autoRefundCashOfUserPay(paymentId);
				if (!EsyT.isResultBeanSuccess(resultBean)) {
					Logger.logError("支付成功但订单关闭，发起自动退现金失败");
				}
			} else {
				doPayTrailing.doPaySuccess(order);
			}

			return new ResultBean(ResultCode.success);
		} else if (STATE_CODE_PAY_FAILED.equals(stateCode)) {
			// 支付失败
			dataWrap.modifyPaymentToPayFailedOfVerify(paymentId, stateCode,verifyRemark,"主动查询支付结果，支付失败",billNo);
			dataWrap.removePaymentTimeFromRedis(paymentId);
			
			if (!isOrderClosed) { // 订单未关闭
				doPayTrailing.doPayFailed(order); //支付失败
				
				if(BaseSubassembly.isCombinePayment(order.getPaymentMethod())){
					// 如果是合并订单支付，则涉及到需要退还积分

					// 查询出用户已经完成支付的积分流水记录
					Payment pointPayment = dataWrap.queryPaymentOfUserPaiedTypePoint(order.getOrderId());
					if (pointPayment == null) {
						Logger.logError("组合支付通知失败后应当退还积分，但未能找到成功支付的积分流水");
						return new ResultBean(ResultCode.failed, "组合支付通知失败后应当退还积分，但未能找到成功支付的积分流水");
					}

					resultBean = refundLogic.autoRefundPointOfUserPay(pointPayment.getPaymentId());
					if (!EsyT.isResultBeanSuccess(resultBean)) {
						Logger.logError("组合支付通知失败后应当退还积分，但退还积分失败", "resultBean", resultBean);
						EsyT.sendPoint(MonitorPointEnum.order_pay_user_refund_point_failed);
						return new ResultBean(ResultCode.failed, resultBean.getMsg());
					}
				}
			}
			return new ResultBean(ResultCode.failed);
		} else {
			// 支付状态未明确
			// Nothing To Do
			rollBackPaymentStep(paymentId);
			return new ResultBean(ResultCode.success);
		}
		
	}

	@Override
	public ResultBean typeOfPointForUserPay(Payment payment) {
		String paymentId = payment.getPaymentId();
		PointsRes res;
		try {
			res = awipsThirdparty.verfyPayResult(ThirdpartyRequestBuilder.builderPointVerifyReq(Const.POINTS_ORDER_TYPE_TRANS, payment));
		} catch (Exception e) {
			Logger.logError("核实支付结果请求异常,默认为处理中不做逻辑变化", e);
			rollBackPaymentStep(paymentId);
			return new ResultBean(ResultCode.success);
		}
		
		String resultCode = res.getResultCode();
		
		if(!Const.SUCCESS_CODE.equals(resultCode)){
			rollBackPaymentStep(paymentId);
			return new ResultBean(ResultCode.success);
		}
		
		ResponseQueryOrderStatusApi orderStatus = res.getOrderStatus();
		
		ResultBean resultBean = orderLogic.getOrderByOrderId(payment.getClientId(), payment.getOrderId());
		if(!EsyT.isResultBeanSuccess(resultBean)){
			rollBackPaymentStep(paymentId);
			Logger.logError("确认流水结果关联订单失败","resultBean",resultBean);
			return new ResultBean(ResultCode.success);
		}
		
		OrderMongo order = (OrderMongo) resultBean.getData();
		boolean isOrderClosed = false;
		if(OrderStatus.closed.getCode().equals(order.getOrderStatus()) || OrderStatus.sysclosed.getCode().equals(order.getOrderStatus())){
        	isOrderClosed = true;
        }
		if(StateCode.not_exists.equals(orderStatus.getQueryParam().getOrderProccessState())){
			//订单不存在
			dataWrap.removePaymentTimeFromRedis(paymentId);
			dataWrap.modifyPaymentToPayFailed(paymentId, null, "主动查询积分系统支付结果: 订单不存在");
			Payment cashToPay = dataWrap.queryPaymentOfUserToPayTypeCash(payment.getOrderId());
			if(cashToPay != null){
				dataWrap.modifyPaymentToPayFailed(cashToPay.getPaymentId(), null, "组合支付: 积分支付订单不存在,现金支付取消");
			}
			doPayTrailing.doPayFailed(order);
			return new ResultBean(ResultCode.success);
		}else if(StateCode.success.equals(orderStatus.getQueryParam().getOrderProccessState())){
			dataWrap.removePaymentTimeFromRedis(paymentId);
			resultBean = refundLogic.autoRefundPointOfUserPay(paymentId);
			if(isOrderClosed){
				//如果订单已经关闭，但发现该异常订单也支付成功，则自动退款，但不执行失败逻辑
				return resultBean;
			}
			doPayTrailing.doPayFailed(order);
			return resultBean;
		}else if(StateCode.failed.equals(orderStatus.getQueryParam().getOrderProccessState())){
			dataWrap.removePaymentTimeFromRedis(paymentId);
			dataWrap.modifyPaymentToPayFailed(paymentId, null, "主动查询积分系统支付结果: 订单支付失败");
			Payment cashToPay = dataWrap.queryPaymentOfUserToPayTypeCash(payment.getOrderId());
			if(cashToPay != null){
				dataWrap.modifyPaymentToPayFailed(cashToPay.getPaymentId(), null, "组合支付: 积分支付失败,现金支付取消");
			}
			doPayTrailing.doPayFailed(order);
			return new ResultBean(ResultCode.success);
		}
		
		Logger.logInfo("主动确认积分支付结果: 支付结果不明确");
		rollBackPaymentStep(paymentId);
		return new ResultBean(ResultCode.failed);
	}

	@Override
	public ResultBean typeOfPointForUserRefund(Payment payment) {
		return new ResultBean(ResultCode.failed);
	}

}
