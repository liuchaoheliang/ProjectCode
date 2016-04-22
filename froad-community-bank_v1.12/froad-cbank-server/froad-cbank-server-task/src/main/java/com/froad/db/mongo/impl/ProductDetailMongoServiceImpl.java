package com.froad.db.mongo.impl;

import com.froad.db.mongo.ProductDetailMongoService;
import com.froad.po.mongo.ProductDetail;
import com.froad.util.MongoTableName;

public class ProductDetailMongoServiceImpl implements ProductDetailMongoService {
	
	private MongoManager manager = new MongoManager();
	
	@Override
	public ProductDetail findByProductId(String productId) {
		return manager.findOneById(productId, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
	}

}
