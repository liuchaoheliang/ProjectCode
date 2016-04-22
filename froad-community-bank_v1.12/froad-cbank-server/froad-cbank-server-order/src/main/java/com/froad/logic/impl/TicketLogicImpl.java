package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;

import com.froad.common.beans.ResultBean;
import com.froad.enums.FieldMapping;
import com.froad.enums.ResultCode;
import com.froad.enums.TicketStatus;
import com.froad.handler.TicketHandler;
import com.froad.handler.impl.TicketHandlerImpl;
import com.froad.logic.TicketLogic;
import com.froad.po.Ticket;
import com.froad.po.mongo.OrderMongo;
import com.froad.support.TicketSupport;
import com.froad.support.impl.TicketSupportImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class TicketLogicImpl implements TicketLogic {

	@Override
	public ResultBean addTicket(OrderMongo order) {
		ResultBean resultBean = null;
		TicketHandler handler = null;
		List<Ticket> ticketList = null;
		
		handler = new TicketHandlerImpl();
		
		ticketList = handler.generateTicket(order);
		
		if (null == ticketList || ticketList.size() == 0){
			resultBean = new ResultBean(ResultCode.failed);
		} else {
			resultBean = new ResultBean(ResultCode.success, "", ticketList);
		}
		
		return resultBean;
	}

	@Override
	public boolean isProductConsumed(String clientId, Long memberCode,
			String subOrderId, String productId) {
		TicketSupport ticketSupport = null;
		DBObject queryObj = null;
		List<Ticket> ticketList = null;
		boolean isConsumed = false;
		
		queryObj = new BasicDBObject();
		queryObj.put(FieldMapping.CLIENT_ID.getMongoField(), clientId);
		queryObj.put(FieldMapping.MEMBER_CODE.getMongoField(), String.valueOf(memberCode));
		queryObj.put(FieldMapping.SUB_ORDER_ID.getMongoField(), subOrderId);
		queryObj.put(FieldMapping.PRODUCT_ID.getMongoField(), productId);
		queryObj.put(FieldMapping.STATUS.getMongoField(), TicketStatus.consumed.getCode());
		
		ticketSupport = new TicketSupportImpl();
		ticketList = ticketSupport.getListByDBObject(queryObj);
		
		if (null != ticketList && ticketList.size() > 0){
			isConsumed = true;
		}
		
		return isConsumed;
	}

	@Override
	public boolean isTicketExist(String clientId, String subOrderId) {
		boolean isExist = false;
		TicketSupport ticketSupport = null;
		DBObject queryObj = null;
		List<Ticket> ticketList = null;
		List<String> statusList = null;
		
		queryObj = new BasicDBObject();
		queryObj.put(FieldMapping.CLIENT_ID.getMongoField(), clientId);
		queryObj.put(FieldMapping.SUB_ORDER_ID.getMongoField(), subOrderId);
		statusList = new ArrayList<String>();
		statusList.add(TicketStatus.consumed.getCode());
		statusList.add(TicketStatus.expired.getCode());
		statusList.add(TicketStatus.expired_refunded.getCode());
		statusList.add(TicketStatus.refunded.getCode());
		statusList.add(TicketStatus.refunding.getCode());
		statusList.add(TicketStatus.sent.getCode());
		queryObj.put(FieldMapping.STATUS.getMongoField(), new BasicDBObject("$in", statusList));
		
		ticketSupport = new TicketSupportImpl();
		ticketList = ticketSupport.getListByDBObject(queryObj);
		
		if (null != ticketList && ticketList.size() > 0){
			isExist = true;
		}
		
		return isExist;
	}

}
