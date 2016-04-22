package com.froad.process.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.froad.cbank.persistent.common.enums.PaymentStatus;
import com.froad.cbank.persistent.common.enums.PaymentTypeDetails;
import com.froad.cbank.persistent.entity.Payment;
import com.froad.cbank.persistent.entity.Refunds;
import com.froad.common.mongo.PaymentMongoEntity;
import com.froad.config.ProcessServiceConfig;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.db.chongqing.entity.OrderEx;
import com.froad.db.chongqing.mappers.OrderMapper;
import com.froad.db.chongqing.mappers.PaymentMapper;
import com.froad.db.chongqing.mappers.RefundsMapper;
import com.froad.enums.CashType;
import com.froad.enums.PaymentReason;
import com.froad.enums.TransferTypeEnum;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;
import com.froad.util.Arith;
import com.froad.util.Constans;
import com.froad.util.MongoTableName;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 *  支付数据转移
  * @ClassName: PaymentProcess
  * @Description: TODO
  * @author share 2015年7月1日
  * @modify share 2015年7月1日
 */
public class PaymentProcess extends AbstractProcess {

	private Map<String,String> orderMap = new HashMap<String, String>();
	private Map<String,String> orderRateMap = new HashMap<String, String>();
	private Map<String,PaymentMongoEntity> paymentMap = new HashMap<String, PaymentMongoEntity>();
	
