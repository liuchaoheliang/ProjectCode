package com.froad.db.mongo;


import java.util.ArrayList;
import java.util.List;

import com.froad.db.mongo.impl.CursorHandleImpl;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.logback.LogCvt;
import com.froad.po.ReportMerchantSale;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.ProductMongo;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.util.EmptyChecker;
import com.froad.util.JSonUtil;
import com.froad.util.MongoTableName;
import com.mongodb.AggregationOptions;
import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DBObject;

public class ReportMerchantSaleMongo {
	/**
	 *  MongoDb操作类
	 */
	private MongoManager mongo = new MongoManager();
	
	
	public ReportMerchantSale findReportMerchantProduct(){
		
		return null;
	}
	
	
	
	/**通过订单号查询大订单 Mongo
	* @Title: getOrderByOrderId 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param orderId
	* @param @return
	* @return OrderMongo
	* @throws 
	*/
	public OrderMongo getOrderByOrderId(String clientId, String orderId) {
		DBObject query = new BasicDBObject();
		query.put("client_id", clientId);
		query.put("_id", orderId);
		return mongo.findOne(query, MongoTableName.CB_ORDER, OrderMongo.class);
	}
	
	
	/**
	 *  根据商品ID，订单ID查询子订单商品信息
	  * @Title: getProductMongo
	  * @Description: 根据商品ID，订单ID查询子订单商品信息
	  * @author: share 2015年3月27日
	  * @modify: share 2015年3月27日
	  * @param @param orderId
	  * @param @param subOrderId
	  * @param @param productId
	  * @param @return
	  * @throws
	  * @see com.froad.support.OrderSupport#getProductMongo(java.lang.String, java.lang.String, java.lang.String)
	 */
	public ProductMongo getProductMongo(String orderId, String subOrderId,String productId) {
		List<DBObject> pipeLine = new ArrayList<DBObject>();
		DBObject query = new BasicDBObject();
		query.put("order_id", orderId);
		query.put("sub_order_id", subOrderId);
        pipeLine.add(new BasicDBObject("$match", query));
        pipeLine.add(new BasicDBObject("$unwind", "$products"));
        
        DBObject query2 = new BasicDBObject();
		query2.put("products.product_id", productId);
        
        pipeLine.add(new BasicDBObject("$match", query2));
        Cursor cursor = mongo.getReadDBCollection(MongoTableName.CB_SUBORDER_PRODUCT).aggregate(pipeLine, AggregationOptions.builder().build());
        DBObject dbObj = new CursorHandleImpl().handle(cursor);
        if(dbObj != null){
        	 String json = JSonUtil.toJSonString(((DBObject)dbObj.get("products")));
             return JSonUtil.toObject(json, ProductMongo.class);
        }
       return null;
	}
	
	
	/**
	 *  查询子订单集合
	  * @Title: getSubOrderListByOrderId
	  * @Description: 按订单ID及字典的状态查询
	  * @author: share 2015年4月2日
	  * @modify: share 2015年4月2日
	  * @param @param orderId
	  * @param @return
	  * @throws
	  * @see com.froad.support.OrderSupport#getSubOrderListByOrderId(java.lang.String)
	 */
	public List<SubOrderMongo> getSubOrderListByCondition(SubOrderMongo subOrderMongo) {
		List<SubOrderMongo> subOrderList = new ArrayList<SubOrderMongo>();
		if(EmptyChecker.isNotEmpty(subOrderMongo)){
			return subOrderList;
		}
		DBObject query = new BasicDBObject();
		if(EmptyChecker.isNotEmpty(subOrderMongo.getMerchantId())){
			query.put("merchant_id", subOrderMongo.getMerchantId());
		}
		if(EmptyChecker.isNotEmpty(subOrderMongo.getType())){
			query.put("type", subOrderMongo.getType());
		}
		if(EmptyChecker.isNotEmpty(subOrderMongo.getCreateTime())){
			query.put("create_time", subOrderMongo.getCreateTime());
		}
		subOrderList = (List<SubOrderMongo>) mongo.findAll(query, MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
		return subOrderList;
	}
}
