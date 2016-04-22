/**
 * Project Name:froad-cbank-server-boss
 * File Name:MerchantDetailMongo.java
 * Package Name:com.froad.db.mongo
 * Date:2015年11月2日下午2:46:06
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.db.mongo;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.collections.CollectionUtils;

import com.froad.db.mongo.impl.MongoManager;
import com.froad.po.mongo.CategoryInfo;
import com.froad.po.mongo.MerchantDetail;
import com.froad.util.JSonUtil;
import com.froad.util.MongoTableName;
import com.froad.util.PropertiesUtil;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * ClassName:MerchantDetailMongo
 * Reason:	 TODO ADD REASON.
 * Date:     2015年11月2日 下午2:46:06
 * @author   huangyihao
 * @version  
 * @see 	 
 */
public class MerchantDetailMongo {
	
	private MongoManager mongo = new MongoManager();
	
	private static final String COMMAND_SET = "$set";
	private static final String MONGO_KEY_ID = "_id";
	private static final String CATEGORY_ID = "category_info.category_id";
	
	/**
	 * 查询商户CategoryInfo
	 * @Title: findMerchantCategoryInfo 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchat_id
	 * @return
	 * @return List<CategoryInfo>    返回类型 
	 * @throws
	 */
	public List<CategoryInfo> findMerchantCategoryInfo(String merchat_id){
		BasicDBObject merchantId = new BasicDBObject();
		merchantId.append(MONGO_KEY_ID, merchat_id);
		
		List<String> fieldNames = new ArrayList<String>();
		fieldNames.add("category_info");
		List<MerchantDetail> md = (List<MerchantDetail>) mongo.findAll(merchantId, fieldNames, MongoTableName.CB_MERCHANT_DETAIL, MerchantDetail.class);
		if(CollectionUtils.isEmpty(md)){
			return null;
		}
		return md.get(0).getCategoryInfo();
	}
	
	
	/**
	 * 
	 * updateMerchantCategoryById:(修改商户分类信息).
	 *
	 * @author huangyihao
	 * 2015年11月2日 下午3:45:43
	 * @param merchantId
	 * @param categoryInfoList
	 * @return
	 *
	 */
	public boolean updateMerchantCategoryById(String merchantId,List<CategoryInfo> categoryInfoList) {
		DBObject where = new BasicDBObject();
		where.put(MONGO_KEY_ID, merchantId);
		
		DBObject value = new BasicDBObject();
		if(categoryInfoList != null && categoryInfoList.size() > 0){
			value.put("category_info", (BasicDBList) com.mongodb.util.JSON.parse(JSonUtil.toJSonString(categoryInfoList)));
		}
		mongo.update(value, where, MongoTableName.CB_MERCHANT_DETAIL, COMMAND_SET);
		return true;
	}	
	
	
	/**
	 * (修改商户分类名称)
	 * @param categoryId
	 * @param categoryName
	 * @return
	 */
	public boolean updateMerchantCategoryNameByCategoryId(long categoryId, String categoryName) {
		DBObject where = new BasicDBObject();
		where.put(CATEGORY_ID, categoryId);
		DBObject value = new BasicDBObject();
		value.put("category_info.$.name", categoryName);
		mongo.updateMulti(value, where, MongoTableName.CB_MERCHANT_DETAIL, COMMAND_SET);
		return true;
	}
	

	
}
