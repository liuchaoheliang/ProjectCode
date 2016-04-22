package com.froad.mq.redis.impl;

import java.util.ArrayList;
import java.util.List;

import com.froad.logback.LogCvt;
import com.froad.mq.redis.RedisMQService;

/**
 *  Redis消息服务处理类
 * @author zjz
 *
 */
public class RedisMQServiceImpl implements RedisMQService {

	List<RedisMQThread> threadList = new ArrayList<RedisMQThread>();
	
	/**
	 *  服务注册
	 */
	@Override
	public void registerMQService(RedisMQThread thread) {
		// TODO Auto-generated method stub
		threadList.add(thread);
	}

	/**
	 *  注册服务启动
	 */
	@Override
	public void start() {
		// TODO Auto-generated method stub
		for(RedisMQThread thread : threadList){
			LogCvt.info("Redis MQ 服务："+thread.getClass().getSimpleName()+"启动...");
			new Thread(thread).start();
			
		}
	}

}
