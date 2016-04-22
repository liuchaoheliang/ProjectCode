package com.froad.db.mongo.impl;

import java.util.List;

import com.froad.db.mongo.ShippingMongoService;
import com.froad.po.mongo.ShippingOrderMongo;
import com.froad.util.MongoTableName;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class ShippingMongoServiceImpl implements ShippingMongoService {
	
	private MongoManager manager = new MongoManager();
	
	@Override
	public List<ShippingOrderMongo> findByCondition(DBObject query) {
		return (List<ShippingOrderMongo>)manager.findAll(query, MongoTableName.CB_SHIPPING, ShippingOrderMongo.class);
	}

	@Override
	public Boolean modifyShipping(DBObject set, DBObject where, String modifier) {
		DBObject value = new BasicDBObject(modifier, set);
		ShippingOrderMongo shipping = manager.findAndModify(value, where, MongoTableName.CB_SHIPPING, false, ShippingOrderMongo.class);
		return shipping != null;
	}

}
