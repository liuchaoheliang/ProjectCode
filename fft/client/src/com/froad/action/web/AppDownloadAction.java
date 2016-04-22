package com.froad.action.web;

import com.froad.action.support.AgreementActionSupport;
import com.froad.baseAction.BaseActionSupport;
import com.froad.client.agreement.Agreement;

/** 
 * @author FQ 
 * @date 2013-2-19 下午04:39:00
 * @version 1.0
 * App下载页面
 */
public class AppDownloadAction extends BaseActionSupport {
	
	public String index(){
		log.info("App下载");
		return "index";
	}
}
