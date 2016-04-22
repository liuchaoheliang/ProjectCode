package com.froad.support.payment;

import java.util.List;

import com.froad.po.Client;
import com.froad.po.ClientPaymentChannel;
import com.froad.po.Payment;


/**
 * 支付相关操作Mongo Redis MySQL
* <p>Function: DataPersistent</p>
* <p>Description: </p>
* @author zhaoxy@thankjava.com
* @date 2015-5-20 下午1:55:37
* @version 1.0
 */
public interface DataPersistent {

	/**
	 * 保存支付流水到MongoDB  ---->  <b>必要参数必须有效</b>  
	* <p>Function: savePaymentToMongoDB</p>
	* <p>Description:</p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-20 下午3:46:56
	* @version 1.0
	* @param payment
	* @return
	 */
	boolean savePaymentToMongoDB(Payment payment);
	
	/**
	 * 通过paymentId条件更新Payment于MongoDB
	* <p>Function: updateByPaymentIdFromMongoDB</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-21 上午10:52:52
	* @version 1.0
	* @return
	 */
	boolean updateByPaymentIdFromMongoDB(Payment payment);
	
	/**
	 * 利用mongoDB findAndModify 锁定流水并修改 原子性操作（只能针对一条数据 paymentId 必须有效）
	* <p>Function: findAndModifyPaymentFromMongoDB</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-21 上午11:26:12
	* @version 1.0
	* @param paymentOriginal 修改前的数据原始状态 必须含有paymentId
	* @param paymentTarget 修改后的数据状态
	* @return
	 */
	Payment findAndModifyPaymentFromMongoDB(Payment paymentOriginal,Payment paymentTarget);
	
	/**
	 * 用于锁定状态为2或者3的流水变迁到状态4
	* <p>Function: findAndModifyPaymentToStepFour</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-6-14 下午4:32:48
	* @version 1.0
	* @param paymentId
	* @return
	 */
	Payment findAndModifyPaymentToStepFour(String paymentId);
	
	/**
	 * 通过paymentId查询Payment
	* <p>Function: findByPaymentId</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-21 下午2:22:48
	* @version 1.0
	* @param paymentId
	* @return
	 */
	Payment findByPaymentIdFromMongoDB(String paymentId);
	
	/**
	 * 通过orderId查询出enable=true状态下的用户支付流水Payment
	* <p>Function: findEnableOfUserPayByOrderId</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-21 下午2:36:33
	* @version 1.0
	* @param orderId
	* @return
	 */
	List<Payment> findEnableOfUserPayByOrderIdFromMongoDB(String orderId);
	
	/**
	 * 通过orderId查询出所有enable=true状态下Payment
	* <p>Function: findAllEnableByOrderId</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-21 下午2:51:18
	* @version 1.0
	* @param orderId
	* @return
	 */
	List<Payment> findAllEnableByOrderIdFromMongoDB(String orderId);
	
	/**
	 * 从redis中获取客户端缓存信息 clientId必须有效
	* <p>Function: acquireClientFromRedis</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-20 下午4:39:36
	* @version 1.0
	* @param clientId
	* @return
	 */
	public Client acquireClientFromRedis(String clientId);
	
	/**
 	 * 
	* <p>Function: acquireClientPaymentChannelFromRedis</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-20 下午5:08:11
	* @version 1.0
	* @param clientId
	* @return
	 */
	public List<ClientPaymentChannel> acquireClientPaymentChannelFromRedis(String clientId);
	
	/**
	 * 添加paymentId到Redis中
	* <p>Function: addPaymentTimeFromRedis</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-22 上午10:40:27
	* @version 1.0
	* @param paymentId
	* @return
	 */
	public boolean addPaymentTimeFromRedis(String paymentId);
	
	/**
	 * 移除paymentId从Redis中
	* <p>Function: removePaymentTimeFromRedis</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-22 上午10:42:02
	* @version 1.0
	* @param paymentId
	* @return
	 */
	public boolean removePaymentTimeFromRedis(String paymentId);
	
	/**
	 * 移除缓存订单从Redis中
	* <p>Function: removeTimeOrderFromRedis</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-22 下午3:55:48
	* @version 1.0
	* @param clinetId
	* @param orderId
	* @return
	 */
	public boolean removeTimeOrderFromRedis(String clinetId, String orderId);
	
	/**
	 * 条件查询Payment
	* <p>Function: findByPaymentConditionFromMongoDB</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-26 下午4:57:54
	* @version 1.0
	* @param payment
	* @return
	 */
	public List<Payment> findByPaymentConditionFromMongoDB(Payment payment);
	
	
	public boolean updateOrderStatusOfSeckillingFromRedis(String reqId,boolean isPaiedSuccess);
}
