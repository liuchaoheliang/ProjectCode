package com.froad.db.mongo;

import java.util.ArrayList;
import java.util.List;

import com.froad.Constants;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.enums.OrderStatus;
import com.froad.logback.LogCvt;
import com.froad.po.mongo.OrderMongo;
import com.froad.util.JSonUtil;
import com.froad.util.MongoTableName;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

public class OrderMongoService {
	
	private MongoManager mongo = new MongoManager();
	
	public List<OrderMongo> findByCondition(DBObject dbObject){
	    LogCvt.info("查询CB_ORDER， 查询条件：" + JSonUtil.toJSonString(dbObject));
		return (List<OrderMongo>) mongo.findAll(dbObject, MongoTableName.CB_ORDER, OrderMongo.class);
	}
	
	
	public OrderMongo findById(String orderId) {
	    return mongo.findOneById(orderId, MongoTableName.CB_ORDER, OrderMongo.class);
	}
	
	
	public List<OrderMongo> findByCondtion(String outletId, long startTime, long endTime, int isQrcode) {
	    DBObject where = new BasicDBObject();
	    where.put("outlet_id", outletId);
	    where.put("is_qrcode", isQrcode);
	    where.put("order_status", "6");
	    
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
	
	public List<OrderMongo> findByCondtion(List<String> outletIds, long startTime, long endTime, int isQrcode) {
        DBObject where = new BasicDBObject();
        where.put("outlet_id", new BasicDBObject(QueryOperators.IN, outletIds));
        where.put("is_qrcode", isQrcode);
        where.put("order_status", "6");
        
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
	 * 查询成功支付的面对面订单
	 * @Title: findFaceOrder 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年6月13日
	 * @modify: froad-huangyihao 2015年6月13日
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws
	 */
	public List<OrderMongo> findFaceOrder(long startTime, long endTime){
		DBObject where = new BasicDBObject();
		where.put("is_qrcode", 1);
		where.put("order_status", OrderStatus.paysuccess.getCode());
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
	
	
	public List<OrderMongo> findPaySucOrders(long startTime, long endTime){
		DBObject where = new BasicDBObject();
//		where.put("client_id", clientId);
		where.put("order_status", OrderStatus.paysuccess.getCode());
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
	
	
	
	public static void main(String[] args) {
	    
	    System.setProperty(Constants.CONFIG_PATH, "./config/");
	    
	    OrderMongoService orderMongo = new OrderMongoService();
	    
	    List<String> list = new ArrayList<String>();
	    
	    list.add("0");
	    
	    List<OrderMongo> order = orderMongo.findByCondtion(list, 1433208791261L, 1433502827403L, 1);
	    
	    System.out.println(JSonUtil.toJSonString(order));
	    
    }
	
	
}
