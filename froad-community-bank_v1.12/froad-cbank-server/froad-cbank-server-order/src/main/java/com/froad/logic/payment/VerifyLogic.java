package com.froad.logic.payment;

import com.froad.common.beans.ResultBean;

/**
 * 主动校验支付结果
* <p>Function: VerifyLogic</p>
* <p>Description: </p>
* @author zhaoxy@thankjava.com
* @date 2015-5-21 下午3:31:09
* @version 1.0
 */
public interface VerifyLogic {

	/**
	 * 验证流水结果
	* <p>Function: verifyPaymentResult</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-6-5 下午2:43:28
	* @version 1.0
	* @param paymentId
	* @return
	 */
	ResultBean verifyPaymentResult(String paymentId);
}
