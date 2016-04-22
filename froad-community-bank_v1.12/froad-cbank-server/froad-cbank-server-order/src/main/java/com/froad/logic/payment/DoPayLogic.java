package com.froad.logic.payment;

import java.util.List;

import com.froad.common.beans.ResultBean;
import com.froad.exceptions.PaymentException;
import com.froad.po.Payment;
import com.froad.po.mongo.OrderMongo;


/**
 * 支付处理
* <p>Function: DoPayLogic</p>
* <p>Description: </p>
* @author zhaoxy@thankjava.com
* @date 2015-5-21 下午3:29:59
* @version 1.0
 */
public interface DoPayLogic {

	/**
	 * 进行支付
	* <p>Function: doPayCoreForTrade</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-21 下午3:53:51
	* @version 1.0
	* @param payments
	* @return
	 */
	ResultBean doPayCoreForTrade(OrderMongo order,List<Payment> payments) throws PaymentException;
	
	
}
