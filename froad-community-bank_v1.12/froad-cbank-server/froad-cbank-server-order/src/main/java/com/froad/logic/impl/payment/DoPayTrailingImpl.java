package com.froad.logic.impl.payment;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.froad.common.beans.ResultBean;
import com.froad.db.redis.RedisCommon;
import com.froad.enums.OrderState;
import com.froad.enums.OrderStatus;
import com.froad.enums.ResultCode;
import com.froad.handler.OrderLogHandler;
import com.froad.handler.impl.OrderLogHandlerImpl;
import com.froad.logic.OrderLogic;
import com.froad.logic.SettlementLogic;
import com.froad.logic.TicketLogic;
import com.froad.logic.impl.LogBeanClone;
import com.froad.logic.impl.OrderLogicImpl;
import com.froad.logic.impl.SettlementLogicImpl;
import com.froad.logic.impl.TicketLogicImpl;
import com.froad.logic.payment.AssistLogic;
import com.froad.logic.payment.BusinessExpandLogic;
import com.froad.logic.payment.DoPayTrailing;
import com.froad.po.Payment;
import com.froad.po.mongo.OrderMongo;
import com.froad.support.PaymentSupport;
import com.froad.support.impl.PaymentSupportImpl;
import com.froad.support.impl.payment.DataWrapImpl;
import com.froad.support.payment.DataWrap;
import com.froad.thirdparty.request.active.ActiveFunc;
import com.froad.thirdparty.request.active.impl.ActiveFuncImpl;
import com.froad.util.EmptyChecker;
import com.froad.util.active.ActiveConst;
import com.froad.util.payment.BaseSubassembly;
import com.froad.util.payment.Const;
import com.froad.util.payment.EsyT;
import com.froad.util.payment.Logger;

public class DoPayTrailingImpl implements DoPayTrailing {

	
	
	private DataWrap dataWrap = new DataWrapImpl();
	private OrderLogic orderLogic = new OrderLogicImpl();
	private PaymentSupport paymentSupport = new PaymentSupportImpl();
	private TicketLogic ticketLogic = new TicketLogicImpl();
	private SettlementLogic settlementLogic = new SettlementLogicImpl();
	private AssistLogic assistLogic = new AssistLogicImpl();
	private BusinessExpandLogic businessExpandLogic = new BusinessExpandLogicImpl();
	private ActiveFunc activeFunc = new ActiveFuncImpl(); 
	private OrderLogHandler orderLogHandler = new OrderLogHandlerImpl();
	
	@Override
	public ResultBean doPaySuccess(OrderMongo order) {
		
		//移除缓存的订单
		dataWrap.removeTimeOrderFromRedis(order.getClientId(), order.getOrderId());
		
		String orderId = order.getOrderId();
		
		OrderMongo orderUpdate = new OrderMongo();
		orderUpdate.setOrderId(orderId);
		orderUpdate.setClientId(order.getClientId());
		orderUpdate.setOrderStatus(OrderStatus.paysuccess.getCode());
		//清除历史remark
		String historyRemark = order.getRemark();
		if (StringUtils.isNotEmpty(historyRemark)) {
			Logger.logInfo("清除历史的订单remark信息", "historyRemark", historyRemark);
			orderUpdate.setRemark("");
		}
		
		ResultBean resultBean = orderLogic.updateOrderForPay(orderUpdate);
		if(!EsyT.isResultBeanSuccess(resultBean)){
			Logger.logError("支付完成，未能将订单状态改为支付成功");
		}
		
		//保持内存对象数据同步
		order.setOrderStatus(OrderStatus.paysuccess.getCode());
		
		//--Bussiness 业务拓展代码
		if(1 == order.getIsVipOrder()){
			//VIP订单直接为用户开通VIP资格然后结束不需要执行其他附属逻辑
			return businessExpandLogic.attachUserVIPStatus(order);
		}
		
		//是否需要赠送积分
		if(order.getGivePoints() != null && order.getGivePoints() > 0){
			resultBean = businessExpandLogic.presentFroadPoint(order);
			Logger.logInfo("自动赠送积分", "resultBean", resultBean);
		}
		
		//--Bussiness 业务拓展代码 -- 支付完毕其他模块代码调用
		try { //容错其他模块处理
			resultBean = ticketLogic.addTicket(order);
			resultBean = settlementLogic.paySettlement(order);
			if(order.getIsSeckill() == 1){
				resultBean = orderLogic.updateSeckillProductSellCount(order); //增加秒杀商品销售数量
			}
			resultBean = orderLogic.updateProductSellCount(order); //增加商品销售数量
			resultBean = paymentSupport.updateMerchantMonthCountForPaySuccess(order); //商户月销售统计数量累计
		} catch (Exception e) {
			Logger.logError("支付完毕处理后续逻辑异常 orderId: " + order.getOrderId(), e);
		}
		
		
		//如果订单参加了满减活动，支付成功,通知营销平台
		if(ActiveConst.IS_ACTIVE_TRUE.equals(order.getIsActive())){
			activeFunc.noticeMarketingPlatformPaySuccess(order,true);
		}
		//保存日志 2015-11-10新增
		savePaymentLog(order,true);
		
		return new ResultBean(ResultCode.success);
		
	}

