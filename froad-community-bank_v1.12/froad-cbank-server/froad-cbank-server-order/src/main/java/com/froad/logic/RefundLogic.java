package com.froad.logic;

import java.util.Map;

public interface RefundLogic {
	
	/**
	 * 退款审批-更新退款支付记录状态
	 * 
	 * @param paymentId
	 * @param isSuccess
	 * @return
	 */
	public boolean updateApprovalResult(Map<String, Boolean> noticeMap);
	
	/**
	 * 更加大订单ID获取每个商品可退款的数量
	 * @param orderId:大订单ID
	 * @param clientId
	 */
	public Map<String, Integer> getCanRefundProductList(String clientId, String orderId);
	
	/**
	 * 获取已退商品数量
	 * 
	 * @param orderId
	 * @return
	 */
	public Map<String, Integer> getRefundedProduct(String orderId);
	
	/**
	 * 获取退款详细信息
	* <p>Function: getRefundDetails</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-6-15 上午11:22:57
	* @version 1.0
	* @param clientId
	* @param orderId
	* @param productId
	 */
	public int getRefundDetails(String clientId,String orderId,String productId);
}
