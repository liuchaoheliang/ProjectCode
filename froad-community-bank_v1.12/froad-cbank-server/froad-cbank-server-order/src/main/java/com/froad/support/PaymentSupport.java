package com.froad.support;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.froad.common.beans.PaymentPoTemp;
import com.froad.common.beans.ResultBean;
import com.froad.po.ClientPaymentChannel;
import com.froad.po.Payment;
import com.froad.po.mongo.OrderMongo;
import com.froad.thirdparty.dto.request.openapi.OpenApiRes;
import com.froad.thirdparty.dto.request.points.PointsReq.QueryOrderType;
import com.froad.thirdparty.dto.response.openapi.NoticeFroadApi;
import com.froad.thirdparty.dto.response.points.PointsRes;
import com.pay.user.dto.UserSpecDto;

/**
 * Copyright © 2015 F-Road. All rights reserved.
 * @ClassName: PaymentSupport
 * @Description: 引接数据层，对接逻辑层 ----> 使逻辑层与数据层分离  数据层（第三方）在此处汇聚 含redis和mongo、mysql
 * @Author: zhaoxiaoyao@f-road.com.cn
 * @Date: 2015年3月21日 下午4:50:30
 */
@Deprecated
public interface PaymentSupport {
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: getPaymentChannelSetFromRedis
	 * @Description: 通过客户端获取所支持的payemnt_channel_id
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param clientId
	 * @return
	 * @Return: List<Map<String, String>>
	 */
	public List<ClientPaymentChannel> getPaymentChannelSetFromRedis(String clientId);
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: updatePaymentAndLock
	 * @Description: findAndModif 锁定更新数据
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param payment
	 * @return
	 * @Return: boolean
	 */
	public Payment updatePaymentAndLock(Payment paymentOriginal,Payment paymentTarget);
	
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: updatePayment
	 * @Description: 普通更改接口
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param paymentTarget
	 * @return
	 * @Return: boolean
	 */
	public boolean updatePayment(Payment paymentTarget);
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: findEnablePaymentsByOrderId
	 * @Description: 获取某笔订单已生成的可用支付信息记录(只限用户支付流水)
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param orderId
	 * @return
	 * @Return: List<Payment>
	 */
	public List<Payment> findEnablePaymentsOfUserPayByOrderId(String orderId);
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: findPaymentByPaymentId
	 * @Description: 通过Payment获取支付记录
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param paymentId
	 * @return
	 * @Return: Payment
	 */
	public Payment findPaymentByPaymentId(String paymentId);
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: addInitPaymentInfo
	 * @Description: 初始化支付记录
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param clientId
	 * @param orderId
	 * @param payType
	 * @param payValue
	 * @param payOrgNo
	 * @param cashType
	 * @return
	 * @Return: boolean
	 */
	public boolean addPaymentToMongo(PaymentPoTemp paymentPoTemp);
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: removeRedisOfPayment
	 * @Description: 删除缓存中的支付流水集合中的某条数据
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param paymentId
	 * @Return: void
	 */
	public boolean removeTimePayment(String paymentId);
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: addTimePayment
	 * @Description: 创建支付流水缓存集合
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @return
	 * @Return: boolean
	 */
	public boolean addTimePayment(String paymentId);
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: findEnablePaymentsByOrderId
	 * @Description: 查询有效的支付信息记录
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param orderId
	 * @return
	 * @Return: List<Payment>
	 */
	public List<Payment> findAllEnablePaymentsByOrderId(String orderId);
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: autoRefundPoints
	 * @Description: 自动退款积分
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param payment
	 * @return
	 * @throws Exception
	 * @Return: ResultBean
	 */
	ResultBean autoRefundPoints(Payment payment);
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: userRefundPoints
	 * @Description: 用户进行积分退款操作----该接口是用户现金通知完毕继续退款积分
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param payment
	 * @return
	 * @Return: ResultBean
	 */
	ResultBean userGoOnRefundPoints(Payment payment);
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: autoPresentPoints
	 * @Description: 自动赠送方付通积分
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @return
	 * @Return: ResultBean
	 */
	public ResultBean autoPresentFroadPoints(Long memberCode, Integer givePoints,String clientId,String orderId);

	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: removeTimeOrder
	 * @Description: 从cbbank:time_order中移除order
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param clientId
	 * @param orderId
	 * @return
	 * @Return: boolean
	 */
	public boolean removeTimeOrder(String clientId,String orderId);
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: refundPresentFroadPoints
	 * @Description: 退还用户已赠送的积分数目
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param paymentTemp
	 * @return
	 * @Return: ResultBean
	 */
	public ResultBean refundPresentFroadPoints(PaymentPoTemp paymentPoTemp,String loginID);
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: autoRefundCash
	 * @Description: 现金退款并保存积分退款申请记录（如果有）
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param payment
	 * @return
	 * @Return: ResultBean
	 */
	public ResultBean refundCashaAndSavePointInfo(PaymentPoTemp paymentCash,PaymentPoTemp PaymentPoint,Double paymentCashHis,String refundTypeCode);
	
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: redundPointOfUserPay
	 * @Description: 退款用户积分支付流水
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param paymentPoint
	 * @return
	 * @Return: ResultBean
	 */
	public ResultBean refundPointOfUserPay(PaymentPoTemp paymentPoint);
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: settleCashFromFroadToMerchant
	 * @Description: 方付通现金转入指定商户/门店资金
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param paymentPoTemp
	 * @return
	 * @Return: ResultBean
	 */
	public ResultBean transferCashFromFroadToMerchant(PaymentPoTemp paymentPoTemp,String merchantId,String merchantOutletId);
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: doPayCore
	 * @Description: 对支付记录进行支付
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param paymentArray
	 * @return
	 * @Return: ResultBean
	 */
	public ResultBean doPayCore(Payment[] paymentArray,String token);
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: queryByMemberCode
	 * @Description: 连接账户平台通过memeberCode查询用户
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param memberCode
	 * @return
	 * @Return: UserSpecDto
	 */
	public UserSpecDto queryByMemberCode(Long memberCode);
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: combineXMLToEntity
	 * @Description: 将合并订单通知转化为实体
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param xmlString
	 * @return
	 * @Return: NoticeFroadApi
	 */
	public NoticeFroadApi combineXMLToEntity(String xmlString);
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: refundXMLToEntity
	 * @Description: 将退款通知转化为实体
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param xmlString
	 * @return
	 * @Return: NoticeFroadApi
	 */
	public NoticeFroadApi refundXMLToEntity(String xmlString);
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: queryOpenAPIOrderState
	 * @Description: 主动确认OpenAPI的支付异常
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param paymentId
	 * @param orderType
	 * @param clientType
	 * @param clientId
	 * @return
	 * @Return: OpenApiRes
	 */
	OpenApiRes queryOpenAPIPaymentState(String paymentId, Date createTime,String orderType, String clientType, String clientId) throws Exception;

	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: verifyFoilCardNum
	 * @Description: 检查对应的手机是否是手机银行贴膜卡手机号
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param paymentOrgNo
	 * @param mobileNum
	 * @return
	 * @Return: ResultBean
	 */
	ResultBean verifyFoilCardNum(String clientId,String paymentOrgNo,String mobileNum);
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: queryBankPointOfBankNo
	 * @Description: 通过银行卡号查询对应的银行卡积分
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param clientId
	 * @param bankNo
	 * @return
	 * @Return: ResultBean
	 */
	public ResultBean queryBankPointOfBankNo(String clientId,String bankNo);

