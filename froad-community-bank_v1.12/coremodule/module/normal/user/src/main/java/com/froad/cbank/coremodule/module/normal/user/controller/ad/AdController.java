package com.froad.cbank.coremodule.module.normal.user.controller.ad;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.module.normal.user.support.AdSupport;
import com.froad.cbank.coremodule.module.normal.user.support.BaseSupport.Results;


	/**
	 * 类描述：广告相关接口
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2015 
	 * @author: 刘超 liuchao@f-road.com.cn
	 * @time: 2015年4月2日 上午9:32:24 
	 */
@Controller
@RequestMapping
public class AdController extends BasicSpringController{

	
	@Resource
	private AdSupport adSupport ; 
	
	
	
	/**
	  * 方法描述：
	  * @param: clientId - 客户端编号     ，positionPage - 页面标识 ,  terminalType - 终端类型 ， paramX - 广告参数
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年4月1日 下午4:04:32
	  */
	
	@RequestMapping(value="/ad/list" ,method=RequestMethod.GET)
	public void list(ModelMap model, String positionPage, String terminalType, String paramOne, String paramTwo, String paramThree, HttpServletRequest req){
		String clientId=getClient(req);
		if(StringUtil.isNotBlank(positionPage) && StringUtil.isNotBlank(terminalType)){
			model.putAll(adSupport.getAd(clientId, positionPage, terminalType, paramOne, paramTwo, paramThree));
			model.put(Results.result, null);
		}else{
			model.put("code", "9999");
			model.put("message", "必要参数为空");
		}
	}
	
	
	
	
}
