package com.froad.db.mongo;

import java.util.List;

import com.froad.db.mongo.impl.MongoManager;
import com.froad.logback.LogCvt;
import com.froad.po.mongo.PaymentMongo;
import com.froad.util.MongoTableName;
import com.mongodb.DBObject;

public class PaymentMongoService {
	
	private MongoManager mongo = new MongoManager();
	
	@SuppressWarnings("unchecked")
	public List<PaymentMongo> findByCondition(DBObject dbObject){
		List<PaymentMongo> paymentList = null;
		
		LogCvt.info("支付流水查找条件："+ dbObject.toString());
		
		paymentList = (List<PaymentMongo>) mongo.findAll(dbObject, MongoTableName.CB_PAYMENT, PaymentMongo.class);
		
		return paymentList;
	}
}
