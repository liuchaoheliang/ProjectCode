package com.froad.cbank.coremodule.module.normal.user.controller.ticket;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.module.normal.user.annotation.FunctionModule;
import com.froad.cbank.coremodule.module.normal.user.annotation.Token;
import com.froad.cbank.coremodule.module.normal.user.pojo.PagePojo;
import com.froad.cbank.coremodule.module.normal.user.support.TicketSupport;


	/**
	 * 类描述：券相关
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2015 
	 * @author: 刘超 liuchao@f-road.com.cn
	 * @time: 2015年3月23日 下午5:55:47 
	 */

@Controller
@RequestMapping
public class TicketController extends BasicSpringController{
	
	
	@Resource 
	private TicketSupport ticketSupport;
	
	/**
	  * 方法描述：
	  * @param: 获取用户所有券信息
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年3月23日 下午5:59:06
	  */
	@Token
	@FunctionModule
	@RequestMapping(value="/ticket/list",method=RequestMethod.GET)
	public void getMyTicket(ModelMap model, HttpServletRequest req,@RequestHeader String memberCode,PagePojo pagePoj){
		model.clear();
		String clientId=getClient(req);
//		JSONObject obj=toJsonObj(req);
		//基础数据
//		String memberCode = obj.getString("memberCode");
		String type = req.getParameter("type");
		String status = req.getParameter("status");
		String timeType = req.getParameter("timeType");
		model.putAll(ticketSupport.getMyTicket(clientId, memberCode, type, status,timeType,pagePoj));
		
	}
	
	
	/**
	  * 方法描述：查询券号详情
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年3月24日 下午3:54:12
	  */
	@Token
	@FunctionModule
	@RequestMapping(value="/ticket/detail",method=RequestMethod.GET)
	public void getTicketDatail(ModelMap model,HttpServletRequest req,String ticketId){
		String clientId=getClient(req);
		if(!StringUtil.empty(clientId)){
			model.putAll(ticketSupport.getTicketDetail(clientId,ticketId));
		}else{
			model.put("code", "9999");
			model.put("message", "必要参数为空");
		}
		
	}
	
	
	
	
	
	
	/**
	  * 方法描述：根据子订单号查询券列表
	  * @param: memberCode 用户标识，clientId 客户端 ,subOrderId 自订单号
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年4月7日 上午9:58:13
	  */
	@Token
	@RequestMapping(value="/ticket/subOrderId",method=RequestMethod.GET)
	public void getTransTicket(ModelMap model,HttpServletRequest req,String subOrderId, @RequestHeader  String memberCode,PagePojo pagePojo){
		String clientId=getClient(req);
		if(!StringUtil.empty(memberCode) && !StringUtil.empty(clientId) ){
			model.putAll(ticketSupport.getTransTicket(clientId,subOrderId,memberCode,pagePojo));
		}else{
			model.put("code", "9999");
			model.put("message", "必要参数为空");
		}
	}
	
	
}
