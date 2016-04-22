package com.froad.db.mongo.impl;

import com.froad.db.mongo.SettlementMongoService;
import com.froad.db.mongo.page.MongoPage;
import com.froad.enums.SettlementStatus;
import com.froad.po.mongo.Settlement;
import com.froad.util.MongoTableName;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;


public class SettlementMongoServiceImpl implements SettlementMongoService {

	private MongoManager manager = new MongoManager();
	
	@Override
	public MongoPage queryPointSettlementSuccByPage(MongoPage page, long startTime, long endTime) {
		DBObject where = new BasicDBObject();
		
		//设置默认条件，结算成功
		where.put("settle_state", SettlementStatus.settlementsucc.getCode());
		
		//设置是积分支付（ 1 -- 方付通积分	2 -- 银行积分）
		Object [] obj = {new BasicDBObject("deductible_point_type", "1"),
                new BasicDBObject("deductible_point_type", "2")};
		where.put(QueryOperators.OR, obj);

		//设置结算时间条件
		BasicDBList values = new BasicDBList();
		values.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.GT,startTime)));
		values.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.LTE,endTime)));
		where.put(QueryOperators.AND,values);
		
		return manager.findByPageWithRedis(page, where,  MongoTableName.CB_SETTLEMENT, Settlement.class);
	}}

