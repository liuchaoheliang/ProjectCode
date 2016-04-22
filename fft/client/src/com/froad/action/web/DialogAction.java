package com.froad.action.web;

import com.froad.baseAction.BaseActionSupport;

/** 
 * @author FQ 
 * @date 2013-2-19 下午04:39:00
 * @version 1.0
 * Dialog test
 */
public class DialogAction extends BaseActionSupport {
	
	/**
	 * 
	 * @return
	 */
	public String test(){
		
		return "test";
	}
	
	public String agreement(){
		String agreement="这里是从后台取出的注册协议！";
		
		return ajaxHtml(agreement);
	}
}
