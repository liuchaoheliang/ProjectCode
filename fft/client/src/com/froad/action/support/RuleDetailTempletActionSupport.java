package com.froad.action.support;

import org.apache.log4j.Logger;

import com.froad.client.ruleDetailTemplet.RuleDetailTempletService;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-19  
 * @version 1.0
 * 交易规则明细模板 client service  ActionSupport
 */
public class RuleDetailTempletActionSupport {
	private static Logger logger = Logger.getLogger(RuleDetailTempletActionSupport.class);
	private RuleDetailTempletService ruleDetailTempletService;
	
	
	
	public RuleDetailTempletService getRuleDetailTempletService() {
		return ruleDetailTempletService;
	}
	public void setRuleDetailTempletService(
			RuleDetailTempletService ruleDetailTempletService) {
		this.ruleDetailTempletService = ruleDetailTempletService;
	}
	
}
