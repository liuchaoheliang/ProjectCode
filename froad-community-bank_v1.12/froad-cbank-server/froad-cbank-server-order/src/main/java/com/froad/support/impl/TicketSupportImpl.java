package com.froad.support.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;

import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mongo.page.MongoPage;
import com.froad.enums.FieldMapping;
import com.froad.enums.TicketStatus;
import com.froad.log.OrderLogs;
import com.froad.logic.impl.LogBeanClone;
import com.froad.po.Ticket;
import com.froad.support.TicketSupport;
import com.froad.util.MongoTableName;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class TicketSupportImpl implements TicketSupport {
	
	// 券表
	private String COLLECTION_NAME = MongoTableName.CB_TICKET;
	
	private MongoManager mongoManager = null;
	
	public TicketSupportImpl() {
		mongoManager = new MongoManager();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Ticket> getTicketByTicketId(String ticketId) {
		DBObject queryObj = null;

		queryObj = new BasicDBObject();
		queryObj.put(FieldMapping.TICKET_ID.getMongoField(), ticketId);
		
		return (List<Ticket>) mongoManager.findAll(queryObj, COLLECTION_NAME, Ticket.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Ticket> getTickets(String orderId, String subOrderId, String productId) {
		DBObject queryObj = null;

		queryObj = new BasicDBObject();
		queryObj.put(FieldMapping.ORDER_ID.getMongoField(), orderId);
		if (null != subOrderId){
			queryObj.put(FieldMapping.SUB_ORDER_ID.getMongoField(), subOrderId);
		}
		if (null != productId){
			queryObj.put(FieldMapping.PRODUCT_ID.getMongoField(), productId);
		}
		
		return (List<Ticket>) mongoManager.findAll(queryObj, COLLECTION_NAME, Ticket.class);
	}

	public void updateTicketState(Ticket ticket) {
		DBObject qyObj = null;
		DBObject valObj = null;
		
		qyObj = new BasicDBObject();
		if(StringUtils.isNotEmpty(ticket.getTicketId())) {
			qyObj.put(FieldMapping.TICKET_ID.getMongoField(), ticket.getTicketId());
		}
		if(StringUtils.isNotEmpty(ticket.getRefundId())) {
			qyObj.put(FieldMapping.REFUND_ID.getMongoField(), ticket.getRefundId());
		}
		if(ticket.getClientId() != null) {
			qyObj.put(FieldMapping.CLIENT_ID.getMongoField(), ticket.getClientId());
		}
		
		valObj = new BasicDBObject();
		valObj.put(FieldMapping.STATUS.getMongoField(), ticket.getStatus());
		mongoManager.updateMulti(valObj, qyObj, COLLECTION_NAME, "$set");
		
		// 保存日志 2015-11-10新增
		doUpdateLog(ticket.getTicketId());
	}
	
	@Override
	public void addTicket(Ticket ticket) {
		mongoManager.add(ticket, COLLECTION_NAME);
		
		//写入日志
		OrderLogs.addCoupon(LogBeanClone.cloneTicketLog(ticket));
	}

	@Override
	public List<Ticket> getValidTicketList(List<String> ticketIdList) {
		DBObject queryObj = null;
		List<Ticket> ticketList = null;
		
		queryObj = new BasicDBObject();
		queryObj.put(FieldMapping.STATUS.getMongoField(), TicketStatus.sent.getCode());
		
		ticketList = mongoManager.findByList(COLLECTION_NAME, queryObj,
				FieldMapping.TICKET_ID.getMongoField(), ticketIdList,
				Ticket.class);
		
		return ticketList;
	}
	
	/**
	 * @Override
	 * @param ticket
	 * @return    
	 * @see com.froad.support.TicketSupport#getTicketList(com.froad.po.Ticket)
	 */
	public List<Ticket> getTicketList(Ticket ticket) {
		DBObject queryObj = null;
		
		queryObj = new BasicDBObject();
		if (StringUtils.isNotEmpty(ticket.getClientId())) 
			queryObj.put(FieldMapping.CLIENT_ID.getMongoField(), ticket.getClientId());
		if (StringUtils.isNotEmpty(ticket.getTicketId())) 
			queryObj.put(FieldMapping.TICKET_ID.getMongoField(), ticket.getTicketId());
		if (StringUtils.isNotEmpty(ticket.getOrderId())) 
			queryObj.put(FieldMapping.ORDER_ID.getMongoField(), ticket.getOrderId());
		if (StringUtils.isNotEmpty(ticket.getType())) 
			queryObj.put(FieldMapping.TYPE.getMongoField(), ticket.getType());
		if (StringUtils.isNotEmpty(ticket.getStatus()))
			queryObj.put(FieldMapping.STATUS.getMongoField(), ticket.getStatus());
		if (StringUtils.isNotEmpty(ticket.getMemberCode()))
			queryObj.put(FieldMapping.MEMBER_CODE.getMongoField(), ticket.getMemberCode());
		if (StringUtils.isNotEmpty(ticket.getSubOrderId()))
			queryObj.put(FieldMapping.SUB_ORDER_ID.getMongoField(), ticket.getSubOrderId());
		if (StringUtils.isNotEmpty(ticket.getProductId())) 
			queryObj.put(FieldMapping.PRODUCT_ID.getMongoField(), ticket.getProductId());
		if (StringUtils.isNotEmpty(ticket.getMerchantId()))
			queryObj.put(FieldMapping.MERCHANT_ID.getMongoField(), ticket.getMerchantId());
		if (StringUtils.isNotEmpty(ticket.getOutletId()) && !StringUtils.equals("0", ticket.getOutletId())) 
			queryObj.put(FieldMapping.OUTLET_ID.getMongoField(), ticket.getOutletId());
		if (null != ticket.getMerchantUserId() && ticket.getMerchantUserId() != 0)
			queryObj.put(FieldMapping.MERCHANT_USER_ID.getMongoField(), ticket.getMerchantUserId());
		if (StringUtils.isNotEmpty(ticket.getMerchantUserName())) 
			queryObj.put(FieldMapping.MERCHANT_USER_NAME.getMongoField(), ticket.getMerchantUserName());
		
		DBObject orderby = new BasicDBObject();
		orderby.put(FieldMapping.CREATE_TIME.getMongoField(), 1);
		return (List<Ticket>) mongoManager.findAll(queryObj,orderby, COLLECTION_NAME, Ticket.class);
	}
	
	/**
	 * 根据子订单id,提货人id,商户id,门店id获取有效券券详细列表
	 * @param memberCode
	 * @param subOrderId
	 * @param merchantId
	 * @param outletId
	 * @return    
	 * @see com.froad.support.TicketSupport#getValidTicketList(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Ticket> getValidTicketList(String memberCode, String subOrderId,String merchantId, String outletId){
		DBObject queryObj = null;
		
		queryObj = new BasicDBObject();
		queryObj.put(FieldMapping.STATUS.getMongoField(), TicketStatus.sent.getCode());
		
		if (StringUtils.isNotEmpty(memberCode))
			queryObj.put(FieldMapping.MEMBER_CODE.getMongoField(), memberCode);
		if (StringUtils.isNotEmpty(subOrderId))
			queryObj.put(FieldMapping.SUB_ORDER_ID.getMongoField(), subOrderId);
		if (StringUtils.isNotEmpty(merchantId))
			queryObj.put(FieldMapping.MERCHANT_ID.getMongoField(), merchantId);
		if (StringUtils.isNotEmpty("0".equals(outletId) ? null : outletId)){
			queryObj.put(FieldMapping.OUTLET_ID.getMongoField(), outletId);
		}
		DBObject orderby = new BasicDBObject();
		orderby.put(FieldMapping.CREATE_TIME.getMongoField(), 1);
		return (List<Ticket>) mongoManager.findAll(queryObj,orderby, COLLECTION_NAME, Ticket.class);
	}

	@Override
	public Ticket getUniqueTicket(String ticketId, String status) {
		DBObject queryObj = null;
		Ticket ticket = null;
		
		queryObj = new BasicDBObject();
		queryObj.put(FieldMapping.TICKET_ID.getMongoField(), ticketId);
		queryObj.put(FieldMapping.STATUS.getMongoField(), status);
		
		ticket = mongoManager.findOne(queryObj, COLLECTION_NAME, Ticket.class);
		
		return ticket;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Ticket> getListByDBObject(DBObject queryObj) {
		List<Ticket> ticketList = null;
		
		ticketList = (List<Ticket>) mongoManager.findAll(queryObj, COLLECTION_NAME, Ticket.class);
		
		return ticketList;
	}

	@Override
	public MongoPage getTicketPageByDBObject(DBObject queryObj, MongoPage page) {
		MongoPage resultPage = null;
		
		resultPage = mongoManager.findByPageWithRedis(page, queryObj, COLLECTION_NAME, Ticket.class);
		
		return resultPage;
	}
	
	public MongoPage exportTicketPageByDBObject(DBObject queryObj, MongoPage page) {
		
		return mongoManager.findByPageForExport(page, queryObj, new BasicDBObject(), COLLECTION_NAME, Ticket.class);
	}
	
	@Override
	public MongoPage getTicketPageByConsumeTime(DBObject queryObj, MongoPage page) {
		MongoPage resultPage = null;
		resultPage = mongoManager.findByPageWithSortObject(page, queryObj, new BasicDBObject(), COLLECTION_NAME, Ticket.class);
		
		return resultPage;
	}
	
	public MongoPage getTicketPageByDBObjectNoRedis(DBObject queryObj, MongoPage page) {
		
		return mongoManager.findByPage(page, queryObj, COLLECTION_NAME, Ticket.class);
		
	}
	
	/**
	 * 根据条件查询总金额
	 * @param queryObj
	 * @return    
	 * @see com.froad.support.TicketSupport#getTicketTotalAmountDBObject(com.mongodb.DBObject)
	 */
	@Override
	public int getTicketTotalAmountDBObject(DBObject queryObj){
		int totalAmount = 0;
		List<DBObject> pipeline = new ArrayList<DBObject>();
		
		
		BasicDBObject where = new BasicDBObject();
		where.put("$match", queryObj);
		pipeline.add(where);
		
		BasicDBObject group = new BasicDBObject();
//		{ $group: { _id: null, totalAmount: { $sum: "$price" } } }
//		group.put("$group", new BasicDBObject().append("_id", null).append("totalAmount", new BasicDBObject("$sum", "$price")));
		
		
//		{ $group: { _id: null, totalAmount: { $sum: { $multiply: [ "$price", "$quantity" ] } } } }
		BasicDBList multiply =new BasicDBList();
		multiply.add("$price");
		multiply.add("$quantity");
		group.put("$group", new BasicDBObject().append("_id", null).append("totalAmount", new BasicDBObject("$sum", new BasicDBObject("$multiply", multiply))));
		
		pipeline.add(group);
		
		Iterator<DBObject> it = mongoManager.findByPipeline(pipeline, COLLECTION_NAME);
		if (null != it) {
//			it = aggOutput.results().iterator();
			while (it.hasNext()) {
				DBObject obj = it.next();
				Integer totalAmount_ = (Integer) obj.get("totalAmount");
				if(null != totalAmount_)
					return totalAmount_;
			}
		}
		return totalAmount;
	}

	@Override
	public void updateByTicketIdList(DBObject valueObj,
			List<String> ticketIdList) {
		DBObject queryObj = null;
		
		queryObj = new BasicDBObject();
		queryObj.put(FieldMapping.TICKET_ID.getMongoField(), new BasicDBObject("$in", ticketIdList));
		
		mongoManager.updateMulti(valueObj, queryObj, COLLECTION_NAME, "$set");
		
		//保存日志 2015-11-10新增
		for(String ticketId : ticketIdList){
			doUpdateLog(ticketId);
		}
	}

	@Override
	public void updateById(DBObject valueObj, ObjectId id) {
		DBObject queryObj = null;
		
		queryObj = new BasicDBObject();
		queryObj.put(FieldMapping.ID.getMongoField(), id);
		
		mongoManager.update(valueObj, queryObj, COLLECTION_NAME, "$set");
	}
	
	@Override
	public void update(DBObject valueObj, DBObject where) {
		mongoManager.update(valueObj, where, COLLECTION_NAME, "$set");
	}

	@Override
	public Ticket findAndModifyByDBObject(DBObject queryObj, DBObject updateObj) {
		Ticket ticket = null;
		
		ticket = mongoManager.findAndModify(updateObj, queryObj, COLLECTION_NAME, false, Ticket.class);
		
		return ticket;
	}

	@Override
	public void updateMultiByDBObject(DBObject queryObj, DBObject updateObj) {
		mongoManager.updateMulti(updateObj, queryObj, COLLECTION_NAME, "$set");
		
		//写入日志
		List<Ticket> tlist =  (List<Ticket>) mongoManager.findAll(queryObj, COLLECTION_NAME, Ticket.class);
		if(tlist != null && tlist.size() == 0){
			for(Ticket t : tlist){
				OrderLogs.updateCoupon(LogBeanClone.cloneTicketLog(t));
			}
		}
	}

	@Override
	public int getTotalCount(DBObject queryObj) {
		return mongoManager.getCount(queryObj, COLLECTION_NAME);
	}

	@Override
	public void removeByDBObject(DBObject queryObj) {
		mongoManager.remove(queryObj, COLLECTION_NAME);
	}

	@Override
	public Iterator<DBObject> findByPipeline(List<DBObject> pipeline) {
		return mongoManager.findByPipeline(pipeline, COLLECTION_NAME);
	}

	/**
	 * 增加更新券码的日志
	 * Function : doUpdateLog<br/>
	 * 2015年11月10日 上午11:12:55 <br/>
	 *  
	 * @author KaiweiXiang
	 * @param ticketId
	 */
	private void doUpdateLog(String ticketId){
		if(ticketId == null || "".equals(ticketId)){
			return;
		}
		try {
			List<Ticket> tlist = getTicketByTicketId(ticketId);
			if(tlist != null && tlist.size() == 0){
				for(Ticket t : tlist){
					OrderLogs.updateCoupon(LogBeanClone.cloneTicketLog(t));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
