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

package com.froad.test.mongodb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mongo.page.MongoPage;
import com.froad.db.mongo.page.OrderBy;
import com.froad.db.mongo.page.Sort;
import com.froad.util.PropertiesUtil;
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
	static {
		PropertiesUtil.load();
	}
	/**
	 * main:(这里用一句话描述这个方法的作用).
	 * 
	 * @author vania 2015年9月11日 上午11:26:44
	 * @param args
	 * 
	 */
	public static void main(String[] args) {
		MongoManager manager = new MongoManager();
//		 add(manager);

		MongoPage page = new MongoPage();

		page.setPageNumber(1);
		page.setFirstRecordTime(0l);
		page.setLastRecordTime(0l);
		page.setPageSize(3);

		boolean hasNext = page.getHasNext();

		do {
			System.out.println("第" + page.getPageNumber() + "页的数据：" + JSON.toJSONString(page));
//			page.setLastPageNumber(page.getPageNumber());
			page.setLastPageNumber(5);
//			page.setPageNumber(page.getPageNumber() + 1);
			page.setPageNumber(4);
			page.setFirstRecordTime(20000000000l);
			page.setLastRecordTime(10000000000l);
			page = manager.findByPageWithRedis(page, new BasicDBObject(), new BasicDBObject(), "wy_test_page", WY_TEST_PAGE.class);
			hasNext = page.getHasNext();
		} while (false);

	}
	
	public static void add(MongoManager manager){

		for (long i = 140000000000l; i > 0; i -= 10000000000l) {
			WY_TEST_PAGE dbObjects = new WY_TEST_PAGE();
			dbObjects.setCreate_time(i);
			manager.add(dbObjects, "wy_test_page");

		}
	}

}

class WY_TEST_PAGE {
	@JSONField(name = "create_time", deserialize = true, serialize = true)
	Long create_time;

	/**
	 * create_time.
	 * 
	 * @return the create_time
	 */
	public Long getCreate_time() {
		return create_time;
	}

	/**
	 * create_time.
	 * 
	 * @param create_time
	 *            the create_time to set
	 */
	public void setCreate_time(Long create_time) {
		this.create_time = create_time;
	}

}
