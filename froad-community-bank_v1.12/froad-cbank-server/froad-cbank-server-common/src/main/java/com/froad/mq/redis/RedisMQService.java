package com.froad.mq.redis;

import com.froad.mq.redis.impl.RedisMQThread;

/**
 * RedisMQ 服务
 * @author zjz
 *
 */
public interface RedisMQService{
	
	/**
	 *  注册服务
	 * @param thread
	 */
	public void registerMQService(RedisMQThread thread);
	
	/**
	 *  启动服务
	 */
	public void start();
	
}
