package com.froad.logic.payment;

import com.froad.common.beans.ResultBean;
import com.froad.common.beans.payment.PayThriftVoBean;
import com.froad.po.mongo.OrderMongo;

/**
 * 支付辅助接口
* <p>Function: AssistLogic</p>
* <p>Description: </p>
* @author zhaoxy@thankjava.com
* @date 2015-5-22 上午11:27:35
* @version 1.0
 */
public interface AssistLogic {
	
	/**
	 * 支付前的全部校验
	* <p>Function: validationAllBeforePay</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-22 上午11:27:25
	* @version 1.0
	* @param order
	* @param PaythriftVoBean
	* @return
	 */
	ResultBean validationAllBeforePay(OrderMongo order,PayThriftVoBean payThriftVoBean);
	
	/**
	 * 补全订单支付相关的参数
	* <p>Function: completeOrderOfPaymentInfo</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-25 上午10:03:00
	* @version 1.0
	* @return
	 */
	ResultBean completeOrderOfPaymentInfo(OrderMongo order,PayThriftVoBean payThriftVoBean);
	
	/**
	 * 补全订单支付相关参数（返回补全的Order对象，不持久化Order）
	* <p>Function: completeOrderOfPaymentInfoForSeckilling</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-6-5 下午4:08:51
	* @version 1.0
	* @param order
	* @param payThriftVoBean
	* @return
	 */
	OrderMongo completeOrderOfPaymentInfoForSeckilling(OrderMongo order,PayThriftVoBean payThriftVoBean);
	
	/**
	 * 保存并禁用历史支付流水
	* <p>Function: savePaymentAndDisableOldPaymentOfUserPay</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-22 下午2:04:40
	* @version 1.0
	* @param paythriftVoBean
	* @return
	 */
	ResultBean savePaymentAndDisableOldPaymentOfUserPay(PayThriftVoBean payThriftVoBean,Object[] dataValidationRes);
	
	/**
	 * 普通商品操作库存
	* <p>Function: checkAndOperateStore</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-25 上午10:24:21
	* @version 1.0
	* @param order
	* @param isFreeStore true : 归还库存  false : 扣除库存
	* @return
	 */
	ResultBean checkAndOperateStore(OrderMongo order,boolean isFreeStore);

	/**
	 * 归还秒杀商品库存
	* <p>Function: returnStoreOfSeckilling</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-6-7 上午9:22:07
	* @version 1.0
	* @param order
	* @return
	 */
	ResultBean returnStoreOfSeckilling(OrderMongo order);
	
	/**
	 * 结算资金到商户
	 * settleToMerchantCapital:(这里用一句话描述这个方法的作用).
	 *
	 * @author Zxy
	 * 2015年9月14日 下午5:57:10
	 * @param orderId
	 * @param clientId
	 * @param settleCash
	 * @param merchantId
	 * @param merchantOutletId
	 * @return
	 *
	 */
	ResultBean settleToMerchantCapital(String orderId, String clientId, double settleCash, String merchantId,String merchantOutletId);
}
