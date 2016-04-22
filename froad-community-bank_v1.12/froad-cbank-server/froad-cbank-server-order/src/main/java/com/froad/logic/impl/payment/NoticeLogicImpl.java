package com.froad.logic.impl.payment;

import org.apache.commons.lang.StringUtils;

import com.froad.common.beans.ResultBean;
import com.froad.enums.OrderStatus;
import com.froad.enums.ResultCode;
import com.froad.logic.OrderLogic;
import com.froad.logic.impl.OrderLogicImpl;
import com.froad.logic.payment.NoticeLogic;
import com.froad.po.Payment;
import com.froad.po.mongo.OrderMongo;
import com.froad.support.impl.payment.CashTradeCompleteImpl;
import com.froad.support.impl.payment.DataWrapImpl;
import com.froad.support.payment.CashTradeComplete;
import com.froad.support.payment.DataWrap;
import com.froad.thirdparty.dto.response.openapi.NoticeFroadApi;
import com.froad.util.payment.EsyT;
import com.froad.util.payment.Logger;
import com.froad.util.payment.ThirdpartyResponseBuilder;

public class NoticeLogicImpl implements NoticeLogic {

	/**
	 * 流水类型为用户支付
	 */
	private static final String PAYMENT_REASON_USER_PAY = "2";
	
	/**
	 * 流水类型为结算
	 */
	private static final String PYAMENT_REASON_SETTLE = "0";
	
	/**
	 * 流水类型为自动退款 
	 */
	private static final String PAYMENT_REASON_AUTO_REFUND = "5";
	
