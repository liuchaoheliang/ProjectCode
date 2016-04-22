package com.froad.support.impl.payment;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.froad.enums.MonitorPointEnum;
import com.froad.enums.PaymentReason;
import com.froad.log.OrderLogs;
import com.froad.logic.impl.LogBeanClone;
import com.froad.po.Payment;
import com.froad.po.refund.RefundPaymentInfo;
import com.froad.support.RefundSupport;
import com.froad.support.impl.RefundSupportImpl;
import com.froad.support.payment.DataPersistent;
import com.froad.support.payment.DataWrap;
import com.froad.util.payment.Const;
import com.froad.util.payment.EsyT;
import com.froad.util.payment.Logger;

public class DataWrapImpl implements DataWrap {

	private DataPersistent dataPersistent = new DataPersistentImpl();
	private RefundSupport refundSupport = new RefundSupportImpl();
	
	@Override
	public boolean modifyPaymentToPaying(String paymentId) {
		Payment payment = new Payment();
		payment.setPaymentId(paymentId);
		payment.setStep(2); //支付中
		return dataPersistent.updateByPaymentIdFromMongoDB(payment);
	}

	@Override
	public boolean initializePayment(Payment payment) {
		if(payment == null){
			return false;
		}
		payment.setCreateTime(new Date());
		payment.setPaymentId(StringUtils.isEmpty(payment.getPaymentId()) ? EsyT.simpleID() : payment.getPaymentId());
		payment.setStep(1);
		payment.setIsEnable(true);
		payment.setIsDisposeException("0");
		payment.setPaymentStatus(StringUtils.isEmpty(payment.getPaymentStatus()) ? "1" : payment.getPaymentStatus());
		boolean flag = dataPersistent.savePaymentToMongoDB(payment);
		if(!flag){
			EsyT.sendPoint(MonitorPointEnum.order_pay_save_payment_failed);
		}
		
		return flag;
	}

	@Override
	public boolean modifyPaymentToRequestSuccess(String paymentId) {
		boolean flag = false;
		Payment payment = new Payment();
		payment.setPaymentId(paymentId);
		payment.setStep(3); //请求发送完成
		payment.setPaymentStatus("2"); //请求发送成功
		flag = dataPersistent.updateByPaymentIdFromMongoDB(payment);
		Logger.logInfo("修改支付流水为请求发送成功",new String[]{"paymentId","flag"},new Object[]{paymentId,flag});
		return flag;
	}


	@Override
	public boolean modifyPaymentToPaySuccess(String paymentId,String resultCode,String resultDesc,String pointPayNo,String remark) {
		boolean flag = false;
		Payment payment = new Payment();
		payment.setPaymentId(paymentId);
		payment.setStep(4); //明确支付结果
		payment.setPaymentStatus("4"); //支付成功
		payment.setResultCode(resultCode);
		payment.setResultDesc(resultDesc);
		payment.setBillNo(pointPayNo);
		payment.setRemark(remark);
		flag = dataPersistent.updateByPaymentIdFromMongoDB(payment);
		Logger.logInfo("修改支付流水为支付成功",new String[]{"paymentId","flag"},new Object[]{paymentId,flag});
		return flag;
	}

	@Override
	public boolean modifyPaymentToPayFailed(String paymentId,String resultCode,String resultDesc) {
		boolean flag = false;
		Payment payment = new Payment();
		payment.setPaymentId(paymentId);
		payment.setStep(4); //明确支付结果
		payment.setPaymentStatus("5"); //支付失败
		payment.setResultCode(resultCode);
		payment.setResultDesc(resultDesc);
		flag = dataPersistent.updateByPaymentIdFromMongoDB(payment);
		Logger.logInfo("修改支付流水为支付失败",new String[]{"paymentId","flag"},new Object[]{paymentId,flag});
		
		return flag;
	}

	@Override
	public boolean addPaymentTimeFromRedis(String paymentId) {
		return dataPersistent.addPaymentTimeFromRedis(paymentId);
	}

	@Override
	public boolean removePaymentTimeFromRedis(String paymentId) {
		return dataPersistent.removePaymentTimeFromRedis(paymentId);
	}

