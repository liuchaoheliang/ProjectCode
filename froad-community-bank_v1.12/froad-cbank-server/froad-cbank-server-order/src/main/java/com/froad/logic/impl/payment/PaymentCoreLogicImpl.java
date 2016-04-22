package com.froad.logic.impl.payment;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.froad.common.beans.ResultBean;
import com.froad.common.beans.payment.PayThriftVoBean;
import com.froad.enums.OrderState;
import com.froad.enums.OrderStatus;
import com.froad.enums.ResultCode;
import com.froad.exceptions.PaymentException;
import com.froad.logic.OrderLogic;
import com.froad.logic.impl.OrderLogicImpl;
import com.froad.logic.payment.AssistLogic;
import com.froad.logic.payment.DoPayLogic;
import com.froad.logic.payment.DoPayTrailing;
import com.froad.logic.payment.NoticeLogic;
import com.froad.logic.payment.PaymentCoreLogic;
import com.froad.logic.payment.RefundLogic;
import com.froad.logic.payment.VerifyLogic;
import com.froad.po.Payment;
import com.froad.po.mongo.OrderMongo;
import com.froad.thirdparty.request.active.ActiveFunc;
import com.froad.thirdparty.request.active.impl.ActiveFuncImpl;
import com.froad.util.payment.BaseSubassembly;
import com.froad.util.payment.EsyT;
import com.froad.util.payment.Logger;

public class PaymentCoreLogicImpl implements PaymentCoreLogic {

