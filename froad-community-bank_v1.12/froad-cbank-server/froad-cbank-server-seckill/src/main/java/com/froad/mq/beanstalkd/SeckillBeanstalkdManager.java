package com.froad.mq.beanstalkd;

import com.froad.mq.beanstalkd.impl.BeanstalkdServiceImpl;
import com.froad.util.PropertiesUtil;

/**
 * SeckillBeanstalkdManager
 * 
 * @author wangzhangxu
 * @date 2015年4月17日 下午12:53:00
 * @version v1.0
 */
public class SeckillBeanstalkdManager {
	
	static final String FILENAME = "beanstalkd-seckill";
	static final String KEY_SECKILL_MASTER = "seckill.master";
	static final String KEY_SECKILL_TUBE_NAME = "seckill.tube.name";
	
	static int master;
	static String tubeName;
	
	private BeanstalkdService client;
	
	static {
		master = Integer.parseInt(PropertiesUtil.getProperty(FILENAME, KEY_SECKILL_MASTER));
		tubeName = PropertiesUtil.getProperty(FILENAME, KEY_SECKILL_TUBE_NAME);
	}
	
	public BeanstalkdService getClient(){
		if (client == null) {
			client = new BeanstalkdServiceImpl(master, tubeName);
		}
		
		return client;
	}
}
