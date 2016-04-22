package com.froad.logic;

import java.util.List;

import com.froad.common.beans.PayTempBean;
import com.froad.common.beans.ResultBean;
import com.froad.po.Payment;
import com.froad.po.mongo.OrderMongo;


/**
 * Copyright © 2015 F-Road. All rights reserved.
 * @ClassName: PaymentLogic
 * @Description: 支付逻辑
 * @Author: zhaoxiaoyao@f-road.com.cn
 * @Date: 2015年3月17日 上午10:34:27
 */
@Deprecated
public interface PaymentLogic {

    /**order  订单状态是否可以允许支付1:order订单状态是否可以允许支付,2:支付记录是否正常可支付,3:支付方式是否正常,4:支付金额是否足够,5:*要知道是否有历史支付记录，如果在本次预支付校验通过的情况，在此处把历史记录置为无效
     * <br>Copyright © 2015 F-Road. All rights reserved.
     * @Title: validationAllBeforePayOrder
     * @Description: 支付前的聚合校验（已通过基本参数校验，参数存在性有效，真实性需要校验）
     * @Author: zhaoxiaoyao@f-road.com.cn
     * @param payTempBean
     * @return
     * @Return: ResultBean
     */                     
	public ResultBean validationAllBeforePayOrder(PayTempBean payTempBean,OrderMongo order);
	
	/**
	 * 校验所传的手机号码是不是有效的贴膜卡手机号
	* <p>Function: verifyFoilCardNum</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-14 上午9:52:09
	* @version 1.0
	* @param mobileNum
	* @return
	 */
	public ResultBean verifyFoilCardNum(String clientId,String mobileNum);
	
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: doPay
	 * @Description: 支付具体金额
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param order
	 * @return
	 * @Return: ResultBean
	 */
	
	//-----------------------------------------------------------------------------
	// 根据支付订单-------> 抽出支付记录
	//
	//  				point       ↔      yes : (↓ or success) | no : refund point  (fail)
	// 支付支付记录      |     
	//					cash               请求推送  →    yes : success  |  no : ↑
	//-----------------------------------------------------------------------------
	public ResultBean doPay(List<Payment> payList,String token);
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: doPayRedress
	 * @Description: 成功支付完毕的后续逻辑
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param order
	 * @param payTempBean
	 * @return
	 * @Return: ResultBean
	 */
	//  是否自动赠送方付通积分
	//  --根据子订单类型  - 团购 预售 面对面
	// 
	//
	// * 面对面涉及立即转账到商户
	public ResultBean doPayRedress(OrderMongo order);
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: doPayResressFailed
	 * @Description: 处理支付失败的后续逻辑
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @return
	 * @Return: ResultBean
	 */
	public ResultBean doPayResressFailed(OrderMongo order);
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: combinePaymentNotice
	 * @Description: 获取到合并订单通知处理逻辑
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param xmlString
	 * @return
	 * @Return: ResultBean
	 */
	public ResultBean combinePaymentNotice(String xmlString);
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: refundPyamentNotice
	 * @Description: 获取到退款通知逻辑处理
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param xmlsString
	 * @return
	 * @Return: ResultBean
	 */
	public ResultBean refundPyamentNotice(String xmlString);
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: verifyPaymentResultForTask
	 * @Description: 定时任务主动查询订单结果
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param paymentId
	 * @param clientType
	 * @return
	 * @Return: String
	 */
	public ResultBean verifyPaymentResultForTask(String paymentId);
//	
//	/**
//	 * Copyright © 2015 F-Road. All rights reserved.
//	 * @Title: verifyRefundResultForTask
//	 * @Description: 定时任务主动查询退款结果
//	 * @Author: zhaoxiaoyao@f-road.com.cn
//	 * @param paymentId
//	 * @param clientType
//	 * @return
//	 * @Return: ResultBean
//	 */
//	public ResultBean verifyRefundResultForTask(String paymentId,String clientType);
//	
//	/**
//	 * Copyright © 2015 F-Road. All rights reserved.
//	 * @Title: verifySettleResultForTask
//	 * @Description: 定时任务主动查询结算结果
//	 * @Author: zhaoxiaoyao@f-road.com.cn
//	 * @param paymentId
//	 * @return
//	 * @Return: ResultBean
//	 */
//	public ResultBean verifySettleResultForTask(String paymentId);
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: refundMemberPaymentData
	 * @Description: 退款  由用户已成功付款的流水中进行 资金返转  （* 注意后续规定退款的校验由退款模块把关，该接口主要实现资金逆转流水记录）
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param orderId 订单id
	 * @param rufundPresentPoint 退款的赠送积分值 null 或 不大于 0 视为不退该种类型积分值
	 * @param rufundPayPoint 退款的支付积分值  null 或 不大于 0 视为不退该种类型积分值
	 * @param refundPayCash 退款的支付现金值  null 或 不大于 0 视为不退该种类型积分值
	 * @return
	 * @Return: ResultBean
	 */
	public ResultBean refundMemberPaymentData(String orderId,Double rufundPresentPoint,Double rufundPayPoint,Double refundPayCash,long memberCode);

	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: settleMerchantPaymentData
	 * @Description: 向商户发起结算资金流动，生成流水并实际由 方付通  ----> 商户门店下帐号
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @return
	 * @Return: ResultBean
	 */
	public ResultBean settleMerchantPaymentData(String orderId, double sellteCash,String clientId,String merchantId,String merchantOutletId);

    /**
    * 补全订单支付相关信息
    * <p>Function: completeOrderPaymentInfo</p>
    * <p>Description: </p>
    * @author caishican@f-road.com.cn
    * @date 2015年4月10日 下午1:38:05
    * @version 1.0
    * @param tempBean
    * @return
    * ResultBean
    */

    public ResultBean completeOrderPaymentInfo(PayTempBean tempBean, OrderMongo order);

    /**
     * 操作库存
    * <p>Function: checkAndOperateStore</p>
    * <p>Description: </p>
    * @author zhaoxy@thankjava.com
    * @date 2015-5-4 上午10:54:55
    * @version 1.0
    * @param order
    * @param freeStore
    * @return
     */
    public ResultBean checkAndOperateStore(OrderMongo order,boolean freeStore);
    
    /**
     * 验证秒杀类型订单
    * <p>Function: doPaySeckillingOrderValidationAll</p>
    * <p>Description: </p>
    * @author zhaoxy@thankjava.com
    * @date 2015-5-4 上午11:00:08
    * @version 1.0
    * @param poTemp
    * @return
     */
    public ResultBean doPaySeckillingOrderValidationAll(OrderMongo order,PayTempBean tempBean);
    
    /**
	 * 对接秒杀支付接口
	* <p>Function: doPayOrderTypeSeckilling</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-4 上午10:39:18
	* @version 1.0
	* @param order
	* @param poTemp
	* @return
	 */
	public ResultBean doPayOrderTypeSeckilling(OrderMongo order,PayTempBean tempBean);
	
	/**
	 * 支付中的关单处理
	* <p>Function: cancelPayingOrder</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-19 上午11:39:06
	* @version 1.0
	* @return
	 */
	public ResultBean cancelPayingOrder(OrderMongo orderMongo);

}
