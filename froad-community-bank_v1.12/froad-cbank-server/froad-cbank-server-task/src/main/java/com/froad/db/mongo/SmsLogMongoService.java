package com.froad.db.mongo;

import com.froad.po.mongo.SmsLogMongo;

public interface SmsLogMongoService {
	
	Boolean addSmsLog(SmsLogMongo smsLog);
}