	public PaymentProcess(String name, ProcessServiceConfig config) {
		super(name, config);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void before() {
		// 删除旧的mongo支付信息
		DBObject dbo = new BasicDBObject();
		dbo.put("client_id", Constans.clientId);
		mongo.remove(dbo, MongoTableName.CB_PAYMENT);
		// 加载数据
		loadData();
		
	}

	private void loadData() {
		TransferMapper transferMapper = sqlSession.getMapper(TransferMapper.class);
		
		OrderMapper orderMapper = cqSqlSession.getMapper(OrderMapper.class);
		
		List<Transfer> orderList = transferMapper.queryGroupList(TransferTypeEnum.order_id);
		if(orderList != null && orderList.size() >0) {
			for(Transfer tf : orderList) {
				orderMap.put(tf.getOldId()  , tf.getNewId());
			}
		}
		

		List<OrderEx> orderPointList = orderMapper.selectPointAll();
		if(orderPointList != null && orderPointList.size() >0) {
			for(OrderEx order : orderPointList) {
				orderRateMap.put(order.getId()+"", order.getPointRate());
			}
		}
	}

	@Override
	public void process() {
		
		// 支付记录转移
		paymentStart();
		// 退款补支付记录
		refundStart();
		
	}
	
	private void paymentStart() {
		PaymentMapper paymentMapper = cqSqlSession.getMapper(PaymentMapper.class);
		List<Payment> paymentList = paymentMapper.selectAll();
		for(Payment pay : paymentList){
			PaymentMongoEntity entity = new PaymentMongoEntity();
			entity.setClient_id(Constans.clientId);
			entity.setCreate_time(pay.getCreateTime());
			entity.setBill_no(pay.getBillNo()==null?pay.getPointNo():pay.getBillNo());
			//0 未发生自动退款 1 自动退款失败 2 自动退款成功
			entity.setAuto_refund("0");
			entity.setFrom_account_name(pay.getFromAccountName());
			entity.setFrom_account_no(pay.getFromAccountNo());
			entity.setFrom_phone(pay.getFromPhone());
			entity.setFrom_user_name(pay.getFromUserName());
			//异常支付流水是否处理   0 - 默认    1 - 已处理
			entity.setIs_dispose_exception("0");
			entity.setIs_enable(pay.getIsEnabled());
			String oderIds = orderMap.get(pay.getOrderId().toString());
			String orderId = null;
			if(oderIds != null){
				orderId = oderIds.split(",")[0];
			}
			 
			entity.setOrder_id(orderId);
			entity.setPayment_id(pay.getSn());
			entity.setPayment_org_no(pay.getPaymentOrgNo());
			//0-结算 1-支付退款 2-用户支付 3-赠送积分 4-赠送积分退款(扣除已赠送的积分)
			entity.setPayment_reason(getPayReason(pay.getPaymentTypeDetails().getCode()));
			entity.setPayment_status(getPayStatus(pay.getPaymentStatus().getCode()));
			// 支付类型  1-积分 2-现金
			entity.setPayment_type("10".equals(pay.getPaymentType().getCode())?2:1);
			entity.setPayment_type_details(getPaymentTypeDetail(pay.getPaymentTypeDetails(),pay.getFromPhone()));
			// 如果是积分支付，记录积分比例
			if(entity.getPayment_type() == 1){
				String ratePoint = orderRateMap.get(pay.getOrderId()+"");
				int pointRate = 0;
				if(ratePoint != null && !"".equals(ratePoint)){
					try {
						pointRate = Integer.parseInt(ratePoint);
					} catch (Exception e) {
						LogCvt.error("积分比例转换异常1.order_id:"+pay.getOrderId());
					}
				}else{
					LogCvt.error("积分比例转换异常2.order_id:"+pay.getOrderId());
				}
				
				entity.setPoint_rate(pointRate);
				// 如果是积分支付，需要X积分比例
				entity.setPayment_value(Arith.mul(pointRate, arith(pay.getPaymentValue())));
			}else{
				entity.setPayment_value(arith(pay.getPaymentValue()));
			}
			
			entity.setRefund_points_bill_no(null);
			entity.setRemark(pay.getRemark());
			entity.setResult_code(pay.getResultCode());
			entity.setResult_desc(pay.getResultDesc());
			entity.setStep(getPaymentStep(pay.getPaymentStatus()));
			entity.setTo_account_name(pay.getToAccountName());
			entity.setTo_account_no(pay.getToAccountNo());
			entity.setTo_phone(pay.getToPhone());
			entity.setTo_user_name(pay.getToUserName());
			
			mongo.add(entity, MongoTableName.CB_PAYMENT);
			
			
			paymentMap.put(entity.getPayment_id(), entity);
		}
	}
	

	private Integer getPaymentTypeDetail(PaymentTypeDetails detail,String fromPhone) {
		// TODO Auto-generated method stub
		String code = detail.getCode();
		
		if(code.equals(PaymentTypeDetails.PAY_AMOUNT.getCode())){
			if(fromPhone!=null && !"".equals(fromPhone.trim())){
				return CashType.foil_card.code();
			}else{
				return CashType.bank_fast_pay.code();
			}
		}else if(code.equals(PaymentTypeDetails.FROAD_TO_MERCHANT.getCode())){
			return CashType.timely_pay.code();
		}else{
			return 0;
		}
	}

	private void refundStart() {
		LogCvt.info("退款记录补支付流水开始");
		RefundsMapper refundMapper = cqSqlSession.getMapper(RefundsMapper.class);
		List<Refunds> refundList = refundMapper.selectForPayment();
		for(Refunds refund : refundList){
			
			PaymentMongoEntity payentity = paymentMap.get(refund.getPaymentSn());
			if(payentity == null){
				LogCvt.error("退款记录补支付流水失败，无查询支付记录信息：refud_sn:"+refund.getSn());
				continue;
			}
			
			PaymentMongoEntity entity = new PaymentMongoEntity();
			entity.setClient_id(Constans.clientId);
			entity.setCreate_time(refund.getCreateTime());
			entity.setBill_no(refund.getRefundBillNo());
			//0 未发生自动退款 1 自动退款失败 2 自动退款成功
			entity.setAuto_refund("0");
			entity.setFrom_account_name(payentity.getTo_account_name());
			entity.setFrom_account_no(payentity.getTo_account_no());
			entity.setFrom_phone(payentity.getTo_phone());
			entity.setFrom_user_name(payentity.getTo_user_name());
			//异常支付流水是否处理   0 - 默认    1 - 已处理
			entity.setIs_dispose_exception("0");
			entity.setIs_enable(refund.getIsEnabled());
			entity.setOrder_id(payentity.getOrder_id());
			entity.setPayment_id(refund.getSn());
			entity.setPayment_org_no(payentity.getPayment_org_no());
			//0-结算 1-支付退款 2-用户支付 3-赠送积分 4-赠送积分退款(扣除已赠送的积分)
			entity.setPayment_reason("1");
			entity.setPayment_status(getPayRefundStatus(refund.getState().getCode()));
			// 支付类型  1-积分 2-现金
			entity.setPayment_type("30".equals(refund.getType().getCode())?2:1);
			entity.setPayment_type_details(payentity.getPayment_type_details());
			// 如果是积分支付，记录积分比例
			if(entity.getPayment_type() == 1){
				entity.setPoint_rate(payentity.getPoint_rate());
				// 如果是积分支付，需要X积分比例
				entity.setPayment_value(Arith.mul(payentity.getPoint_rate(), arith(refund.getRefundValue())));
			}else{
				entity.setPayment_value(arith(refund.getRefundValue()));
			}
			
			entity.setRefund_points_bill_no(refund.getRefundBillNo());
			entity.setRemark(refund.getRemark());
			entity.setResult_code(refund.getResultCode());
			entity.setResult_desc(refund.getResultDesc());
			entity.setStep(getPayRefundStep(refund.getState().getCode()));
			entity.setTo_account_name(payentity.getFrom_account_name());
			entity.setTo_account_no(payentity.getFrom_account_no());
			entity.setTo_phone(payentity.getFrom_phone());
			entity.setTo_user_name(payentity.getFrom_user_name());
			
			mongo.add(entity, MongoTableName.CB_PAYMENT);
			
		}
		
		LogCvt.info("退款记录补支付流水结束");
	}
	
	public String getPayReason(String payTypeDetails){
		if("104".equals(payTypeDetails)){
			return PaymentReason.settle.getCode();
		}else{
			return PaymentReason.payment.getCode();
		}
	}
	
	public String getPayStatus(String payStatus){
		if("10".equals(payStatus)){
			return PaymentStatus.pay_wait.getCode();
		}else if("20".equals(payStatus)){
			return PaymentStatus.pay_request_success.getCode();
		}else if("30".equals(payStatus)){
			return PaymentStatus.pay_request_fail.getCode();
		}else if("40".equals(payStatus)){
			return PaymentStatus.pay_success.getCode();
		}else if("50".equals(payStatus)){
			return PaymentStatus.pay_fail.getCode();
		}else{
			LogCvt.error("支付状态转换失败");
			return null;
		}
	}
	
	public String getPayRefundStatus(String payStatus){
		if("10".equals(payStatus)){
			return PaymentStatus.pay_wait.getCode();
		}else if("20".equals(payStatus)){
			return PaymentStatus.pay_request_success.getCode();
		}else if("30".equals(payStatus)){
			return PaymentStatus.pay_request_fail.getCode();
		}else if("40".equals(payStatus)){
			return PaymentStatus.pay_success.getCode();
		}else if("50".equals(payStatus)){
			return PaymentStatus.pay_fail.getCode();
		}else{
			LogCvt.error("支付状态转换失败");
			return null;
		}
	}
	
	/**
	 *   1-等待支付  2-开始支付中 3-支付请求发送完毕 4-明确支付结果
	  * @Title: getPayRefundStep
	  * @Description: TODO
	  * @author: share 2015年7月23日
	  * @modify: share 2015年7月23日
	  * @param @param payStatus
	  * @param @return    
	  * @return String    
	  * @throws
	 */
	public int getPayRefundStep(String payStatus){
		if("10".equals(payStatus)){
			return 1;
		}else if("20".equals(payStatus)){
			return 3;
		}else if("30".equals(payStatus)){
			return 4;
		}else if("40".equals(payStatus)){
			return 4;
		}else if("50".equals(payStatus)){
			return 4;
		}
		return 4;
	}
	
	public int getPaymentStep(PaymentStatus payStatus){
		if("10".equals(payStatus.getCode())){
			return 1;
		}else if("20".equals(payStatus.getCode())){
			return 3;
		}else if("30".equals(payStatus.getCode())){
			return 4;
		}else if("40".equals(payStatus.getCode())){
			return 4;
		}else if("50".equals(payStatus.getCode())){
			return 4;
		}
		return 4;
	}
	
	/**
	 *  金额算法
	  * @Title: arith
	  * @Description: TODO
	  * @author: share 2015年7月20日
	  * @modify: share 2015年7月20日
	  * @param @param str
	  * @param @return    
	  * @return int    
	  * @throws
	 */
	public int arith(String str){
		if(str == null){
			return 0;
		}
		
		double amt = 0;
		try {
			amt = Double.parseDouble(str);
		} catch (Exception e) {
		}
		
		return Arith.mul(amt, 1000);
		
	}

}

