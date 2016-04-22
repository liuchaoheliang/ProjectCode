package com.froad.cbank.coremodule.module.normal.user.controller.user;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.framework.common.util.type.BooleanUtil;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.module.normal.user.annotation.FunctionModule;
import com.froad.cbank.coremodule.module.normal.user.annotation.Token;
import com.froad.cbank.coremodule.module.normal.user.support.ClientConfigSupport;
import com.froad.cbank.coremodule.module.normal.user.support.CommentSupport;
import com.froad.cbank.coremodule.module.normal.user.support.FavoriteSupport;
import com.froad.cbank.coremodule.module.normal.user.support.TicketSupport;
import com.froad.cbank.coremodule.module.normal.user.support.VipSupport;

/**
 * 个人
 */
@Controller
public class UserController extends BasicSpringController{

	@Resource
	private VipSupport vipSupport;
	
	@Resource
	private TicketSupport ticketSupport;
	
	@Resource
	private CommentSupport commentSupport;
	
	@Resource
	private FavoriteSupport favoriteSupport;
	
	@Resource
	private ClientConfigSupport clientConfigSupport;
	
	
	/**
	 * 获取用户信息概要
	 * @param req
	 * @param memberCode
	 */
	@Token
	@RequestMapping(value = "/personal/summary", method = RequestMethod.GET)
	public void getPersonalCenterInfo(HttpServletRequest req, ModelMap model, @RequestHeader Long memberCode){
		String clientId = getClient(req);
		String vipStatus = null;
		int remainDay = 0;
		int ticketNum = 0;
		int proComment = 0;
		int outComment = 0;
		int proFav = 0;
		int outFav = 0;
		double discount = 0;
		Long expireTime = null;
		
		//VIP优惠金额
		discount = vipSupport.getVipDiscount(clientId, memberCode);
		
		//VIP信息
		Map<String, Object> vipMap = vipSupport.getMemberVipInfomation(memberCode,clientId);
		
		if(BooleanUtil.toBoolean(vipMap.get("isVip"))){
			//NORMAL("0001","正常") EXPIRED("0003","已过期");
			vipStatus = vipMap.get("vipStatus").toString();
			remainDay = Integer.parseInt(vipMap.get("remainDay").toString());
			expireTime = (Long) vipMap.get("expireTime");
		}
		
		
		ticketNum = ticketSupport.totalTickets(clientId, memberCode);
		
		proComment = commentSupport.getMemberProductCommentCount(clientId, memberCode);
		outComment = commentSupport.getMemberOutletCommentCount(clientId, memberCode);
		
		Map<String,Object> favMap = favoriteSupport.getFavoriteCount(clientId, memberCode);
		proFav = Integer.parseInt(favMap.get("selfProductFavoriteCount").toString());
		outFav = Integer.parseInt(favMap.get("selfOutletFavoriteCount").toString());
		
		model.put("vipStatus", vipStatus);
		model.put("discount", discount);
		model.put("remainDay", remainDay);
		model.put("expireTime", expireTime);
		model.put("comNum", proComment + outComment);
		model.put("favNum", proFav + outFav);
		model.put("ticNum", ticketNum);
		model.putAll(clientConfigSupport.getClientPaymentChannel(clientId));
	}
	
	
	
	/**
	 * @Description:我的订单首页
	 * @Author: liaolibiao@f-road.com.cn
	 * @Date: 2015年10月16日 下午3:16:50
	 * @param req
	 * @param model
	 */
	@Token
	@FunctionModule
	@RequestMapping(value = "/personal/myorder", method = RequestMethod.GET)
	public void viewMyorder(HttpServletRequest req, ModelMap model){
		model.put("result", null);
	}
	
	
	/**
	 * @Description: 安全中心首页
	 * @Author: liaolibiao@f-road.com.cn
	 * @Date: 2015年10月16日 下午3:17:43
	 * @param req
	 * @param model
	 */
	@Token
	@RequestMapping(value = "/personal/securityCenter", method = RequestMethod.GET)
	public void viewSecurityCenter(HttpServletRequest req, ModelMap model){
		String clientId = getClient(req);
		model.putAll(clientConfigSupport.getClientPaymentChannel(clientId));
	}
}
