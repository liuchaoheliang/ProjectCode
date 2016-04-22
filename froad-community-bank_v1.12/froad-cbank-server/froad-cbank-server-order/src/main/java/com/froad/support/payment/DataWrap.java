package com.froad.support.payment;

import java.util.List;

import com.froad.po.Payment;

/**
 * 包装DataPersistent原子接口，提供含部分逻辑的数据操作
* <p>Function: DataWrap</p>
* <p>Description: </p>
* @author zhaoxy@thankjava.com
* @date 2015-5-21 下午4:58:57
* @version 1.0
 */
public interface DataWrap {
	
	/**
	 * 初始化保存支付流水记录
	* <p>Function: initializePayment</p>
	* <p>Description: 将自动默认以下参数 </br>
	* &nbsp;&nbsp;&nbsp;&nbsp;paymentId: 如果为空将自动生成 </br>
	* &nbsp;&nbsp;&nbsp;&nbsp;createTime: 将默认该方法体被调用执行时的系统时间 </br>
	* &nbsp;&nbsp;&nbsp;&nbsp;step: 1</br>
	* &nbsp;&nbsp;&nbsp;&nbsp;isEnable: true</br>
	* &nbsp;&nbsp;&nbsp;&nbsp;isDisposeException: 0</br>
	* </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-21 下午7:50:11
	* @version 1.0
	* @param payment
	* @return
	 */
	public boolean initializePayment(Payment payment);
	
	/**
	 * 修改流水为支付中状态 √
	* <p>Function: modifyPaymentPaying</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-21 下午5:02:06
	* @version 1.0
	* @param paymentId
	* @return
	 */
	public boolean modifyPaymentToPaying(String paymentId);
	
	/**
	 * 修改支付流水为请求发送成功（所有）√
	* <p>Function: modifyPaymentToRequestSuccess</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-22 上午10:17:14
	* @version 1.0
	* @param paymentId
	* @return
	 */
	public boolean modifyPaymentToRequestSuccess(String paymentId);
	
	/**
	 * 修改支付流水为支付失败（积分）√
	* <p>Function: modifyPaymentToRequestFailedd</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-22 上午10:21:58
	* @version 1.0
	* @param paymentId
	* @return
	 */
	public boolean modifyPaymentToPayFailed(String paymentId,String resultCode,String resultDesc,String remark);

	/**
	 * 修改支付请求为发送支付请求异常√
	 * modifyPaymentToRequestException:(这里用一句话描述这个方法的作用).
	 *
	 * @author Zxy
	 * 2015年9月11日 下午2:52:15
	 * @param paymentId
	 * @param remark
	 * @return
	 *
	 */
	public boolean modifyPaymentToRequestException(String paymentId,String remark);
	
	/**
	 * 修改支付流水为支付成功 （积分）√
	* <p>Function: modifyPaymentToPaySuccess</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-22 上午10:34:09
	* @version 1.0
	* @param paymentId
	* @return
	 */
	public boolean modifyPaymentToPaySuccess(String paymentId,String resultCode,String resultDesc,String pointPayNo,String remark);
	
	/**
	* 主动验证支付结果 支付失败
	* modifyPaymentToPayFailedOfVerify:(这里用一句话描述这个方法的作用).
	*
	* @author Zxy
	* 2015年9月14日 下午4:03:08
	* @param paymentId
	* @param verifyResultCode
	* @param verifyResultDesc
	* @param remark
	* @return
	*
	*/
	public boolean modifyPaymentToPayFailedOfVerify(String paymentId,String verifyResultCode,String verifyResultDesc,String remark,String billNo);
	
	/**
	* 主动验证支付结果 支付成功
	* modifyPaymentToPaySuccessOfVerify:(这里用一句话描述这个方法的作用).
	*
	* @author Zxy
	* 2015年9月14日 下午4:15:59
	* @param paymentId
	* @param verifyResultCode
	* @param verifyResultDesc
	* @param remark
	* @param billNo
	* @return
	*
	*/
	public boolean modifyPaymentToPaySuccessOfVerify(String paymentId, String verifyResultCode, String verifyResultDesc,String remark,String billNo);
	
	/**
	 * 修改支付流水为支付受理成功（现金）√
	* <p>Function: modifyPaymentToPayAccessSuccess</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-25 上午11:23:52
	* @version 1.0
	* @param paymentId
	* @param resultCode
	* @param resultDesc
	* @param billNo
	* @return
	 */
	public boolean modifyPaymentToPayAccessSuccess(String paymentId,String billNo,String remark);
	
	/**
	* 修改支付请求为支付失败（现金）√
	* modifyPaymentToPayAccessFailedFailed:(这里用一句话描述这个方法的作用).
	*
	* @author Zxy
	* 2015年9月14日 下午1:57:04
	* @param paymentId
	* @param resultCode
	* @param resultDesc
	* @param remark
	* @return
	*
	*/
	public boolean modifyPaymentToPayAccessFailed(String paymentId,String resultCode,String resultDesc,String remark);
	
	/**
	 * 修改支付流水为支付受理成功（现金退款）√
	* <p>Function: modifyPaymentToPayAccessSuccess</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-27 上午10:03:57
	* @version 1.0
	* @param paymentId
	* @param resultCode
	* @param resultDesc
	* @param remark
	* @return
	 */
	public boolean modifyPaymentToPayAccessSuccess(String paymentId,String resultCode,String resultDesc,String remark);
	
	/**
	* 获得通知结果成功的流水更改
	* modifyPaymentToPaySuccessOfNotice:(这里用一句话描述这个方法的作用).
	*
	* @author Zxy
	* 2015年9月11日 下午4:18:23
	* @return
	*
	*/
	public boolean modifyPaymentToPaySuccessOfNotice(String paymentId,String noticeResultCode,String noticeResultDesc,String remark,String billNo);
	
