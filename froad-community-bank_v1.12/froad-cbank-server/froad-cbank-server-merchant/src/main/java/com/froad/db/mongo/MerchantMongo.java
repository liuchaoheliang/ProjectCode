package com.froad.db.mongo;

import com.froad.db.mongo.impl.MongoManager;
import com.froad.po.Merchant;
import com.froad.util.MongoTableName;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class MerchantMongo {
	private static MongoManager manager = new MongoManager();
	
	/**
	 * 更新商户详情表信息
	 * @Title: updateAuditMerchant 
	 * @Description: TODO
	 * @author: ghw 20150915
	 * @param merchant
	 * @return
	 * @throws
	 */
	public static Boolean updateAuditMerchant(Merchant merchant){
		DBObject where = new BasicDBObject();
		where.put("_id", merchant.getMerchantId());
		DBObject value = new BasicDBObject();
		value.put("is_enable", merchant.getIsEnable());
		int result = manager.update(value, where, MongoTableName.CB_MERCHANT_DETAIL, "$set");
		return result != -1;
	}
}
