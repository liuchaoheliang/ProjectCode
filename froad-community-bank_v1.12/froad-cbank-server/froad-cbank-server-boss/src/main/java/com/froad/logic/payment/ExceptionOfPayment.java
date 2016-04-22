/**
 * Project Name:Froad Cbank Server Boss
 * File Name:ExceptionOfPayment.java
 * Package Name:com.froad.logic
 * Date:2015-9-18上午9:58:02
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.logic.payment;

import com.froad.common.beans.ResultBean;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.payment.BossPaymentDetialVo;
import com.froad.thrift.vo.payment.BossPaymentPage;
import com.froad.thrift.vo.payment.BossQueryConditionVo;


/**
 * ClassName:ExceptionOfPayment
 * Reason:	 TODO ADD REASON.
 * Date:     2015-9-18 上午9:58:02
 * @author   Zxy
 * @version  
 * @see 	 
 */
public interface ExceptionOfPayment {

	
	/**
	 * 查询结算失败异常
	 * queryOfSettle:(这里用一句话描述这个方法的作用).
	 *
	 * @author Zxy
	 * 2015-9-18 上午11:33:16
	 * @param condition
	 * @return
	 *
	 */
	BossPaymentPage queryOfSettleFailed(BossQueryConditionVo condition);
	
	/**
	 * 查询退款失败的异常列表
	 * queryOfRefundFailed:(这里用一句话描述这个方法的作用).
	 *
	 * @author Zxy
	 * 2015-10-12 上午11:42:26
	 * @param condition
	 * @return
	 *
	 */
	BossPaymentPage queryOfRefundFailed(BossQueryConditionVo condition);
	
	/**
	 * 查询支付中异常自动退款积分失败
	 * queryOfPayRefundPointFailed:(这里用一句话描述这个方法的作用).
	 *
	 * @author Zxy
	 * 2015-10-19 上午11:24:23
	 * @param condition
	 * @return
	 *
	 */
	BossPaymentPage queryOfPayRefundPointFailed(BossQueryConditionVo condition);

	
	BossPaymentPage queryOfPayRefundCashFailed(BossQueryConditionVo condition);
	
	/**
	 * 
	 * queryOfSettle:结算异常订单详情
	 *
	 * @author vania
	 * 2015年9月18日 下午3:37:41
	 * @param orderId
	 * @return
	 *
	 */
	BossPaymentDetialVo queryOfSettleDetial(String orderId);
	
	/**
	 * 查询退款详情
	 * queryOfRefundDetial:(这里用一句话描述这个方法的作用).
	 *
	 * @author Zxy
	 * 2015-10-13 上午10:56:48
	 * @param paymentId
	 * @return
	 *
	 */
	BossPaymentDetialVo queryOfRefundDetial(String paymentId);

	BossPaymentDetialVo queryOfPayRefundPointFailedDetial(String paymentId);
	
	BossPaymentDetialVo queryOfPayRefundCashFailedDetial(String paymentId);
	
	/**
	 * 再次发起结算请求
	 * retryOfSettle:(这里用一句话描述这个方法的作用).
	 *
	 * @author Zxy
	 * 2015-9-22 下午2:20:34
	 * @param paymentId
	 * @return
	 *
	 */
	ResultBean retryOfSettle(String paymentId);
	
	/**
	 * 退还现金（支付异常应该退还的却还未退还）
	 * refundOfCash:(这里用一句话描述这个方法的作用).
	 *
	 * @author Zxy
	 * 2015-10-20 下午2:57:26
	 * @param paymentId
	 * @return
	 *
	 */
	ResultVo refundOfCash(String paymentId);
	
	/**
	 * 退还积分（支付异常应该退还的却还未退还）
	 * refundOfPoint:(这里用一句话描述这个方法的作用).
	 *
	 * @author Zxy
	 * 2015-10-20 下午2:57:52
	 * @param paymentId
	 * @return
	 *
	 */
	ResultVo refundOfPoint(String paymentId);
	
	ResultVo retryOfRefund(String refundId);
	
}