	/**Copyright © 2015 F-Road. All rights reserved.
	 * @Title: updateMerchantMonthCount
	 * @Description: 累加商户月销售数据
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param order
	 * @return
	 * @Return: ResultBean
	 */
	public ResultBean updateMerchantMonthCountForPaySuccess(OrderMongo order);

    /**
    * 查询订单支付渠道
    * <p>Function: queryOrderPaymentChannel</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月22日 下午7:10:25
    * @version 1.0
    * @param orderId
    * @return
    * ResultBean
    */
    String queryOrderPaymentChannel(String orderId);
    
    /**
     * Copyright © 2015 F-Road. All rights reserved.
     * @Title: queryPointPaymentState
     * @Description: 查询积分支付结果状态
     * @Author: zhaoxiaoyao@f-road.com.cn
     * @return
     * @throws Exception 
     * @Return: PointsRes
     */
    PointsRes queryPointPaymentState(String paymentId,QueryOrderType orderType,String clientId) throws Exception;

	/**
	 * 退款后调用，扣除用户销售月统计
	 * @param clientId
	 * @param merchantId
	 * @param orderType
	 * @param createTime
	 * @param money
	 * @return
	 */
	ResultBean updateMerchantMonthCountForRefund(String clientId,
			String merchantId, String subOrderType, Long createTime,
			Integer money,int refundCount);
 
	/**
	 * 系统自动退还现金
	* <p>Function: systemAutoRefundCash</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-19 下午3:21:59
	* @version 1.0
	* @param payment
	* @return
	 */
	ResultBean systemAutoRefundCash(Payment payment);
}
