package com.froad.db.redis.impl;

import java.util.Map;

import com.froad.db.redis.ProductRedisService;
import com.froad.util.RedisKeyUtil;

public class ProductRedisServiceImpl implements ProductRedisService {
	
	private RedisManager manager = new RedisManager();
	
	private final String CLUSTER_STATE = "cluster_state";
	
	@Override
	public Map<String, String> getProductInfo(String clientId, String merchantId, String productId) {
		String key = RedisKeyUtil.cbbank_product_client_id_merchant_id_product_id(clientId, merchantId, productId);
		return manager.getMap(key);
	}

	@Override
	public Boolean updateClusterState(String clientId, String merchantId, String productId, String clusterState) {
		String key = RedisKeyUtil.cbbank_product_client_id_merchant_id_product_id(clientId, merchantId, productId);
		long result = manager.hset(key, CLUSTER_STATE, clusterState);
		return result == 0 || result == 1;
	}

}
