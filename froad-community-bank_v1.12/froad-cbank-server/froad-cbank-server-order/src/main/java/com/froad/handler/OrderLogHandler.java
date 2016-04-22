package com.froad.handler;

import java.util.List;

import com.froad.po.Payment;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.refund.RefundHistory;

/**
 * 日志处理类 Description : TODO<br/>
 * Project Name : froad-cbank-server-order<br/>
 * File Name : OrderLogHandler.java<br/>
 * 
 * Date : 2015年11月20日 下午2:30:02 <br/>
 * 
 * @author KaiweiXiang
 * @version
 */
public interface OrderLogHandler {
	/**
	 * 支付流水创建日志
	 * 
	 * @return
	 */
	public void paymentLogCreate(List<Payment> plist, OrderMongo order, String paymentType, 
			int paymentLogType);

	public void paymentLogCreate(List<Payment> plist, OrderMongo order, String paymentType,
			int paymentLogType, String paymentStatus);
	
	public void createRefundLog(RefundHistory refundHistory);

}
