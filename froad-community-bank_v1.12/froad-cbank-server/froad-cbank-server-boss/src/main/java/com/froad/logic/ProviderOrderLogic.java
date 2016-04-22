/**
 * Project Name:froad-cbank-server-boss-1.8.0-SNAPSHOT
 * File Name:ProviderOrderLogic.java
 * Package Name:com.froad.logic
 * Date:2015年11月25日下午3:39:11
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.logic;

import java.util.List;
import java.util.Map;

import com.froad.db.mongo.page.MongoPage;
import com.froad.exceptions.FroadBusinessException;
import com.froad.po.ProviderOrderQuery;
import com.froad.po.ShippingExcelInfo;
import com.mongodb.DBObject;

/**
 * ClassName:ProviderOrderLogic
 * Reason:	 TODO ADD REASON.
 * Date:     2015年11月25日 下午3:39:11
 * @author   huangyihao
 * @version  
 * @see 	 
 */
public interface ProviderOrderLogic {
	
	/**
	 * 
	 * queryProviderOrderInfoByPage:(供应商订单列表分页查询).
	 *
	 * @author huangyihao
	 * 2015年11月25日 下午4:06:55
	 * @param reqQuery
	 * @param page
	 * @return
	 * @throws Exception
	 *
	 */
	public MongoPage queryProviderOrderInfoByPage(DBObject reqQuery, MongoPage page) throws Exception;
	
	
	/**
	 * 
	 * inputShippingInfo:(导入物流信息).
	 *
	 * @author huangyihao
	 * 2015年11月27日 上午11:51:01
	 * @param inputs
	 * @return
	 * @throws Exception
	 *
	 */
	public List<ShippingExcelInfo> inputShippingInfo(List<ShippingExcelInfo> inputs) throws Exception;
	
	
	/**
	 * 
	 * shippingByOrderId:(出库).
	 *
	 * @author huangyihao
	 * 2015年11月30日 下午2:05:52
	 * @param orderId
	 * @return
	 * @throws Exception
	 *
	 */
	public Boolean shippingByOrderId(String orderId) throws Exception;
	
	
	/**
	 * 
	 * updateShippingInfo:(更新物流信息).
	 *
	 * @author huangyihao
	 * 2015年11月30日 下午2:14:45
	 * @param reqMap
	 * @return
	 * @throws FroadBusinessException
	 * @throws Exception
	 *
	 */
	public Boolean updateShippingInfo(Map<String, Object> reqMap) throws FroadBusinessException, Exception;
	
	
}
