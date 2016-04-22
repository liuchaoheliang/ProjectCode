package com.froad.support.impl.payment;


import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mongo.page.MongoPage;
import com.froad.db.redis.RedisService;
import com.froad.db.redis.impl.RedisManager;
import com.froad.enums.FieldMapping;
import com.froad.logback.LogCvt;
import com.froad.po.Payment;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.RefundHistory;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.po.settlement.Settlement;
import com.froad.support.payment.DataPersistent;
import com.froad.util.MongoTableName;
import com.froad.util.RedisKeyUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

public class DataPersistentImpl implements DataPersistent {

	private MongoManager mongoManager;
	private RedisService redisService;
	

	
	/**
	 * 	INIT CLEZZ
	* <p>Title: </p>
	* <p>Description: </p>
	 */
	public DataPersistentImpl(){
		this.mongoManager = new MongoManager();
		this.redisService = new RedisManager();
	}



	@Override
	public MongoPage findByPaymentByConditionByPay(MongoPage mongoPage,DBObject queryDB) {
		return mongoManager.findByPageWithSortObject(mongoPage, queryDB, new BasicDBObject(), MongoTableName.CB_PAYMENT, Payment.class);
//		return mongoManager.findByPageWithRedis(mongoPage, queryDB, MongoTableName.CB_PAYMENT, Payment.class);
	}


	@Override
	public Payment findPaymentByCondition(String paymentId) {
		Object dbObject = new BasicDBObject(FieldMapping.PAYMENT_ID.getMongoField(), paymentId);
		return mongoManager.findOne(dbObject,  MongoTableName.CB_PAYMENT, Payment.class);
	}

	@Override
	public OrderMongo findOrderByCondition(String orderId) {
		Object dbObject = new BasicDBObject(FieldMapping.ID.getMongoField(), orderId);
		return mongoManager.findOne(dbObject,  MongoTableName.CB_ORDER, OrderMongo.class);
	}
	
	@Override
	public Settlement findSettlementByCondition(String paymentId){
		Object dbObject = new BasicDBObject(FieldMapping.PAYMENT_ID.getMongoField(), paymentId);
		return mongoManager.findOne(dbObject,  MongoTableName.CB_SETTLEMENT, Settlement.class);
	}



	@Override
	public boolean saveSettlement(Settlement settlement) {
		boolean flag = mongoManager.add(settlement, MongoTableName.CB_SETTLEMENT) != -1; 
		if(!flag){
			LogCvt.error("保存结算记录失败 " + JSONObject.toJSONString(settlement));
		}
		return flag;
	}


	@Override
	public boolean savePayment(Payment payment) {
		boolean flag = mongoManager.add(payment, MongoTableName.CB_PAYMENT) != -1; 
		if(!flag){
			LogCvt.error("保存支付流水失败 " + JSONObject.toJSONString(payment));
		}
		return flag;
	}


	@Override
	public Payment queryPaymentOfSettlementingOrSettlemented(String orderId) {
		DBObject dbObject = new BasicDBObject();
		dbObject.put("order_id", orderId);
		dbObject.put("payment_reason", "0");
		dbObject.put(QueryOperators.OR,new Object[]{new BasicDBObject("step",3),new BasicDBObject("payment_status","4")});
		return mongoManager.findOne(dbObject,  MongoTableName.CB_PAYMENT, Payment.class);
	}

	@Override
	public RefundHistory findRefundByPaymentId(String paymentId) {
		DBObject dbObject = new BasicDBObject();
		dbObject.put("payment_info.payment_id", paymentId);
		return mongoManager.findOne(dbObject,  MongoTableName.CB_ORDER_REFUNDS, RefundHistory.class);
	}
	
	
	@Override
	public boolean modifyPaymentToRequestException(String paymentId,String remark) {
		boolean flag = false;
		DBObject dbObject = new BasicDBObject();
		DBObject dbObjectQ = new BasicDBObject();
		dbObjectQ.put("step", 3);
		dbObjectQ.put("payment_status", "3");
		dbObjectQ.put("remark", remark);
		dbObject.put("$set", dbObjectQ);
		flag = mongoManager.update(dbObject, new BasicDBObject("payment_id", paymentId), MongoTableName.CB_PAYMENT, null) > 0;
		return flag;
	}

