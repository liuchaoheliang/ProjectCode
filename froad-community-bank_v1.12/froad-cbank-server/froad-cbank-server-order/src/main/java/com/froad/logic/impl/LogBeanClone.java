package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.froad.db.redis.MerchantRedis;
import com.froad.enums.CashType;
import com.froad.log.vo.CouponDetailLog;
import com.froad.log.vo.CouponLog;
import com.froad.log.vo.HeadKey;
import com.froad.log.vo.OrderRefundDetailLog;
import com.froad.log.vo.OrderRefundLog;
import com.froad.log.vo.PaymentDetailLog;
import com.froad.log.vo.PaymentLog;
import com.froad.log.vo.Payments;
import com.froad.log.vo.SettlementLog;
import com.froad.log.vo.SettlementLogDetail;
import com.froad.po.Payment;
import com.froad.po.Ticket;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.refund.RefundHistory;
import com.froad.po.refund.RefundPaymentInfo;
import com.froad.po.refund.RefundProduct;
import com.froad.po.refund.RefundShoppingInfo;
import com.froad.po.settlement.Settlement;
import com.froad.util.EmptyChecker;
import com.froad.util.payment.Const;

/**
 * 构造保存日志所需的bean
 * Description : TODO<br/>
 * Project Name : froad-cbank-server-order<br/>
 * File Name : LogBeanClone.java<br/>
 * 
 * Date : 2015年11月9日 下午5:58:51 <br/> 
 * @author KaiweiXiang
 * @version
 */
public class LogBeanClone {
	
	/**
	 * 构造支付日志的保存的bean
	 * Function : getPaymentLog<br/>
	 * 2015年11月9日 下午4:13:24 <br/>
	 *  
	 * @author KaiweiXiang
	 * @param payment
	 * @param action
	 * @return
	 */
	public static PaymentLog clonePaymentLog(List<Payment> paymentList,OrderMongo order,String paymentType){
		
		HeadKey headKey = new HeadKey();
		headKey.setOrder_id(order.getOrderId());
		
		PaymentLog paymentLog = new PaymentLog();
		paymentLog.setClient_id(order.getClientId());
		paymentLog.setTime(new Date().getTime());
		
		PaymentDetailLog paymentDetailLog = new PaymentDetailLog();
		paymentDetailLog.setOrder_id(order.getOrderId());
		paymentLog.setData(paymentDetailLog);
		
		List<Payments> payments = new ArrayList<Payments>();
		paymentDetailLog.setPayments(payments);
		
		if(paymentList != null && paymentList.size() > 0){
			for(Payment p : paymentList){
				payments.add(clonePayments(p,paymentType));
			}
		}
		
//		List<SubOrders> sub_orders = new ArrayList<SubOrders>();
//		paymentDetailLog.setSub_orders(sub_orders);
//		
//		if(subOrderList != null && subOrderList.size() > 0){
//			for(SubOrderMongo subOrder : subOrderList){
//				SubOrders sub = new SubOrders();
//				sub.setSub_order_id(subOrder.getSubOrderId());
//				sub.setOrder_type(subOrder.getType());
//				
//				if(StringUtils.equals(subOrder.getType(), SubOrderType.group_merchant.getCode()) || StringUtils.equals(subOrder.getType(), SubOrderType.special_merchant.getCode())){
//					sub.setOrg_code("");
//				}else{
//					sub.setOrg_code(subOrder.getMerchantId());
//				}
//			}
//		}
		
		return paymentLog;
	}
	
