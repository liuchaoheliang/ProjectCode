package com.froad.CB.service;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.po.RuleDetailTemplet;
@WebService
public interface RuleDetailTempletService {
	
	/**
	  * 方法描述：更新规则明细模板
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘丽 liuli@f-road.com.cn
	  * @time: 2013-1-30 下午01:50:59
	 */
	List<RuleDetailTemplet> getRuleDetailTemplets(RuleDetailTemplet ruleDetailTemplet);
	
	/**
	  * 方法描述：添加规则明细模板
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘丽 liuli@f-road.com.cn
	 * @throws Exception 
	  * @time: 2013-1-30 下午01:50:59
	 */
	Integer addRuleDetailTemplet(RuleDetailTemplet ruleDetailTemplet) throws Exception;
	
	/**
	  * 方法描述：更新规则明细模板
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘丽 liuli@f-road.com.cn
	  * @time: 2013-1-30 下午01:50:59
	 */
	boolean updateRuleDetailTemplet(RuleDetailTemplet ruleDetailTemplet);
}
