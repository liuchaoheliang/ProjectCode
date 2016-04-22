package com.froad.timer.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.froad.timer.bean.TimerTaskConfig;
import com.froad.util.Arith;

/**
 * 任务 管理操作类
 * 
 * @author lf 2015.03.12
 * @modify lf 2015.03.24
 * 
 * */
public class TaskManager {

	private static TaskManager manager;
	
	private List<TimerTaskConfig> configs = new ArrayList<TimerTaskConfig>();
	
	private ScheduledExecutorService service;
	
	private TaskManager(){}
	
	public synchronized static TaskManager getInstance(){
		if(manager == null){
			manager = new TaskManager();
			manager.init();
		}
		return manager;
	}
	
	private void init(){
		configs.clear();
	}
	
	public void add(TimerTaskConfig config){
		configs.add(config);
	}
	
	public void run(){
		int corePoolSize = Arith.mul(1.5, configs.size());
		service = Executors.newScheduledThreadPool(corePoolSize);
		for(TimerTaskConfig config : configs){
			service.scheduleAtFixedRate(config.getTimerTask(), config.getDelay(), config.getPeriod(), TimeUnit.MILLISECONDS);
		}
	}
	
	public void shutdown(){
		if(service != null){
			service.shutdown();
		}
	}
}
