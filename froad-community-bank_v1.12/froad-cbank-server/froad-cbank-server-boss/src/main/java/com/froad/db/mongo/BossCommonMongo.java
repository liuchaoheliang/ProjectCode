package com.froad.db.mongo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.froad.db.mongo.impl.CursorHandleImpl;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mongo.page.MongoPage;
import com.froad.enums.FieldMapping;
import com.froad.enums.RefundState;
import com.froad.po.OrderQueryByBossCondition;
import com.froad.po.PageEntity;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.RecvInfo;
import com.froad.po.mongo.RefundHistory;
import com.froad.po.mongo.RefundProduct;
import com.froad.po.mongo.RefundShoppingInfo;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.util.EmptyChecker;
import com.froad.util.JSonUtil;
import com.froad.util.MongoTableName;
import com.mongodb.AggregationOptions;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

public class BossCommonMongo {
	/**
	 *  MongoDb操作类
	 */
	private MongoManager mongo = new MongoManager();
	
	/**
	 * 
	 * getOrderList:方法待扩展
	 * @author zhouzhiwei
	 * 2015年8月6日 下午5:35:16
	 * @param order
	 * @param startTime
	 * @param endTime
	 * @return
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<OrderMongo> getOrderList(OrderMongo order, long payStartTime, long payEndTime, long createStartTime, long createEndTime) {
		
		BasicDBObject orderValues = new BasicDBObject();
		if(payStartTime > 0 && payEndTime > 0) {
			BasicDBList payConList = new BasicDBList(); 
			payConList.add(new BasicDBObject("payment_time",new BasicDBObject(QueryOperators.GTE, payStartTime)));
			payConList.add(new BasicDBObject("payment_time",new BasicDBObject(QueryOperators.LTE, payEndTime)));
			orderValues.put("$and", payConList);
		}
		
		if(createStartTime > 0 && createEndTime > 0) {
			BasicDBList createConList = new BasicDBList(); 
			createConList.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.GTE, createStartTime)));
			createConList.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.LTE, createEndTime)));
			orderValues.put("$and", createConList);
		}
		
		if(StringUtils.isNotEmpty(order.getClientId())) {
			orderValues.put("client_id", order.getClientId());
		}
		
		if(StringUtils.isNotEmpty(order.getOrderStatus())) {
			orderValues.put("order_status", order.getOrderStatus());
		}
		List<OrderMongo> orderList = (List<OrderMongo> )mongo.findAll(orderValues, MongoTableName.CB_ORDER, OrderMongo.class);
		return orderList;
	}
	
	
	/**
	 * 
	 * getSubOrderList:方法待扩展
	 *
	 * @author zhouzhiwei
	 * 2015年8月6日 下午5:38:02
	 * @param sub
	 * @param startTime
	 * @param endTime
	 * @return
	 * 
	 */
	public List<SubOrderMongo> getSubOrderList(SubOrderMongo sub, long startTime, long endTime) {
		BasicDBObject subValues = new BasicDBObject();
		//根据查询条件查询子订单记录
		if(StringUtils.isNotEmpty(sub.getOrderId())) {
			subValues.put("order_id", sub.getOrderId());
		}
		if(StringUtils.isNotEmpty(sub.getMemberName())) {
			subValues.put("member_name", sub.getMemberName());
		}
		
		if(StringUtils.isNotEmpty(sub.getClientId())) {
			subValues.put("client_id", sub.getClientId());
		}
		
		if(sub.getProducts() != null && sub.getProducts().size() == 1) {
			subValues.put("products.product_id", sub.getProducts().get(0).getProductId());
		}
		if(StringUtils.isNotEmpty(sub.getSubOrderId())) {
			subValues.put("sub_order_id", sub.getSubOrderId());
		}
		if(StringUtils.isNotEmpty(sub.getType())) {
			subValues.put("type", sub.getType());
		}
		if(StringUtils.isNotEmpty(sub.getOrderStatus())) {
			subValues.put("order_status", sub.getOrderStatus());
		}
		
		if(startTime > 0 && endTime > 0) {
			BasicDBList createConList = new BasicDBList(); 
			createConList.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.GTE, startTime)));
			createConList.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.LTE, endTime)));
			subValues.put("$and", createConList);
		}
		
		List<SubOrderMongo> subList  = (List<SubOrderMongo>)mongo.findAll(subValues, MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
		return subList;
	}
	
	
	public List<RecvInfo> queryRecvInfos(String phone) {
	    List<RecvInfo> list = new ArrayList<RecvInfo>();
	    
	    List<DBObject> pipeLine = new ArrayList<DBObject>();
	    
	    pipeLine.add(new BasicDBObject("$unwind", "$recv_info"));
	    pipeLine.add(new BasicDBObject("$match", new BasicDBObject("recv_info.phone", phone)));
	    
	    Cursor cursor = mongo.getReadDBCollection(MongoTableName.CB_MERCHANT_OUTLET_FAVORITE).aggregate(pipeLine, AggregationOptions.builder().build());
	    
	    List<DBObject> dbObj = new CursorHandleImpl().handleList(cursor);
	    
	    if(dbObj != null) {
	        for(DBObject db : dbObj) {
	            String json = JSonUtil.toJSonString(db.get("recv_info"));
	            list.add(JSonUtil.toObject(json, RecvInfo.class));
	        }
	    }
	    return list;
	}
	
	public MongoPage queryOrderOfBossByCondition(PageEntity<OrderQueryByBossCondition> pageEntity, String flag) {
//	    设置分页参数
	    MongoPage page = new MongoPage(pageEntity.getPageNumber(), pageEntity.getPageSize());
	    
	    convert(page, pageEntity);
	    
	    OrderQueryByBossCondition condition = pageEntity.getCondition();
	    
//	    设置查询条件
	    DBObject where = new BasicDBObject();
	    if(EmptyChecker.isNotEmpty(condition.getOrderId())) {
	        where.put("_id", condition.getOrderId());
	    }
	    if(condition.getMemberCode() > 0) {
	        where.put("member_code", condition.getMemberCode());
	    }
	    if(EmptyChecker.isNotEmpty(condition.getCreateSource())) {
	        where.put("create_source", condition.getCreateSource());
	    }
	    
	    if(EmptyChecker.isNotEmpty(condition.getPaymentMethod())) {
	        where.put("payment_method", condition.getPaymentMethod());
	    }
	    
	    
	    if(EmptyChecker.isNotEmpty(condition.getOrderStatus())) {
	        String [] os = condition.getOrderStatus().split(",");
	        if(os.length > 1) {
	            // 支持多状态查询
	            List<Object> orders = new ArrayList<Object>();
	            for (String s : os) {
	                DBObject orderwhere = new BasicDBObject("order_status", s);
	                orders.add(orderwhere);
	            }
	            where.put(QueryOperators.OR, orders);
	        } else {
	            where.put("order_status", condition.getOrderStatus());
	        }
	    }
	    if(EmptyChecker.isNotEmpty(condition.getClientId())) {
	        where.put("client_id", condition.getClientId());
	    }
	    if(EmptyChecker.isNotEmpty(condition.getPhone())) {
	        if(EmptyChecker.isNotEmpty(condition.getRecvId())) {
	            DBObject obj = new BasicDBObject();
	            obj.put("phone", condition.getPhone());
	            obj.put("recv_id", condition.getRecvId());
	            where.put(QueryOperators.OR, obj);
	        } else {
	            where.put("phone", condition.getPhone());
	        }
	    }
	    
//      创建时间
        if(EmptyChecker.isNotEmpty(condition.getStartTime()) && EmptyChecker.isNotEmpty(condition.getEndTime()) && condition.getStartTime() > 0 && condition.getEndTime() > 0 ){
			BasicDBList values = new BasicDBList();
			values.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.GTE,condition.getStartTime())));
			values.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.LTE,condition.getEndTime())));
			where.put(QueryOperators.AND,values);
		}else if(EmptyChecker.isNotEmpty(condition.getStartTime()) && condition.getStartTime() > 0){
			where.put("create_time",new BasicDBObject(QueryOperators.GTE,condition.getStartTime()));
        }else if(EmptyChecker.isNotEmpty(condition.getEndTime()) && condition.getEndTime() > 0){
        	where.put("create_time",new BasicDBObject(QueryOperators.LTE,condition.getEndTime()));
        }
        
        //排序
        /*if(EmptyChecker.isNotEmpty(pageEntity.getOrderByColumn())){
            page.setSort(new Sort(pageEntity.getOrderByColumn(), OrderBy.DESC));
        }*/
        //如果是导出查询
        if("1".equals(flag)) {
        	 mongo.findByPageForExport(page, where, new BasicDBObject(),  MongoTableName.CB_ORDER, OrderMongo.class);
        } else {
        	 mongo.findByPageWithRedis(page, where, MongoTableName.CB_ORDER, OrderMongo.class);
        }
       
	    return page;
	}
	
	private MongoPage convert(MongoPage mongoPage, PageEntity<?> page) {
	    mongoPage.setLastPageNumber(page.getLastPageNumber());
        mongoPage.setLastRecordTime(page.getLastRecordTime());
        mongoPage.setFirstRecordTime(page.getFirstRecordTime());
        return mongoPage;
	}
	
	public List<RefundHistory> findRefundListByDBObject(DBObject queryObj) {
		List<RefundHistory> refundHisList = null;
		refundHisList = (List<RefundHistory>) mongo.findAll(queryObj, MongoTableName.CB_ORDER_REFUNDS, RefundHistory.class);
		return refundHisList;
	}
	

	public MongoPage findRefundPageByDBObject(DBObject queryObj, MongoPage mongoPage) {
		mongoPage = mongo.findByPageWithRedis(mongoPage, queryObj, MongoTableName.CB_ORDER_REFUNDS, RefundHistory.class);
		return mongoPage;
	}
	
	public MongoPage findPageForExport(DBObject queryObj, MongoPage mongoPage, String mongoTableName, Class<?> cls ) {
		mongoPage = mongo.findByPageForExport(mongoPage, queryObj,  new BasicDBObject(), mongoTableName, cls);
		return mongoPage;
	}


	@SuppressWarnings("unchecked")
	public List<SubOrderMongo> getSubOrderListByOrderId(String clientId, String orderId) {
		DBObject query = new BasicDBObject();
		query.put("client_id", clientId);
		query.put("order_id", orderId);
		List<SubOrderMongo> subOrderList = (List<SubOrderMongo>) mongo.findAll(query, MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
		return subOrderList;
	}
	
	
	public Map<String, Integer> getRefundedProduct(String orderId){
		Map<String, Integer> productMap = null;// key = subOrderId + "_" + productId
		List<RefundHistory> refundHisList = null;
		RefundHistory refundHis = null;
		DBObject queryObj = null;
		List<String> stateList = null;
		RefundShoppingInfo shoppingInfo = null;
		List<RefundProduct> productList = null;
		RefundProduct product = null;
		StringBuffer key = null;
		int quantity = 0;
		
		productMap = new HashMap<String, Integer>();
		queryObj = new BasicDBObject();
		key = new StringBuffer();
		
		queryObj.put(FieldMapping.ORDER_ID.getMongoField(), orderId);
		stateList = new ArrayList<String>();
		stateList.add(RefundState.REFUND_SUCCESS.getCode());
		stateList.add(RefundState.REFUND_MANUAL_SUCCESS.getCode());
		queryObj.put(FieldMapping.REFUND_STATE.getMongoField(), new BasicDBObject("$in", stateList));
		refundHisList = (List<RefundHistory>) mongo.findAll(queryObj, MongoTableName.CB_ORDER_REFUNDS, RefundHistory.class);
		
		
		// 按subOrderId + "_" + productId存储商品退款数量
		if (null != refundHisList && refundHisList.size() > 0){
			for (int i = 0; i < refundHisList.size(); i++){
				refundHis = refundHisList.get(i);
				
				shoppingInfo = refundHis.getShoppingInfo().get(0);
				productList = shoppingInfo.getProducts();
				
				for (int j = 0; j < productList.size(); j++){
					product = productList.get(j);
					key.delete(0, key.length());
					key.append(shoppingInfo.getSubOrderId());
					key.append("_");
					key.append(product.getProductId());
					quantity = product.getQuantity();
					if (!EmptyChecker.isEmpty(product.getVipQuantity())){
						quantity += product.getVipQuantity();
					}
					
					if (productMap.get(key.toString()) != null){
						productMap.put(key.toString(), (productMap.get(key.toString()) + quantity));
					} else {
						productMap.put(key.toString(), quantity);
					}
				}
			}
		}
		return productMap;
	}
	
	public RefundHistory getByRefundId(String refundId) {
		DBObject queryObj = null;
		RefundHistory refundHis = null;
		
		queryObj = new BasicDBObject();
		queryObj.put(FieldMapping.ID.getMongoField(), refundId);
		refundHis = mongo.findOne(queryObj, MongoTableName.CB_ORDER_REFUNDS, RefundHistory.class);
		
		return refundHis;
	}
	
	
	public OrderMongo findOrderByOrderId(String orderId){
		return mongo.findOneById(orderId, MongoTableName.CB_ORDER, OrderMongo.class);
	}
	
	public List<OrderMongo> queryOrderInfoByCondition(DBObject query){
		return (List<OrderMongo>) mongo.findAll(query, MongoTableName.CB_ORDER, OrderMongo.class);
	}
	
	public MongoPage queryOrderInfoByPage(DBObject query, MongoPage page){
		return mongo.findByPage(page, query, MongoTableName.CB_ORDER, OrderMongo.class);
	}
	
	public List<SubOrderMongo> querySubOrderInfoByCondition(DBObject query){
		return (List<SubOrderMongo>) mongo.findAll(query, MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
	}
	
	public MongoPage querySubOrderInfoByPage(DBObject query, MongoPage page){
		return mongo.findByPage(page, query, MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
	}
	
	public SubOrderMongo findSubOrderBySubOrderId(String subOrderId){
		return mongo.findOne(new BasicDBObject(FieldMapping.SUB_ORDER_ID.getMongoField(), subOrderId), MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
	}
	
	public RecvInfo queryRecvInfoByRecvId(String recvId){
		RecvInfo recvInfo = null;
		List<DBObject> pipeline = new ArrayList<DBObject>();
		pipeline.add(new BasicDBObject("$unwind", "$recv_info"));
		pipeline.add(new BasicDBObject("$match", new BasicDBObject("recv_info.recv_id", recvId)));
		Iterator<DBObject> it = mongo.findByPipeline(pipeline, MongoTableName.CB_MERCHANT_OUTLET_FAVORITE);
		while(it.hasNext()){
			DBObject db = it.next();
			recvInfo = JSonUtil.toObject(JSonUtil.toJSonString(db.get("recv_info")), RecvInfo.class);
		}
		return recvInfo;
	}
	
	
	public Boolean modifySubDeliveryState(String clientId, String orderId, String subOrderId, String deliveryState){
		DBObject where = new BasicDBObject();
		where.put(FieldMapping.CLIENT_ID.getMongoField(), clientId);
		where.put(FieldMapping.ORDER_ID.getMongoField(), orderId);
		where.put(FieldMapping.SUB_ORDER_ID.getMongoField(), subOrderId);
		
		DBObject modify = new BasicDBObject();
		modify.put("delivery_state", deliveryState);
		
		boolean flag = false;
		int result = mongo.update(modify, where, MongoTableName.CB_SUBORDER_PRODUCT, "$set");
//		SubOrderMongo sub =mongo.findAndModify(new BasicDBObject("$set", modify), where, MongoTableName.CB_SUBORDER_PRODUCT, false, SubOrderMongo.class);
		if(result != -1){
			flag = true;
		}
		return flag;
	}
	
}
