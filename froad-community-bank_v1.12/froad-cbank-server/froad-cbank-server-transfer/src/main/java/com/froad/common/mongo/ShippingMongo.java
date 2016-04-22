/*   
 * Copyright © 2008 F-Road All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */

/**  
 * @Title: MerchantMongo.java
 * @Package com.froad.db.mongo
 * @Description: TODO
 * @author vania
 * @date 2015年3月25日
 */

package com.froad.common.mongo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.froad.db.chonggou.entity.ShippingCG;
import com.froad.db.mongo.impl.CursorHandleImpl;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.util.JSonUtil;
import com.froad.util.MongoTableName;
import com.mongodb.AggregationOptions;
import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

/**
 * <p>
 * Title: MerchantMongo.java
 * </p>
 * <p>
 * Description: 描述
 * </p>
 * 
 * @author vania
 * @version 1.0
 * @created 2015年3月25日 下午8:47:31
 */

public class ShippingMongo {
	
	/**
	 *  MongoDb操作类
	 */
	private MongoManager mongo = new MongoManager();
	

	public int addShipping(String order_id, String sub_order_id,ShippingCG shipping){
		String id=order_id+"_"+sub_order_id;
		
		ShippingCG shp = new ShippingCG();
		shp.setId(id);
		shp.setShippingStatus(shipping.getShippingStatus());
		shp.setDeliveryCorpId(shipping.getDeliveryCorpId());
		shp.setDeliveryCorpName(shipping.getDeliveryCorpName());
		shp.setTrackingNo(shipping.getTrackingNo());
		shp.setRemark(shipping.getRemark());
		shp.setShippingTime(shipping.getShippingTime());
		shp.setShippingStatus(shipping.getShippingStatus());
		shp.setMerchantUserId(shipping.getMerchantUserId());
		return mongo.add(shp, MongoTableName.CB_SHIPPING);
	}
	
	
	public int isExitsShippingInfo(String order_id, String sub_order_id) {
		String id=order_id+"_"+sub_order_id;
		DBObject queryObj = new BasicDBObject();
		queryObj.put("_id", id);
		return mongo.getCount(queryObj, MongoTableName.CB_SHIPPING);
		
	}

	
}
