package com.froad.support.impl.payment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.redis.RedisService;
import com.froad.db.redis.impl.RedisManager;
import com.froad.logic.CommonLogic;
import com.froad.logic.impl.CommonLogicImpl;
import com.froad.po.Client;
import com.froad.po.ClientPaymentChannel;
import com.froad.po.Payment;
import com.froad.support.payment.DataPersistent;
import com.froad.util.JSonUtil;
import com.froad.util.RedisKeyUtil;
import com.froad.util.payment.Const;
import com.froad.util.payment.Logger;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;
import com.mongodb.util.JSON;

public class DataPersistentImpl implements DataPersistent {

	private final String COLLECTION_PAYMENT_NAME = Const.COLLECTION_PAYMENT_NAME;
	
	private MongoManager mongoManager;
	private RedisService redisService;
	
	private CommonLogic commonLogic = new CommonLogicImpl();

	
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
	public boolean savePaymentToMongoDB(Payment payment) {
		if(payment == null){
			return false;
		}
		boolean flag = mongoManager.add(payment, COLLECTION_PAYMENT_NAME) != -1; 
		if(!flag){
			Logger.logError("保存支付流水失败", "payment", payment);
		}
		return flag;
	}

	@Override
	public Client acquireClientFromRedis(String clientId) {
		return commonLogic.getClientById(clientId);
	}

	@Override
	public List<ClientPaymentChannel> acquireClientPaymentChannelFromRedis(String clientId) {
		return commonLogic.getClientPaymentChannelByClientId(clientId);
	}

	@Override
	public boolean updateByPaymentIdFromMongoDB(Payment payment) {
		if(payment == null || StringUtils.isEmpty(payment.getPaymentId())){
			Logger.logError("通过paymentId更新Payment失败", "payment", payment);
			return false;
		}
		DBObject queryDBObject = new BasicDBObject();
		DBObject valueDBObject = new BasicDBObject();
		DBObject valueSet = null;
		queryDBObject.put("payment_id", payment.getPaymentId());
		payment.setPaymentId(null);
		valueSet = (BasicDBObject) JSON.parse(JSonUtil.toJSonString(payment));
		valueDBObject.put("$set", valueSet);
		boolean flag = mongoManager.update(valueDBObject, queryDBObject, COLLECTION_PAYMENT_NAME, null) > 0;
		if(flag){
			return true;
		}
		Logger.logError("通过paymentId更新Payment失败", new String[]{"queryDbObject","valueDBObject"}, new Object[]{queryDBObject,valueDBObject});
		return false;
	}

	@Override
	public Payment findAndModifyPaymentFromMongoDB(Payment paymentOriginal,Payment paymentTarget) {
		if(paymentOriginal == null || StringUtils.isEmpty(paymentOriginal.getPaymentId())){
			Logger.logError("通过findAndModify原子更新Payment失败", "paymentOriginal", paymentOriginal);
			return null;
		}
		if(paymentTarget == null){
			Logger.logError("通过findAndModify原子更新Payment失败", "paymentTarget", paymentTarget);
			return null;
		}
		DBObject queryDBObject = null;
		DBObject valueDBObject = new BasicDBObject();
		DBObject valueSet = null;
		queryDBObject = (BasicDBObject) JSON.parse(JSonUtil.toJSonString(paymentOriginal));
		paymentTarget.setPaymentId(null);//防止主键被恶意或无意修改
		valueSet = (BasicDBObject) JSON.parse(JSonUtil.toJSonString(paymentTarget));
		valueDBObject.put("$set", valueSet);
		Payment paymentResult = mongoManager.findAndModify(valueDBObject, queryDBObject, COLLECTION_PAYMENT_NAME, false, Payment.class);
		if(paymentResult != null){
			return paymentResult;
		}
		
		Logger.logError("通过findAndModify原子更新Payment失败", new String[]{"queryDbObject","valueDBObject","paymentOriginal","paymentTarget"}, new Object[]{queryDBObject,valueDBObject,paymentOriginal,paymentTarget});
		return null;
	}
	