	@Override
	public List<Payment> queryEnableOfUserPayByOrderId(String orderId) {
		return dataPersistent.findEnableOfUserPayByOrderIdFromMongoDB(orderId);
	}

	@Override
	public boolean modifyDisablePayment(String paymentId) {
		boolean flag = false;
		Payment payment = new Payment();
		payment.setPaymentId(paymentId);
		payment.setIsEnable(false);
		flag = dataPersistent.updateByPaymentIdFromMongoDB(payment);
		Logger.logInfo("作废支付流水",new String[]{"paymentId","flag"},new Object[]{paymentId,flag});
		return flag;
	}

	@Override
	public boolean removeTimeOrderFromRedis(String clinetId, String orderId) {
		boolean flag = false;
		flag = dataPersistent.removeTimeOrderFromRedis(clinetId, orderId);
		Logger.logInfo("移除订单缓存",new String[]{"clinetId","orderId","flag"},new Object[]{clinetId,orderId,flag});
		return flag;
	}

	@Override
	public boolean modifyPaymentToRequestException(String paymentId, String remark) {
		boolean flag = false;
		Payment payment = new Payment();
		payment.setPaymentId(paymentId);
		payment.setStep(3); //请求发送完成
		payment.setPaymentStatus("3"); //请求发送失败
		payment.setRemark(remark);
		flag = dataPersistent.updateByPaymentIdFromMongoDB(payment);
		Logger.logInfo("修改支付流水为请求发送异常",new String[]{"paymentId","flag"},new Object[]{paymentId,flag});
		return flag;
	}
	
	@Override
	public boolean modifyPaymentToPayAccessSuccess(String paymentId,String billNo,String remark) {
		boolean flag = false;
		Payment payment = new Payment();
		payment.setPaymentId(paymentId);
		payment.setResultCode(Const.SUCCESS_CODE);
		payment.setResultDesc("支付请求受理成功");
		payment.setBillNo(billNo);
		payment.setRemark(remark);
		flag = dataPersistent.updateByPaymentIdFromMongoDB(payment);
		Logger.logInfo("修改支付流水为受理成功",new String[]{"paymentId","flag"},new Object[]{paymentId,flag});
		return flag;
	}

	@Override
	public Payment queryByPaymentId(String paymentId) {
		return dataPersistent.findByPaymentIdFromMongoDB(paymentId);
	}

	@Override
	public Payment lockAndModify(Payment paymentOriginal, Payment paymentTarget) {
		return dataPersistent.findAndModifyPaymentFromMongoDB(paymentOriginal, paymentTarget);
	}

	@Override
	public boolean modifyPaymentById(Payment payment) {
		return dataPersistent.updateByPaymentIdFromMongoDB(payment);
	}

	@Override
	public Payment queryPaymentOfUserPaiedTypePoint(String orderId) {
		Payment payment = new Payment();
		payment.setOrderId(orderId);
		payment.setIsEnable(true);
		payment.setPaymentTypeDetails(0); //可以确定出积分的不论哪种积分
		payment.setStep(4);
		payment.setPaymentStatus("4");
		payment.setPaymentReason("2");
		//该条件一定能确定只有一条
		List<Payment> list = dataPersistent.findByPaymentConditionFromMongoDB(payment);
		if(list == null || list.size() == 0){
			return null;
		}
		return list.get(0);
	}

	@Override
	public boolean modifyPaymentToPayAccessSuccess(String paymentId,String resultCode, String resultDesc, String remark) {
		boolean flag = false;
		Payment payment = new Payment();
		payment.setPaymentId(paymentId);
		payment.setResultCode(resultCode);
		payment.setResultDesc(resultDesc);
		payment.setRemark(remark);
		flag = dataPersistent.updateByPaymentIdFromMongoDB(payment);
		Logger.logInfo("修改支付流水为受理成功",new String[]{"paymentId","flag"},new Object[]{paymentId,flag});
		return flag;
	}

