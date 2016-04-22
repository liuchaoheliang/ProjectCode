package com.froad.CB.service;

import javax.jws.WebService;

import com.froad.CB.po.DailyTaskRule;

/** 
 * @author TXL
 * @date 2013-2-4 上午09:47:29
 * @version 1.0
 */
@WebService
public interface DailyTaskRuleService {
	
	DailyTaskRule getDailyTaskRuleByPrimaryId(Integer id);

}
