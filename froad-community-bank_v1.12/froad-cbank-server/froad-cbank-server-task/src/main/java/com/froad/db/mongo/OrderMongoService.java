package com.froad.db.mongo;

import java.util.List;

import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.SubOrderMongo;
import com.mongodb.DBObject;

public interface OrderMongoService {
	
	/**
	 * 
	 * @Title: updateOrder 
	 * @Description: 原子性修改cb_order
	 * @author: froad-huangyihao 2015年4月17日
	 * @modify: froad-huangyihao 2015年4月17日
	 * @param value
	 * @param where
	 * @param modifier
	 * @return
	 * @throws
	 */
	OrderMongo updateOrder(DBObject update, DBObject where, String modifier);
	
	/**
	 * 
	 * @Title: findByOrderId 
	 * @Description: 通过orderId查询
	 * @author: froad-huangyihao 2015年4月27日
	 * @modify: froad-huangyihao 2015年4月27日
	 * @param orderId
	 * @return
	 * @throws
	 */
	OrderMongo findByOrderId(String orderId);
	
	
	/**
	 * 
	 * @Title: findOrderMongo 
	 * @Description: 查询单个OrderMongo
	 * @author: froad-huangyihao 2015年4月27日
	 * @modify: froad-huangyihao 2015年4月27日
	 * @param query
	 * @return
	 * @throws
	 */
	OrderMongo findOrderMongo(DBObject query);
	
	
	/**
	 * 获取子订单集合
	 * @param orderId
	 * @return
	 */
	List<SubOrderMongo> findSubOrderListByOrderId(String orderId);
}
