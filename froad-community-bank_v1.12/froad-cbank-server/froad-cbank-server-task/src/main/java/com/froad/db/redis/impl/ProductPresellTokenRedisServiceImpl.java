package com.froad.db.redis.impl;

import com.froad.db.redis.ProductPresellTokenRedisService;
import com.froad.util.Checker;
import com.froad.util.RedisKeyUtil;

public class ProductPresellTokenRedisServiceImpl implements ProductPresellTokenRedisService {
	
	private RedisManager manager = new RedisManager();
	
	private final String ZERO = "0";
	
	@Override
	public Long decrCount(String clientId, String orgCode, String productId, Long count) {
		String key = RedisKeyUtil.cbbank_product_presell_token_org_code_product_id(clientId,orgCode,productId);
		return manager.decrBy(key, count);
	}

	@Override
	public Long getCount(String clientId, String orgCode, String productId) {
		String key = RedisKeyUtil.cbbank_product_presell_token_org_code_product_id(clientId,orgCode,productId);
		String count = manager.getString(key);
		return Checker.isEmpty(count)?0l:Long.valueOf(count);
	}

	@Override
	public Long resetCount(String clientId, String orgCode, String productId, Long count) {
		if(count < 0){
			String key = RedisKeyUtil.cbbank_product_presell_token_org_code_product_id(clientId,orgCode,productId);
			manager.putString(key, ZERO);
			return 0l;
		}
		return count;
	}

}
