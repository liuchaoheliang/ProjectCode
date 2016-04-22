/**
 * Project Name:froad-cbank-server-boss
 * File Name:ActivityNoLockRedis.java
 * Package Name:com.froad.db.redis
 * Date:2015年10月27日下午3:46:52
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.db.redis;

import com.froad.db.redis.impl.RedisManager;
import com.froad.util.Checker;
import com.froad.util.PropertiesUtil;
import com.froad.util.RedisKeyUtil;

/**
 * ClassName:ActivityNoLockRedis
 * Reason:	 TODO ADD REASON.
 * Date:     2015年10月27日 下午3:46:52
 * @author   huangyihao
 * @version  
 * @see 	 
 */
public class ActivityNoLockRedis {
	
	private RedisManager redis = new RedisManager();
	
	public static final int EXPIRED_SECONDS = 300;
	
	public Boolean setexLockInfo(String clientId, String activityType, String activityNo, String operator){
		String redisKey = RedisKeyUtil.cbbank_activity_no_lock_client_id_type_activity_no(clientId, activityType, activityNo);
		Boolean flag = false;
		String value = redis.get(redisKey);
		if(Checker.isEmpty(value)){
			String result = redis.setex(redisKey, ActivityNoLockRedis.EXPIRED_SECONDS, operator);
			flag = "OK".equals(result);
		}else{
			if(operator.equals(value)){
				flag = true;
			}
		}
		return flag;
	}
	
	public String getLockInfo(String clientId, String activityType, String activityNo){
		String redisKey = RedisKeyUtil.cbbank_activity_no_lock_client_id_type_activity_no(clientId, activityType, activityNo);
		return redis.get(redisKey);
	}
	
	
	public void delLockInfo(String clientId, String activityType, String activityNo){
		String redisKey = RedisKeyUtil.cbbank_activity_no_lock_client_id_type_activity_no(clientId, activityType, activityNo);
		redis.del(redisKey);
	}
	
	
	public static void main(String[] args) {
		PropertiesUtil.load();
		ActivityNoLockRedis s = new ActivityNoLockRedis();
		
//		s.delLockInfo("anhui", "1", "ah-eisdf-ff");
		boolean flag = s.setexLockInfo("anhui", "1", "ah-eisdf-ff", "admin");
		
		System.out.println(flag);
	}
	
	
}
