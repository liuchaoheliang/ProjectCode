package com.froad.db.mongo;

import java.util.List;

import com.froad.po.Ticket;
import com.mongodb.DBObject;

public interface TicketMongoService {
	
	/**
	 * 
	 * @Title: findByCondition 
	 * @Description: 根据条件查询Ticket
	 * @author: froad-huangyihao 2015年4月17日
	 * @modify: froad-huangyihao 2015年4月17日
	 * @param where
	 * @return
	 * @throws
	 */
	List<Ticket> findByCondition(DBObject where);
	
	/**
	 * 
	 * @Title: updateTicket 
	 * @Description: 原子性更新Ticket
	 * @author: froad-huangyihao 2015年4月17日
	 * @modify: froad-huangyihao 2015年4月17日
	 * @param set
	 * @param where
	 * @param modifier
	 * @return
	 * @throws
	 */
	Boolean updateTicket(DBObject set, DBObject where, String modifier);
	
	
	List<String> distinct(String field, DBObject query);
	
}
