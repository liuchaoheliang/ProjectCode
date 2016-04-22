package com.froad.pool;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.froad.config.ProcessServiceConfig;
import com.froad.config.XmlService;
import com.froad.logback.LogCvt;


public class ServicePool {
	
	private static ExecutorService pool = Executors.newFixedThreadPool(50);
	
	private static Map<String,ProcessServiceConfig> serviceMap = new HashMap<String, ProcessServiceConfig>();
	
	/**
	 *  加载启动服务
	  * @Title: loadService
	  * @Description: TODO
	  * @author: share 2015年4月29日
	  * @modify: share 2015年4月29日
	  * @param @param services    
	  * @return void    
	  * @throws
	 */
	public static void loadService(XmlService services){
		for(ProcessServiceConfig cfg : services.getConfig()){
			serviceMap.put(cfg.getServiceName(), cfg);
			startService(cfg);
		}
	}

	/**
	 *  加载单个服务
	  * @Title: startService
	  * @Description: TODO
	  * @author: share 2015年4月29日
	  * @modify: share 2015年4月29日
	  * @param @param cfg    
	  * @return void    
	  * @throws
	 */
	public static void startService(ProcessServiceConfig cfg) {
		try {
			Class clazz = Class.forName(cfg.getServiceClass());
			Constructor cstru = clazz.getConstructor(String.class,ProcessServiceConfig.class);
			cstru.setAccessible(true); 
			com.froad.process.Process process = (com.froad.process.Process)cstru.newInstance(cfg.getServiceName(),cfg);
			if(cfg.getLoadOonStartup() == 0){
				LogCvt.info("服务["+cfg.getServiceName()+"]开始执行...");
				pool.submit(process);
			}
		} catch (Exception e) {
			LogCvt.info("加载启动服务线程异常...",e);
		}
	}
	
	
	public static void nextService(ProcessServiceConfig config) {
		try {
			String nextName = config.getServiceNext();
			if(nextName==null || "".equals(nextName)){
				return;
			}
			LogCvt.info("下一模块["+config.getServiceNext()+"]即将执行");
			String[] nextNames = nextName.split(",");
			for(String next : nextNames){
				ProcessServiceConfig cfg = serviceMap.get(next);
				if(cfg != null){
					Class clazz = Class.forName(cfg.getServiceClass());
					Constructor cstru = clazz.getConstructor(String.class,ProcessServiceConfig.class);
					cstru.setAccessible(true); 
					com.froad.process.Process process = (com.froad.process.Process)cstru.newInstance(cfg.getServiceName(),cfg);
					if(cfg.getLoadOonStartup() > 0){
						LogCvt.info("服务["+cfg.getServiceName()+"]开始执行...");
						pool.submit(process);
					}
				}
			}
		} catch (Exception e) {
			LogCvt.info("启动下一个服务线程异常...",e);
		}
	}
}

