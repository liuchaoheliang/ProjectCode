package com.froad.logic.impl.payment;


import java.util.Date;

import com.froad.common.beans.PointInfo;
import com.froad.common.beans.ResultBean;
import com.froad.db.redis.RedisCommon;
import com.froad.enums.MonitorPointEnum;
import com.froad.enums.OrderStatus;
import com.froad.enums.ResultCode;
import com.froad.enums.SmsType;
import com.froad.exceptions.PaymentException;
import com.froad.logic.SmsLogic;
import com.froad.logic.impl.SmsLogicImpl;
import com.froad.logic.payment.BusinessExpandLogic;
import com.froad.logic.payment.RefundLogic;
import com.froad.po.Payment;
import com.froad.po.mongo.OrderMongo;
import com.froad.support.OrderSupport;
import com.froad.support.impl.OrderSupportImpl;
import com.froad.support.impl.payment.AWIPSThirdpartyImpl;
import com.froad.support.impl.payment.DataWrapImpl;
import com.froad.support.payment.AWIPSThirdparty;
import com.froad.support.payment.DataWrap;
import com.froad.thirdparty.dto.request.points.PointsReq;
import com.froad.thirdparty.dto.response.points.PointsRes;
import com.froad.util.Arith;
import com.froad.util.payment.BaseSubassembly;
import com.froad.util.payment.Const;
import com.froad.util.payment.EsyT;
import com.froad.util.payment.Logger;
import com.froad.util.payment.ThirdpartyRequestBuilder;
import com.froad.util.refund.ProxyRefundType;
import com.pay.user.dto.Result;
import com.pay.user.dto.UserSpecDto;
import com.pay.user.dto.VIPSpecDto;
import com.pay.user.helper.VIPLevel;

public class BusinessExpandLogicImpl implements BusinessExpandLogic {

	private DataWrap dataWrap = new DataWrapImpl();
	private AWIPSThirdparty awipsThirdparty = new AWIPSThirdpartyImpl();
	private OrderSupport orderSupport = new OrderSupportImpl();
	private SmsLogic smsLogic = new SmsLogicImpl();
	private RefundLogic refundLogic = new RefundLogicImpl();
	
	@Override
	public ResultBean presentFroadPoint(OrderMongo order) {
		
		UserSpecDto userSpecDto = awipsThirdparty.queryUserByMemberCode(order.getMemberCode());
		PointInfo pointInfo = awipsThirdparty.queryUserPoints(userSpecDto.getLoginID(), order.getClientId());
		
		//--生成赠送积分流水
		Payment paymentPresent = new Payment();
		String paymentId = EsyT.simpleID();
		paymentPresent.setOrderId(order.getOrderId());
		paymentPresent.setClientId(order.getClientId());
		paymentPresent.setPaymentId(paymentId);
		paymentPresent.setPaymentTypeDetails(0); //代表是积分
		paymentPresent.setPaymentReason("3"); //赠送积分
		paymentPresent.setPaymentOrgNo(Const.FROAD_POINT_ORG_NO);
		paymentPresent.setPaymentType(1);
		paymentPresent.setPointRate(BaseSubassembly.acquireFroadPointPointRate(order.getClientId()));
		paymentPresent.setPaymentValue(order.getGivePoints());
		paymentPresent.setFromUserName(userSpecDto.getLoginID());
		paymentPresent.setFromUserMemberCode(userSpecDto.getMemberCode());
		paymentPresent.setFromAccountNo(pointInfo.getFroadAccountId());
		boolean flag = dataWrap.initializePayment(paymentPresent);
		
		if(!flag){
			EsyT.sendPoint(MonitorPointEnum.order_pay_auto_refund_point_failed);
			Logger.logError("保存赠送积分流水失败", "paymentPresent", paymentPresent);
			return new ResultBean(ResultCode.failed);
		}
		
		PointsReq req = ThirdpartyRequestBuilder.builderFroadPointPresentReq(paymentPresent);
		PointsRes res;
		try {
			res = awipsThirdparty.pointPresent(req);
		} catch (Exception e) {
			EsyT.sendPoint(MonitorPointEnum.order_pay_auto_refund_point_failed);
			dataWrap.modifyPaymentToRequestException(paymentId, "请求支付系统Point异常: " + e.getMessage());
			Logger.logError("发送自动赠送积分异常", e);
			return new ResultBean(ResultCode.failed,"发送赠送积分请求异常");
		}
		
		dataWrap.modifyPaymentToRequestSuccess(paymentId);
		
		if(Const.SUCCESS_CODE.equals(res.getResultCode())){
			dataWrap.modifyPaymentToPaySuccess(paymentId, res.getResultCode(), res.getResultDesc(), res.getPresentPointsNo(),"赠送积分成功");
			orderSupport.updateGivePointStatus(order.getClientId(), order.getOrderId(), true);//赠送积分成功
			return new ResultBean(ResultCode.success);
		}else{
			EsyT.sendPoint(MonitorPointEnum.order_pay_auto_refund_point_failed);
			dataWrap.modifyPaymentToPayFailed(paymentId, res.getResultCode(),res.getResultDesc(),"积分赠送失败");
			orderSupport.updateGivePointStatus(order.getClientId(), order.getOrderId(), false);//赠送积分失败
			return new ResultBean(ResultCode.failed,res.getResultDesc());
		}
		
	}

