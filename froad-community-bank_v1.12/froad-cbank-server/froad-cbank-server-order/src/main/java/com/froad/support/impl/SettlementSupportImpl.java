package com.froad.support.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mongo.page.MongoPage;
import com.froad.db.mongo.page.OrderBy;
import com.froad.db.mongo.page.Sort;
import com.froad.enums.FieldMapping;
import com.froad.enums.OrderType;
import com.froad.enums.SettlementStatus;
import com.froad.log.OrderLogs;
import com.froad.logic.impl.LogBeanClone;
import com.froad.po.PaymentMongoEntity;
import com.froad.po.Ticket;
import com.froad.po.settlement.Settlement;
import com.froad.po.settlement.SettlementReq;
import com.froad.support.SettlementSupport;
import com.froad.util.JSonUtil;
import com.froad.util.MongoTableName;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;
import com.mongodb.util.JSON;

/**
 *  结算数据支持接口
  * @ClassName: SettlementSupportImpl
  * @Description: TODO
  * @author share 2015年3月26日
  * @modify share 2015年3月26日
 */
public class SettlementSupportImpl implements SettlementSupport {
	
	private MongoManager mongo = new MongoManager();
	
	/**
	 *  根据订单ID查询结算记录
	  * @Title: querySettlement
	  * @Description: TODO
	  * @author: share 2015年3月26日
	  * @modify: share 2015年3月26日
	  * @param @param orderId
	  * @param @return
	  * @throws
	  * @see com.froad.support.SettlementSupport#querySettlement(java.lang.String)
	 */
	@Override
	public List<Settlement> querySettlement(String orderId) {
		// TODO Auto-generated method stub
		DBObject dbo = new BasicDBObject();
		dbo.put("order_id", orderId);
		return (List<Settlement>) mongo.findAll(dbo, MongoTableName.CB_SETTLEMENT, Settlement.class);
	}
	
	/**
	 *  获取结算记录
	  * @Title: querySettlement
	  * @Description: TODO
	  * @author: share 2015年5月16日
	  * @modify: share 2015年5月16日
	  * @param @param orderId
	  * @param @return
	  * @throws
	  * @see com.froad.support.SettlementSupport#querySettlement(java.lang.String)
	 */
	@Override
	public int countSettlement(String orderId) {
		// TODO Auto-generated method stub
		DBObject dbo = new BasicDBObject();
		dbo.put("order_id", orderId);
		return mongo.getCount(dbo, MongoTableName.CB_SETTLEMENT);
	}
	
	
	/**
	 *  根据ticketId查询结算记录
	  * @Title: querySettlement
	  * @Description: TODO
	  * @author: share 2015年3月27日
	  * @modify: share 2015年3月27日
	  * @param @param orderId
	  * @param @param subOrderId
	  * @param @param ticketId
	  * @param @return
	  * @throws
	  * @see com.froad.support.SettlementSupport#querySettlement(java.lang.String, java.lang.String, java.lang.String)
	 */
	public int querySettlement(String orderId,String subOrderId,String ticketId) {
		// TODO Auto-generated method stub
		DBObject dbo = new BasicDBObject();
		dbo.put("order_id", orderId);
		dbo.put("sub_order_id", subOrderId);
		dbo.put("tickets", ticketId);
		return mongo.getCount(dbo, MongoTableName.CB_SETTLEMENT);
	}

	/**
	 *  添加面对面结算记录
	  * @Title: createQrcideSettlement
	  * @Description: TODO
	  * @author: share 2015年3月26日
	  * @modify: share 2015年3月26日
	  * @param @param settlement
	  * @param @return
	  * @throws
	  * @see com.froad.support.SettlementSupport#createQrcideSettlement(com.froad.po.Settlement)
	 */
	@Override
	public boolean createSettlement(Settlement settlement) {
		DBObject dbo = (DBObject)JSON.parse(JSonUtil.toJSonString(settlement));
		int result = mongo.add(dbo, MongoTableName.CB_SETTLEMENT);
		
		if(result != -1){
			//2015-11-25 记录结算创建日志
			OrderLogs.settlementCreate(LogBeanClone.cloneSettlementLog(settlement));
		}
		return result != -1;
	}