	@Override
	public Payment queryPaymentOfUserPaiedTypeCash(String orderId) {
		Payment payment = new Payment();
		payment.setOrderId(orderId);
		payment.setIsEnable(true);
		payment.setPaymentType(2); //可以确定出现金支付流水
		payment.setStep(4);
		payment.setPaymentStatus("4");
		payment.setPaymentReason("2");
		//该条件一定能确定只有一条
		List<Payment> list = dataPersistent.findByPaymentConditionFromMongoDB(payment);
		if(list == null || list.size() == 0){
			return null;
		}
		return list.get(0);
	}

	@Override
	public Payment queryPaymentOfPresentedTypeFroadPoint(String orderId) {
		Payment payment = new Payment();
		payment.setOrderId(orderId);
		payment.setIsEnable(true);
		payment.setPaymentType(1);
		payment.setPaymentReason("3");
		payment.setStep(4);
		payment.setPaymentStatus("4");
		//该条件一定能确定只有一条
		List<Payment> list = dataPersistent.findByPaymentConditionFromMongoDB(payment);
		if(list == null || list.size() == 0){
			return null;
		}
		return list.get(0);
	}

	@Override
	public boolean modifyOrderStatusOfSeckillingFromRedis(String reqId,boolean isPaiedSuccess) {
		return dataPersistent.updateOrderStatusOfSeckillingFromRedis(reqId, isPaiedSuccess);
	}

	@Override
	public Payment queryPaymentOfUserToPayTypeCash(String orderId) {
		Payment payment = new Payment();
		payment.setOrderId(orderId);
		payment.setIsEnable(true);
		payment.setPaymentType(2);
		payment.setStep(1);
		payment.setPaymentStatus("1");
		payment.setPaymentReason("2");
		//该条件一定能确定只有一条
		List<Payment> list = dataPersistent.findByPaymentConditionFromMongoDB(payment);
		if(list == null || list.size() == 0){
			return null;
		}
		return list.get(0);
	}

	@Override
	public boolean lockAndModifyPaymentToStepFour(String paymentId) {
		Payment payment = dataPersistent.findAndModifyPaymentToStepFour(paymentId);
		if(payment == null){
			return false;
		}
		return true;
	}

	@Override
	public Payment queryEnbalePaymentOfUserPayFailedTypeCash(String orderId) {
		Payment payment = new Payment();
		payment.setOrderId(orderId);
		payment.setIsEnable(true);
		payment.setPaymentType(2); //可以确定出现金支付流水
		payment.setStep(4);
		payment.setPaymentStatus("5");
		payment.setPaymentReason("2");
		//该条件一定能确定只有一条
		List<Payment> list = dataPersistent.findByPaymentConditionFromMongoDB(payment);
		if(list == null || list.size() == 0){
			return null;
		}
		return list.get(0);
	}

	@Override
	public Payment queryToRefundPaymentOfPoint(String clientId,String paymentId) {
		List<RefundPaymentInfo> list = refundSupport.findRefundPaymentListByPaymentId(clientId, paymentId);
		if(list == null || list.size() == 0){
			return null;
		}
		RefundPaymentInfo paymentInfo =list.get(0);
		return queryByPaymentId(paymentInfo.getPaymentId());
	}

	@Override
	public boolean modifyPaymentToPaySuccessOfNotice(String paymentId,String noticeResultCode,String noticeResultDesc,String remark,String billNo) {
		boolean flag = false;
		Payment payment = new Payment();
		payment.setPaymentId(paymentId);
		payment.setPaymentStatus("4");
		payment.setStep(4);
		payment.setNoticeResultCode(noticeResultCode);
		payment.setNoticeResultDesc(noticeResultDesc);
		payment.setRemark(remark);
		if(StringUtils.isNotEmpty(billNo)){
			payment.setBillNo(billNo);
		}
		flag = dataPersistent.updateByPaymentIdFromMongoDB(payment);
		Logger.logInfo("修改支付流水为支付成功",new String[]{"paymentId","flag"},new Object[]{paymentId,flag});
		return flag;
	}

