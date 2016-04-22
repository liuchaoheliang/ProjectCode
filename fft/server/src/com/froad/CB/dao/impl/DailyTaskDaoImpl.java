package com.froad.CB.dao.impl;

import java.util.HashMap;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.DailyTaskDao;
import com.froad.CB.po.DailyTask;

public class DailyTaskDaoImpl implements DailyTaskDao{

	private SqlMapClientTemplate sqlMapClientTemplate;
	
	@Override
	public Integer addDailyTask(DailyTask dailyTask) {
		return (Integer)sqlMapClientTemplate.insert("dailyTask.insert",dailyTask);
	}

	@Override
	public boolean deleteById(Integer id) {
		sqlMapClientTemplate.delete("dailyTask.deleteByPrimaryKey",id);
		return true;
	}

	@Override
	public DailyTask getDailyTaskById(Integer id) {
		return (DailyTask)sqlMapClientTemplate.queryForObject("dailyTask.selectByPrimaryKey",id);
	}
	
	@Override
	public DailyTask getByUserId(String userId) {
		if(userId == null || "".equals(userId.trim())) return null;
		return (DailyTask)sqlMapClientTemplate.queryForObject("dailyTask.getByUserId",userId);

	}

	@Override
	public boolean updateById(DailyTask dailyTask) {
		sqlMapClientTemplate.update("dailyTask.updateById",dailyTask);
		return true;
	}

	@Override
	public boolean updateStateById(Integer id, String state) {
		HashMap<String, Object> params=new HashMap<String, Object>();
		params.put("id", id);
		params.put("state", state);
		sqlMapClientTemplate.update("dailyTask.updateStateById",params);
		return true;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}



}
