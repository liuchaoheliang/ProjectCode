package com.froad.db.redis.impl;

import java.util.Set;

import com.froad.db.redis.TimeOrderRedisService;
import com.froad.util.RedisKeyUtil;

public class TimeOrderRedisServiceImpl implements TimeOrderRedisService {
	
	private RedisManager manager = new RedisManager();
	
	@Override
	public Set<String> getOrderInfo() {
		return manager.getSet(RedisKeyUtil.cbbank_time_order_key());
	}

	@Override
	public Boolean removeOrder(String orderInfo) {
		long result = manager.srem(RedisKeyUtil.cbbank_time_order_key(), orderInfo);
		return result == 1;
	}

}