	@Override
	public boolean modifyPaymentToPayAccessSuccess(String paymentId,String billNo, String remark) {
		boolean flag = false;
		DBObject dbObject = new BasicDBObject();
		DBObject dbObjectQ = new BasicDBObject();
		dbObjectQ.put("bill_no", billNo);
		dbObjectQ.put("result_code", "0000");
		dbObjectQ.put("result_desc", "支付请求受理成功");
		dbObjectQ.put("remark", remark);
		dbObject.put("$set", dbObjectQ);
		flag = mongoManager.update(dbObject, new BasicDBObject("payment_id", paymentId), MongoTableName.CB_PAYMENT, null) > 0;
		return flag;
	}



	@Override
	public boolean modifyPaymentToRequestSuccess(String paymentId) {
		boolean flag = false;
		DBObject dbObject = new BasicDBObject();
		DBObject dbObjectQ = new BasicDBObject();
		dbObjectQ.put("step", 3);
		dbObjectQ.put("payment_status", "2");
		dbObject.put("$set", dbObjectQ);
		flag = mongoManager.update(dbObject, new BasicDBObject("payment_id", paymentId), MongoTableName.CB_PAYMENT, null) > 0;
		return flag;
	}

	@Override
	public boolean modifyPaymentToPayFailed(String paymentId,String resultCode, String resultDesc) {
		boolean flag = false;
		DBObject dbObject = new BasicDBObject();
		DBObject dbObjectQ = new BasicDBObject();
		dbObjectQ.put("step", 4);
		dbObjectQ.put("payment_status", "5");
		dbObjectQ.put("result_code", resultCode);
		dbObjectQ.put("result_desc", resultDesc);
		dbObject.put("$set", dbObjectQ);
		flag = mongoManager.update(dbObject, new BasicDBObject("payment_id", paymentId), MongoTableName.CB_PAYMENT, null) > 0;
		return flag;
	}

	@Override
	public boolean findAndModifyPaymentToProcessed(String paymentId) {
		boolean flag = false;
		DBObject dbObject = new BasicDBObject();
		DBObject dbObjectQ = new BasicDBObject();
		dbObjectQ.put("is_dispose_exception", "1");
		dbObject.put("$set", dbObjectQ);
		DBObject query = new BasicDBObject();
		query.put("payment_id", paymentId);
		query.put("is_dispose_exception", "0");
		flag = mongoManager.findAndModify(dbObject, query, MongoTableName.CB_PAYMENT, false,null) != null;
		return flag;
	}

	@Override
	public boolean addPaymentTimeFromRedis(String paymentId) {
		return redisService.put(RedisKeyUtil.cbbank_time_payment_key(), paymentId) != 0;
	}

	@Override
	public boolean modifySettlementToFailed(String settlementId) {
		boolean flag = false;
		DBObject dbObject = new BasicDBObject();
		DBObject dbObjectQ = new BasicDBObject();
		dbObjectQ.put("settle_state", "3");
		dbObject.put("$set", dbObjectQ);
		flag = mongoManager.update(dbObject, new BasicDBObject("settlement_id", settlementId), MongoTableName.CB_SETTLEMENT, null) > 0;
		return flag;
	}



	@Override
	public List<SubOrderMongo> querySubOrderByOrderId(String orderId) {
		DBObject dbObject = new BasicDBObject();
		dbObject.put("order_id", orderId);
		return (List<SubOrderMongo>) mongoManager.findAll(dbObject,MongoTableName.CB_SUBORDER_PRODUCT,SubOrderMongo.class);
	}



	
}
