package com.froad.handler.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.froad.enums.SubOrderType;
import com.froad.handler.OrderLogHandler;
import com.froad.log.OrderLogs;
import com.froad.log.vo.OrderRefundLog;
import com.froad.log.vo.PaymentLog;
import com.froad.log.vo.SubOrders;
import com.froad.logic.impl.LogBeanClone;
import com.froad.po.Payment;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.po.refund.RefundHistory;
import com.froad.support.OrderSupport;
import com.froad.support.impl.OrderSupportImpl;
import com.froad.support.impl.payment.DataWrapImpl;
import com.froad.support.payment.DataWrap;
import com.froad.util.payment.Const;

public class OrderLogHandlerImpl implements OrderLogHandler{

	private OrderSupport orderSupport = new OrderSupportImpl();
	private DataWrap dataWrap = new DataWrapImpl();
	@Override
	public void paymentLogCreate(List<Payment> plist, OrderMongo order, String paymentType, 
			int paymentLogType) {
		paymentLogCreate(plist, order, paymentType, paymentLogType, null);
	}
	
	/**
	 * 创建支付流水的时候，记录日志，有多少个子订单，输出多少条记录（面对面订单保存一条记录）
	 * paymentLogType :{1:创建支付流水，2:支付成功，3:支付失败}
	 */
	@Override
	public void paymentLogCreate(List<Payment> plist, OrderMongo order, String paymentType,int paymentLogType,String paymentStatus) {
		if(order == null){
			order = orderSupport.getOrderByOrderId(plist.get(0).getClientId(), plist.get(0).getOrderId());
		}
		PaymentLog log = LogBeanClone.clonePaymentLog(plist, order,paymentType);
		
		List<SubOrders> sub_orders = new ArrayList<SubOrders>();
		if(order.getIsQrcode() == 1){ //面对面订单
			SubOrders subOrders = new SubOrders();
			subOrders.setSub_order_id(order.getOrderId());
			subOrders.setOrder_type("0");
			subOrders.setOrg_code(getOrderOrg(order));
			sub_orders.add(subOrders);
//			//输出支付流水创建的日志
//			savePaymentLog(log, paymentLogType);
		}else{
			
			List<SubOrderMongo> subOrderList = orderSupport.getSubOrderListByOrderId(order.getClientId(), order.getOrderId());
			
			//多个子订单输出多条日志
			if(subOrderList != null && subOrderList.size() > 0){
				for(SubOrderMongo subOrder :subOrderList){
					SubOrders subOrders = new SubOrders();
					subOrders.setSub_order_id(subOrder.getSubOrderId());
					subOrders.setOrder_type(subOrder.getType());
					
					String org_code = "";
					if(StringUtils.equals(subOrder.getType(), SubOrderType.presell_org.getCode())){
						org_code = subOrder.getMerchantId();
					}
					
					if(StringUtils.isEmpty(org_code)){
						org_code = getOrderOrgFromSubOrder(subOrder);
					}
					
					subOrders.setOrg_code(org_code);
					/*if(StringUtils.equals(subOrder.getType(), SubOrderType.group_merchant.getCode()) 
							|| StringUtils.equals(subOrder.getType(), SubOrderType.special_merchant.getCode())){
						subOrders.setOrg_code("");
					}else{
						subOrders.setOrg_code(subOrder.getMerchantId());
					}*/
					
					sub_orders.add(subOrders);
					
				}
			}
		}
		
		log.getData().setSub_orders(sub_orders);
		
		//输出支付流水创建的日志
		savePaymentLog(log, paymentLogType);
		
		
//		log.getData().setPayment_type(paymentType);
//		log.getData().setPayment_value(paymentValue);
//		if(paymentStatus != null){
//			log.getData().setPayment_status(paymentStatus);
//		}
		
		
	}
	
	
	public void createRefundLog(RefundHistory refundHistory){
		OrderRefundLog refundLog = LogBeanClone.cloneRefundLog(refundHistory);
		
		String clientId = refundHistory.getClientId();
		String orderId = refundHistory.getOrderId();
		
		//设置paymentType
		refundLog.getData().setPayment_type(getPaymentTypeOfLog(orderId));
		
		//设置机构号（面对面订单，从大订单取，非面对面订单从子订单中取）
		OrderMongo order = orderSupport.getOrderByOrderId(clientId, orderId);
		if(order != null){
			if(order.getIsQrcode() == 1){
				refundLog.getData().setF_org_code(order.getForgCode());
				refundLog.getData().setS_org_code(order.getSorgCode());
				refundLog.getData().setT_org_code(order.getTorgCode());
				refundLog.getData().setL_org_code(order.getLorgCode());
			}else{
				SubOrderMongo subOrder = orderSupport.getSubOrderByClient(clientId, orderId, refundLog.getData().getSub_order_id());
				if(subOrder != null){
					refundLog.getData().setF_org_code(subOrder.getForgCode());
					refundLog.getData().setS_org_code(subOrder.getSorgCode());
					refundLog.getData().setT_org_code(subOrder.getTorgCode());
					refundLog.getData().setL_org_code(subOrder.getLorgCode());
				}
			}
		}
		
		OrderLogs.refundSuccess(refundLog);
	}
	
