package com.froad.action.support;

import org.apache.log4j.Logger;

import com.froad.client.transDetails.TransDetailsService;


/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-19  
 * @version 1.0
 * 交易明细 client service  ActionSupport
 */
public class TransDetailsActionSupport {
	private static Logger logger = Logger.getLogger(TransDetailsActionSupport.class);
	private TransDetailsService transDetailsService;
	
	
	
	
	public TransDetailsService getTransDetailsService() {
		return transDetailsService;
	}
	public void setTransDetailsService(TransDetailsService transDetailsService) {
		this.transDetailsService = transDetailsService;
	}
	
}
