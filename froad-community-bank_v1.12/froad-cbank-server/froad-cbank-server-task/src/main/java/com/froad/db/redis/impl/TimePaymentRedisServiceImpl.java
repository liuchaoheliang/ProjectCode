package com.froad.db.redis.impl;

import java.util.Set;

import com.froad.db.redis.TimePaymentRedisService;
import com.froad.util.RedisKeyUtil;

public class TimePaymentRedisServiceImpl implements TimePaymentRedisService{
	
	private RedisManager manager = new RedisManager();
	
	@Override
	public Set<String> getPaymentInfo() {
		return manager.getSet(RedisKeyUtil.cbbank_time_payment_key());
	}
	
}
