package com.froad.support.payment;

import com.froad.common.beans.ResultBean;
import com.froad.po.Payment;
import com.froad.po.mongo.OrderMongo;
import com.froad.thirdparty.dto.response.openapi.NoticeFroadApi;

/**
 * 现金交易的完善处理
* <p>Function: CashTradeComplete</p>
* <p>Description: </p>
* @author zhaoxy@thankjava.com
* @date 2015-5-26 下午3:39:14
* @version 1.0
 */
public interface CashTradeComplete {

	/**
	 * 用户支付流水通知
	* <p>Function: noticeOfUserPay</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-26 下午3:40:27
	* @version 1.0
	* @param payment
	* @param noticeFroadApi
	* @return
	 */
	ResultBean noticeOfUserPay(Payment payment,NoticeFroadApi noticeFroadApi,boolean isOrderClosed,OrderMongo order);
	
	/**
	 * 结算类型流水通知
	* <p>Function: noticeOfSettlePay</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-27 下午12:02:40
	* @version 1.0
	* @param payment
	* @param noticeFroadApi
	* @param isOrderClosed
	* @param order
	* @return
	 */
	ResultBean noticeOfSettlePay(Payment payment,NoticeFroadApi noticeFroadApi,OrderMongo order);
	
	/**
	 * 系统自动退现金通知
	* <p>Function: noticeOfAntoRefund</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-28 上午11:33:35
	* @version 1.0
	* @param payment
	* @param noticeFroadApi
	* @return
	 */
	ResultBean noticeOfAntoRefund(Payment payment,NoticeFroadApi noticeFroadApi);
	
	
	/**
	 * 用户退款通知
	* <p>Function: noticeOfUserRefund</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-28 上午11:34:15
	* @version 1.0
	* @param payment
	* @param noticeFroadApi
	* @return
	 */
	ResultBean noticeOfUserRefund(Payment payment,NoticeFroadApi noticeFroadApi);
}
