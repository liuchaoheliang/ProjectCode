package com.froad.db.mongo.impl;

import java.util.List;

import com.froad.db.mongo.RefundMongoService;
import com.froad.po.mongo.RefundHistory;
import com.froad.util.MongoTableName;
import com.mongodb.DBObject;

public class RefundsMongoServiceImpl implements RefundMongoService{
	private MongoManager manager = new MongoManager();
	@Override
	public List<RefundHistory> findByCondition(DBObject query) {
	
		return (List<RefundHistory>)manager.findAll(query, MongoTableName.CB_ORDER_REFUNDS, RefundHistory.class);
	}

}

