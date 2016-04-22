package com.froad.action.support;

import org.apache.log4j.Logger;
import com.froad.client.dailyTaskRule.DailyTaskRule;
import com.froad.client.dailyTaskRule.DailyTaskRuleService;

/**  
 * @author txl
 * @date 2013-3-01 am
 * @version 1.0
 * 签到 每日任务
 */
public class DailyTaskRuleActionSupport {
	
	private static Logger logger = Logger.getLogger(AdvertActionSupport.class);
	private DailyTaskRuleService dailyTaskRuleService;
	
	public DailyTaskRule getByPrimaryKey(Integer id){
		if(id == null){
			logger.error("DailyTaskActionSupport.getDailyTaskByUserId出错 id is null ");
			return null;
		}
		
		DailyTaskRule dtr = null;
		try {
			dtr =  dailyTaskRuleService.getDailyTaskRuleByPrimaryId(id);
		} catch (Exception e) {
			logger.error("DailyTaskRuleActionSupport.getByPrimaryKey出错 id :"+id);
		}
		
		return dtr;
	}
	
	public DailyTaskRuleService getDailyTaskRuleService() {
		return dailyTaskRuleService;
	}
	public void setDailyTaskRuleService(DailyTaskRuleService dailyTaskRuleService) {
		this.dailyTaskRuleService = dailyTaskRuleService;
	}
	
	

}
