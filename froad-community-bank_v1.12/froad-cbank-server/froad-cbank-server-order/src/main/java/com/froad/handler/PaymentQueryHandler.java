package com.froad.handler;

import com.froad.thrift.vo.payment.PaymentQueryExcetionVo;
import com.froad.thrift.vo.payment.PaymentQueryPageVo;
import com.froad.thrift.vo.payment.PaymentQueryVo;

public interface PaymentQueryHandler {
	
	
	/**
	 * Boss 普通流水查询
	* <p>Function: queryPaymentForBoss</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-7 下午2:36:21
	* @version 1.0
	* @param paymentQueryVo
	* @return
	 */
	public PaymentQueryPageVo queryPaymentForBoss(PaymentQueryVo paymentQueryVo);

	
	/**
	 * Boss 异常流水查询
	* <p>Function: queryPaymentForBossOfException</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-7 下午2:36:11
	* @version 1.0
	* @param excetionVo
	* @return
	 */
	public PaymentQueryPageVo queryPaymentForBossOfException(PaymentQueryExcetionVo excetionVo);
}
