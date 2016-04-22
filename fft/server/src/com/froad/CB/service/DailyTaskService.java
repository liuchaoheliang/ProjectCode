package com.froad.CB.service;

import javax.jws.WebService;

import com.froad.CB.po.DailyTask;

@WebService
public interface DailyTaskService {

	
	/**
	  * 方法描述：添加每日签到信息
	  * @param: DailyTask
	  * @return: id
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 25, 2013 2:49:59 PM
	  */
	public Integer addDailyTask(DailyTask dailyTask);
	
	
	/**
	  * 方法描述：查询签到信息
	  * @param: id
	  * @return: DailyTask
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 25, 2013 2:50:21 PM
	  */
	public DailyTask getDailyTaskById(Integer id);
	
	
	/**
	  * 方法描述：删除签到信息
	  * @param: id
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 25, 2013 2:50:40 PM
	  */
	public boolean deleteById(Integer id);
	
	/**
	  * 方法描述：查询签到信息
	  * @param: userId
	  * @return: DailyTask
	  * @version: 1.0
	  * @author: txl
	  * @time: 2013-3-01 am
	  */
	public DailyTask getByUserId(String userId);
		
	/**
	  * 方法描述：更新签到信息
	  * @param: id
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 25, 2013 2:50:59 PM
	  */
	public boolean updateById(DailyTask dailyTask);
	
	
	/**
	  * 方法描述：更新签到状态
	  * @param: id
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 25, 2013 2:51:13 PM
	  */
	public boolean updateStateById(Integer id,String state);
}