	@Override
	public void doPayFailed(OrderMongo order) {
		
		ResultBean resultBean = null;
		OrderMongo orderUpdate = new OrderMongo();
		
		if(order.getIsSeckill() == 1){ //秒杀类型订单
			orderUpdate.setIsSeckill(1);
			resultBean = assistLogic.returnStoreOfSeckilling(order);
		}else{ //普通类型的订单
			if(1 != order.getIsQrcode() && 1 != order.getIsVipOrder()){ //如果不是面对面和VIP资格订单订单则需要进行库存退还
				resultBean = assistLogic.checkAndOperateStore(order,true);
			}
		}
		
		//如果是包含现金支付的订单，则存在异步过程，所以需要将异步的失败信息记录在订单上，方便页面轮询
		if(!BaseSubassembly.isPurePointsPayment(order.getPaymentMethod())){
			Payment paiedCash = dataWrap.queryEnbalePaymentOfUserPayFailedTypeCash(order.getOrderId());
			if(paiedCash != null){
				orderUpdate.setRemark(paiedCash.getRemark());
			}
		}
		
		orderUpdate.setOrderId(order.getOrderId());
		orderUpdate.setClientId(order.getClientId());
		orderUpdate.setBankPoints(0);
		orderUpdate.setPaymentMethod("");
		orderUpdate.setFftPoints(0);
		orderUpdate.setPointRate("0");
		orderUpdate.setRealPrice(0);
		
		if(1 != order.getIsQrcode() && 1 != order.getIsVipOrder()){ //非面对面订单|非VIP订单
			if(EsyT.isResultBeanSuccess(resultBean)){
				Logger.logInfo("退还库存成功,标记订单OrderState库存已退");
				orderUpdate.setState(OrderState.RETURNED.getCode()); //库存已退
			}else{
				Logger.logError("退还库存失败","orderId",order.getOrderId());
			}
		}
		
		orderUpdate.setOrderStatus(OrderStatus.payfailed.getCode());//改订单为支付失败
		

		//---------------------------新增订单关闭逻辑
		/**
		 * 面对面订单支付失败关闭，普通订单纯积分支付失败关闭      （惠付订单支付失败不直接关闭）
		 */
		if(1 == order.getIsQrcode() && (EmptyChecker.isEmpty(order.getIsPrefPay()) || 0 == order.getIsPrefPay())){//面对面订单
			orderUpdate.setIsQrcode(1);
			orderUpdate.setQrcode(order.getQrcode());
			orderUpdate.setOrderStatus(OrderStatus.sysclosed.getCode());
		}else{
			if(BaseSubassembly.isPurePointsPayment(order.getPaymentMethod())){
				orderUpdate.setOrderStatus(OrderStatus.sysclosed.getCode());
				//普通类型订单
				if(1 == order.getIsVipOrder()){
					RedisCommon.updateUserVIPOrderRedis(order.getClientId(),order.getMemberCode(),false);
				}
			}
		}
		
		//---------------------------新增订单关闭逻辑
		
		orderLogic.updateOrderForPay(orderUpdate);
		
		//删除积分拆分规则所产生的数据
		orderLogic.deleteUnitProductCutPoint(order.getClientId(), order.getOrderId());
		
		//如果订单参加了营销活动，面对面订单支付失败,且使用了红包，通知营销平台
		if(ActiveConst.IS_ACTIVE_TRUE.equals(order.getIsActive()) && 1 == order.getIsQrcode()
				&& StringUtils.isNotBlank(order.getRedPacketId())){
			activeFunc.noticeMarketingPlatformPaySuccess(order,false);
		}
				
		//保存日志 2015-11-10新增
		savePaymentLog(order,false);
	}

	/**
	 * 保存日志
	 * Function : savePaymentLog<br/>
	 * 2015年11月10日 上午10:10:30 <br/>
	 *  
	 * @author KaiweiXiang
	 * @param orderId
	 */
	private void savePaymentLog(OrderMongo order,boolean isSuccess){
		String orderId = order.getOrderId();
		try {
			List<Payment> plist = dataWrap.queryEnableOfUserPayByOrderId(orderId);
			if(plist == null || plist.size() == 0){
				return;
			}
			
			String pointOrgNo = null;
			int cashType = 0;
//			int paymentPointValue = 0;
//			int paymentCashValue = 0;
			//4-支付成功（获得支付结果-成功）/ 5-支付失败（获得支付结果-失败）
			String paymentStatus = "4"; //所有支付流水成功才算成功
			
			for(Payment payment : plist){
				int paymentType = payment.getPaymentType();
				if(paymentType == 2){//现金
					cashType = payment.getPaymentTypeDetails();
//					paymentCashValue = payment.getPaymentValue();
				}else if(paymentType == 1){//方付通积分
					pointOrgNo = Const.FROAD_POINT_ORG_NO;
//					paymentPointValue = payment.getPaymentValue();
				}else if(paymentType == 3){//银行积分
					pointOrgNo = String.valueOf(payment.getPaymentOrgNo());
//					paymentPointValue = payment.getPaymentValue();
				}
				if(!"4".equals(payment.getPaymentStatus())){
					paymentStatus = payment.getPaymentStatus();
				}
			}
			
			//输出日志
			orderLogHandler.paymentLogCreate(plist, order, LogBeanClone.getPaymentType(pointOrgNo, cashType),
					(isSuccess ? 2: 3),paymentStatus);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
