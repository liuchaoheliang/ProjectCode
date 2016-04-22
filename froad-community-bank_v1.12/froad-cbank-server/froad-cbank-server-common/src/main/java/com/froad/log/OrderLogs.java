package com.froad.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.froad.log.vo.CouponLog;
import com.froad.log.vo.OrderLog;
import com.froad.log.vo.OrderRefundLog;
import com.froad.log.vo.PaymentLog;
import com.froad.log.vo.SettlementLog;



public class OrderLogs {
	private static Logger logger = LoggerFactory.getLogger(OrderLogs.class);
	 
	public static void main(String[] args) {
		OrderLogs log= new OrderLogs();

	}
	//创建订单
	public static Boolean addOrder(OrderLog log){
		logger.info(JSON.toJSONString(log));
		return true;
	} 
	//创建成功
	public static Boolean successOrder(OrderLog log){
		logger.info(JSON.toJSONString(log));
		return true;
	} 
	//券码创建
	public static Boolean addCoupon(CouponLog log){
		if(log == null){
			return null;
		}
		log.setAction(LogActionConstent.ACTION_TICKET_ADD);
		logger.info(JSON.toJSONString(log));
		return true;
	} 
	//券码修改
	public static Boolean updateCoupon(CouponLog log){
		if(log == null){
			return null;
		}
		log.setAction(LogActionConstent.ACTION_TICKET_MODIFY);
		logger.info(JSON.toJSONString(log));
		return true;
	} 
	//支付创建
	public static Boolean addPay(PaymentLog log){
		if(log == null){
			return false;
		}
		
		log.setAction(LogActionConstent.ACTION_PAYMENT_ADD);
		logger.info(JSON.toJSONString(log));
		return true;
	} 
	//支付成功/失败
	public static Boolean updatePaySuccess(PaymentLog log){
		if(log == null){
			return false;
		}
		
		log.setAction(LogActionConstent.ACTION_PAYMENT_SUCCESS);
		logger.info(JSON.toJSONString(log));
		
		return true;
	} 
	//支付成功/失败
	public static Boolean updatePayFail(PaymentLog log){
		if(log == null){
			return false;
		}
		
		log.setAction(LogActionConstent.ACTION_PAYMENT_FAIL);
		logger.info(JSON.toJSONString(log));
		
		return true;
	}
	//退款成功
	public static Boolean refundSuccess(OrderRefundLog log){
		if(log == null){
			return false;
		}
		
		log.setAction(LogActionConstent.ACTION_REFUND);
		logger.info(JSON.toJSONString(log));
		return true;
	} 
	
	//结算创建
	public static Boolean settlementCreate(SettlementLog log){
		if(log == null){
			return false;
		}
		
		log.setAction(LogActionConstent.ACTION_SETTLEMENT_ADD);
		logger.info(JSON.toJSONString(log));
		return true;
	} 
	
	//结算修改
	public static Boolean settlementUpdate(SettlementLog log){
		if(log == null){
			return false;
		}
		
		log.setAction(LogActionConstent.ACTION_SETTLEMENT_MODIFY);
		logger.info(JSON.toJSONString(log));
		return true;
	} 
}