	@Override
	public Payment findAndModifyPaymentToStepFour(String paymentId){
		if(StringUtils.isEmpty(paymentId)){
			Logger.logError("通过findAndModify原子更新Payment失败", "paymentId", "");
			return null;
		}
		
		DBObject queryDBObject = new BasicDBObject();
		DBObject valueDBObject = new BasicDBObject();
		queryDBObject.put("payment_id", paymentId);
		queryDBObject.put(QueryOperators.OR, new Object[]{new BasicDBObject("step",2),new BasicDBObject("step",3)});
		valueDBObject.put("$set",new  BasicDBObject("step",4));
		
		Payment paymentResult = mongoManager.findAndModify(valueDBObject, queryDBObject, COLLECTION_PAYMENT_NAME, false, Payment.class);
		if(paymentResult != null){
			return paymentResult;
		}
		
		Logger.logError("通过findAndModify原子更新Payment失败", new String[]{"queryDbObject","valueDBObject","paymentId"}, new Object[]{queryDBObject,valueDBObject,paymentId});
		return null;
	}

	@Override
	public Payment findByPaymentIdFromMongoDB(String paymentId) {
		if(StringUtils.isEmpty(paymentId)){
			Logger.logError("通过paymentId查询Payment失败", "paymentId", paymentId);
			return null;
		}
		DBObject queryDBObject = new BasicDBObject();
		queryDBObject.put("payment_id", paymentId);
		return mongoManager.findOne(queryDBObject, COLLECTION_PAYMENT_NAME, Payment.class);
	}

	
	@SuppressWarnings("unchecked")
	public List<Payment> findByPaymentConditionFromMongoDB(Payment payment){
		if(payment == null){
			return null;
		}
		DBObject queryDBObject = new BasicDBObject();
		queryDBObject = (BasicDBObject) JSON.parse(JSonUtil.toJSonString(payment));
		return (List<Payment>) mongoManager.findAll(queryDBObject, COLLECTION_PAYMENT_NAME, Payment.class);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Payment> findEnableOfUserPayByOrderIdFromMongoDB(String orderId) {
		if(StringUtils.isEmpty(orderId)){
			Logger.logError("通过orderId查询Payment失败", "orderId", orderId);
			return null;
		}
		DBObject queryDBObject = new BasicDBObject();
		queryDBObject.put("order_id", orderId);
		queryDBObject.put("is_enable", true);
		queryDBObject.put("payment_reason", "2");
		return (List<Payment>) mongoManager.findAll(queryDBObject, COLLECTION_PAYMENT_NAME, Payment.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Payment> findAllEnableByOrderIdFromMongoDB(String orderId) {
		if(StringUtils.isEmpty(orderId)){
			Logger.logError("通过orderId查询Payment失败", "orderId", orderId);
			return null;
		}
		DBObject queryDBObject = new BasicDBObject();
		queryDBObject.put("order_id", orderId);
		queryDBObject.put("is_enable", true);
		return (List<Payment>) mongoManager.findAll(queryDBObject, COLLECTION_PAYMENT_NAME, Payment.class);
	}

	@Override
	public boolean addPaymentTimeFromRedis(String paymentId) {
		return redisService.put(RedisKeyUtil.cbbank_time_payment_key(), paymentId) != 0;
	}

	@Override
	public boolean removePaymentTimeFromRedis(String paymentId) {
		return redisService.srem(RedisKeyUtil.cbbank_time_payment_key(), paymentId) != 0;
	}
	
	@Override
	public boolean removeTimeOrderFromRedis(String clinetId, String orderId) {
		String timeOrderKey = RedisKeyUtil.cbbank_time_order_key();
		String timeOrderVlue = RedisKeyUtil.cbbank_time_order_value(clinetId, orderId);
		return redisService.srem(timeOrderKey, timeOrderVlue) != 0L;
	}

	@Override
	public boolean updateOrderStatusOfSeckillingFromRedis(String reqId,boolean isPaiedSuccess) {
		String key = RedisKeyUtil.cbbank_seckill_res_req_id(reqId);
		String value = isPaiedSuccess ? "1" : "2";
		Map<String, String> valueMap = new HashMap<String, String>();
		valueMap.put("result_flag", value);
		return redisService.putMap(key, valueMap) != null;
	}

}
