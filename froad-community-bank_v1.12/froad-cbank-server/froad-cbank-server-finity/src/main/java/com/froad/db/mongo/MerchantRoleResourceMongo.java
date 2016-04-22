/**
 * Project Name:froad-cbank-server-finity-1.3merchant
 * File Name:MerchantRoleResourceMongo.java
 * Package Name:com.froad.db.mongo
 * Date:2015年10月16日上午11:38:20
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.db.mongo;

import java.util.List;
import java.util.Map;

import com.froad.db.mongo.bean.BankUserResource;
import com.froad.db.mongo.bean.MerchantRoleResource;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.util.MongoTableName;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * ClassName:MerchantRoleResourceMongo
 * Reason:	 TODO ADD REASON.
 * Date:     2015年10月16日 上午11:38:20
 * @author   刘超 liuchao@f-road.com.cn
 * @version  
 * @see 	 
 */
public class MerchantRoleResourceMongo {
	private MongoManager manager = new MongoManager();
	
	@SuppressWarnings("unchecked")
	public MerchantRoleResource findMerchantRoleResource(Map<String, Object> map){
		DBObject queryObj = new BasicDBObject();
		queryObj.putAll(map);
		return  (MerchantRoleResource)manager.findOne(queryObj, MongoTableName.CB_MERCHANT_ROLE_RESOURCE, MerchantRoleResource.class);
	}	
	
	
}
