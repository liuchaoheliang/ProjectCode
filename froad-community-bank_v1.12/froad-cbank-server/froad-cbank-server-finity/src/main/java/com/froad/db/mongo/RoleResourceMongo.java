/**
 * Project Name:froad-cbank-server-finity
 * File Name:RoleResourceMongo.java
 * Package Name:com.froad.db.mongo
 * Date:2015年9月24日下午1:56:30
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.db.mongo;

import java.util.List;
import java.util.Map;

import com.froad.db.mongo.bean.BankUserResource;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.util.MongoTableName;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * ClassName:RoleResourceMongo
 * Reason:	 角色资源关系mongo操作
 * Date:     2015年9月24日 下午1:56:30
 * @author   刘超 liuchao@f-road.com.cn
 * @version  
 * @see 	 
 */

public class RoleResourceMongo {
	private MongoManager manager = new MongoManager();
	
	
	/**
	 * findRoleResource:()
	 *	 角色资源关系查询
	 * @author 刘超 liuchao@f-road.com.cn
	 * 2015年9月24日 下午2:18:00
	 * @param map
	 * @return
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<BankUserResource> findRoleResource(Map<String, Object> map){
		DBObject queryObj = new BasicDBObject();
		queryObj.putAll(map);
		List<BankUserResource> list = (List<BankUserResource>)manager.findAll(queryObj, MongoTableName.CB_BANK_USER_RESOURCE, BankUserResource.class);
		return list;
	}	
	

}