	public static Payments clonePayments(Payment payment,String paymentType){
		if(payment == null){
			return null;
		}
		Payments payments = new Payments();
		
		//设置详细字段值
		payments.setPayment_id(payment.getPaymentId());
		payments.setPayment_time(payment.getCreateTime() != null ? payment.getCreateTime().getTime() : 0L);
		payments.setPayment_status(payment.getPaymentStatus());
		payments.setBill_no(payment.getBillNo());
		payments.setPayment_org_no(payment.getPaymentOrgNo());
		payments.setPayment_reason(payment.getPaymentReason());
		payments.setPayment_type(paymentType);
		payments.setPayment_type_details(String.valueOf(payment.getPaymentTypeDetails()));
		payments.setPayment_value(String.valueOf(getInteger(payment.getPaymentValue())));
		
		payments.setPoint_rate(String.valueOf(payment.getPointRate()));
		payments.setFrom_account_no(payment.getFromAccountNo());
		payments.setFrom_user_name(payment.getFromUserName());
		payments.setTo_account_no(payment.getSettleToAccountNo());
		payments.setTo_account_name(payment.getSettleToAccountName());
		payments.setResult_code(payment.getResultCode());
		payments.setRemark(payment.getRemark());
		
		return payments;
	}
	
	/**
	 * 构造支付日志的保存的bean
	 * Function : getPaymentLog<br/>
	 * 2015年11月9日 下午4:13:24 <br/>
	 *  
	 * @author KaiweiXiang
	 * @param payment
	 * @param action
	 * @return
	 */
	public static CouponLog cloneTicketLog(Ticket ticket){
		if(ticket == null){
			return null;
		}
		
		HeadKey headKey = new HeadKey();
		headKey.setTicket_id(ticket.getTicketId());
		
		CouponLog couponLog = new CouponLog();
		couponLog.setKey(headKey);
		couponLog.setClient_id(ticket.getClientId());
		couponLog.setTime(new Date().getTime());
		
		CouponDetailLog data = new CouponDetailLog();
		data.setId(getString(ticket.get_id()));
		data.setTicket_id(getString(ticket.getTicketId()));
		data.setOrder_id(getString(ticket.getOrderId()));
		data.setSub_order_id(getString(ticket.getSubOrderId()));
		data.setCreate_time(getLong(ticket.getCreateTime()));
		data.setExpire_time(getLong(ticket.getExpireTime()));
		data.setType(getString(ticket.getType()));
		data.setMember_code(getString(ticket.getMemberCode()));
		data.setMember_name(getString(ticket.getMemberName()));
		data.setMerchant_id(getString(ticket.getMerchantId()));
		data.setMerchant_name(getString(ticket.getMerchantName()));
		data.setMobile(getString(ticket.getMobile()));
		data.setOrg_code(getString(ticket.getOrgCode()));
		data.setOrg_name(getString(ticket.getOrgName()));
		
		data.setForg_code(getString(ticket.getForgCode()));
		data.setSorg_code(getString(ticket.getSorgCode()));
		data.setTorg_code(getString(ticket.getTorgCode()));
		data.setLorg_code(getString(ticket.getLorgCode()));
		data.setProduct_id(getString(ticket.getProductId()));
		data.setProduct_name(getString(ticket.getProductName()));
		data.setImage(getString(ticket.getImage()));
		data.setPrice(getInteger(ticket.getPrice()));
		data.setQuantity(getInteger(ticket.getQuantity()));
		data.setStatus(getString(ticket.getStatus()));
		data.setConsume_time(getLong(ticket.getConsumeTime()));
		data.setOutlet_id(getString(ticket.getOutletId()));
		data.setOutlet_name(getString(ticket.getOutletName()));
		data.setMerchant_user_id(String.valueOf(getLong(ticket.getMerchantUserId())));
		data.setMerchant_user_name(getString(ticket.getMerchantUserName()));
		data.setRefund_time(getLong(ticket.getRefundTime()));
		data.setRefund_id(getString(ticket.getRefundId()));
		
		couponLog.setData(data);
		return couponLog;
	}
	
	
	/**
	 * 构造退款日志的保存的bean
	 * Function : getPaymentLog<br/>
	 * 2015年11月9日 下午4:13:24 <br/>
	 *  
	 * @author KaiweiXiang
	 * @param payment
	 * @param action
	 * @return
	 */
	public static OrderRefundLog cloneRefundLog(RefundHistory refundHistory){
		if(refundHistory == null){
			return null;
		}
		HeadKey headKey = new HeadKey();
		headKey.setRefund_id(refundHistory.get_id());
		
		OrderRefundLog orderRefundLog = new OrderRefundLog();
		orderRefundLog.setKey(headKey);
		orderRefundLog.setClient_id(refundHistory.getClientId());
		orderRefundLog.setTime(new Date().getTime());
		
		
		OrderRefundDetailLog data = new OrderRefundDetailLog();
		data.setRefund_id(refundHistory.get_id());
		data.setCreate_time(refundHistory.getCreateTime());
		data.setMember_code(refundHistory.getMemberCode());
		data.setOrder_id(refundHistory.getOrderId());
		data.setRefund_resource(refundHistory.getRefundResource());
		data.setRefund_state(refundHistory.getRefundState());
		data.setRefund_time(refundHistory.getRefundTime());
		
		data.setMerchant_id("");
		data.setMerchant_name("");
		data.setSub_order_id("");
		data.setOrder_type("");
		data.setRefund_price(0.0D); //暂不给值
		
	
		List<RefundPaymentInfo> paymentInfoList = refundHistory.getPaymentInfo();
		if(paymentInfoList != null && paymentInfoList.size() > 0){
			List<com.froad.log.vo.RefundPaymentInfo> payment_info = new ArrayList<com.froad.log.vo.RefundPaymentInfo>();
			for(RefundPaymentInfo paymentInfo : paymentInfoList){
				com.froad.log.vo.RefundPaymentInfo pefundPaymentInfo_log = new com.froad.log.vo.RefundPaymentInfo();
				pefundPaymentInfo_log.setPayment_id(paymentInfo.getPaymentId());
				pefundPaymentInfo_log.setRefund_value(Double.valueOf(paymentInfo.getRefundValue()));
				pefundPaymentInfo_log.setResult_code(paymentInfo.getResultCode());
				pefundPaymentInfo_log.setResult_desc(paymentInfo.getResultDesc());
				pefundPaymentInfo_log.setType(paymentInfo.getType());
				payment_info.add(pefundPaymentInfo_log);
			}
			data.setPayment_info(payment_info);
		}
		
		
		List<RefundShoppingInfo> shoppingInfoList = refundHistory.getShoppingInfo();
				
		if(shoppingInfoList != null && shoppingInfoList.size() > 0){
			List<com.froad.log.vo.RefundProductInfo> productInfo_log = new ArrayList<com.froad.log.vo.RefundProductInfo>();
			for(RefundShoppingInfo shoppingInfo : shoppingInfoList){
				data.setMerchant_id(shoppingInfo.getMerchantId());
				data.setMerchant_name(shoppingInfo.getMerchantName());
				data.setSub_order_id(shoppingInfo.getSubOrderId());
				data.setOrder_type(shoppingInfo.getType());
				
				List<RefundProduct> products = shoppingInfo.getProducts();
				if(products != null && products.size() > 0){
					for(RefundProduct product : products){
						com.froad.log.vo.RefundProductInfo refundProductInfo_log = new com.froad.log.vo.RefundProductInfo();
						refundProductInfo_log.setPrice(Double.valueOf(product.getPrice()));
						refundProductInfo_log.setProduct_id(product.getProductId());
						refundProductInfo_log.setProduct_name(product.getProductName());
						refundProductInfo_log.setQuantity(product.getQuantity());
						
						productInfo_log.add(refundProductInfo_log);
					}
				}
			}
			data.setProducts(productInfo_log);
		}
		
		//商户类目:根据商户ID去查询
		if(EmptyChecker.isNotEmpty(data.getMerchant_id())){
			Map<String, String> merchantMap = MerchantRedis.get_cbbank_merchant_client_id_merchant_id(refundHistory.getClientId(),data.getMerchant_id());
			if(EmptyChecker.isNotEmpty(merchantMap)){
				String merchant_category_id = merchantMap.get("category_id");
				String merchant_category_name = merchantMap.get("category_name");
				if(EmptyChecker.isNotEmpty(merchant_category_id)){
					data.setMerchant_category_id(merchant_category_id);
				}
				if(EmptyChecker.isNotEmpty(merchant_category_name)){
					data.setMerchant_category_id(merchant_category_name);
				}
			}
		}
		
		orderRefundLog.setData(data);
		return orderRefundLog;
	}
	
