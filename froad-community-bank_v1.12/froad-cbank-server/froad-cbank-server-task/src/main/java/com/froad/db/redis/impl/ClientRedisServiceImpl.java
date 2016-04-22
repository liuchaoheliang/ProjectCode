/**
 * Project Name:froad-cbank-server-task
 * File Name:ClientRedisServiceImpl.java
 * Package Name:com.froad.db.redis.impl
 * Date:2015年8月26日上午10:39:17
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.db.redis.impl;

import java.util.Map;

import com.froad.db.redis.ClientRedisService;
import com.froad.util.RedisKeyUtil;

/**
 * ClassName:ClientRedisServiceImpl
 * Reason:	 TODO ADD REASON.
 * Date:     2015年8月26日 上午10:39:17
 * @author   Zxy
 * @version  
 * @see 	 
 */
public class ClientRedisServiceImpl implements ClientRedisService {

	private RedisManager manager = new RedisManager();
	
	@Override
	public Map<String, String> getClientByClientId(String clientId) {
		return manager.getMap(RedisKeyUtil.cbbank_client_client_id(clientId));
	}

}