	private String getOrderOrg(OrderMongo order){
		if(!StringUtils.isEmpty(order.getLorgCode())){ //四级机构（面对面订单专用）
			return order.getLorgCode();
		}else if(!StringUtils.isEmpty(order.getTorgCode())){ // 三级机构（面对面订单专用）
			return order.getTorgCode();
		}else if(!StringUtils.isEmpty(order.getSorgCode())){//二级机构（面对面订单专用）
			return order.getSorgCode();
		}
//		else if(!StringUtils.isEmpty(order.getForgCode())){//一级机构（面对面订单专用）
//			return order.getForgCode();
//		}
		else {
			return "";
		}
	}
	private String getOrderOrgFromSubOrder(SubOrderMongo subOrder){
		if(!StringUtils.isEmpty(subOrder.getLorgCode())){ //四级机构
			return subOrder.getLorgCode();
		}else if(!StringUtils.isEmpty(subOrder.getTorgCode())){ // 三级机构
			return subOrder.getTorgCode();
		}else if(!StringUtils.isEmpty(subOrder.getSorgCode())){//二级机构
			return subOrder.getSorgCode();
		}
//		else if(!StringUtils.isEmpty(order.getForgCode())){//一级机构（面对面订单专用）
//			return order.getForgCode();
//		}
		else {
			return "";
		}
	}
	private void savePaymentLog(PaymentLog log,int paymentLogType){
		if(paymentLogType == 1){
			OrderLogs.addPay(log);
		}else if(paymentLogType == 2){
			OrderLogs.updatePaySuccess(log);
		}else if(paymentLogType == 3){
			OrderLogs.updatePayFail(log);
		}
	}

	/**
	 * 获取日志对应的支付类型
	 * Function : getPaymentTypeOfLog<br/>
	 * 2015年12月7日 下午2:21:26 <br/>
	 *  
	 * @author KaiweiXiang
	 * @param orderId
	 * @return
	 */
	private String getPaymentTypeOfLog(String orderId){
		if(StringUtils.isEmpty(orderId)){
			return "";
		}
		try {
			List<Payment> plist = dataWrap.queryEnableOfUserPayByOrderId(orderId);
			if(plist == null || plist.size() == 0){
				return "";
			}
			
			String pointOrgNo = null;
			int cashType = 0;
			
			for(Payment payment : plist){
				int paymentType = payment.getPaymentType();
				if(paymentType == 2){//现金
					cashType = payment.getPaymentTypeDetails();
				}else if(paymentType == 1){//方付通积分
					pointOrgNo = Const.FROAD_POINT_ORG_NO;
				}else if(paymentType == 3){//银行积分
					pointOrgNo = String.valueOf(payment.getPaymentOrgNo());
				}
			}
			
			return LogBeanClone.getPaymentType(pointOrgNo, cashType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
