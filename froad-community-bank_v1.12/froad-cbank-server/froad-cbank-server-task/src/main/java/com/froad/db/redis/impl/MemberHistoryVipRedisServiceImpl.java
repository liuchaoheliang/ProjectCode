package com.froad.db.redis.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;

import com.froad.db.redis.MemberHistoryVipRedisService;
import com.froad.logback.LogCvt;
import com.froad.util.Checker;
import com.froad.util.RedisKeyUtil;

public class MemberHistoryVipRedisServiceImpl implements MemberHistoryVipRedisService {
	
	private RedisManager manager = new RedisManager();
	
	private final String ZERO = "0";
	
	@Override
	public Long decrVipDiscount(String clientId, Long memberCode,Long vipDiscount) {
		String key = RedisKeyUtil.cbbank_history_vip_client_id_member_code_key(clientId, memberCode);
		return manager.decrBy(key, vipDiscount);
	}

	@Override
	public Long getVipDiscount(String clientId, Long memberCode) {
		String key = RedisKeyUtil.cbbank_history_vip_client_id_member_code_key(clientId, memberCode);
		String vipDiscount = manager.getString(key);
		return Checker.isEmpty(vipDiscount)?0:Long.valueOf(vipDiscount);
	}

	@Override
	public Long resetVipDiscount(String clientId, Long memberCode, Long vipDiscount) {
		if(vipDiscount < 0){
			String key = RedisKeyUtil.cbbank_history_vip_client_id_member_code_key(clientId, memberCode);
			manager.putString(key, ZERO);
			return 0l;
		}
		return vipDiscount;
	}

	@Override
	public Boolean updateUserVIPOrderRedis(String key, String isSuccess) {
		// TODO Auto-generated method stub
    //	String result = manager.putString(key, isSuccess);
		String result="";
		Map<String,String> valueMap = new HashMap<String,String>();
			valueMap.put("order_created",isSuccess );
		if(Checker.isNotEmpty(valueMap)){
			 result=	manager.putMap(key, valueMap);
		}
		return "OK".equals(result);
	}

}
