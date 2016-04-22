package com.froad.CB.service;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.po.RuleDetail;
import com.froad.CB.po.TransRule;

@WebService
public interface TransRuleService {
	/**
	  * 方法描述：获取交易规则
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘丽 liuli@f-road.com.cn
	  * @time: 2013-1-30 下午02:02:03
	 */
	List<TransRule> getTransRules(TransRule queryCon);
	/**
	  * 方法描述：添加交易规则
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘丽 liuli@f-road.com.cn
	 * @throws Exception 
	  * @time: 2013-1-30 下午01:50:20
	 */
	Integer addTransRule(TransRule transRule) throws Exception;
	
	/**
	  * 方法描述：添加交易规则明细
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘丽 liuli@f-road.com.cn
	  * @time: 2013-1-30 下午01:50:59
	 */
	Integer addTranRuleDetail(RuleDetail ruleDetail);
	
	
	
	/**
	  * 方法描述：更新交易规则
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘丽 liuli@f-road.com.cn
	  * @time: 2013-1-30 下午01:50:59
	 */
	boolean updateTransRule(TransRule transRule);
	
	/**
	  * 方法描述：更新交易规则明细
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘丽 liuli@f-road.com.cn
	  * @time: 2013-1-30 下午01:50:59
	 */
	boolean updateTranRuleDetail(RuleDetail ruleDetail);
	
}
