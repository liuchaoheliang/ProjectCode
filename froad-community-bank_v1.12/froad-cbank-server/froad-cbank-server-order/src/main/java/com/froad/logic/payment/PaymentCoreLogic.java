package com.froad.logic.payment;

import com.froad.common.beans.ResultBean;
import com.froad.common.beans.payment.PayThriftVoBean;
import com.froad.po.mongo.OrderMongo;

/**
 * 对外核心支付
* <p>Function: PaymentCoreLogic</p>
* <p>Description: </p>
* @author zhaoxy@thankjava.com
* @date 2015-5-22 上午11:06:18
* @version 1.0
 */
public interface PaymentCoreLogic {

	/**
	 * 普通交易支付接口
	* <p>Function: doPayCoreForGeneralTrade</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-27 上午10:09:39
	* @version 1.0
	* @param payThriftVoBean
	* @return
	 */
	public ResultBean doPayCoreForGeneralTrade(PayThriftVoBean payThriftVoBean);
	
	/**
	 * OpenAPI通知（消费通知）
	* <p>Function: noticeOfConsume</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-27 上午10:11:58
	* @version 1.0
	* @param noticeXML
	* @return
	 */
	public ResultBean noticeOfConsume(String noticeXML);
	
	/**
	 * OpenAPI（退款通知）
	* <p>Function: noticeOfRefund</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-6-1 上午10:18:54
	* @version 1.0
	* @param noticeXML
	* @return
	 */
	public ResultBean noticeOfRefund(String noticeXML);
	
	/**
	 * 用于用户退款模块调用接口（该接口只用于资金逆转）
	* <p>Function: refundUserPaymentCapital</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-6-1 下午2:17:50
	* @version 1.0
	* @param orderId
	* @param refundPresentPoint
	* @param refundPayPoint
	* @param refundPayCash
	* @param memberCode
	* @return
	 */
	public ResultBean refundUserPaymentCapital(String orderId,Double refundPresentPoint, Double refundPayPoint,Double refundPayCash);
	
	/**
	 * 验证支付流水支付结果（定时任务调用）
	* <p>Function: verifyPaymentResult</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-6-7 上午11:27:52
	* @version 1.0
	* @param paymentId
	* @return
	 */
	public ResultBean verifyPaymentResult(String paymentId);
	
	/**
	 * 秒杀接口支付前完整校验
	* <p>Function: verifyCoreForSeckillingTrade</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-6-5 下午4:01:08
	* @version 1.0
	* @return
	 */
	@Deprecated
	public ResultBean verifyCoreForSeckillingTrade(OrderMongo order,PayThriftVoBean payThriftVoBean);
	
	/**
	 * 秒杀接口支付（调用方需要组合verifyCoreForSeckillingTrade接口，同步完成校验后才可以调用该支付）
	* <p>Function: doPayCoreForSeckillingTrade</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-6-5 下午4:01:24
	* @version 1.0
	* @return
	 */
	@Deprecated
	public ResultBean doPayCoreForSeckillingTrade(OrderMongo order,PayThriftVoBean payThriftVoBean);
	
	/**
	 * 取消支付中的订单（退还积分）
	* <p>Function: cancelPayingOrder</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-6-9 下午3:42:35
	* @version 1.0
	* @param order
	* @return
	 */
	public ResultBean cancelPayingOrderToRefundPaiedPoint(OrderMongo order);
	
	/**
	 * 将资金结算给商户
	 * settleToMerchantCapital:(这里用一句话描述这个方法的作用).
	 *
	 * @author Zxy
	 * 2015年9月14日 下午5:55:57
	 * @param orderId
	 * @param clientId
	 * @param settleCash
	 * @param merchantId
	 * @param merchantOutletId
	 * @return
	 *
	 */
	public ResultBean settleToMerchantCapital(String orderId,String clientId, double settleCash,String merchantId,String merchantOutletId);
	
}