	@Override
	public ResultBean deductFroadPoint(Payment payment) {
		PointInfo pointInfo = awipsThirdparty.queryUserPoints(payment.getFromUserName(), payment.getClientId());
		double deductFroadPoint = Arith.div(payment.getPaymentTypeDetails(),Const.HDOP_1000);
		if(deductFroadPoint > pointInfo.getFroadPoint()){
			return new ResultBean(ResultCode.failed,"当前用户剩余积分不足以扣除历史已赠送的积分");
		}
		
		PointsReq req = ThirdpartyRequestBuilder.builderPointPayReq(payment);
		String paymentId = payment.getPaymentId();
		PointsRes res;
		
		try {
			res = awipsThirdparty.pointConsume(req);
		} catch (PaymentException e) {
			dataWrap.modifyPaymentToRequestException(paymentId,"请求支付系统Point异常: " + e.getMessage());
			Logger.logError("发送扣除已赠送积分请求失败",e);
			return new ResultBean(ResultCode.failed, "发送扣除已赠送积分请求失败");
		}

		dataWrap.modifyPaymentToRequestSuccess(paymentId);
		
		if(Const.SUCCESS_CODE.equals(res.getResultCode())){
			dataWrap.modifyPaymentToPaySuccess(paymentId,res.getResultCode(),res.getResultDesc(),res.getPayPointsNo(),"扣除赠送积分成功");
			return new ResultBean(ResultCode.success);
		}else{
			dataWrap.modifyPaymentToPayFailed(paymentId, res.getResultCode(),res.getResultDesc(),"扣除用户赠送积分失败");
			return new ResultBean(ResultCode.failed, res.getResultDesc());
		}
	}

	@Override
	public ResultBean attachUserVIPStatus(OrderMongo order) {
		
		long memberCode = order.getMemberCode();
		VIPLevel vipLevel = VIPLevel.VIP_LEVEL_1;
		Date vipExpirationTime = null;
		
		VIPSpecDto vipSpecDto = awipsThirdparty.queryVIPInfoByMemberCode(order.getMemberCode(), order.getClientId());
		if(vipSpecDto == null){//会员当前并不是VIP，则是首次开通VIP
			vipExpirationTime = EsyT.nowToNaturalYearDaysOffset();
		}else{
			vipExpirationTime = EsyT.targetToNaturalYearDaysOffset(vipSpecDto.getVipExpirationTime());
		}
		String bankLabelID = order.getBankLabelID();
		String bankOrg = BaseSubassembly.acquireBankOrg(order.getClientId()); //考虑从缓存中获取
		String clientChannel = order.getClientChannel();
		
		Logger.logInfo("开通会员VIP",new String[]{"memberCode","availableDays","bankLabelID","bankOrg","clientChanelName"},new Object[]{memberCode,vipExpirationTime,bankLabelID,bankOrg,clientChannel});
	
		Result result = awipsThirdparty.processVIPOrder(order.getMemberCode(),vipLevel, vipExpirationTime, bankLabelID, bankOrg, clientChannel);
		RedisCommon.updateUserVIPOrderRedis(order.getClientId(),order.getMemberCode(),false);

		if(!result.isResult()){
			Logger.logError("开通会员失败",new String[]{"memberCode","result"},new Object[]{memberCode,result});
			ResultBean resultBean = refundLogic.proxyUserToAutoRefundAllUserPay(order, false,ProxyRefundType.VIP_ORDER_AUTH_FAILED,"VIP开通失败，系统自动退款");
			Logger.logInfo("自动退还用户支付流水","resultBean",resultBean);
			OrderMongo orderUpdate = new OrderMongo();
			orderUpdate.setOrderId(order.getOrderId());
			orderUpdate.setClientId(order.getClientId());
			orderUpdate.setOrderStatus(OrderStatus.sysclosed.getCode());
			orderUpdate.setRemark("开通VIP失败，已发起自动退款");
			orderSupport.updateOrderByCondion(orderUpdate);
			return new ResultBean(ResultCode.failed,result.getMessage());
		}
		
		RedisCommon.updateUserVIPOrderSuccessRedis(order.getClientId(),order.getMemberCode(),vipExpirationTime);
		UserSpecDto userSpecDto = awipsThirdparty.queryUserByMemberCode(memberCode);
		if(userSpecDto == null){
			Logger.logError("VIP开通成功，准备发送短信但未能获取到用户信息");
			return new ResultBean(ResultCode.success);
		}
		boolean flag = smsLogic.sendSMS(awipsThirdparty.queryUserByMemberCode(memberCode).getMobile(), SmsType.openVipReminder.getCode(), order.getClientId(), null);
		
		Logger.logInfo("开通VIP成功并发送短信 flag : " + flag);
		
		return new ResultBean(ResultCode.success);
	}
}
