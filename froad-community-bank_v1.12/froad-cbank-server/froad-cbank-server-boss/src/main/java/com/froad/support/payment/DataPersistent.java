package com.froad.support.payment;


import java.util.List;

import com.froad.db.mongo.page.MongoPage;
import com.froad.po.Payment;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.RefundHistory;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.po.settlement.Settlement;
import com.mongodb.DBObject;


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
	 * 分页条件查询Payment表
	 * findByPaymentConditionByPay:(这里用一句话描述这个方法的作用).
	 *
	 * @author Zxy
	 * 2015-9-18 上午10:22:41
	 * @param mongoPage
	 * @param queryObj
	 * @return
	 *
	 */
	public MongoPage findByPaymentByConditionByPay(MongoPage mongoPage,DBObject queryDB);
	public Payment findPaymentByCondition(String paymentId);
	public OrderMongo findOrderByCondition(String orderId);
	public Settlement findSettlementByCondition(String paymentId);
	public RefundHistory findRefundByPaymentId(String paymentId);
	
	
	/**
	 * 保存结算记录
	 * saveSettlement:(这里用一句话描述这个方法的作用).
	 *
	 * @author Zxy
	 * 2015-9-22 下午2:52:42
	 * @param settlement
	 * @return
	 *
	 */
	public boolean saveSettlement(Settlement settlement);
	
	public boolean savePayment(Payment payment);
	
	/**
	 * 查询某笔订单下是否有已结算或者结算中的流水信息
	 * queryPaymentOfSettlementingOrSettlemented:(这里用一句话描述这个方法的作用).
	 *
	 * @author Zxy
	 * 2015-9-23 上午9:43:35
	 * @param orderId
	 * @return
	 *
	 */
	public Payment queryPaymentOfSettlementingOrSettlemented(String orderId);
	
	public boolean modifyPaymentToRequestException(String paymentId, String remark);
	public boolean modifyPaymentToRequestSuccess(String paymentId);
	public boolean modifyPaymentToPayAccessSuccess(String paymentId,String billNo,String remark);
	public boolean modifyPaymentToPayFailed(String paymentId,String resultCode,String resultDesc);
	public boolean findAndModifyPaymentToProcessed(String paymentId);
	public boolean modifySettlementToFailed(String settlementId);
	public boolean addPaymentTimeFromRedis(String paymentId);
	public List<SubOrderMongo> querySubOrderByOrderId(String orderId);
	
}