	/**
	 *  跟新结算状态
	  * @Title: upateSettlement
	  * @Description: TODO
	  * @author: share 2015年3月27日
	  * @modify: share 2015年3月27日
	  * @param @param code
	  * @param @param paymentId
	  * @throws
	  * @see com.froad.support.SettlementSupport#upateSettlement(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean upateSettlement(String settlementId,String code, String paymentId,String ...remark) {
		// TODO Auto-generated method stub
		DBObject where = new BasicDBObject();
		where.put("settlement_id", settlementId);
		
		DBObject value = new BasicDBObject();
		value.put("payment_id", paymentId);
		value.put("settle_state", code);
		if(remark.length > 0){
			value.put("remark", remark[0]);
		}
		boolean flag = mongo.updateMulti(value, where, MongoTableName.CB_SETTLEMENT, "$set") != -1;
		
		if(flag){
			//2015-11-25 记录结算更新日志
			OrderLogs.settlementUpdate(LogBeanClone.cloneSettlementLog(queryBySettlementId(settlementId)));
		}
		return flag;
	}

	/**
	 *  根据结算ID查询结算信息
	  * @Title: queryBySettlementId
	  * @Description: TODO
	  * @author: share 2015年3月27日
	  * @modify: share 2015年3月27日
	  * @param @param settlementId
	  * @param @return
	  * @throws
	  * @see com.froad.support.SettlementSupport#queryBySettlementId(java.lang.String)
	 */
	@Override
	public Settlement queryBySettlementId(String settlementId) {
		// TODO Auto-generated method stub
		DBObject where = new BasicDBObject();
		where.put("settlement_id", settlementId);
		return mongo.findOne(where, MongoTableName.CB_SETTLEMENT, Settlement.class);
	}

	/**
	 *  更新结算
	  * @Title: upateBySettlementId
	  * @Description: TODO
	  * @author: share 2015年3月27日
	  * @modify: share 2015年3月27日
	  * @param @param settlementId
	  * @param @param code
	  * @param @param remark
	  * @throws
	  * @see com.froad.support.SettlementSupport#upateBySettlementId(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Settlement upateSettlementing(String settlementId,String remark) {
		// TODO Auto-generated method stub
		DBObject where = new BasicDBObject();
		where.put("settle_state", SettlementStatus.unsettlemnt.getCode());
		where.put("settlement_id", settlementId);
		
		DBObject value = new BasicDBObject();
		value.put("settle_state", SettlementStatus.settlementing.getCode());
		value.put("remark", remark);
		
		return mongo.findAndModify(new BasicDBObject("$set",value), where, MongoTableName.CB_SETTLEMENT, false, Settlement.class);
	}
	
	
	/**
	 *  根据结算ID查询结算信息
	  * @Title: queryBySettlementId
	  * @Description: TODO
	  * @author: share 2015年3月27日
	  * @modify: share 2015年3月27日
	  * @param @param settlementId
	  * @param @return
	  * @throws
	  * @see com.froad.support.SettlementSupport#queryBySettlementId(java.lang.String)
	 */
	@Override
	public Settlement queryById(String id) {
		// TODO Auto-generated method stub
		DBObject where = new BasicDBObject();
		where.put("_id", JSonUtil.toObjectId(id));
		return mongo.findOne(where, MongoTableName.CB_SETTLEMENT, Settlement.class);
	}

