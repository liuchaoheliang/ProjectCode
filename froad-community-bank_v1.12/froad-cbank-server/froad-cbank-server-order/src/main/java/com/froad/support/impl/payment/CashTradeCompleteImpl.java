package com.froad.support.impl.payment;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.froad.common.beans.ResultBean;
import com.froad.enums.MonitorPointEnum;
import com.froad.enums.ResultCode;
import com.froad.enums.SettlementStatus;
import com.froad.logic.SettlementLogic;
import com.froad.logic.impl.SettlementLogicImpl;
import com.froad.logic.impl.payment.DoPayTrailingImpl;
import com.froad.logic.impl.payment.RefundLogicImpl;
import com.froad.logic.payment.DoPayTrailing;
import com.froad.logic.payment.RefundLogic;
import com.froad.po.Payment;
import com.froad.po.mongo.OrderMongo;
import com.froad.support.OrderSupport;
import com.froad.support.impl.OrderSupportImpl;
import com.froad.support.payment.CashTradeComplete;
import com.froad.support.payment.DataWrap;
import com.froad.thirdparty.dto.response.openapi.NoticeFroadApi;
import com.froad.util.payment.BaseSubassembly;
import com.froad.util.payment.Const;
import com.froad.util.payment.EsyT;
import com.froad.util.payment.Logger;

public class CashTradeCompleteImpl implements CashTradeComplete {

	private static final String STATE_CODE_PAY_SUCCESS = "0";
	private static final String STATE_CODE_PAY_FAILED = "1";
	private static final String ORDER_NOT_FOUND_CODE = "0311";
	private static final String ORDER_REFUND_FAILED = "0301";

	private DataWrap dataWrap = new DataWrapImpl();
	private DoPayTrailing doPayTrailing = new DoPayTrailingImpl();
	private RefundLogic refundLogic = new RefundLogicImpl();
	private SettlementLogic settlementLogic = new SettlementLogicImpl();
	private OrderSupport orderSupport = new OrderSupportImpl();
	
	/**
	 * 回滚流水步骤
	 * <p>Function: rollBackPaymentStep</p>
	 * <p>Description:</p>
	 * @author zhaoxy@thankjava.com
	 * @date 2015-5-26 下午4:01:49
	 * @version 1.0
	 * @param paymentId
	 * @return
	 */
	private boolean rollBackPaymentStep(String paymentId) {
		Payment payment = new Payment();
		payment.setPaymentId(paymentId);
		payment.setStep(3);
		return dataWrap.modifyPaymentById(payment);
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
		return orderSupport.getOrderByOrderId(clientId, orderId);
	}
	
	/**
	 * 更新订单信息
	* <p>Function: updateOrderByOrderId</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015年8月5日 下午3:07:53
	* @version 1.0
	* @param order
	 */
	private void updateOrderByOrderId(OrderMongo order){
		orderSupport.updateOrderByCondion(order);
	}
	
	/**
	 * 通知退款模块退款结果<p>
	 * Function: noticeRefundFun</p>
	 * <p>Description:</p>
	 * @author zhaoxy@thankjava.com
	 * @date 2015-5-28 下午12:13:38
	 * @version 1.0
	 * @param noticeMap
	 * @return
	 */
	private boolean noticeRefundFun(Map<String, Boolean> noticeMap) {
		return new com.froad.logic.impl.RefundLogicImpl().updateApprovalResult(noticeMap);
	}

