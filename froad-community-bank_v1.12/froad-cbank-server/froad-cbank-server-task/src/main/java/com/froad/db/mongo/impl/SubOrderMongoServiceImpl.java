package com.froad.db.mongo.impl;

import java.util.List;

import com.froad.db.mongo.SubOrderMongoService;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.util.MongoTableName;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class SubOrderMongoServiceImpl implements SubOrderMongoService {

	private MongoManager manager = new MongoManager();
			
	@Override
	public List<SubOrderMongo> findByCondition(DBObject query) {
		return (List<SubOrderMongo>)manager.findAll(query, MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
	}

	@Override
	public List<SubOrderMongo> findByCondition(String clientId_orderId) {
		String [] tValue = clientId_orderId.split(":");
		String clientId = tValue[0];
		String orderId = tValue[1];
		DBObject subQuery = new BasicDBObject();
		subQuery.put("order_id", orderId);
		subQuery.put("client_id", clientId);
		return this.findByCondition(subQuery);
	}

	@Override
	public Boolean modifySubOrder(DBObject setObj, DBObject where, String modifier) {
//		DBObject value = new BasicDBObject(modifier, setObj);
		int result = manager.updateMulti(setObj, where, MongoTableName.CB_SUBORDER_PRODUCT, modifier);
		return result > -1;
	}
	
	@Override
	public SubOrderMongo findSubOrderMongo(String subOrderId){
		DBObject subQuery = new BasicDBObject();
		subQuery.put("sub_order_id", subOrderId);
		return manager.findOne(subQuery, MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
	}

}
