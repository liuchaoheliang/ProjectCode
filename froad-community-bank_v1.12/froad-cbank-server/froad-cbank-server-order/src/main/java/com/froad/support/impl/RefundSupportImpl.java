package com.froad.support.impl;

import java.util.ArrayList;
import java.util.List;

import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mongo.page.MongoPage;
import com.froad.enums.FieldMapping;
import com.froad.po.refund.RefundHistory;
import com.froad.po.refund.RefundPaymentInfo;
import com.froad.support.RefundSupport;
import com.froad.util.JSonUtil;
import com.froad.util.MongoTableName;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;
import com.mongodb.util.JSON;

public class RefundSupportImpl implements RefundSupport {
	
	/**
	 * Mongo collection name
	 */
	private String COLLECTION_REFUND_HISTORY = MongoTableName.CB_ORDER_REFUNDS;
	
	MongoManager mongoManager = null;
	
	/**
	 * Constructor
	 */
	public RefundSupportImpl() {
		mongoManager = new MongoManager();
	}

	@Override
	public RefundHistory getByRefundId(String refundId) {
		DBObject queryObj = null;
		RefundHistory refundHis = null;
		
		queryObj = new BasicDBObject();
		queryObj.put(FieldMapping.ID.getMongoField(), refundId);
		refundHis = mongoManager.findOne(queryObj, COLLECTION_REFUND_HISTORY, RefundHistory.class);
		
		return refundHis;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RefundHistory> getListByOrderId(String orderId) {
		DBObject queryObj = null;
		List<RefundHistory> refundHisList = null;
		
		queryObj = new BasicDBObject();
		queryObj.put(FieldMapping.ORDER_ID.getMongoField(), orderId);
		refundHisList = (List<RefundHistory>) mongoManager.findAll(queryObj, COLLECTION_REFUND_HISTORY, RefundHistory.class);
		
		return refundHisList;
	}

	@Override
	public MongoPage getPageByMemberCode(long memberCode, int pageNumber,
			int pageCount) {
		DBObject queryObj = null;
		MongoPage mongoPage = null;
		
		queryObj = new BasicDBObject();
		queryObj.put(FieldMapping.MEMBER_CODE.getMongoField(), String.valueOf(memberCode));
		mongoPage = new MongoPage(pageNumber, pageCount);
		
		mongoPage = mongoManager.findByPage(mongoPage, queryObj, COLLECTION_REFUND_HISTORY, RefundHistory.class);
		
		return mongoPage;
	}
	
	
	public RefundHistory updateRefundState(String refundId, String oldRefundState, String newRefundState, String subOrderType) {
		DBObject queryObj = null;
		DBObject valueObj = null;
		
		queryObj = new BasicDBObject();
		queryObj.put(FieldMapping.ID.getMongoField(), refundId);
		queryObj.put(FieldMapping.REFUND_STATE.getMongoField(), oldRefundState);
		queryObj.put(
				new StringBuffer(FieldMapping.SHOPPING_INFO.getMongoField())
						.append(".").append(FieldMapping.TYPE.getMongoField())
						.toString(), subOrderType);
		
		valueObj = new BasicDBObject();
		valueObj.put(FieldMapping.REFUND_STATE.getMongoField(), newRefundState);
		RefundHistory refund = mongoManager.findAndModify(valueObj, queryObj, COLLECTION_REFUND_HISTORY, false, RefundHistory.class);
		return refund;
	}

	@Override
	public void updateRefundState(String refundId, String refundState,
			List<RefundPaymentInfo> payInfoList) {
		DBObject queryObj = null;
		DBObject valueObj = null;
		
		queryObj = new BasicDBObject();
		queryObj.put(FieldMapping.ID.getMongoField(), refundId);
		valueObj = new BasicDBObject();
		valueObj.put(FieldMapping.REFUND_STATE.getMongoField(), refundState);
		if (null != payInfoList && payInfoList.size() > 0){
			valueObj.put(FieldMapping.PAYMENT_INFO.getMongoField(), JSON.parse(JSonUtil.toJSonString(payInfoList)));
		}
		mongoManager.update(valueObj, queryObj, COLLECTION_REFUND_HISTORY, "$set");
	}

	@Override
	public void insertRefundHistory(RefundHistory refundHis) {
		mongoManager.add(refundHis, COLLECTION_REFUND_HISTORY);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RefundHistory> findRefundHistoryList(String orderId) {
		DBObject queryObj = null;
		List<RefundHistory> refundHisList = null;
		
		queryObj = new BasicDBObject();
		queryObj.put(FieldMapping.ORDER_ID.getMongoField(), orderId);
		
		refundHisList = (List<RefundHistory>) mongoManager.findAll(queryObj, COLLECTION_REFUND_HISTORY, RefundHistory.class);
		
		return refundHisList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RefundHistory> findRefundListBySubOrderIdAndProductId(String subOrderid, String productId) {
		DBObject queryObj = null;
		List<RefundHistory> refundHisList = null;
		queryObj = new BasicDBObject();
		queryObj.put(new StringBuffer(FieldMapping.SHOPPING_INFO.getMongoField())
		.append(".").append(FieldMapping.SUB_ORDER_ID.getMongoField())
		.toString(), subOrderid);
		queryObj.put(new StringBuffer(FieldMapping.SHOPPING_INFO.getMongoField())
				.append(".").append(FieldMapping.PRODUCT_LIST.getMongoField()).append(".")
				.append(FieldMapping.PRODUCT_ID.getMongoField()).toString(),
				productId);
		refundHisList = (List<RefundHistory>) mongoManager.findAll(queryObj, COLLECTION_REFUND_HISTORY, RefundHistory.class);
		return refundHisList;
	}
	
	public RefundHistory findBySubOrderIdAndProductId(String subOrderid, String productId) {
		DBObject qyObj = null;
		RefundHistory refund = null;
		qyObj = new BasicDBObject();
		
		qyObj.put(new StringBuffer(FieldMapping.SHOPPING_INFO.getMongoField())
				.append(".").append(FieldMapping.SUB_ORDER_ID.getMongoField())
				.toString(), subOrderid);
		qyObj.put(new StringBuffer(FieldMapping.SHOPPING_INFO.getMongoField())
				.append(".").append(FieldMapping.PRODUCT_LIST).append(".")
				.append(FieldMapping.PRODUCT_ID.getMongoField()).toString(),
				productId);
		refund = (RefundHistory)mongoManager.findOne(qyObj, COLLECTION_REFUND_HISTORY, RefundHistory.class);
		return refund;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RefundHistory> findListByDBObject(DBObject queryObj) {
		List<RefundHistory> refundHisList = null;
		
		refundHisList = (List<RefundHistory>) mongoManager.findAll(queryObj, COLLECTION_REFUND_HISTORY, RefundHistory.class);
		
		return refundHisList;
	}

	@Override
	public MongoPage findPageByDBObject(DBObject queryObj, MongoPage mongoPage) {
		
		mongoPage = mongoManager.findByPageWithRedis(mongoPage, queryObj, COLLECTION_REFUND_HISTORY, RefundHistory.class);
		
		return mongoPage;
	}

	@Override
	public RefundHistory findAndModifyByDBObject(DBObject queryObj, DBObject updateObj) {
		RefundHistory refundHis = null;
		
		refundHis = mongoManager.findAndModify(updateObj, queryObj, COLLECTION_REFUND_HISTORY, false, RefundHistory.class);
		
		return refundHis;
	}

	@Override
	public List<RefundPaymentInfo> findRefundPaymentListByPaymentId(
			String clientId, String paymentId) {
		List<RefundPaymentInfo> payList = null;
		RefundHistory refundHis = null;
		DBObject queryObj = null;
		
		queryObj = new BasicDBObject(FieldMapping.PAYMENT_INFO.getMongoField(),
				new BasicDBObject(QueryOperators.ELEM_MATCH,
						new BasicDBObject(FieldMapping.PAYMENT_ID.getMongoField(), paymentId)));
		queryObj.put(FieldMapping.CLIENT_ID.getMongoField(), clientId);
		
		refundHis = mongoManager.findOne(queryObj, MongoTableName.CB_ORDER_REFUNDS, RefundHistory.class);
		
		if (null != refundHis){
			payList = refundHis.getPaymentInfo();
		}
		
		if (null != payList){
			// filter input payment id
			for (int i = 0; i < payList.size(); i++){
				if (paymentId.equals(payList.get(i).getPaymentId())){
					payList.remove(i);
					break;
				}
			}
		} else {
			payList = new ArrayList<RefundPaymentInfo>();
		}
		
		return payList;
	}

}