	@Override
	public ResultBean noticeOfUserPay(Payment payment, NoticeFroadApi noticeFroadApi, boolean isOrderClosed,OrderMongo order) {
		
		String resultCode = noticeFroadApi.getSystem().getResultCode();
		String paymentId = payment.getPaymentId();
		
		//如果数据库中billNo是空的则，该支付在发起后有超时或其他情况，造成支系统受理成功但本系统认为受理失败而没有成功的填写billNo（赋值null则不更改billNo）
		String billNo = StringUtils.isEmpty(payment.getBillNo()) ? noticeFroadApi.getPay().getFroadBillNo() : null;
		
		if (isOrderClosed) {// 订单已关闭
			dataWrap.removePaymentTimeFromRedis(paymentId);
			String stateCode = noticeFroadApi.getOrder().getStateCode();
			String noticeRemark = noticeFroadApi.getOrder().getRemark();
			if (Const.SUCCESS_CODE.equals(resultCode)) {
				if (STATE_CODE_PAY_SUCCESS.equals(stateCode)) {
					dataWrap.modifyPaymentToPaySuccessOfNotice(paymentId, stateCode, noticeRemark, "订单关闭后，通知支付成功（系统自动退款）",billNo);
					ResultBean resultBean = refundLogic.autoRefundCashOfUserPay(paymentId);
					if (!EsyT.isResultBeanSuccess(resultBean)) {
						Logger.logError("自动退还现金失败", "resultBean", resultBean);
					}
				} else if (STATE_CODE_PAY_FAILED.equals(stateCode)) {
					dataWrap.modifyPaymentToPayFailedOfNotice(paymentId, stateCode, noticeRemark, "订单关闭后，通知支付失败（不做其他处理）",billNo);
				}
				return new ResultBean(ResultCode.success);
			} else {
				// Nothing To Do
				return new ResultBean(ResultCode.success);
			}
		} else { // 订单未关闭
			String stateCode = noticeFroadApi.getOrder().getStateCode();
			String noticeRemark = noticeFroadApi.getOrder().getRemark();
			
			if (STATE_CODE_PAY_SUCCESS.equals(stateCode)) {
				// 支付成功
				dataWrap.modifyPaymentToPaySuccessOfNotice(paymentId, stateCode, noticeRemark, "OpenAPI通知支付成功",billNo);
				dataWrap.removePaymentTimeFromRedis(paymentId);
				doPayTrailing.doPaySuccess(order);
				return new ResultBean(ResultCode.success);

			} else if (STATE_CODE_PAY_FAILED.equals(stateCode)) {

				// FIXME this is a big 坑！
				String tempRemark = EsyT.getSpecialRemarkValue(noticeRemark);
				noticeRemark = StringUtils.isEmpty(tempRemark) ? noticeRemark : tempRemark;
				// FIXME THIS IS A BIG 坑！ ---- 转意内容 为约定code

				// 支付失败
				dataWrap.modifyPaymentToPayFailedOfNotice(paymentId, stateCode, noticeRemark, noticeRemark,billNo);
				dataWrap.removePaymentTimeFromRedis(paymentId);
				doPayTrailing.doPayFailed(order);

				if (BaseSubassembly.isCombinePayment(order.getPaymentMethod())) {
					// 如果是合并订单支付，则涉及到需要退还积分

					// 查询出用户已经完成支付的积分流水记录
					Payment pointPayment = dataWrap.queryPaymentOfUserPaiedTypePoint(order.getOrderId());
					if (pointPayment == null) {
						Logger.logError("组合支付通知失败后应当退还积分，但未能找到成功支付的积分流水");
						return new ResultBean(ResultCode.failed, "组合支付通知失败后应当退还积分，但未能找到成功支付的积分流水");
					}

					ResultBean resultBean = refundLogic.autoRefundPointOfUserPay(pointPayment.getPaymentId());
					if (!EsyT.isResultBeanSuccess(resultBean)) {
						Logger.logError("组合支付通知失败后应当退还积分，但退还积分失败", "resultBean", resultBean);
						return new ResultBean(ResultCode.failed, resultBean.getMsg());
					}
				}
				return new ResultBean(ResultCode.success);
			} else {
				// 支付状态未明确
				// Nothing To Do
				rollBackPaymentStep(paymentId);
				return new ResultBean(ResultCode.success);
			}
		}
	}

	@Override
	public ResultBean noticeOfSettlePay(Payment payment, NoticeFroadApi noticeFroadApi,OrderMongo order) {

		String resultCode = noticeFroadApi.getSystem().getResultCode();
		String paymentId = payment.getPaymentId();
		String stateCode = noticeFroadApi.getOrder().getStateCode();
		String noticeRemark = noticeFroadApi.getOrder().getRemark();
		//如果数据库中billNo是空的则，该支付在发起后有超时或其他情况，造成支系统受理成功但本系统认为受理失败而没有成功的填写billNo（赋值null则不更改billNo）
		String billNo = StringUtils.isEmpty(payment.getBillNo()) ? noticeFroadApi.getPay().getFroadBillNo() : null;
		
		
		if (!Const.SUCCESS_CODE.equals(resultCode)) {
			// Nothing To Do
			rollBackPaymentStep(paymentId);
			return new ResultBean(ResultCode.success);
		}
		if (STATE_CODE_PAY_SUCCESS.equals(stateCode)) {
			// 支付成功
			dataWrap.modifyPaymentToPaySuccessOfNotice(paymentId, stateCode, noticeRemark, "OpenAPI通知结算成功",billNo);
			dataWrap.removePaymentTimeFromRedis(paymentId);
			settlementLogic.notitySettlement(paymentId, SettlementStatus.settlementsucc, null);
			return new ResultBean(ResultCode.success);
		} else if (STATE_CODE_PAY_FAILED.equals(stateCode)) {
			// 支付失败
			dataWrap.modifyPaymentToPayFailedOfNotice(paymentId, stateCode, noticeRemark, "OpenAPI通知结算失败",billNo);
			dataWrap.removePaymentTimeFromRedis(paymentId);
			settlementLogic.notitySettlement(paymentId, SettlementStatus.settlementfailed, null);
			return new ResultBean(ResultCode.success);
		} else {
			// 支付状态未明确
			// Nothing To Do
//			if (ORDER_NOT_FOUND_CODE.equals(resultCode)) {
//				dataWrap.modifyPaymentToPayFailedOfNotice(paymentId, resultCode, noticeRemark, "OpenAPI通知结算失败（订单不存在）",billNo);
//				dataWrap.removePaymentTimeFromRedis(paymentId);
//				settlementLogic.notitySettlement(paymentId, SettlementStatus.settlementfailed, null);
//				return new ResultBean(ResultCode.success);
//			}
			rollBackPaymentStep(paymentId);
			return new ResultBean(ResultCode.success);
		}
	}