	/**
	* 获得通知结果失败的流水更改
	* modifyPaymentToPayFailedOfNotice:(这里用一句话描述这个方法的作用).
	*
	* @author Zxy
	* 2015年9月11日 下午4:24:06
	* @param paymentId
	* @param noticeResultCode
	* @param noticeResultDesc
	* @param remark
	* @return
	*
	*/
	public boolean modifyPaymentToPayFailedOfNotice(String paymentId,String noticeResultCode,String noticeResultDesc,String remark,String billNo);
	
	/**
	 * 修改支付流水为支付失败（积分支付失败|现金受理失败）
	* <p>Function: modifyPaymentToPayOrAccessFailed</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-22 上午10:34:25
	* @version 1.0
	* @param paymentId
	* @return
	 */
	public boolean modifyPaymentToPayFailed(String paymentId,String resultCode,String resultDesc);
	
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
	 * 更新秒杀订单缓存状态
	* <p>Function: modifyOrderStatusOfSeckillingFromRedis</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-6-7 上午11:50:15
	* @version 1.0
	* @param reqId
	* @param isPaiedSuccess true:支付成功 false:支付失败
	* @return
	 */
	public boolean modifyOrderStatusOfSeckillingFromRedis(String reqId,boolean isPaiedSuccess);
	
	
	/**
	 * 查询isEnable=true的用户支付流水
	* <p>Function: queryEnableOfUserPayByOrderIdFromMongoDB</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-22 下午2:07:19
	* @version 1.0
	* @param orderId
	* @return
	 */
	public List<Payment> queryEnableOfUserPayByOrderId(String orderId);
	
	/**
	 * 作废支付流水
	* <p>Function: modifyDisablePayment</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-22 下午2:12:47
	* @version 1.0
	* @param paymentId
	* @return
	 */
	public boolean modifyDisablePayment(String paymentId);
	
	/**
	 * 移除缓存的订单信息
	* <p>Function: removeTimeOrderFromRedis</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-22 下午3:56:56
	* @version 1.0
	* @param clinetId
	* @param orderId
	* @return
	 */
	public boolean removeTimeOrderFromRedis(String clinetId, String orderId);
	
	/**
	 * 根据paymentId查询Payment
	* <p>Function: queryByPaymentId</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-25 下午4:08:12
	* @version 1.0
	* @param paymentId
	* @return
	 */
	public Payment queryByPaymentId(String paymentId);
	
	/**
	 * 将原始状态的Payment锁定并修改成目标状态的Payment
	* <p>Function: lockAndModify</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-26 下午2:05:32
	* @version 1.0
	* @param paymentOriginal 原始状态（必含paymentId）
	* @param paymentTarget 目标状态
	* @return
	 */
	public Payment lockAndModify(Payment paymentOriginal,Payment paymentTarget);
	
	/**
	 * 通过paymentId更新为Entity中已有属性值
	* <p>Function: modifyPaymentById</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-26 下午4:00:19
	* @version 1.0
	* @param payment
	* @return
	 */
	public boolean modifyPaymentById(Payment payment);
	
	/**
	 * 查询用户完成的有效支付积分流水
	* <p>Function: queryPaymentOfUserPayTypePoint</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-26 下午4:41:09
	* @version 1.0
	* @return
	 */
	public Payment queryPaymentOfUserPaiedTypePoint(String orderId);
	
	/**
	 * 查询自动退积分成功的流水
	 * Function : queryPaymentOfAutoRefundTypePoint<br/>
	 * 2016年1月26日 下午2:06:14 <br/>
	 *  
	 * @author KaiweiXiang
	 * @param orderId
	 * @return
	 */
	public Payment queryPaymentOfAutoRefundTypePoint(String orderId);
	
	/**
	 * 查询用户待发起现金支付的流水记录
	* <p>Function: queryPaymentOfUserToPayTypeCash</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-6-7 下午2:59:53
	* @version 1.0
	* @param orderId
	* @return
	 */
	public Payment queryPaymentOfUserToPayTypeCash(String orderId);
	
	/**
	 * 查询用户完成的支付现金流水
	* <p>Function: queryPaymentOfUserPaiedTypeCash</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-6-1 上午10:41:46
	* @version 1.0
	* @param orderId
	* @return
	 */
	public Payment queryPaymentOfUserPaiedTypeCash(String orderId);
	
	/**
	 * 查询用户支付失败的现金流水（enable=true）
	* <p>Function: queryEnbalePaymentOfUserPayFailedTypeCash</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-6-17 上午10:27:27
	* @version 1.0
	* @param orderId
	* @return
	 */
	public Payment queryEnbalePaymentOfUserPayFailedTypeCash(String orderId);
	
	/**
	 * 查询用户获得赠送的方付通积分流水信息
	* <p>Function: queryPaymentOfPresentTypeFroadPoint</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-6-1 下午1:48:57
	* @version 1.0
	* @param orderId
	* @return
	 */
	public Payment queryPaymentOfPresentedTypeFroadPoint(String orderId);
	
	/**
	 * 用于锁定状态为2或者3的流水变迁到状态4
	* <p>Function: lockAndModifyPaymentToStepFour</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-6-14 下午4:46:49
	* @version 1.0
	* @param paymentId
	* @return
	 */
	public boolean lockAndModifyPaymentToStepFour(String paymentId);
	
	/**
	 * 查询一个退款中是否还有另外一条流水需要退款
	* <p>Function: queryToRefundPaymentOfPoint</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-7-5 下午12:36:42
	* @version 1.0
	* @return
	 */
	public Payment queryToRefundPaymentOfPoint(String clientId,String paymentId);
}