	/**
	 *  更新结算ID
	  * @Title: upateBySettlementId
	  * @Description: TODO
	  * @author: share 2015年3月27日
	  * @modify: share 2015年3月27日
	  * @param @param settlementId
	  * @param @param code
	  * @param @param remark
	  * @throws
	  * @see com.froad.support.SettlementSupport#upateBySettlementId(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean upateById(String id, String code,String remark) {
		// TODO Auto-generated method stub
		DBObject where = new BasicDBObject();
		where.put("_id",JSonUtil.toObjectId(id));
		
		DBObject value = new BasicDBObject();
		if(remark != null){
			value.put("remark", remark);
		}
		value.put("settle_state", code);
		
		return mongo.update(value, where, MongoTableName.CB_SETTLEMENT, "$set") != -1;
	}

	/**
	 *  删除结算记录
	  * @Title: deleteSettlement
	  * @Description: TODO
	  * @author: share 2015年3月28日
	  * @modify: share 2015年3月28日
	  * @param @param settlementId
	  * @param @return
	  * @throws
	  * @see com.froad.support.SettlementSupport#deleteSettlement(java.lang.String)
	 */
	@Override
	public boolean deleteSettlement(String settlementId) {
		// TODO Auto-generated method stub
		DBObject where = new BasicDBObject();
		where.put("settlement_id", settlementId);
		
		return mongo.findAndRemove(where, MongoTableName.CB_SETTLEMENT)!=-1;
	}

	/**
	 *  分页查询
	  * @Title: queryByPage
	  * @Description: TODO
	  * @author: share 2015年3月28日
	  * @modify: share 2015年3月28日
	  * @param @param req
	  * @param @return
	  * @throws
	  * @see com.froad.support.SettlementSupport#queryByPage(com.froad.po.settlement.SettlementReq)
	 */
	@Override
	public MongoPage queryByPage(SettlementReq req) {
		// TODO Auto-generated method stub
		DBObject dbo = new BasicDBObject();
		MongoPage page = buildFindByPageCondition(req, dbo);
		return mongo.findByPageWithRedis(page, dbo,  MongoTableName.CB_SETTLEMENT, Settlement.class);
	}
	
	public MongoPage exportByPage(SettlementReq req) {
		// TODO Auto-generated method stub
		DBObject dbo = new BasicDBObject();
		MongoPage page = buildFindByPageCondition(req, dbo);
		return mongo.findByPageForExport(page, dbo, new BasicDBObject(), MongoTableName.CB_SETTLEMENT, Settlement.class);
	}

