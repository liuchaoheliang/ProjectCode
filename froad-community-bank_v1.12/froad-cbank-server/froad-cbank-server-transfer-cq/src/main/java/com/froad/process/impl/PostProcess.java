package com.froad.process.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.froad.config.ProcessServiceConfig;
import com.froad.enums.OrderStatus;
import com.froad.enums.RefundState;
import com.froad.enums.SubOrderRefundState;
import com.froad.logback.LogCvt;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.process.AbstractProcess;
import com.froad.util.Constans;
import com.froad.util.MongoTableName;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class PostProcess extends AbstractProcess {

	public PostProcess(String name, ProcessServiceConfig config) {
		super(name, config);
	}

	@Override
	public void before() {
	}

	@Override
	public void process() {
		// 退款后续处理
		processPostRefund();
	}
	
	protected void processPostRefund(){
		try {
			LogCvt.info("退款记录添加mongo完之后，线程停15秒，让mongo数据库读写同步");
			Thread.currentThread().sleep(15 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		}
		Map<String, Integer> refundMap = null;
		String subOrderId = null;
		Integer refundQuantity = 0;
		Iterator<String> it = null;
		SubOrderMongo subOrder = null;
		SubOrderRefundState refundState = null;
		
		// 查询所有成功退款记录，按子订单聚合，重庆旧数据一个订单只对应一种商品（数量可有多个）
		refundMap = getRefundSubOrderMap();
		it = refundMap.keySet().iterator();
		
		while (it.hasNext()){
			subOrderId = it.next();
			refundQuantity = refundMap.get(subOrderId);
			
			subOrder = findSubOrder(subOrderId);
			
			if (subOrder.getProducts().get(0).getQuantity() > refundQuantity) {
				LogCvt.info(new StringBuffer("子订单部分退款：").append(subOrderId).toString());
				refundState = SubOrderRefundState.REFUND_PART;
			} else {
				LogCvt.info(new StringBuffer("子订单全部退款：").append(subOrderId).toString());
				refundState = SubOrderRefundState.REFUND_SUCCESS;
				// 关闭大订单，重庆订单与子订单为一一对应关系
				closeOrder(subOrder.getOrderId());
			}
			
			// 更新子订单退款状态
			updateSubOrderRefundState(subOrderId, refundState);
		}
	}

	/**
	 * 获取退款子订单，key = subOrderId，value = quantity
	 * 
	 * @return
	 */
	private Map<String, Integer> getRefundSubOrderMap(){
		Map<String, Integer> resultMap = null;
		List<DBObject> pipeline = null;
		DBObject matchObj = null;
		DBObject matchFieldObj = null;
		DBObject unwindObj1 = null;
		DBObject unwindObj2 = null;
		DBObject groupObj = null;
		DBObject groupValueObj = null;
		DBObject sumObj = null;
		Iterator<DBObject> it = null;
		DBObject resultObj = null;
		
		// $match
		matchFieldObj = new BasicDBObject();
		matchFieldObj.put("client_id", Constans.clientId);
		matchFieldObj.put("refund_state", RefundState.REFUND_SUCCESS.getCode());
		matchObj = new BasicDBObject("$match", matchFieldObj);
		
		// $unwind shopping_info
		unwindObj1 = new BasicDBObject("$unwind", "$shopping_info");
		
		// $unwind shopping_info.products
		unwindObj2 = new BasicDBObject("$unwind", "$shopping_info.products");
		
		// $group
		groupValueObj = new BasicDBObject();
		groupValueObj.put("_id", "$shopping_info.sub_order_id");
		sumObj = new BasicDBObject("$sum", "$shopping_info.products.quantity");
		groupValueObj.put("quantity", sumObj);
		groupObj = new BasicDBObject("$group", groupValueObj);
		
		pipeline = new ArrayList<DBObject>();
		pipeline.add(matchObj);
		pipeline.add(unwindObj1);
		pipeline.add(unwindObj2);
		pipeline.add(groupObj);
		it = mongo.findByPipeline(pipeline, MongoTableName.CB_ORDER_REFUNDS);
		
		resultMap = new HashMap<String, Integer>();
		while (it.hasNext()){
			resultObj = it.next();
			
			if (resultObj.containsField("_id")){
				resultMap.put(resultObj.get("_id").toString(), Integer.valueOf(resultObj.get("quantity").toString()));
			}
		}
		
		return resultMap;
	}
	
	/**
	 * 根据子订单号查找子订单信息
	 * 
	 * @param subOrderId
	 * @return
	 */
	private SubOrderMongo findSubOrder(String subOrderId){
		SubOrderMongo subOrder = null;
		DBObject queryObj = null;
		
		queryObj = new BasicDBObject();
		queryObj.put("client_id", Constans.clientId);
		queryObj.put("sub_order_id", subOrderId);
		subOrder = mongo.findOne(queryObj, MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
		
		return subOrder;
	}
	
	/**
	 * 更新子订单退款状态
	 * 
	 * @param subOrderId
	 * @param refundState
	 */
	private void updateSubOrderRefundState(String subOrderId, SubOrderRefundState refundState){
		DBObject queryObj = null;
		DBObject valueObj = null;
		
		queryObj = new BasicDBObject();
		queryObj.put("client_id", Constans.clientId);
		queryObj.put("sub_order_id", subOrderId);
		
		valueObj = new BasicDBObject();
		valueObj.put("refund_state", refundState.getCode());
		if (refundState.equals(SubOrderRefundState.REFUND_SUCCESS)){
			// 全退款，更新订单状态为系统关闭
			valueObj.put("order_status", OrderStatus.sysclosed.getCode());
		}
		
		mongo.update(valueObj, queryObj, MongoTableName.CB_SUBORDER_PRODUCT, "$set");
	}
	
	/**
	 * 大订单下所有子订单已退款，关闭订单
	 * 
	 * @param orderId
	 */
	private void closeOrder(String orderId){
		DBObject queryObj = null;
		DBObject valueObj = null;
		
		queryObj = new BasicDBObject();
		queryObj.put("client_id", Constans.clientId);
		queryObj.put("_id", orderId);
		
		valueObj = new BasicDBObject();
		valueObj.put("order_status", OrderStatus.sysclosed.getCode());
		
		mongo.update(valueObj, queryObj, MongoTableName.CB_SUBORDER_PRODUCT, "$set");
	}
}
