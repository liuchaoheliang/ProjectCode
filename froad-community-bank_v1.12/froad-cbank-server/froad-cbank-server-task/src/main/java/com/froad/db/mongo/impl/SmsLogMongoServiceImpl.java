package com.froad.db.mongo.impl;

import com.froad.db.mongo.SmsLogMongoService;
import com.froad.po.mongo.SmsLogMongo;
import com.froad.util.MongoTableName;

public class SmsLogMongoServiceImpl implements SmsLogMongoService {
	
	private MongoManager manager = new MongoManager();
	
	@Override
	public Boolean addSmsLog(SmsLogMongo smsLog) {
		int result = manager.add(smsLog, MongoTableName.CB_SMS_LOG);
		return result != -1;
	}

}
