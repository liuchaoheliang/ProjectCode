package com.froad.support;

import java.util.List;

import com.froad.db.mongo.page.MongoPage;
import com.froad.po.refund.RefundHistory;
import com.froad.po.refund.RefundPaymentInfo;
import com.mongodb.DBObject;

public interface RefundSupport {
	/**
	 * 根据退款ID获取退款详情
	 * 
	 * @param refundId
	 * @return
	 */
	public RefundHistory getByRefundId(String refundId);
	
	/**
	 * 根据订单号获取退款列表
	 * 
	 * @param orderId
	 * @return
	 */
	public List<RefundHistory> getListByOrderId(String orderId);
	
	/**
	 * 根据开始结束时间(startDate, endDate)查找退款记录
	 * 
	 * @param memberCode
	 * @param pageNumber
	 * @param pageCount
	 * @return
	 */
	public MongoPage getPageByMemberCode(long memberCode, int pageNumber, int pageCount);
	
	/**
	 * 更新退款状态
	 * 
	 * @param refundId
	 * @param refundState
	 * @param payInfoList
	 * @return
	 */
	public void updateRefundState(String refundId, String refundState, List<RefundPaymentInfo> payInfoList);
	
	/**
	 * 更新退款状态
	 * @param refundId
	 * @param oldRefundState
	 * @param newRefundState
	 * @param subOrderType
	 * @return
	 */
	public RefundHistory updateRefundState(String refundId, String oldRefundState, String newRefundState, String subOrderType);
	
	/**
	 * 插入退款记录
	 * 
	 * @param refundHis
	 */
	public void insertRefundHistory(RefundHistory refundHis);
	
	/**
	 * 根据订单获取退款记录
	 * 
	 * @param orderId
	 * @return
	 */
	public List<RefundHistory> findRefundHistoryList(String orderId);
	
	/**
	 * 根据子订单ID以及产品ID获取退款记录
	 * @param subOrderid
	 * @param productId
	 * @return
	 */
	public RefundHistory findBySubOrderIdAndProductId(String subOrderid, String productId);
	
	/**
	 * 根据子订单ID以及产品ID获取退款记录
	 * @param subOrderid
	 * @param productId
	 * @return
	 */
	public List<RefundHistory> findRefundListBySubOrderIdAndProductId(String subOrderid, String productId);
	
	/**
	 * 根据DBObject获取退款列表
	 * 
	 * @param queryObj
	 * @return
	 */
	public List<RefundHistory> findListByDBObject(DBObject queryObj);
	
	/**
	 * 分页查找
	 * 
	 * @param queryObj
	 * @param mongoPage
	 * @return
	 */
	public MongoPage findPageByDBObject(DBObject queryObj, MongoPage mongoPage);
	
	/**
	 * 查找并更新
	 * 
	 * @param queryObj
	 * @param updateObj
	 * @return
	 */
	public RefundHistory findAndModifyByDBObject(DBObject queryObj, DBObject updateObj);
	
	/**
	 * find refund payment list by payment id
	 * 
	 * @param clientId
	 * @param paymentId
	 * @return
	 */
	public List<RefundPaymentInfo> findRefundPaymentListByPaymentId(String clientId, String paymentId);
	
}
