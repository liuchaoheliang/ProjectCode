package com.froad.logic.impl.payment;

import org.apache.commons.lang.StringUtils;

import com.froad.common.beans.ResultBean;
import com.froad.enums.ResultCode;
import com.froad.logic.payment.VerifyLogic;
import com.froad.po.Payment;
import com.froad.support.impl.payment.VerifyBusinessImpl;
import com.froad.support.impl.payment.DataWrapImpl;
import com.froad.support.payment.VerifyBusiness;
import com.froad.support.payment.DataWrap;
import com.froad.util.payment.Logger;

public class VerifyLogicImpl implements VerifyLogic {

	
	private static final int PAYMENT_TYPE_CASH = 2;
	
	private static final int PAYMENT_REASON_SETTLE = 0; //结算类型支付流水
	
	private static final int PAYMENT_REASON_USER_REFUND = 1; //退款类型支付流水
	
	private static final int PAYMENT_REASON_USER_PAY = 2; //用户支付类型支付流水
	
//	private static final int PAYMENT_REASON_PRESENT_FROADPOINT = 3;//赠送方付通积分流水
//	
//	private static final int PAYMENT_REASON_DEDUCT_PRESENT_FROADPOINT = 4;//扣除赠送方付通积分流水

	private DataWrap dataWrap = new DataWrapImpl();
	private VerifyBusiness verifyBusiness = new VerifyBusinessImpl();
	
	
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
		if(!flag){
			dataWrap.removePaymentTimeFromRedis(paymentId);
		}
		return flag;
	}
	
	@Override
	public ResultBean verifyPaymentResult(String paymentId) {
		
		if(StringUtils.isEmpty(paymentId)){
			return new ResultBean(ResultCode.failed);
		}
		
		Payment payment = dataWrap.queryByPaymentId(paymentId);
		
		if(payment == null){
			Logger.logError("验证支付流水不存在", "paymentId", paymentId);
			dataWrap.removePaymentTimeFromRedis(paymentId);
			return new ResultBean(ResultCode.failed,"验证支付流水不存在");
		}
		
		boolean flag = lockAndModifyPaymentToSetpFour(paymentId);
		if(!flag){
			Logger.logError("流水当前状态不能通过该接口进行支付结果确认,并移除该无法锁定的非step: 2/3 的流水", "payment", payment);
			return new ResultBean(ResultCode.failed,"该流水当前状态不需要再验证");
		}
		
		return issueVerifyDisposeFun(payment);
	}

	/**
	 * 分发验证类型验证结果处理接口
	* <p>Function: issueVerifyDisposeFun</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-6-5 下午2:53:20
	* @version 1.0
	* @param payment
	* @return
	 */
	private ResultBean issueVerifyDisposeFun(Payment payment){
		if(PAYMENT_TYPE_CASH == payment.getPaymentType()){
			//支付流水类型是现金
			switch (Integer.valueOf(payment.getPaymentReason())) {
			
				case PAYMENT_REASON_SETTLE:
				return verifyBusiness.typeOfCashForSettle(payment);
				case PAYMENT_REASON_USER_REFUND:
				return verifyBusiness.typeOfCashForUserRefund(payment);
				
				case PAYMENT_REASON_USER_PAY:
				return verifyBusiness.typeOfCashForUserPay(payment);
				
				default:
				Logger.logError("校验支付流水无效","paymentReason",payment.getPaymentReason());
				dataWrap.removePaymentTimeFromRedis(payment.getPaymentId());
				return new ResultBean(ResultCode.failed);
			}
		}else{
			//支付流水类型是积分
			switch (Integer.valueOf(payment.getPaymentReason())) {
				case PAYMENT_REASON_USER_REFUND:
				return verifyBusiness.typeOfPointForUserRefund(payment);
				
				case PAYMENT_REASON_USER_PAY:
				return verifyBusiness.typeOfPointForUserPay(payment);
				
				default:
				Logger.logError("校验支付流水无效","paymentReason",payment.getPaymentReason());
				dataWrap.removePaymentTimeFromRedis(payment.getPaymentId());
				return new ResultBean(ResultCode.failed);
			}
		}
	}
	
}