	@Override
	public ResultBean noticeOfAntoRefund(Payment payment, NoticeFroadApi noticeFroadApi) {

		String resultCode = noticeFroadApi.getSystem().getResultCode();
		String paymentId = payment.getPaymentId();
		String stateCode = noticeFroadApi.getOrder().getStateCode();
		String noticeRemark = noticeFroadApi.getOrder().getRemark();
		
		//如果数据库中billNo是空的则，该支付在发起后有超时或其他情况，造成支系统受理成功但本系统认为受理失败而没有成功的填写billNo（赋值null则不更改billNo）
		String billNo = StringUtils.isEmpty(payment.getBillNo()) ? noticeFroadApi.getPay().getFroadBillNo() : null;
		
		if (!Const.SUCCESS_CODE.equals(resultCode)) {
			if (ORDER_REFUND_FAILED.equals(resultCode) || ORDER_NOT_FOUND_CODE.equals(resultCode)) { // 通知失败code执行失败逻辑
				dataWrap.modifyPaymentToPayFailedOfNotice(paymentId, resultCode, noticeRemark, "OpenAPI通知退款失败",billNo);
				dataWrap.removePaymentTimeFromRedis(paymentId);
				return new ResultBean(ResultCode.success);
			}
			// Nothing To Do
			rollBackPaymentStep(paymentId);
			return new ResultBean(ResultCode.success);
		}

		if (STATE_CODE_PAY_SUCCESS.equals(stateCode)) {
			// 退款成功
			dataWrap.modifyPaymentToPaySuccessOfNotice(paymentId, stateCode, noticeRemark, "OpenAPI通知退款成功",billNo);
			dataWrap.removePaymentTimeFromRedis(paymentId);
			return new ResultBean(ResultCode.success);

		} else if (STATE_CODE_PAY_FAILED.equals(stateCode)) {
			// 退款失败
			dataWrap.modifyPaymentToPayFailedOfNotice(paymentId, stateCode, noticeRemark, "OpenAPI通知退款失败",billNo);
			dataWrap.removePaymentTimeFromRedis(paymentId);
			return new ResultBean(ResultCode.success);
		} else {
			// 退款状态未明确
			// Nothing To Do
			rollBackPaymentStep(paymentId);
			return new ResultBean(ResultCode.success);
		}
	}

