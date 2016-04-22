package com.froad.db.redis.impl;

import com.froad.db.redis.ProductStoreRedisService;
import com.froad.util.Checker;
import com.froad.util.RedisKeyUtil;

public class ProductStoreRedisServiceImpl implements ProductStoreRedisService {
	
	private RedisManager manager = new RedisManager();
	
	@Override
	public Long incrStore(String clientId, String merchantId, String productId, Long store) {
		String key = RedisKeyUtil.cbbank_product_store_client_id_merchant_id_product_id(clientId, merchantId, productId);
		return manager.incrBy(key, store);
	}

	@Override
	public Long getStore(String clientId, String merchantId, String productId) {
		String key = RedisKeyUtil.cbbank_product_store_client_id_merchant_id_product_id(clientId, merchantId, productId);
		String store = manager.getString(key);
		return Checker.isEmpty(store)?0l:Long.valueOf(store);
	}

	
}
