package com.froad.logic.payment;

import com.froad.common.beans.ResultBean;
import com.froad.po.mongo.OrderMongo;

/**
 * 支付完成的后续逻辑
* <p>Function: DoPayTrailing</p>
* <p>Description: </p>
* @author zhaoxy@thankjava.com
* @date 2015-5-22 下午3:52:41
* @version 1.0
 */
public interface DoPayTrailing {

	/**
	 * 支付成功后续逻辑
	* <p>Function: doPayRedress</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-22 下午3:53:24
	* @version 1.0
	* @param order
	* @return
	 */
	public ResultBean doPaySuccess(OrderMongo order);
	
	/**
	 * 支付失败后续逻辑
	* <p>Function: doPayFailed</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-22 下午3:54:19
	* @version 1.0
	* @param order
	* @return
	 */
	public void doPayFailed(OrderMongo order);
}