	@Override
	public ResultBean noticeOfUserRefund(Payment payment, NoticeFroadApi noticeFroadApi) {

		String resultCode = noticeFroadApi.getSystem().getResultCode();
		String paymentId = payment.getPaymentId();
		String stateCode = noticeFroadApi.getOrder().getStateCode();
		String noticeRemark = noticeFroadApi.getOrder().getRemark();
		//如果数据库中billNo是空的则，该支付在发起后有超时或其他情况，造成支系统受理成功但本系统认为受理失败而没有成功的填写billNo（赋值null则不更改billNo）
		String billNo = StringUtils.isEmpty(payment.getBillNo()) ? noticeFroadApi.getPay().getFroadBillNo() : null;
		
		
		Map<String, Boolean> noticeMap = new HashMap<String, Boolean>();
		if (!Const.SUCCESS_CODE.equals(resultCode)) {
			if (ORDER_REFUND_FAILED.equals(resultCode) || ORDER_NOT_FOUND_CODE.equals(resultCode)) { //退款失败或者退款订单不存在
				OrderMongo order = queryOrderByOrderId(payment.getClientId(), payment.getOrderId());
				//退款失败
				noticeMap.put(payment.getPaymentId(), false);
				dataWrap.modifyPaymentToPayFailedOfNotice(paymentId, resultCode, noticeRemark, "OpenAPI通知退款失败",billNo);
				dataWrap.removePaymentTimeFromRedis(paymentId);
				Payment refundPoint = dataWrap.queryToRefundPaymentOfPoint(payment.getClientId(),payment.getPaymentId());
				if (refundPoint == null) {
					Logger.logInfo("此次退款没有连带积分退款，退款已完成 paymentId: " + payment.getOrderId());
				}else{
					dataWrap.modifyPaymentToPayFailed(refundPoint.getPaymentId(), "", "", "现金退款已经失败，积分标记为退款失败");
					noticeMap.put(refundPoint.getPaymentId(), false);
				}
				
//				if(1 == order.getIsVipOrder()){
//					OrderMongo orderUpdate = new OrderMongo();
//					orderUpdate.setOrderId(order.getOrderId());
//					orderUpdate.setClientId(order.getClientId());
//					orderUpdate.setRemark("自动退款失败: " + noticeFroadApi.getOrder().getRemark());
//					updateOrderByOrderId(orderUpdate);
//				}else{
//					noticeRefundFun(noticeMap);
//				}
				
				noticeRefundFun(noticeMap);
				
				return new ResultBean(ResultCode.failed);
			}
			//Nothing To Do
			rollBackPaymentStep(paymentId);
			return new ResultBean(ResultCode.success);
		}

		if (STATE_CODE_PAY_SUCCESS.equals(stateCode)) {
			OrderMongo order = queryOrderByOrderId(payment.getClientId(), payment.getOrderId());
			noticeMap.put(payment.getPaymentId(), true);
			// 支付成功
			dataWrap.modifyPaymentToPaySuccessOfNotice(paymentId, stateCode, noticeRemark, "OpenAPI通知退款成功",billNo);
			dataWrap.removePaymentTimeFromRedis(paymentId);

			// 找出待退积分流水
			Payment refundPoint = dataWrap.queryToRefundPaymentOfPoint(payment.getClientId(), payment.getPaymentId());
			if (refundPoint == null) {
				
//				if(1 == order.getIsVipOrder()){ //VIP订单特殊处理
//					OrderMongo orderUpdate = new OrderMongo();
//					orderUpdate.setOrderId(order.getOrderId());
//					orderUpdate.setClientId(order.getClientId());
//					orderUpdate.setRemark("自动退款已完成");
//					updateOrderByOrderId(orderUpdate);
//				}else{
//					noticeRefundFun(noticeMap);
//				}
				noticeRefundFun(noticeMap);
				
				return new ResultBean(ResultCode.success);
			}
			
			//如果有待退款的积分，则继续退款待退款积分
			ResultBean resultBean = refundLogic.userRefundGoOnPointOfUserPay(refundPoint);
			if (!EsyT.isResultBeanSuccess(resultBean)) {
				noticeMap.put(refundPoint.getPaymentId(), false);
				Logger.logError("现金退款成功，退款积分失败", "orderId", payment.getOrderId());
				EsyT.sendPoint(MonitorPointEnum.order_pay_user_refund_point_failed);
				
//				if(1 == order.getIsVipOrder()){
//					OrderMongo orderUpdate = new OrderMongo();
//					orderUpdate.setOrderId(order.getOrderId());
//					orderUpdate.setClientId(order.getClientId());
//					orderUpdate.setRemark("自动退款异常: 现金退款完成，积分退款失败");
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
//				orderUpdate.setRemark("自动退款已完成");
//				updateOrderByOrderId(orderUpdate);
//			}else{
//				noticeRefundFun(noticeMap);
//			}
			noticeRefundFun(noticeMap);
			return new ResultBean(ResultCode.success);
		} else if (STATE_CODE_PAY_FAILED.equals(stateCode)) {
			OrderMongo order = queryOrderByOrderId(payment.getClientId(), payment.getOrderId());
			// 支付失败
			noticeMap.put(payment.getPaymentId(), false);
			dataWrap.modifyPaymentToPayFailedOfNotice(paymentId, resultCode, noticeRemark, "OpenAPI通知退款失败",billNo);
			dataWrap.removePaymentTimeFromRedis(paymentId);

			Payment refundPoint = dataWrap.queryToRefundPaymentOfPoint(payment.getClientId(), payment.getPaymentId());
			if (refundPoint == null) {
				Logger.logInfo("此次退款没有连带积分退款，退款已完成");
			}else{
				dataWrap.modifyPaymentToPayFailed(refundPoint.getPaymentId(), "", "", "现金退款已经失败，积分标记为退款失败");
				noticeMap.put(refundPoint.getPaymentId(), false);
			}
//			if(1 == order.getIsVipOrder()){
//				OrderMongo orderUpdate = new OrderMongo();
//				orderUpdate.setOrderId(order.getOrderId());
//				orderUpdate.setClientId(order.getClientId());
//				orderUpdate.setRemark("自动退款失败: " + noticeFroadApi.getOrder().getRemark());
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

}
