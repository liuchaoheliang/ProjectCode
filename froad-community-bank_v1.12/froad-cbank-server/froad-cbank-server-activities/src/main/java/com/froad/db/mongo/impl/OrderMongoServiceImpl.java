package com.froad.db.mongo.impl;

import java.util.List;

import com.froad.db.mongo.OrderMongoService;
import com.froad.po.mongo.OrderMongo;
import com.froad.util.MongoTableName;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class OrderMongoServiceImpl implements OrderMongoService {

	private MongoManager manager = new MongoManager();
	
	/**
	 * 
	 * @Title: isExistOrder 
	 * @Description: 查询是否存在此会员编号的订单
	 * @author: lf 2016年2月24日
	 * @param query
	 * @return
	 * @throws
	 */
	@Override
	public boolean isExistOrder(Long memberCode, String clientId) {
		DBObject query = new BasicDBObject();
		query.put("client_id", clientId);
		query.put("member_code", memberCode);
		List<OrderMongo> orderList = (List<OrderMongo>)manager.findAll(query, MongoTableName.CB_ORDER, OrderMongo.class);
		return (orderList != null && orderList.size() > 0) ? true : false;
	}

}