	/**
	 * 支付方式:1-方付通积分支付，2-银行积分支付，3-快捷支付，4-贴膜卡支付，5-方付通积分+快捷支付，6-方付通积分+贴膜卡支付，7-银行积分+快捷支付，8-银行积分+贴膜卡支付
	 * Function : getPaymentType<br/>
	 * 2015年11月19日 下午4:48:49 <br/>
	 *  
	 * @author KaiweiXiang
	 * @return
	 */
	public static String getPaymentType(String pointOrgNo,int cashType){
		if(cashType == 0){//纯积分支付
			if(Const.FROAD_POINT_ORG_NO.equals(pointOrgNo)){ //方付通积分
				return "1";
			}else{//银行积分
				return "2";
			}
		}else if(pointOrgNo == null){//纯现金支付
			if(CashType.foil_card.code() == cashType){ //使用贴膜卡
				return "4";
			}else if(CashType.bank_fast_pay.code() == cashType){ //如果使用的快捷支付
				return "3";
			}
		}else{
			if(Const.FROAD_POINT_ORG_NO.equals(pointOrgNo)){ //方付通积分
				if(CashType.foil_card.code() == cashType){ //使用贴膜卡
					return "6";
				}else if(CashType.bank_fast_pay.code() == cashType){ //如果使用的快捷支付
					return "7";
				}
			}else{//银行积分
				if(CashType.foil_card.code() == cashType){ //使用贴膜卡
					return "8";
				}else if(CashType.bank_fast_pay.code() == cashType){ //如果使用的快捷支付
					return "7";
				}
			}
		}
		return "0";
	}
	