	/**
	 * buildFindByPageCondition:构建分页查询结算条件
	 *
	 * @author vania
	 * 2015年9月16日 下午7:01:09
	 * @param req
	 * @param dbo
	 * @return
	 * 
	 */
	private MongoPage buildFindByPageCondition(SettlementReq req, DBObject dbo) {
		MongoPage page = req.getPage();
		page.setSort(new Sort("create_time", OrderBy.DESC));
		
		// 订单编号
		if(!StringUtils.isEmpty(req.getOrderId())){
//			dbo.put("order_id", req.getOrderId());
			BasicDBList or = new BasicDBList();
			or.add(new BasicDBObject("order_id", req.getOrderId()));
			or.add(new BasicDBObject("sub_order_id", req.getOrderId()));
			dbo.put(QueryOperators.OR, or);
		}
		
		// 类型
		String type = req.getType();
		if(StringUtils.isNotBlank(type)){
			dbo.put(FieldMapping.TYPE.getMongoField(), type);
		}
		// 账单编号
		String billNo = req.getBillNo();
		if (StringUtils.isNotBlank(billNo)) {
//			PaymentMongoEntity pay = mongo.findOne(new BasicDBObject(FieldMapping.BILL_NO.getMongoField(), billNo), MongoTableName.CB_PAYMENT, PaymentMongoEntity.class);
			PaymentMongoEntity pay = mongo.findOne(new BasicDBObject(FieldMapping.BILL_NO.getMongoField(), billNo), new BasicDBObject(FieldMapping.PAYMENT_ID.getMongoField(), 1), MongoTableName.CB_PAYMENT, PaymentMongoEntity.class);
			if (pay == null || StringUtils.isBlank(pay.getPayment_id())) {// 根据订单号查不到直接返回
				return page;
			}
			dbo.put(FieldMapping.PAYMENT_ID.getMongoField(), pay.getPayment_id());
			
		}
		
		// 客户端ID
		if(req.getClientId() != null && !"".equals(req.getClientId())){
			dbo.put("client_id", req.getClientId());
		}
		// 开始时间&&结束时间
		if(req.getBegDate() > 0 || req.getEndDate() > 0){
			if(req.getBegDate() > 0 && req.getEndDate() > 0){
				dbo.put("create_time", new BasicDBObject(QueryOperators.GTE,req.getBegDate()).append(QueryOperators.LTE,req.getEndDate()));
			}else if(req.getBegDate() > 0){
				dbo.put("create_time", new BasicDBObject(QueryOperators.GTE,req.getBegDate()));
			}else if(req.getEndDate() > 0){
				dbo.put("create_time", new BasicDBObject(QueryOperators.LTE,req.getEndDate()));
			}
		}
		// 商户名称
		/*if(req.getMerchantName() != null){
			dbo.put("merchant_id", new BasicDBObject(QueryOperators.IN,req.getMerchantName()));
		}*/
		if(req.getMerchantName() != null) {
            Pattern pattern = Pattern.compile("^.*" + req.getMerchantName()+ ".*$", Pattern.CASE_INSENSITIVE);
            dbo.put("merchant_name", pattern);
        }
		// 门店名称
		/*if(req.getOutletName() != null){
			dbo.put("outlet_id", new BasicDBObject(QueryOperators.IN,req.getOutletName()));
		}*/
		if(req.getOutletName() != null) {
            Pattern pattern = Pattern.compile("^.*" + req.getOutletName()+ ".*$", Pattern.CASE_INSENSITIVE);
            dbo.put("outlet_name", pattern);
        }
		
		BasicDBList and = new BasicDBList();
		// 结算状态
		if(req.getSettleState() != null){
//			dbo.put("settle_state", req.getSettleState().getCode());
			and.add(new BasicDBObject("settle_state", req.getSettleState().getCode()));
		}else{
//			dbo.put("settle_state", new BasicDBObject(QueryOperators.NE,SettlementStatus.settlementNoInvalid.getCode()));
			and.add(new BasicDBObject("settle_state", new BasicDBObject(QueryOperators.NE,SettlementStatus.settlementNoInvalid.getCode())));
		}
		
		List<String> in = req.getInSettleState();
		if (in != null && in.size() > 0) {
			and.add(new BasicDBObject("settle_state", new BasicDBObject(QueryOperators.IN, in)));
		}
		List<String> notin = req.getNotInSettleState();
		if (notin != null && notin.size() > 0) {
			and.add(new BasicDBObject("settle_state", new BasicDBObject(QueryOperators.NIN, notin)));
		}
		dbo.put(QueryOperators.AND, and);
		
		String ticketId = req.getTicketId(); // 券id
		if (StringUtils.isNotEmpty(ticketId)) { // 查询券表
			dbo.put("tickets", ticketId);
		}
		return page;
	}
	
