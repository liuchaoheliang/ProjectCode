package com.froad.db.mongo.impl;

import java.util.List;

import com.froad.db.mongo.TicketMongoService;
import com.froad.po.Ticket;
import com.froad.util.MongoTableName;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class TicketMongoServiceImpl implements TicketMongoService {
	
	private MongoManager manager = new MongoManager();
	
	@Override
	public List<Ticket> findByCondition(DBObject where) {
		return (List<Ticket>)manager.findAll(where, MongoTableName.CB_TICKET, Ticket.class);
	}

	@Override
	public Boolean updateTicket(DBObject set, DBObject where, String modifier) {
//		DBObject value = new BasicDBObject(modifier, set);
//		Ticket ticket = manager.findAndModify(value, where, MongoTableName.CB_TICKET, false, Ticket.class);
//		int result = manager.update(value, where, MongoTableName.CB_TICKET, modifier);
		int result = manager.updateMulti(set, where, MongoTableName.CB_TICKET, modifier);
		return result > -1;
	}

	@Override
	public List<String> distinct(String field, DBObject query) {
		return manager.distinct(MongoTableName.CB_TICKET, field, query);
	}

}