	@Override
	public boolean modifyPaymentToPayFailedOfNotice(String paymentId, String noticeResultCode, String noticeResultDesc,String remark,String billNo) {
		boolean flag = false;
		Payment payment = new Payment();
		payment.setPaymentId(paymentId);
		payment.setPaymentStatus("5");
		payment.setStep(4);
		payment.setNoticeResultCode(noticeResultCode);
		payment.setNoticeResultDesc(noticeResultDesc);
		payment.setRemark(remark);
		if(StringUtils.isNotEmpty(billNo)){
			payment.setBillNo(billNo);
		}
		flag = dataPersistent.updateByPaymentIdFromMongoDB(payment);
		Logger.logInfo("修改支付流水为支付失败",new String[]{"paymentId","flag"},new Object[]{paymentId,flag});
		return flag;
	}

	@Override
	public boolean modifyPaymentToPayFailed(String paymentId, String resultCode, String resultDesc, String remark) {
		boolean flag = false;
		Payment payment = new Payment();
		payment.setPaymentId(paymentId);
		payment.setStep(4);
		payment.setPaymentStatus("5");
		payment.setResultCode(resultCode);
		payment.setResultDesc(resultDesc);
		payment.setRemark(remark);
		flag = dataPersistent.updateByPaymentIdFromMongoDB(payment);
		Logger.logInfo("修改支付流水为支付失败",new String[]{"paymentId","flag"},new Object[]{paymentId,flag});
		return flag;
	}

	@Override
	public boolean modifyPaymentToPayAccessFailed(String paymentId, String resultCode, String resultDesc,String remark) {
		boolean flag = false;
		Payment payment = new Payment();
		payment.setPaymentId(paymentId);
		payment.setStep(4);
		payment.setPaymentStatus("5");
		payment.setResultCode(resultCode);
		payment.setResultDesc(resultDesc);
		payment.setRemark(remark);
		flag = dataPersistent.updateByPaymentIdFromMongoDB(payment);
		Logger.logInfo("修改支付流水为支付失败",new String[]{"paymentId","flag"},new Object[]{paymentId,flag});
		return flag;
	}

	@Override
	public boolean modifyPaymentToPayFailedOfVerify(String paymentId, String verifyResultCode, String verifyResultDesc,String remark,String billNo) {
		boolean flag = false;
		Payment payment = new Payment();
		payment.setPaymentId(paymentId);
		payment.setPaymentStatus("5");
		payment.setStep(4);
		payment.setVerifyResultCode(verifyResultCode);
		payment.setVerifyResultDesc(verifyResultDesc);
		payment.setRemark(remark);
		if(StringUtils.isNotEmpty(billNo)){
			payment.setBillNo(billNo);
		}
		flag = dataPersistent.updateByPaymentIdFromMongoDB(payment);
		Logger.logInfo("修改支付流水为支付失败",new String[]{"paymentId","flag"},new Object[]{paymentId,flag});
		return flag;
	}

	@Override
	public boolean modifyPaymentToPaySuccessOfVerify(String paymentId, String verifyResultCode, String verifyResultDesc,String remark, String billNo) {
		boolean flag = false;
		Payment payment = new Payment();
		payment.setPaymentId(paymentId);
		payment.setPaymentStatus("4");
		payment.setStep(4);
		payment.setVerifyResultCode(verifyResultCode);
		payment.setVerifyResultDesc(verifyResultDesc);
		payment.setRemark(remark);
		if(StringUtils.isNotEmpty(billNo)){
			payment.setBillNo(billNo);
		}
		flag = dataPersistent.updateByPaymentIdFromMongoDB(payment);
		Logger.logInfo("修改支付流水为支付成功",new String[]{"paymentId","flag"},new Object[]{paymentId,flag});
		return flag;
	}

	@Override
	public Payment queryPaymentOfAutoRefundTypePoint(String orderId) {

		Payment payment = new Payment();
		payment.setOrderId(orderId);
		payment.setIsEnable(true);
		payment.setPaymentTypeDetails(0); //可以确定出积分的不论哪种积分
		payment.setStep(4);
		payment.setPaymentStatus("4");
		payment.setPaymentReason(PaymentReason.auto_refund.getCode());
		//该条件一定能确定只有一条
		List<Payment> list = dataPersistent.findByPaymentConditionFromMongoDB(payment);
		if(list == null || list.size() == 0){
			return null;
		}
		return list.get(0);
	
	}


}
