package com.froad.mq.redis.impl;

import java.util.List;

import com.froad.db.redis.impl.RedisManager;
import com.froad.logback.LogCvt;
import com.froad.service.ProcessService;

/**
 *  RedisMQ 监听服务
 * @author zjz
 *
 */
public class RedisMQThread implements Runnable {

	// 处理队列消息Key
	protected String redisKey;
	
	// 处理队列消息类
	protected ProcessService processService;
	
	// 队列超时时间(单位秒)
	public  int timeout = 0; 
	
	// Redis服务实例
	private RedisManager reids = new RedisManager();
	
	public RedisMQThread(String redisKey,ProcessService service){
		this.redisKey = redisKey;
		this.processService = service;
	}
	
	public RedisMQThread(){
		
	}

	public void setRedisKey(String redisKey) {
		this.redisKey = redisKey;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	
	public void registerProcessService(ProcessService processService) {
		this.processService = processService;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				LogCvt.error("MQ消息队列处理异常1...",e1);
			}
//			List<String> msgs = reids.brpop(timeout, redisKey);
//			if(msgs == null) continue;
			for(int i = 0; i < 5 ; i++){
				String msgs = reids.rpop(redisKey);
				if(msgs == null) continue;
				try {
					processService.processMsg(msgs);
				} catch (Exception e) {
					// TODO: handle exception
					LogCvt.error("MQ消息队列处理异常...",e);
				}
			}
		}
		
	}
	
	

}
