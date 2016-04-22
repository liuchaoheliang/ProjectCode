package com.froad.db.mongo;

import java.util.List;

import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.PaymentMongo;

/**
 * Payment模块相关Mongo操作
* <p>Function: PaymentMongoService</p>
* <p>Description: </p>
* @author zhaoxy@thankjava.com
* @date 2015年7月30日 上午10:07:01
* @version 1.0
 */
public interface PaymentMongoService {


	/**
	 * 拉取对账基础数据
	* <p>Function: queryPaymentOfDaily</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015年8月4日 下午2:34:40
	* @version 1.0
	* @return
	 */
	List<PaymentMongo> queryPaymentOfDaily();
	
	/**
	 * 拉取对账基础数据，如果orderId不为空，则按orderId进行拉取数据
	* <p>Function: queryPaymentOfDaily</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015年8月4日 下午2:34:40
	* @version 1.0
	* @return
	 */
	List<PaymentMongo> queryPaymentOfDaily(String orderId);
	
	/**
	 * 获取orderId去重后的基数据
	 * queryOrderIdDistinctOfDaily:(这里用一句话描述这个方法的作用).
	 *
	 * @author Zxy
	 * 2015-10-23 上午10:48:30
	 * @return
	 *
	 */
	List<String> queryOrderIdDistinctOfDaily();
	
	/**
	 * 通过orderId查询出该笔订单的支付用户memberCode
	* <p>Function: queryMemberCodeByOrderId</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015年8月4日 下午2:35:21
	* @version 1.0
	* @param orderId
	* @return
	 */
	OrderMongo queryByOrderId(String orderId);
}
