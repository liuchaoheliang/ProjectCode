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
 * @Title: OutletDetailMongo.java
 * @Package com.froad.db.mongo
 * @Description: TODO
 * @author vania
 * @date 2015年4月1日
 */

package com.froad.po.mongo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mongo.page.MongoPage;
import com.froad.db.mongo.page.OrderBy;
import com.froad.db.mongo.page.Sort;
import com.froad.logback.LogCvt;
import com.froad.po.mongo.CategoryInfo;
//import com.froad.po.mongo.Location;
import com.froad.po.mongo.MerchantDetail;
import com.froad.po.mongo.OutletDetail;
import com.froad.po.mongo.TypeInfo;
import com.froad.util.BeanUtil;
import com.froad.util.JSonUtil;
import com.froad.util.MongoTableName;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;


/**    
 * <p>Title: OutletDetailMongo.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年4月1日 上午11:54:15   
 */

public class OutletDetailMongo {
	private MongoManager manager = new MongoManager();
	
	/**
	 * 查询outlet详情
	 * @Title: findMerchantDetailById 
	 * @author zxh
	 * @version 1.0
	 * @see: TODO
	 * @param outletId
	 * @return
	 * @return OutletDetail    返回类型 
	 * @throws
	 */
	public OutletDetail findOutletDetailByoutletId (String outletId)throws Exception {
		
		DBObject dbObj = new BasicDBObject();
		dbObj.put("_id", outletId);
		return manager.findOne(dbObj, MongoTableName.CB_OUTLET_DETAIL, OutletDetail.class);
	}
	
}

