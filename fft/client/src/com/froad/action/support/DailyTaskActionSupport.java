package com.froad.action.support;

import org.apache.log4j.Logger;
import com.froad.client.dailyTask.DailyTask;
import com.froad.client.dailyTask.DailyTaskService;

/**
 * @author Qiaopeng.Lee
 * @date 2013-2-19
 * @version 1.0
 * 签到 client service  ActionSupport
 */
public class DailyTaskActionSupport {
	
	private static Logger logger = Logger.getLogger(DailyTaskActionSupport.class);
	private DailyTaskService dailyTaskSerivce;
		
	public DailyTask getDailyTaskByUserId(String userId){
		if(userId == null || "".equals(userId.trim())){
			logger.error("DailyTaskActionSupport.getDailyTaskByUserId出错 userId is null ");
			return null;
		}
		
		DailyTask dt = null;
		try {
			dt =  dailyTaskSerivce.getByUserId(userId);
		} catch (Exception e) {
			logger.error("DailyTaskActionSupport.getDailyTaskByUserId出错 userId :"+userId);
		}
		
		return dt;
	}
	
	public Integer addDailyTask(DailyTask dailyTask){
		Integer i = 0;

		if(dailyTask == null){
			logger.error("DailyTaskActionSupport.updateDailyTask出错");
			return i;
		}
		try {
			i = dailyTaskSerivce.addDailyTask(dailyTask);
		} catch (Exception e) {
			logger.error("DailyTaskActionSupport.updateDailyTask出错");
		}
		
		return i;
	}
	
	public boolean updateDailyTaskByUserId(DailyTask dailyTask){
		boolean bol = false;

		if(dailyTask == null){
			logger.error("DailyTaskActionSupport.updateDailyTask出错");
			return bol;
		}
		try {
			bol = dailyTaskSerivce.updateById(dailyTask);
		} catch (Exception e) {
			logger.error("DailyTaskActionSupport.updateDailyTask出错");
		}
		
		return bol;
	}
	

	public DailyTaskService getDailyTaskSerivce() {
		return dailyTaskSerivce;
	}

	public void setDailyTaskSerivce(DailyTaskService dailyTaskSerivce) {
		this.dailyTaskSerivce = dailyTaskSerivce;
	}
	
	
}
