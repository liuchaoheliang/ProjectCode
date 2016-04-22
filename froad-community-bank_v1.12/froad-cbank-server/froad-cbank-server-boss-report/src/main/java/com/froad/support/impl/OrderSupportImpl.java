/**
 * Project Name:froad-cbank-server-boss-report
 * File Name:OrderSupportImpl.java
 * Package Name:com.froad.support.impl
 * Date:2015年8月11日下午7:38:46
 * Copyright (c) 2015, F-Road, Inc.
 */
package com.froad.support.impl;

import com.froad.db.mongo.impl.ReportMongoManager;
import com.froad.db.mongo.page.MongoPage;
import com.froad.db.mongo.page.OrderBy;
import com.froad.db.mongo.page.Sort;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.support.OrderSupport;
import com.froad.util.MongoTableName;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * ClassName: OrderSupportImpl <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年8月11日 下午7:38:46
 * 
 * @author zachary.wang
 * @version
 * @see
 */
public class OrderSupportImpl implements OrderSupport {
	
	/**
	 *  ReportMongoDb操作类
	 */
	private ReportMongoManager reportMongo = new ReportMongoManager();
	
	@Override
	public MongoPage querySubOrderListByPage(int pageNo, int pageSize) {
		MongoPage page = new MongoPage(pageNo, pageSize);
		
		DBObject where = new BasicDBObject();
		Sort sort = new Sort("_id", OrderBy.ASC);
		page.setSort(sort);
		page = reportMongo.findByPage(page, where, MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
		
		return page;
	}

	@Override
	public MongoPage queryFace2FaceOrderListByPage(int pageNo, int pageSize) {
		MongoPage page = new MongoPage(pageNo, pageSize);
		
		DBObject where = new BasicDBObject();
		where.put("is_qrcode", 1);
		Sort sort = new Sort("_id", OrderBy.ASC);
		page.setSort(sort);
		page = reportMongo.findByPage(page, where, MongoTableName.CB_ORDER, OrderMongo.class);
		
		return page;
	}

}
