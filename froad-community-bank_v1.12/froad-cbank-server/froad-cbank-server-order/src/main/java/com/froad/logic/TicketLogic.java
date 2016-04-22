package com.froad.logic;

import com.froad.common.beans.ResultBean;
import com.froad.po.mongo.OrderMongo;

public interface TicketLogic {
	/**
	 * 生成券
	 * 
	 * @param orderId
	 * @return
	 */
	public ResultBean addTicket(OrderMongo order);
	
	/**
	 * 检查商品是否消费过
	 * 
	 * @param clientId
	 * @param memberCode
	 * @param subOrderId
	 * @param productId
	 * @return
	 */
	public boolean isProductConsumed(String clientId, Long memberCode, String subOrderId, String productId);
	
	/**
	 * 检查子订单是否存在券
	 * 
	 * @param clientId
	 * @param subOrderId
	 * @return
	 */
	public boolean isTicketExist(String clientId, String subOrderId);
}
