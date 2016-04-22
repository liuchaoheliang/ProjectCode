package com.froad.logic.payment;

import com.froad.common.beans.ResultBean;
import com.froad.po.Payment;
import com.froad.po.mongo.OrderMongo;

/**
 * 业务拓展规则
* <p>Function: BusinessExpandLogic</p>
* <p>Description: </p>
* @author zhaoxy@thankjava.com
* @date 2015-5-26 上午10:43:22
* @version 1.0
 */
public interface BusinessExpandLogic {

	/**
	 * 向指定用户赠送方付通积分
	* <p>Function: presentFroadPoint</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-26 上午10:46:05
	* @version 1.0
	* @return
	 */
	ResultBean presentFroadPoint(OrderMongo order);
	
	/**
	 * 扣除用户已获得赠送的方付通积分
	* <p>Function: deductFroadPoint</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-6-1 上午11:51:01
	* @version 1.0
	* @return
	 */
	ResultBean deductFroadPoint(Payment payment);
	
	/**
	 * 为用户开通VIP资格身份
	* <p>Function: attachUserVIPStatus</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015年6月17日 下午1:43:50
	* @version 1.0
	* @param order
	* @return
	 */
	ResultBean attachUserVIPStatus(OrderMongo order);
}
