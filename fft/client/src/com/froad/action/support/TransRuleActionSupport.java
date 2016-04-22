package com.froad.action.support;

import org.apache.log4j.Logger;

import com.froad.client.transRule.TransRuleService;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-19  
 * @version 1.0
 * 交易规则 client service  ActionSupport
 */
public class TransRuleActionSupport {
	private static Logger logger = Logger.getLogger(TransRuleActionSupport.class);
	private TransRuleService transRuleService;
	
	
	public TransRuleService getTransRuleService() {
		return transRuleService;
	}
	public void setTransRuleService(TransRuleService transRuleService) {
		this.transRuleService = transRuleService;
	}
	
}
