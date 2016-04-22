/**
 * Project Name:froad-cbank-server-boss-report
 * File Name:OrderLogicImpl.java
 * Package Name:com.froad.logic.impl
 * Date:2015年8月11日下午8:33:46
 * Copyright (c) 2015, F-Road, Inc.
 */
package com.froad.logic.impl;

import com.froad.db.mongo.page.MongoPage;
import com.froad.logic.OrderLogic;
import com.froad.support.OrderSupport;
import com.froad.support.impl.OrderSupportImpl;

/**
 * ClassName: OrderLogicImpl <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年8月11日 下午8:33:46
 * 
 * @author zachary.wang
 * @version
 * @see
 */
public class OrderLogicImpl implements OrderLogic {
	
	private OrderSupport orderSupport = new OrderSupportImpl();
	
	@Override
	public MongoPage querySubOrderListByPage(int pageNo, int pageSize) {
		return orderSupport.querySubOrderListByPage(pageNo, pageSize);
	}

	@Override
	public MongoPage queryFace2FaceOrderListByPage(int pageNo, int pageSize) {
		return orderSupport.queryFace2FaceOrderListByPage(pageNo, pageSize);
	}

}
