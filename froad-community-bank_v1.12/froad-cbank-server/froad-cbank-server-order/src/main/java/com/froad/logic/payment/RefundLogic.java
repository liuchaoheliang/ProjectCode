package com.froad.logic.payment;

import com.froad.common.beans.ResultBean;
import com.froad.po.Payment;
import com.froad.po.mongo.OrderMongo;
import com.froad.util.refund.ProxyRefundType;

/**
 * 支付退款接口（逆向交易接口）
* <p>Function: RefundLogic</p>
* <p>Description: </p>
* @author zhaoxy@thankjava.com
* @date 2015-5-21 下午7:42:46
* @version 1.0
 */
public interface RefundLogic {
	
	/**
	 * 自动退还用户支付积分（按照原始支付流水进行自动退还）
	* <p>Function: autoRefundPointOfUserPay</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-21 下午7:44:39
	* @version 1.0
	* @param paymentId 原始支付流水记录id
	* @return
	 */
	ResultBean autoRefundPointOfUserPay(String paymentId);
	
	/**
	 * 自动退还用户现金 （按照原始支付流水）
	* <p>Function: autoRefundCashOfUserPay</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-27 上午9:17:53
	* @version 1.0
	* @param paymentId 原始支付流水记录id
	* @return
	 */
	ResultBean autoRefundCashOfUserPay(String paymentId);
	
	/**
	 * 根据支付流水进行组合积分退款（组合支付已成功退还现金后的后续积分退款）
	* <p>Function: userRefundPointOfUserPay</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-28 上午11:54:20
	* @version 1.0
	* @param payment 现金已退款的流水
	* @return
	 */
	ResultBean userRefundGoOnPointOfUserPay(Payment payment);
	
	/**
	 * 退还用户支付使用的资金（接口只用于退款模块的资金逆转）
	* <p>Function: refundUserPaymentCapital</p>
	* <p>Description: result.ResultCode 如果代表success 则表示 处理正常 </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-6-1 上午10:24:24
	* @version 1.0
	* @param orderId
	* @param rufundPresentPoint
	* @param rufundPayPoint
	* @param refundPayCash
	* @param memberCode
	* @return
	 */
	ResultBean refundUserPaymentCapital(String orderId,Double refundPresentPoint, Double refundPayPoint,Double refundPayCash);

	/**
	 * 退还支付中订单状态的已支付积分
	* <p>Function: cancelPayingOrderToRefundPaiedPoint</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-6-9 下午3:45:59
	* @version 1.0
	* @param order
	* @return
	 */
	ResultBean cancelPayingOrderToRefundPaiedPoint(OrderMongo order);
	
	/**
	 * 系统自动代理用户发起退还用户所有支付的流水（目前该接口用于VIP开通失败的自动退款）
	* <p>Function: proxyUserToAutoRefundAllUserPay</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015年8月5日 下午2:03:39
	* @version 1.0
	* @param order
	* @param isAuto 是否生成的流水为系统自动退款
	* @return
	 */
	public ResultBean proxyUserToAutoRefundAllUserPay(OrderMongo order,boolean isAuto,ProxyRefundType proxyRefundType,String refundReason);
}