	public static SettlementLog cloneSettlementLog(Settlement settlement){
		
		HeadKey headKey = new HeadKey();
		headKey.setRefund_id(settlement.getSettlementId());
		
		SettlementLog settlementLog = new SettlementLog();
		settlementLog.setKey(headKey);
		
		settlementLog.setClient_id(settlement.getClientId());
		settlementLog.setTime(new Date().getTime());
		
		SettlementLogDetail data = new SettlementLogDetail();
		data.setSettlement_id(settlement.getSettlementId());
		data.setCreate_time(settlement.getCreateTime());
		data.setMerchant_id(getString(settlement.getMerchantId()));
		data.setMerchant_name(getString(settlement.getMerchantName()));
		data.setMerchant_user_id(String.valueOf(settlement.getMerchantUserId()));
		data.setMoney(getInteger(settlement.getMoney()));
		data.setOrder_id(settlement.getOrderId());
		
		data.setOutlet_id(getString(settlement.getOutletId()));
		data.setOutlet_name(getString(settlement.getOutletName()));
		data.setPayment_id(settlement.getPaymentId());
		data.setProduct_count(settlement.getProductCount());
		data.setProduct_id(settlement.getProductId());
		data.setProduct_name(settlement.getProductName());
		data.setSettle_state(settlement.getSettleState());
		
		data.setSub_order_id(getString(settlement.getSubOrderId()));
		data.setType(settlement.getType());
		
		List<String> tickets = new ArrayList<String>();
		if(settlement.getTickets() != null && settlement.getTickets().size() > 0){
			tickets.addAll(tickets);
		}
		data.setTickets(tickets);
		
		settlementLog.setData(data);
		
		return settlementLog;
	}
	private static String getString(String s){
		return StringUtils.isEmpty(s) ? "" : s;
	}
	private static Integer getInteger(Integer i){
		return i == null ? 0 : i;
	}
	private static Long getLong(Long l){
		return l == null ? 0L : l;
	}
}
