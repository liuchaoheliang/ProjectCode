package com.froad.cbank.coremodule.module.normal.user.controller.area;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.module.normal.user.support.AreaSupport;


@Controller
@RequestMapping
public class AreaController extends BasicSpringController {
		
	@Resource
	private  AreaSupport areaSupport;
	
	
	/**
	  * 方法描述：根据父级区域查询子级区域集合
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年4月3日 上午9:58:54
	  */
	@RequestMapping(value = "/area/list" , method = RequestMethod.GET)
	public void getArea(ModelMap model,HttpServletRequest req,
														@RequestParam(value="parentId", defaultValue="0") Long parentId,
														@RequestParam(value="areaCode", defaultValue="") String areaCode){
		String clientId=getClient(req);
		if( !StringUtil.empty(clientId)){
			model.putAll(areaSupport.getAreaList( parentId,areaCode,clientId));
		}else{
			model.put("code", "9999");
			model.put("message", "必要参数为空");
		}
	}
	
	
	/**
	  * 方法描述：根据 clientId 查询一级地区（首页定位专用）
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年5月26日 上午10:30:34
	  */
	@RequestMapping(value = "/area/index_list" , method = RequestMethod.GET)
	public void getArea(ModelMap model,HttpServletRequest req){
		String clientId=getClient(req);
		model.put("resResult",areaSupport.getIndexList( clientId));
	}
	
	
	/**
	  * 方法描述：根据areaCode + clientId 查询是否为当前客户端支持的地区
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年5月26日 上午10:30:34
	  */
	@RequestMapping(value = "/area/is_support" , method = RequestMethod.GET)
	public void getArea(ModelMap model,HttpServletRequest req,													
														@RequestParam(value="areaCode", defaultValue="") String areaCode){
		String clientId=getClient(req);
		if( !StringUtil.empty(clientId) &&  !StringUtil.empty(areaCode) ){
			model.putAll(areaSupport.isSupport( clientId,areaCode));
		}else{
			model.put("code", "9999");
			model.put("message", "必要参数为空");
		}
	}
	
	
	
}