	/**
	 *  按条件查询
	  * @Title: queryList
	  * @Description: TODO
	  * @author: share 2015年3月28日
	  * @modify: share 2015年3月28日
	  * @param @param req
	  * @param @return
	  * @throws
	  * @see com.froad.support.SettlementSupport#queryList(com.froad.po.settlement.SettlementReq)
	 */
	@Override
	public List<Settlement> queryList(SettlementReq req) {
		// TODO Auto-generated method stub
		
		DBObject dbo = new BasicDBObject();
		// 订单编号
		if(!StringUtils.isEmpty(req.getOrderId())){
			dbo.put("order_id", req.getOrderId());
		}
		// 客户端ID
		if(req.getClientId() != null && !"".equals(req.getClientId())){
			dbo.put("client_id", req.getClientId());
		}
		// 开始时间&&结束时间
		if(req.getBegDate() > 0 || req.getEndDate() > 0){
			if(req.getBegDate() > 0 && req.getEndDate() > 0){
				dbo.put("create_time", new BasicDBObject(QueryOperators.GTE,req.getBegDate()).append(QueryOperators.LTE,req.getEndDate()));
			}else if(req.getBegDate() > 0){
				dbo.put("create_time", new BasicDBObject(QueryOperators.GTE,req.getBegDate()));
			}else if(req.getEndDate() > 0){
				dbo.put("create_time", new BasicDBObject(QueryOperators.LTE,req.getEndDate()));
			}
		}
		// 商户名称
		/*if(req.getMerchantIds() != null){
			dbo.put("merchant_id", new BasicDBObject(QueryOperators.IN,req.getMerchantIds()));
		}*/
		if(req.getMerchantName() != null) {
            Pattern pattern = Pattern.compile("^.*" + req.getMerchantName()+ ".*$", Pattern.CASE_INSENSITIVE);
            dbo.put("merchant_name", pattern);
        }
		// 门店名称
		/*if(req.getOutletIds() != null){
			dbo.put("outlet_id", new BasicDBObject(QueryOperators.IN,req.getOutletIds()));
		}*/
		if(req.getOutletName() != null) {
            Pattern pattern = Pattern.compile("^.*" + req.getOutletName()+ ".*$", Pattern.CASE_INSENSITIVE);
            dbo.put("outlet_name", pattern);
        }
		// 结算状态
		if(req.getSettleState() != null){
			dbo.put("settle_state", req.getSettleState().getCode());
		}else{
			dbo.put("settle_state", new BasicDBObject(QueryOperators.NE,SettlementStatus.settlementNoInvalid.getCode()));
		}
		return (List<Settlement>) mongo.findAll(dbo, MongoTableName.CB_SETTLEMENT, Settlement.class);
	}
	
	@Override
	public List<Settlement> queryListBySubOrderId(String subOrdeId) {
	    DBObject where = new BasicDBObject();
	    where.put("sub_order_id", subOrdeId);
	    return (List<Settlement>) mongo.findAll(where, MongoTableName.CB_SETTLEMENT, Settlement.class);
	}
	
	/**
	 *  查询待结算记录
	  * @Title: querySettlementBySubOrderId
	  * @Description: TODO
	  * @author: share 2015年4月2日
	  * @modify: share 2015年4月2日
	  * @param @param subOrderId
	  * @param @return
	  * @throws
	  * @see com.froad.support.SettlementSupport#querySettlementBySubOrderId(java.lang.String)
	 */
	@Override
	public List<Settlement> querySettlementBySubOrderId(String subOrderId) {
		// TODO Auto-generated method stub
		DBObject dbo = new BasicDBObject();
		// 订单编号
		dbo.put("sub_order_id", subOrderId);
		dbo.put("settle_state", SettlementStatus.unsettlemnt.getCode());
		
		return (List<Settlement>) mongo.findAll(dbo, MongoTableName.CB_SETTLEMENT, Settlement.class);
	}

	/**
	 *  按子订单ID修改结算记录
	  * @Title: updateSettlementBySubOrderId
	  * @Description: TODO
	  * @author: share 2015年4月2日
	  * @modify: share 2015年4月2日
	  * @param @param subOrderId
	  * @param @param code
	  * @param @param remark
	  * @param @return
	  * @throws
	  * @see com.froad.support.SettlementSupport#updateSettlementBySubOrderId(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean updateSettlementBySubOrderId(String subOrderId, String code,String remark) {
		// TODO Auto-generated method stub
		DBObject where = new BasicDBObject();
		where.put("sub_order_id", subOrderId);
		where.put("settle_state", SettlementStatus.unsettlemnt.getCode());
		
		DBObject value = new BasicDBObject();
		if(remark != null){
			value.put("remark", remark);
		}
		value.put("settle_state", code);
		
		return mongo.updateMulti(value, where, MongoTableName.CB_SETTLEMENT, "$set") != -1;
	}

	/**
	 *  查询结算中的支付ID
	  * @Title: querySettlementing
	  * @Description: TODO
	  * @author: share 2015年4月9日
	  * @modify: share 2015年4月9日
	  * @param @return
	  * @throws
	  * @see com.froad.support.SettlementSupport#querySettlementing()
	 */
	@Override
	public List<String> querySettlementing() {
		// TODO Auto-generated method stub
		DBObject query = new BasicDBObject();
		query.put("settle_state", SettlementStatus.settlementing.getCode());
		return mongo.distinct(MongoTableName.CB_SETTLEMENT, "payment_id", query);
	}