	/**
	 * 流水类型为用户支付退款
	 */
	private static final String PYAMENT_REASON_USER_REFUND = "1";
	
	
	private DataWrap dataWrap = new DataWrapImpl();
	private OrderLogic orderLogic = new OrderLogicImpl();
	private CashTradeComplete cashTradeComplete = new CashTradeCompleteImpl();
	
	
	/**
	 * 锁定修改流水为处理已完成状态
	* <p>Function: lockAndModifyPaymentToSetpFour</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-26 下午2:00:38
	* @version 1.0
	* @param paymentId
	* @return
	 */
	private boolean lockAndModifyPaymentToSetpFour(String paymentId){
		boolean flag = dataWrap.lockAndModifyPaymentToStepFour(paymentId);
		if(!flag){ //如果锁定失败则流水无效
			dataWrap.removePaymentTimeFromRedis(paymentId);
		}
		return flag;
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
	public ResultBean consumeNotic(String noticeXML) {
		
		if(StringUtils.isEmpty(noticeXML)){
			return new ResultBean(ResultCode.failed,"收到空的noticeXML");
		}
		
		Logger.logInfo("收到通知","noticeXML",noticeXML);
		
		NoticeFroadApi noticeFroadApi = ThirdpartyResponseBuilder.combineXMLToEntity(noticeXML);
		if(noticeFroadApi == null){
			Logger.logError("noticeXML 转 NoticeFroadApi 失败", "noticeXML", noticeXML);
			return new ResultBean(ResultCode.failed,"收到无效的noticeXML");
		}
		
		//成功|失败
		//    结算通知|支付通知
		//    结算通知
		//			.............
		//	    成功---->已关单|未关单
		//       已关单--发起自动退还现金 
		//       未关单--修改支付流水，调用支付完毕后续逻辑
		//    失败---->已关单|未关单
		//       已关单--不做处理
		//       未关单--修改支付流水，调用失败逻辑
		
		String paymentId = noticeFroadApi.getOrder().getOrderID();
		Payment payment = dataWrap.queryByPaymentId(paymentId);
		if(payment == null){
			return new ResultBean(ResultCode.failed,"支付流水不存在");
		}
		boolean flag = lockAndModifyPaymentToSetpFour(paymentId);
		
		if(!flag){
			Logger.logError("锁定修改流水失败,并移除该无法锁定的非step: 2/3 的流水", "payment", payment);
			return new ResultBean(ResultCode.failed);
		}
		

		boolean isOrderClosed = false;
		String orderId = payment.getOrderId();
		ResultBean resultBean = orderLogic.getOrderByOrderId(payment.getClientId(),orderId);
		
		if (!EsyT.isResultBeanSuccess(resultBean)) {
			rollBackPaymentStep(paymentId);
			Logger.logError("查询订单失败", "orderId", payment.getOrderId());
			return new ResultBean(ResultCode.failed, "订单查询失败");
		}

		OrderMongo order = (OrderMongo) resultBean.getData();
		if (OrderStatus.closed.getCode().equals(order.getOrderStatus()) || OrderStatus.sysclosed.getCode().equals(order.getOrderStatus())) {
			isOrderClosed = true;
		}

		return issueConsumeDisposeFun(payment, noticeFroadApi, isOrderClosed, order);
	}

	/**
	 * 分发消费通知处理函数
	* <p>Function: issueConsumeDisposeFun</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-26 下午2:12:31
	* @version 1.0
	* @param payment
	* @param noticeFroadApi
	* @return
	 */
	private ResultBean issueConsumeDisposeFun(Payment payment,NoticeFroadApi noticeFroadApi,boolean isOrderClosed,OrderMongo order){
		if(PAYMENT_REASON_USER_PAY.equals(payment.getPaymentReason())){ //用户支付流水
			return cashTradeComplete.noticeOfUserPay(payment, noticeFroadApi, isOrderClosed, order);
		}else if(PYAMENT_REASON_SETTLE.equals(payment.getPaymentReason())){//结算流水
			return cashTradeComplete.noticeOfSettlePay(payment, noticeFroadApi, order);
		}else{
			Logger.logError("处理通知流水无效，支付流水类型无效","paymentReason",payment.getPaymentReason());
		}
		return new ResultBean(ResultCode.failed);
	}

	@Override
	public ResultBean refundNotic(String noticeXML) {
		
		if(StringUtils.isEmpty(noticeXML)){
			return new ResultBean(ResultCode.failed,"收到空的noticeXML");
		}
		
		Logger.logInfo("收到通知","noticeXML",noticeXML);
		
		NoticeFroadApi noticeFroadApi = ThirdpartyResponseBuilder.refundXMLToEntity(noticeXML);
		if(noticeFroadApi == null){
			Logger.logError("noticeXML 转 NoticeFroadApi 失败", "noticeXML", noticeXML);
			return new ResultBean(ResultCode.failed,"收到无效的noticeXML");
		}
		
		String paymentId = noticeFroadApi.getOrder().getRefundOrderID();
		Payment payment = dataWrap.queryByPaymentId(paymentId);
		if(payment == null){
			return new ResultBean(ResultCode.failed,"支付流水不存在");
		}
		
		boolean flag = lockAndModifyPaymentToSetpFour(paymentId);
		
		if(!flag){
			Logger.logError("锁定修改流水失败,并移除该无法锁定的非step: 2/3 的流水", "payment", payment);
			return new ResultBean(ResultCode.failed);
		}
		
		boolean isAutoRefund = PAYMENT_REASON_AUTO_REFUND.equals(payment.getPaymentReason()) ? true : false;		
		
		return issueRefundDisposeFun(payment, noticeFroadApi, isAutoRefund);
	}

	/**
	 * 分发退款处理函数
	* <p>Function: issueRefundDisposeFun</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-28 上午11:16:39
	* @version 1.0
	* @param payment
	* @param noticeFroadApi
	* @param isAutoRefund
	* @return
	 */
	private ResultBean issueRefundDisposeFun(Payment payment,NoticeFroadApi noticeFroadApi,boolean isAutoRefund){
		if(isAutoRefund){
			//系统自动退款
			return cashTradeComplete.noticeOfAntoRefund(payment, noticeFroadApi);
		}else if(PYAMENT_REASON_USER_REFUND.equals(payment.getPaymentReason())){
			//用户主动退款
			return cashTradeComplete.noticeOfUserRefund(payment, noticeFroadApi);
		}else{
			Logger.logError("处理通知流水无效，支付流水类型无效","paymentReason",payment.getPaymentReason());
		}
		return new ResultBean(ResultCode.failed);
	}
}
