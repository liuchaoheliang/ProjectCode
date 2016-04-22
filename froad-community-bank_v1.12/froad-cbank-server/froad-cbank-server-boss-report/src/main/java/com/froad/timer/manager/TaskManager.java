package com.froad.timer.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.froad.logback.LogCvt;
import com.froad.timer.bean.TaskBean;
import com.froad.timer.bean.TaskList;
import com.froad.timer.bean.TimerTaskConfig;
import com.froad.timer.task.ITask;
import com.froad.util.Arith;
import com.froad.util.Checker;

/**
 * 任务 管理操作类
 * 
 * @author lf 2015.03.12
 * @modify lf 2015.03.24
 * 
 * */
public class TaskManager {

	private static Map<String, TaskBean> serviceMap = new HashMap<String, TaskBean>();
	
	private static ScheduledExecutorService service;
	
	public static void load(TaskList taskList){
		if(Checker.isNotEmpty(taskList.getList())){
			LogCvt.info("配置中共有" + taskList.getList().size() + "个定时任务需要启动 \n");
			// 初始化任务调度线程池
			initScheduledService(taskList.getList().size());
			
			for(TaskBean bean : taskList.getList()){
				serviceMap.put(bean.getTaskName(), bean);
				LogCvt.info("读取定时任务 "+bean.getTaskName()+" 配置的任务内容");
				LogCvt.info(bean.toString());
				if(Checker.isNotEmpty(bean.getTime()) && Checker.isNotEmpty(bean.getPeriod())){
					TimerTaskConfig config = new TimerTaskConfig(bean);
					service.scheduleAtFixedRate(config.getTask(), config.getDelay(), config.getPeriod(), TimeUnit.MILLISECONDS);
				}
			}
		}
	}
	
	private static void initScheduledService(int size){
		int corePoolSize = Arith.mul(1.5, size);
		service = Executors.newScheduledThreadPool(corePoolSize);
	}
	
	
	public static void shutdown(){
		if(service != null){
			service.shutdown();
		}
	}
	
	public static void nextTask(TaskBean task){
		try {
			String nextName = task.getTaskNext();
			if(Checker.isEmpty(nextName)){
				return;
			}
			String[] nextNames = nextName.split(",");
			for(String next : nextNames){
				TaskBean nextTask = serviceMap.get(next);
				ITask ntask = (ITask)Class.forName(nextTask.getTaskClass()).getConstructor(String.class, TaskBean.class).newInstance(nextTask.getTaskName(), nextTask);
				LogCvt.info("下个定时任务[taskName = "+next+"] 开始执行");
				service.schedule(ntask, 0, TimeUnit.MILLISECONDS);
			}
		} catch (Exception e) {
			LogCvt.error("启动定时任务异常", e);
		}
	}
}
