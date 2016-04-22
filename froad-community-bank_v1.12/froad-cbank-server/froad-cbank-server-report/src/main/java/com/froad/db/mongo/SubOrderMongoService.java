package com.froad.db.mongo;

import java.util.List;

import com.froad.db.mongo.impl.MongoManager;
import com.froad.enums.OrderStatus;
import com.froad.enums.SubOrderRefundState;
import com.froad.logback.LogCvt;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.util.JSonUtil;
import com.froad.util.MongoTableName;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

public class SubOrderMongoService {
	
	private MongoManager mongo = new MongoManager();
	
	public List<SubOrderMongo> findByCondition(DBObject dbObject){
	    LogCvt.info("查找子订单内容条件："+JSonUtil.toJSonString(dbObject));
		return (List<SubOrderMongo>) mongo.findAll(dbObject, MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
	}
	
	
	/**
	 * 根据类型查询支付成功的订单
	 * @Title: findSubOrderByType 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年6月18日
	 * @modify: froad-huangyihao 2015年6月18日
	 * @param startTime
	 * @param endTime
	 * @param subOrderType
	 * @return
	 * @throws
	 */
	public List<SubOrderMongo> findSubOrderByType(long startTime, long endTime, String subOrderType){
		DBObject where = new BasicDBObject();
		where.put("type", subOrderType);
		where.put("order_status", OrderStatus.paysuccess.getCode());
		where.put("refund_state", new BasicDBObject(QueryOperators.NE, SubOrderRefundState.REFUND_SUCCESS.getCode()));
		BasicDBList values = new BasicDBList();
        if (startTime > 0) {
            values.add(new BasicDBObject("create_time", new BasicDBObject(QueryOperators.GTE, startTime)));
        }
        if (endTime > 0) {
            values.add(new BasicDBObject("create_time", new BasicDBObject(QueryOperators.LTE, endTime)));
        }
        if (!values.isEmpty()) {
            where.put(QueryOperators.AND, values);
        }
		return findByCondition(where);
	}
	
	
	/**
	 * 根据多种类型查询支付成功的订单
	 * @Title: findSubOrderByTypes 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年6月18日
	 * @modify: froad-huangyihao 2015年6月18日
	 * @param startTime
	 * @param endTime
	 * @param subOrderTypes
	 * @return
	 * @throws
	 */
	public List<SubOrderMongo> findSubOrderByTypes(long startTime, long endTime, List<String> subOrderTypes){
		DBObject where = new BasicDBObject();
		where.put("type", new BasicDBObject(QueryOperators.IN, subOrderTypes));
		where.put("order_status", OrderStatus.paysuccess.getCode());
		where.put("refund_state", new BasicDBObject(QueryOperators.NE, SubOrderRefundState.REFUND_SUCCESS.getCode()));
		BasicDBList values = new BasicDBList();
        if (startTime > 0) {
            values.add(new BasicDBObject("create_time", new BasicDBObject(QueryOperators.GTE, startTime)));
        }
        if (endTime > 0) {
            values.add(new BasicDBObject("create_time", new BasicDBObject(QueryOperators.LTE, endTime)));
        }
        if (!values.isEmpty()) {
            where.put(QueryOperators.AND, values);
        }
		return findByCondition(where);
	}
	
	
	public List<SubOrderMongo> findPaySucOrders(long startTime, long endTime){
		DBObject where = new BasicDBObject();
		where.put("order_status", OrderStatus.paysuccess.getCode());
		where.put("refund_state", new BasicDBObject(QueryOperators.NE, SubOrderRefundState.REFUND_SUCCESS.getCode()));
		BasicDBList values = new BasicDBList();
		if (startTime > 0) {
			values.add(new BasicDBObject("create_time", new BasicDBObject(QueryOperators.GTE, startTime)));
		}
		if (endTime > 0) {
			values.add(new BasicDBObject("create_time", new BasicDBObject(QueryOperators.LTE, endTime)));
		}
		if (!values.isEmpty()) {
			where.put(QueryOperators.AND, values);
		}
		return findByCondition(where);
	}
}
