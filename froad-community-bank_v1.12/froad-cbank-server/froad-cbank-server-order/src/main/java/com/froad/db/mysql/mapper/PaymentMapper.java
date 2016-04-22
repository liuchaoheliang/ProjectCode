package com.froad.db.mysql.mapper;

import java.util.List;

import com.froad.exceptions.FroadDBException;
import com.froad.po.Payment;

/**
 * Copyright © 2015 F-Road. All rights reserved.
 * @ClassName: PaymentMapper
 * @Description: 支付信息记录数据库处理相关
 * @Author: zhaoxiaoyao@f-road.com.cn
 * @Date: 2015年3月21日 上午11:43:00
 */
public interface PaymentMapper {
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: addPayment
	 * @Description: 插入支付记录信息
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param payment
	 * @return void
	 */
	public void addPayment(Payment payment) throws FroadDBException;

	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: findPaymentByPaymentId
	 * @Description: 通过paymentId获取支付记录
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param paymentId
	 * @return
	 * @throws FroadDBException
	 * @Return: Payment
	 */
	public Payment findPaymentByPaymentId(String paymentId) throws FroadDBException;
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: invalidPaymentByOrderId
	 * @Description: 通过订单Id查询出有效的支付记录信息
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param orderId
	 * @return
	 * @throws FroadDBException
	 * @Return: Boolean
	 */
	public List<Payment> findEnablePaymentsByOrderId(String orderId) throws FroadDBException;
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: findEnablePaymentsByOrderId
	 * @Description: 通过订单Id查询出有效的支付记录信息(用户支付流水)
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param orderId
	 * @return
	 * @Return: List<Payment>
	 */
	public List<Payment> findEnablePaymentsOfUserPayByOrderId(String orderId) throws FroadDBException;
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: updateByPaymentId
	 * @Description: 通过PaymentId更新
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param payment
	 * @return
	 * @throws FroadDBException
	 * @Return: boolean
	 */
	public boolean updateByPaymentId(Payment payment) throws FroadDBException;
	
}
