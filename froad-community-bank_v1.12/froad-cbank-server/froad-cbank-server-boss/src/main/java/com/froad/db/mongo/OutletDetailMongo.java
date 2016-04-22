/**
 * Project Name:froad-cbank-server-boss
 * File Name:OutletDetailMongo.java
 * Package Name:com.froad.db.mongo
 * Date:2015年11月2日下午4:43:17
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.db.mongo;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.froad.db.mongo.impl.MongoManager;
import com.froad.logback.LogCvt;
import com.froad.po.mongo.CategoryInfo;
import com.froad.util.JSonUtil;
import com.froad.util.MongoTableName;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * ClassName:OutletDetailMongo
 * Reason:	 TODO ADD REASON.
 * Date:     2015年11月2日 下午4:43:17
 * @author   huangyihao
 * @version  
 * @see 	 
 */
public class OutletDetailMongo {
	
	private MongoManager mongo = new MongoManager();
	private static final String COMMAND_SET = "$set";
	private static final String CATEGORY_ID = "category_info.category_id";
	
	/**
	 * 根据商户id修改分类信息
	 * @Title: updateOutletDetailCategoryInfo 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchantId
	 * @param categoryInfo
	 * @return
	 * @return boolean    返回类型 
	 * @throws
	 */
	public boolean updateOutletDetailCategoryInfo(String merchantId, List<CategoryInfo> categoryInfo) {
		if(CollectionUtils.isNotEmpty(categoryInfo)) {	
			
			DBObject where = new BasicDBObject();
			where.put("merchant_id", merchantId);
	
			DBObject value = new BasicDBObject();
			value.put("category_info", (BasicDBList) com.mongodb.util.JSON.parse(JSonUtil.toJSonString(categoryInfo)));
			
			LogCvt.info(MongoTableName.CB_OUTLET_DETAIL + "修改商户id为" + merchantId + "下的所有门店的商户分类信息为:" + value.toString());
			mongo.updateMulti(value, where, MongoTableName.CB_OUTLET_DETAIL, "$set");
			return true;
		}
		return false;
	}
	
	/**
	 * (修改门店分类名称)
	 * @param categoryId
	 * @param categoryName
	 * @return
	 */
	public boolean updateOutletCategoryNameByCategoryId(long categoryId, String categoryName) {
		DBObject where = new BasicDBObject();
		where.put(CATEGORY_ID, categoryId);
		DBObject value = new BasicDBObject();
		value.put("category_info.$.name", categoryName);
		mongo.updateMulti(value, where, MongoTableName.CB_OUTLET_DETAIL, COMMAND_SET);
		return true;
	}
}
