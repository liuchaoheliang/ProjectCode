package com.froad.db.mongo.impl;

import com.froad.db.mongo.MerchantOutletService;
import com.froad.po.mongo.MerchantOutletFavorite;
import com.froad.util.MongoTableName;
import com.mongodb.DBObject;

public class MerchantOutletServiceImpl implements MerchantOutletService{
	private MongoManager manager = new MongoManager();
	@Override
	public MerchantOutletFavorite findByCondition(DBObject query) {
		return manager.findOne(query, MongoTableName.CB_MERCHANT_OUTLET_FAVORITE, MerchantOutletFavorite.class);
	}

}

