/**
 * Project Name:Froad Cbank Server Common
 * File Name:TestMongodb.java
 * Package Name:com.froad.mongodb
 * Date:2015年9月11日上午11:26:44
 * Copyright (c) 2015, F-Road, Inc.
 *
 */
/**
 * Project Name:Froad Cbank Server Common
 * File Name:TestMongodb.java
 * Package Name:com.froad.mongodb
 * Date:2015年9月11日 上午11:26:44
 * Copyright (c) 2015, F-Road, Inc.
 *
 */

package com.froad.mongodb;

import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mongo.page.OrderBy;
import com.froad.db.mongo.page.Sort;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * ClassName:TestMongodb
 * Reason:	 TODO ADD REASON.
 * Date:     2015年9月11日 上午11:26:44
 * @author   vania
 * @version  
 * @see 	 
 */
/**
 * ClassName: TestMongodb Function: TODO ADD FUNCTION date: 2015年9月11日
 * 上午11:26:44
 * 
 * @author vania
 * @version
 */
public class TestMongodb {

	/**
	 * main:(这里用一句话描述这个方法的作用).
	 * 
	 * @author vania 2015年9月11日 上午11:26:44
	 * @param args
	 * 
	 */
	public static void main(String[] args) {
		MongoManager manager = new MongoManager();

		DBObject sortObje = new BasicDBObject();
		sortObje.put("b", 1);
		sortObje.put("a", -1);
		sortObje.put("1", 1);

		manager.reverseSort(sortObje);

		System.out.println("反轉之後的DBObject：" + sortObje);

		
//		Sort srot = new Sort("2", OrderBy.ASC).on("1", OrderBy.DESC);
//		manager.reverseSort(srot);
//		System.out.println("反轉之後的srot：" + srot.getSortObject());
	}

}
