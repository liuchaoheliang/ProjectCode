package com.froad.util.payment;

import com.froad.enums.ModuleID;
import com.froad.po.Payment;
import com.froad.po.PaymentMongoEntity;
import com.froad.thrift.vo.payment.BossPaymentQueryVo;
import com.froad.thrift.vo.payment.BossPaymentVo;
import com.froad.util.Arith;
import com.froad.util.SimpleID;

/**
 * 基础的对支付模块的支持服务类
* <p>Function: BaseSubassembly</p>
* <p>Description: </p>
* @author zhaoxy@thankjava.com
* @date 2015-5-20 上午9:43:26
* @version 1.0
 */
public class BaseSubassembly {
	
	private static SimpleID simpleIDServicePayment;
	
	private static SimpleID simpleIDServiceSettlement;

	/**
	 * MongoEntity TO Vo
	* <p>Function: loadyPaymentQueryVo</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-20 上午9:58:58
	* @version 1.0
	* @param mongoEntity
	* @return
	 */
	public static BossPaymentQueryVo loadyPaymentQueryVo(PaymentMongoEntity mongoEntity){
		if(mongoEntity == null){
			return null;
		}
		BossPaymentQueryVo paymentQueryVo = new BossPaymentQueryVo();
		paymentQueryVo.setId(mongoEntity.getId() == null ? 0 : mongoEntity.getId());
		paymentQueryVo.setCreateTime(mongoEntity.getCreate_time() != null ? mongoEntity.getCreate_time().getTime() : 0);
		paymentQueryVo.setPaymentId(mongoEntity.getPayment_id());
		paymentQueryVo.setClientId(mongoEntity.getClient_id());
		paymentQueryVo.setOrderId(mongoEntity.getOrder_id());
		paymentQueryVo.setBillNo(mongoEntity.getBill_no());
		paymentQueryVo.setPaymentType(mongoEntity.getPayment_type());
		paymentQueryVo.setPaymentValue(mongoEntity.getPayment_value() == null ? 0d : Arith.div(mongoEntity.getPayment_value(),Const.HDOP_1000));
		paymentQueryVo.setPaymentTypeDetails(mongoEntity.getPayment_type_details() == null ? -1 : mongoEntity.getPayment_type_details());
		paymentQueryVo.setStep(mongoEntity.getStep());
		paymentQueryVo.setIsEnable(mongoEntity.getIs_enable());
		paymentQueryVo.setPaymentStatus(mongoEntity.getPayment_status());
		paymentQueryVo.setPaymentOrgNo(mongoEntity.getPayment_org_no());
		paymentQueryVo.setFromAccountName(mongoEntity.getFrom_account_name());
		paymentQueryVo.setFromAccountNo(mongoEntity.getFrom_account_no());
		paymentQueryVo.setToAccountName(mongoEntity.getTo_account_name());
		paymentQueryVo.setToAccountNo(mongoEntity.getTo_account_no());
		paymentQueryVo.setFromPhone(mongoEntity.getFrom_phone());
		paymentQueryVo.setToPhone(mongoEntity.getTo_phone());
		paymentQueryVo.setFromUserName(mongoEntity.getFrom_user_name());
		paymentQueryVo.setToUserName(mongoEntity.getTo_user_name());
		paymentQueryVo.setResultCode(mongoEntity.getResult_code());
		paymentQueryVo.setResultDesc(mongoEntity.getResult_desc());
		paymentQueryVo.setRemark(mongoEntity.getRemark());
		paymentQueryVo.setAutoRefund(mongoEntity.getAuto_refund());
		paymentQueryVo.setPointRate(mongoEntity.getPoint_rate() == null ? 0 : mongoEntity.getPoint_rate());
		paymentQueryVo.setPaymentReason(mongoEntity.getPayment_reason());
		paymentQueryVo.setIsDisposeException(mongoEntity.getIs_dispose_exception());
		paymentQueryVo.setRefundPointsBillNo(mongoEntity.getRefund_points_bill_no());
		return paymentQueryVo;
	}

	
	
	public static String simpleIDPayment(){
		if(simpleIDServicePayment == null){
			simpleIDServicePayment = new SimpleID(ModuleID.bossPayment);
		}
		return  Const.BOSS_REPAIR_PREFIX + simpleIDServicePayment.nextId();
	}
	
	public static String simpleIDSettlement(){
		if(simpleIDServiceSettlement == null){
			simpleIDServiceSettlement = new SimpleID(ModuleID.bossSettlement);
		}
		return Const.BOSS_REPAIR_PREFIX + simpleIDServiceSettlement.nextId();
	}
	
	
	public static BossPaymentVo toPaymentVo(Payment payment){
		if(payment == null){
			return null;
		}
		BossPaymentVo paymentQueryVo = new BossPaymentVo();
		paymentQueryVo.setCreateTime(payment.getCreateTime() != null ? payment.getCreateTime().getTime() : 0);
		paymentQueryVo.setPaymentId(payment.getPaymentId());
		paymentQueryVo.setClientId(payment.getClientId());
		paymentQueryVo.setOrderId(payment.getOrderId());
		paymentQueryVo.setBillNo(payment.getBillNo());
		paymentQueryVo.setPaymentType(payment.getPaymentType());
		paymentQueryVo.setPaymentValue(payment.getPaymentValue() == null ? 0d : Arith.div(payment.getPaymentValue(),Const.HDOP_1000));
		paymentQueryVo.setPaymentTypeDetails(payment.getPaymentTypeDetails() == null ? -1 : payment.getPaymentTypeDetails());
		paymentQueryVo.setStep(payment.getStep());
		paymentQueryVo.setIsEnable(payment.getIsEnable());
		paymentQueryVo.setPaymentStatus(payment.getPaymentStatus());
		paymentQueryVo.setPaymentOrgNo(payment.getPaymentOrgNo());
		paymentQueryVo.setFromAccountName(payment.getFromAccountName());
		paymentQueryVo.setFromAccountNo(payment.getFromAccountNo());
		paymentQueryVo.setFromPhone(payment.getFromPhone());
		paymentQueryVo.setFromUserName(payment.getFromUserName());
		paymentQueryVo.setResultCode(payment.getResultCode());
		paymentQueryVo.setResultDesc(payment.getResultDesc());
		paymentQueryVo.setRemark(payment.getRemark());
		paymentQueryVo.setPointRate(payment.getPointRate() == null ? 0 : payment.getPointRate());
		paymentQueryVo.setPaymentReason(payment.getPaymentReason());
		paymentQueryVo.setIsDisposeException(payment.getIsDisposeException());
		return paymentQueryVo;
	}
}
