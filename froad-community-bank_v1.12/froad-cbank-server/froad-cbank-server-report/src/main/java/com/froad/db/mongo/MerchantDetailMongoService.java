package com.froad.db.mongo;

import java.util.List;

import com.froad.db.mongo.impl.MongoManager;
import com.froad.logback.LogCvt;
import com.froad.po.mongo.MerchantDetail;
import com.froad.util.MongoTableName;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

public class MerchantDetailMongoService {
	
	private MongoManager mongo = new MongoManager();
	
	public MerchantDetail findOneMerchantDetail(DBObject dbObject){
		return mongo.findOne(dbObject, MongoTableName.CB_MERCHANT_DETAIL, MerchantDetail.class);
	}
	
	public MerchantDetail findById(String merchantId){
		return mongo.findOneById(merchantId, MongoTableName.CB_MERCHANT_DETAIL, MerchantDetail.class);
	}
	
	public Integer getCount(DBObject dbObject){
		return mongo.getCount(dbObject, MongoTableName.CB_MERCHANT_DETAIL);
	}
	
	public Integer getTypeSummary(List<String> merchantIds, String type){
		DBObject query = new BasicDBObject();
		query.put("_id", new BasicDBObject(QueryOperators.IN, merchantIds));
		query.put("type_info.type", type);
		return getCount(query);
	}
}
