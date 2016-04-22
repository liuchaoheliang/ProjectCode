package com.froad.CB.service.impl;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.dao.DailyTaskDao;
import com.froad.CB.po.DailyTask;
import com.froad.CB.service.DailyTaskService;

@WebService(endpointInterface="com.froad.CB.service.DailyTaskService")
public class DailyTaskServiceImpl implements DailyTaskService{

	private static final Logger logger=Logger.getLogger(DailyTaskServiceImpl.class);
	
	private DailyTaskDao dailyTaskDao;
	
	@Override
	public Integer addDailyTask(DailyTask dailyTask) {
		if(dailyTask==null){
			logger.error("参数为空，添加失败");
			return null;
		}
		return dailyTaskDao.addDailyTask(dailyTask);
	}

	@Override
	public boolean deleteById(Integer id) {
		if(id==null){
			logger.error("编号为空，删除失败");
			return false;
		}
		try {
			dailyTaskDao.deleteById(id);
			return true;
		} catch (Exception e) {
			logger.error("删除操作异常",e);
			return false;
		}
	}

	@Override
	public DailyTask getDailyTaskById(Integer id) {
		if(id==null){
			logger.error("编号为空，查询失败");
			return null;
		}
		return dailyTaskDao.getDailyTaskById(id);
	}
	
	@Override
	public DailyTask getByUserId(String userId) {
		if(userId == null){
			logger.error("参数为空，查询失败");
			return null;
		}
		return dailyTaskDao.getByUserId(userId);

	}

	@Override
	public boolean updateById(DailyTask dailyTask) {
		if(dailyTask==null||dailyTask.getId()==null){
			logger.error("参数为空，更新失败");
			return false;
		}
		return dailyTaskDao.updateById(dailyTask);
	}

	@Override
	public boolean updateStateById(Integer id, String state) {
		if(id==null||state==null||"".equals(state)){
			logger.error("参数为空，更新状态失败");
			return false;
		}
		return dailyTaskDao.updateStateById(id, state);
	}

	public void setDailyTaskDao(DailyTaskDao dailyTaskDao) {
		this.dailyTaskDao = dailyTaskDao;
	}



}