	/**
	 *  按支付ID更新结算状态
	  * @Title: updateByPaymentId
	  * @Description: TODO
	  * @author: share 2015年4月9日
	  * @modify: share 2015年4月9日
	  * @param @param paymentId
	  * @param @param code
	  * @param @return
	  * @throws
	  * @see com.froad.support.SettlementSupport#updateByPaymentId(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean upateByPaymentId(String paymentId, String code,String remark) {
		// TODO Auto-generated method stub
		DBObject where = new BasicDBObject();
		where.put("payment_id", paymentId);
		where.put("settle_state", SettlementStatus.settlementing.getCode());
		
		DBObject value = new BasicDBObject();
		value.put("settle_state", code);
		
		return mongo.updateMulti(value, where, MongoTableName.CB_SETTLEMENT, "$set") != -1;
	}

	/**
	 *  根据订单ID商品ID获取结算信息
	  * @Title: queryByProductId
	  * @Description: TODO
	  * @author: share 2015年4月14日
	  * @modify: share 2015年4月14日
	  * @param @param orderId
	  * @param @param productId
	  * @param @return
	  * @throws 
	  * @see com.froad.support.SettlementSupport#queryByProductId(java.lang.String, java.lang.String)
	 */
	@Override
	public Settlement queryByProductId(String orderId, String productId) {
		// TODO Auto-generated method stub
		DBObject where = new BasicDBObject();
		where.put("order_id", orderId);
		where.put("product_id", productId);
		
		return mongo.findOne(where, MongoTableName.CB_SETTLEMENT, Settlement.class);
		
	}
	
	@Override
	public List<Settlement> querySettlement(String subOrderId, String productId) {
	    DBObject where = new BasicDBObject();
	    where.put("sub_order_id", subOrderId);
        where.put("product_id", productId);
	    return (List<Settlement>) mongo.findAll(where, MongoTableName.CB_SETTLEMENT, Settlement.class);
	}

	@Override
	public List<Settlement> querySettlementStatusList(List<String> tickets) {
		List<DBObject> querys = new ArrayList<DBObject>();
		for (String ids : tickets) {
			 DBObject where = new BasicDBObject();
			 where.put("tickets", ids);
			 querys.add(where);
		}
		return mongo.findOneOfListResult(querys,  MongoTableName.CB_SETTLEMENT, Settlement.class);
	}
	@Override
	public Map<String, String> querySettlementStatusMap(List<String> tickets) {
		Map<String, String> map = new HashMap<String, String>();
		List<DBObject> querys = new ArrayList<DBObject>();
		for (String ids : tickets) {
			DBObject where = new BasicDBObject();
			where.put("tickets", ids);
			querys.add(where);
		}
		List<String> keyColl = new ArrayList<String>();
		keyColl.add(FieldMapping.SETTLE_STATE.getMongoField()); // 查询结算状态settle_state
		keyColl.add(FieldMapping.TICKETS.getMongoField()); // 查询券TICKETS
		List<DBObject> stateList = mongo.findOneOfListResult(querys, keyColl, MongoTableName.CB_SETTLEMENT);
		for (DBObject dbObject : stateList) {
			if(null == dbObject)continue;
			List<String> ticketList = (List<String>) dbObject.get(FieldMapping.TICKETS.getMongoField());
			for (String ti : ticketList) {
				map.put(ti, dbObject.get(FieldMapping.SETTLE_STATE.getMongoField()).toString());
			}
		}
		return map;
	}

	@Override
	public Iterator<DBObject> findByPipeline(List<DBObject> pipeline) {
		return mongo.findByPipeline(pipeline, MongoTableName.CB_SETTLEMENT);
	}
}

