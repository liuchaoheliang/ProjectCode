package com.froad.db.mongo;


import java.util.List;

import com.froad.po.mongo.RefundHistory;
import com.mongodb.DBObject;

public interface RefundMongoService {

	List<RefundHistory> findByCondition(DBObject query);
	
	
}

