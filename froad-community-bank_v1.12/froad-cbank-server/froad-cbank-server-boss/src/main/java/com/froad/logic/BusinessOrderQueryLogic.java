package com.froad.logic;

import com.froad.db.mongo.page.MongoPage;
import com.froad.thrift.vo.Business.BusinessOrderListReq;
import com.froad.thrift.vo.Business.BusinessOrderPyamentInfoRes;
import com.froad.thrift.vo.Business.BusinessOrderRefundInfoVoRes;
import com.froad.thrift.vo.Business.BusinessOrderTradeInfoVoRes;

public interface BusinessOrderQueryLogic {

	/**
	 * 查询运营订单列表
	 * @param req
	 * @return
	 */
	public MongoPage getBusinessOrderList(BusinessOrderListReq req);
	
	/**
	 * 查询运营订单支付信息
	 * @param clientId
	 * @param subOrderId
	 * @return
	 */
	public BusinessOrderPyamentInfoRes getBusinessOrderPaymentInfo(String clientId, String subOrderId);
	
	/**
	 * 查询运营订单交易信息
	 * @param clientId
	 * @param subOrderId
	 * @return
	 */
	public BusinessOrderTradeInfoVoRes getBusinessOrderTradeInfo(String clientId, String subOrderId);
	
	/**
	 * 查询运营订单退款信息
	 * @param clientId
	 * @param subOrderId
	 * @return
	 */
	public BusinessOrderRefundInfoVoRes getBusinessOrderRefundInfo(String clientId, String subOrderId);
}