	private AssistLogic assistLogic = new AssistLogicImpl();
	private DoPayLogic doPayLogic = new DoPayLogicImpl();
	private DoPayTrailing doPayTrailing = new DoPayTrailingImpl();
	private OrderLogic orderLogic = new OrderLogicImpl();
	private NoticeLogic noticeLogic = new NoticeLogicImpl();
	private RefundLogic refundLogic = new RefundLogicImpl();
	private VerifyLogic verifyLogic = new VerifyLogicImpl();
	private ActiveFunc activeFunc = new ActiveFuncImpl(); 
	/**
	 * 支付过程未到最终状态，回滚订单原始状态
	* <p>Function: rollBackOrderStatus</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-22 下午4:48:36
	* @version 1.0
	* @param orderId
	* @param currOrderStatus
	 */
	private void rollBackOrderStatus(String orderId,String clientId,String currOrderStatus){
		OrderMongo order = new OrderMongo();
		order.setOrderId(orderId);
		order.setClientId(clientId);
		order.setOrderStatus(currOrderStatus);
		orderLogic.updateOrderForPay(order);
		Logger.logInfo("回滚订单状态结果","order",order);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultBean doPayCoreForGeneralTrade(PayThriftVoBean payThriftVoBean) {

		ResultBean resultBean = orderLogic.getOrderByOrderId(payThriftVoBean.getClientId(),payThriftVoBean.getOrderId());
		if(!EsyT.isResultBeanSuccess(resultBean)){
			return new ResultBean(ResultCode.payment_order_none);
		}
		
		OrderMongo order = (OrderMongo) resultBean.getData();
		
		if(order == null){
			Logger.logError("支付订单不存在");
			return new ResultBean(ResultCode.payment_order_none);
		}
		
		//如果订单参加满赠，支付前满赠资格检查 - 接口新增
		if(!StringUtils.isEmpty(order.getIsActive())){	//先判断大订单参加了满减活动
			resultBean = activeFunc.fullGiveCheck(order);
			if(!EsyT.isResultBeanSuccess(resultBean)){
				return new ResultBean(ResultCode.payment_full_give_check_faild);
			}
		}
		
		String orderId = order.getOrderId();
		String clientId = order.getClientId();
		String currOrderStatus = order.getOrderStatus();
		
		//findAndModify 锁定订单修改为支付中状态  
		boolean flag = orderLogic.updateOrderStatusByOrderId(payThriftVoBean.getOrderId());
		if(!flag){
			rollBackOrderStatus(orderId,clientId,currOrderStatus);
			Logger.logError("当前订单状态不能支付", "currOrderStatus", currOrderStatus);
			return new ResultBean(ResultCode.payment_order_status_unsupport);
		}
		
		resultBean = assistLogic.validationAllBeforePay(order, payThriftVoBean);
		Logger.logInfo("支付前校验","resultBean",resultBean);
		if(!EsyT.isResultBeanSuccess(resultBean)){
			rollBackOrderStatus(orderId,clientId,currOrderStatus);
			return resultBean;
		}
		Object[] dataValidationRes = (Object[])resultBean.getData();
		if(1 != order.getIsQrcode() && 1 != order.getIsVipOrder()){ //如果不是面对面订单&不是vip订单  （这两种类型没有库存可言）
			if(OrderState.RETURNED.getCode().equals(order.getState())){//如果库存已退
				resultBean = assistLogic.checkAndOperateStore(order, false); //则扣除库存
				Logger.logInfo("操作/库存检查","resultBean",resultBean);
				if(!EsyT.isResultBeanSuccess(resultBean)){
					rollBackOrderStatus(orderId,order.getClientId(),currOrderStatus);
					return resultBean;
				}
			}
		}

		resultBean = assistLogic.completeOrderOfPaymentInfo(order, payThriftVoBean);
		Logger.logInfo("补全订单支付信息","resultBean",resultBean);
		if(!EsyT.isResultBeanSuccess(resultBean)){
			rollBackOrderStatus(orderId,clientId,currOrderStatus);
			return resultBean;
		}
		
		resultBean = assistLogic.savePaymentAndDisableOldPaymentOfUserPay(payThriftVoBean,dataValidationRes);
		Logger.logInfo("保存并禁用历史支付流水","resultBean",resultBean);
		if(!EsyT.isResultBeanSuccess(resultBean)){
			rollBackOrderStatus(orderId,clientId,currOrderStatus);
			return resultBean;
		}
		
		try {
			resultBean = doPayLogic.doPayCoreForTrade(order,(List<Payment>)resultBean.getData());
		} catch (PaymentException e) {
			Logger.logError("支付请求发送异常，返回正常结果，交由定时任务核实支付结果", e);
			return new ResultBean(ResultCode.success);
		}
		
		if(!EsyT.isResultBeanSuccess(resultBean)){ //订单支付失败，执行失败后的逻辑
			doPayTrailing.doPayFailed(order);
			return resultBean;
		}
		
		//订单支付成功
		if(BaseSubassembly.isPurePointsPayment(String.valueOf(payThriftVoBean.getPayType()))){ 
			Logger.logInfo("纯积分支付成功，直接执行后续逻辑");
			doPayTrailing.doPaySuccess(order);
		}
		return resultBean;
		
	}

	@Override
	public ResultBean noticeOfConsume(String noticeXML) {
		return noticeLogic.consumeNotic(noticeXML);
	}

	@Override
	public ResultBean noticeOfRefund(String noticeXML) {
		return noticeLogic.refundNotic(noticeXML);
	}

	@Override
	public ResultBean refundUserPaymentCapital(String orderId,Double refundPresentPoint, Double refundPayPoint,Double refundPayCash) {
		return refundLogic.refundUserPaymentCapital(orderId, refundPresentPoint, refundPayPoint, refundPayCash);
	}

	@Override
	public ResultBean verifyCoreForSeckillingTrade(OrderMongo order,PayThriftVoBean payThriftVoBean) {
		ResultBean resultBean = assistLogic.validationAllBeforePay(order, payThriftVoBean);
		if(!EsyT.isResultBeanSuccess(resultBean)){
			return resultBean;
		}
		order = assistLogic.completeOrderOfPaymentInfoForSeckilling(order, payThriftVoBean);
		return new ResultBean(ResultCode.success,order);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultBean doPayCoreForSeckillingTrade(OrderMongo order,PayThriftVoBean payThriftVoBean) {
		
		OrderMongo orderUpdate = new OrderMongo();
		orderUpdate.setOrderId(order.getOrderId());
		orderUpdate.setClientId(order.getClientId());
		orderUpdate.setOrderStatus(OrderStatus.paying.getCode());
		orderLogic.updateOrderForPay(orderUpdate);
		
		ResultBean resultBean = assistLogic.savePaymentAndDisableOldPaymentOfUserPay(payThriftVoBean,null);
		if(!EsyT.isResultBeanSuccess(resultBean)){
			return resultBean;
		}
		
		try {
			resultBean = doPayLogic.doPayCoreForTrade(order,(List<Payment>)resultBean.getData());
		} catch (PaymentException e) {
			Logger.logError("支付请求发送异常，返回正常结果，交由定时任务核实支付结果", e);
			return new ResultBean(ResultCode.success);
		}
		
		if(!EsyT.isResultBeanSuccess(resultBean)){ //订单支付失败，执行失败后的逻辑
			doPayTrailing.doPayFailed(order);
			return resultBean;
		}
		
		//订单支付成功
		if(BaseSubassembly.isPurePointsPayment(String.valueOf(payThriftVoBean.getPayType()))){ 
			Logger.logInfo("纯积分支付成功，直接执行后续逻辑");
			doPayTrailing.doPaySuccess(order);
		}
		
		return resultBean;
	}

	@Override
	public ResultBean verifyPaymentResult(String paymentId) {
		return verifyLogic.verifyPaymentResult(paymentId);
	}

	@Override
	public ResultBean cancelPayingOrderToRefundPaiedPoint(OrderMongo order) {
		Logger.logInfo("收到支付中退款请求","orderMongo",order);
		return refundLogic.cancelPayingOrderToRefundPaiedPoint(order);
	}

	@Override
	public ResultBean settleToMerchantCapital(String orderId, String clientId, double settleCash, String merchantId,String merchantOutletId) {
		return assistLogic.settleToMerchantCapital(orderId, clientId, settleCash, merchantId, merchantOutletId);
	}

}
