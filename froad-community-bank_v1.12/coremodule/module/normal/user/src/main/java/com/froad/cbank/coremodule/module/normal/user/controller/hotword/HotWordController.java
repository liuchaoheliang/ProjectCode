/**
 * Project Name:coremodule-user
 * File Name:HotWordController.java
 * Package Name:com.froad.cbank.coremodule.module.normal.user.controller.hotword
 * Date:2015年9月18日下午4:18:59
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.user.controller.hotword;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.module.normal.user.pojo.PagePojo;
import com.froad.cbank.coremodule.module.normal.user.support.HotWordSupport;

/**
 * ClassName:HotWordController
 * Reason:	 TODO ADD REASON.
 * Date:     2015年9月18日 下午4:18:59
 * @author   刘超 liuchao@f-road.com.cn
 * @version  
 * @see 	 
 */

@Controller
public class HotWordController  extends BasicSpringController{
	
	@Resource
	private HotWordSupport hotWordSupport;
	
	
	@RequestMapping(value="/hotWord/list",method=RequestMethod.GET)
	public void searchHotWord(HttpServletRequest req, ModelMap model,Long areaId,Integer type,PagePojo pagePojo){
		model.clear();
		String clientId=getClient(req);
		
		if(StringUtil.isNotBlank(clientId) && areaId != null && type != null){
			model.putAll(hotWordSupport.searchHotWord(clientId, areaId, type, pagePojo));
		}else{
			model.put("code", "9999");
			model.put("message", "必要参数为空");
		}
	
	
	}
	
}
