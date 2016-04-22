/**
 * Project Name:froad-cbank-server-task
 * File Name:ClientRedisService.java
 * Package Name:com.froad.db.redis
 * Date:2015年8月26日上午10:38:16
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.db.redis;

import java.util.Map;

/**
 * ClassName:ClientRedisService
 * Reason:	 获取客户端相关的缓存信息
 * Date:     2015年8月26日 上午10:38:16
 * @author   Zxy
 * @version  
 * @see 	 
 */
public interface ClientRedisService {

	/**
	 * 
	 * getClientByClientId:通过clientId获取缓存中的client信息
	 *
	 * @author Zxy
	 * 2015年8月26日 上午10:40:02
	 * @param clientId
	 * @return
	 *
	 */
	public Map<String,String> getClientByClientId(String clientId);
}
