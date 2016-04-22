/**
 * Project Name:froad-cbank-server-boss-report
 * File Name:OrderLogic.java
 * Package Name:com.froad.logic
 * Date:2015年8月11日下午7:47:53
 * Copyright (c) 2015, F-Road, Inc.
 */
package com.froad.logic;

import com.froad.db.mongo.page.MongoPage;

/**
 * ClassName: OrderLogic <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年8月11日 下午7:47:53
 * 
 * @author zachary.wang
 * @version
 * @see
 */
public interface OrderLogic {
	
	/**
	 * 分页获取子订单
	 */
	public MongoPage querySubOrderListByPage(int pageNo, int pageSize);
	
	/**
	 * 分页获取大订单中的面对面订单
	 */
	public MongoPage queryFace2FaceOrderListByPage(int pageNo, int pageSize);
	
}
