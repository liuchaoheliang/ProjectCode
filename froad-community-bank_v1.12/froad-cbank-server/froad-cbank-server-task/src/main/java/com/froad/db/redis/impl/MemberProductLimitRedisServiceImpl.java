package com.froad.db.redis.impl;

import com.froad.db.redis.MemberProductLimitRedisService;
import com.froad.util.Checker;
import com.froad.util.RedisKeyUtil;

public class MemberProductLimitRedisServiceImpl implements
		MemberProductLimitRedisService {

	private RedisManager manager = new RedisManager();
	
	private final String COUNT = "count";
	
	private final String VIP_COUNT = "vip_count";
	
	private final String ZERO = "0";
	
	@Override
	public Long incrCount(String clientId, Long memberCode, String productId, Long count) {
		String key = RedisKeyUtil.cbbank_product_limit_client_id_member_code_product_id(clientId, memberCode, productId);
		return manager.hincrBy(key, COUNT, count);
	}

	@Override
	public Long incrVipCount(String clientId, Long memberCode, String productId, Long vipCount) {
		String key = RedisKeyUtil.cbbank_product_limit_client_id_member_code_product_id(clientId, memberCode, productId);
		return manager.hincrBy(key, VIP_COUNT, vipCount);
	}

	@Override
	public Long resetCount(String clientId, Long memberCode, String productId, Long count) {
		if(count < 0){
			String key = RedisKeyUtil.cbbank_product_limit_client_id_member_code_product_id(clientId, memberCode, productId);
			manager.hset(key, COUNT, ZERO);
			return 0l;
		}
		return count;
	}

	@Override
	public Long resetVipCount(String clientId, Long memberCode, String productId, Long vipCount) {
		if(vipCount < 0){
			String key = RedisKeyUtil.cbbank_product_limit_client_id_member_code_product_id(clientId, memberCode, productId);
			manager.hset(key, VIP_COUNT, ZERO);
			return 0l;
		}
		return vipCount;
	}

	@Override
	public Long getCount(String clientId, Long memberCode, String productId) {
		String key = RedisKeyUtil.cbbank_product_limit_client_id_member_code_product_id(clientId, memberCode, productId);
		String count = manager.hget(key, COUNT);
		return Checker.isEmpty(count)?0l:Long.valueOf(count);
	}

	@Override
	public Long getVipCount(String clientId, Long memberCode, String productId) {
		String key = RedisKeyUtil.cbbank_product_limit_client_id_member_code_product_id(clientId, memberCode, productId);
		String vipCount = manager.hget(key, VIP_COUNT);
		return Checker.isEmpty(vipCount)?0l:Long.valueOf(vipCount);
	}

}
