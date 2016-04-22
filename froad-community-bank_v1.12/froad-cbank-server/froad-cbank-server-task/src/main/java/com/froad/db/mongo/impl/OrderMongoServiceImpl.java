package com.froad.db.mongo.impl;

import java.util.List;

import com.froad.db.mongo.OrderMongoService;
import com.froad.logback.LogCvt;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.util.MongoTableName;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class OrderMongoServiceImpl implements OrderMongoService {
	
	private MongoManager manager = new MongoManager();
	
	@Override
	public OrderMongo updateOrder(DBObject update, DBObject where, String modifier) {
		DBObject value = new BasicDBObject(modifier, update);
		OrderMongo order = manager.findAndModify(value, where, MongoTableName.CB_ORDER, false, OrderMongo.class);
		return order;
	}

	@Override
	public OrderMongo findByOrderId(String orderId) {
		return manager.findOneById(orderId, MongoTableName.CB_ORDER, OrderMongo.class);
	}

	@Override
	public OrderMongo findOrderMongo(DBObject query) {
		return manager.findOne(query, MongoTableName.CB_ORDER, OrderMongo.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SubOrderMongo> findSubOrderListByOrderId(String orderId) {
		long startTime = System.currentTimeMillis();
		DBObject query = new BasicDBObject();
		query.put("order_id", orderId);
		List<SubOrderMongo> subOrderList = (List<SubOrderMongo>) manager.findAll(query, MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
		return subOrderList;
	}

}
