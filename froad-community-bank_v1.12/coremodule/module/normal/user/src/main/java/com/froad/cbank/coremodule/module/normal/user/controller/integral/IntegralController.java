package com.froad.cbank.coremodule.module.normal.user.controller.integral;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.module.normal.user.annotation.Token;
import com.froad.cbank.coremodule.module.normal.user.support.ClientConfigSupport;
import com.froad.cbank.coremodule.module.normal.user.support.IntegralSupport;
import com.froad.cbank.coremodule.module.normal.user.support.UserEngineSupport;
import com.froad.cbank.coremodule.module.normal.user.utils.DateUtils;
import com.froad.cbank.coremodule.module.normal.user.vo.DisplayPayChannelVo;
import com.pay.user.dto.UserSpecDto;

/**
 * 用户积分
 */
@Controller
public class IntegralController extends BasicSpringController {

	@Resource
	private IntegralSupport integralSupport;

	@Resource
	private UserEngineSupport userEngineSupport;
	
	@Resource
	private ClientConfigSupport clientConfigSupport;
	
	/**
	 * 
	 * @Description: 我的积分
	 * @Author: liaolibiao@f-road.com.cn
	 * @Date: 2015年10月18日 下午4:07:32
	 * @param model
	 * @param request
	 * @param memberCode
	 * @param pointsType
	 * @param month
	 * @param pageSize
	 * @param pageNumber
	 */
	@Token
	@RequestMapping(value = "/integral/mine", method = RequestMethod.GET)
	public void queryMyPointsInfo(ModelMap model, HttpServletRequest request, @RequestHeader Long memberCode, String pointsType, int month, int pageSize, int pageNumber){
		
		String clientId=getClient(request);
		LogCvt.info(String.format("查询积分消费列表请求参数:[clientId=%s,pointsType=%s]",clientId,pointsType));
		
		long endTime = 0L;
		long startTime = 0L;
		//查询全部默认查询一年以内数据
		if(month <= 0){
			month = 12;
		}
		if(month > 0) {
			Date endDate = new Date();
			Date startDate = DateUtils.getDateBeforeOrAfterNMonths(-month, endDate);
			endTime = endDate.getTime();
			startTime = startDate.getTime();
		}
		
		UserSpecDto user = userEngineSupport.queryMemberByMemberCode(memberCode);
		if(user == null){
			model.put(Results.code, "9999");
			model.put(Results.msg, "查询用户信息失败");
			return;
		}
		
		//查询用户积分
		model.putAll(integralSupport.getMemberPoints(user.getLoginID(), clientId));
		
		//查询支付渠道
		Map<String,Object> payChannelMap = clientConfigSupport.getClientPaymentChannel(clientId);
		model.putAll(payChannelMap);
		
		DisplayPayChannelVo displayChannel = (DisplayPayChannelVo) payChannelMap.get("payChannel");
		String queryPointsType = "";
		if(StringUtil.isNotBlank(pointsType)){
			queryPointsType = pointsType;
		}else{
			if(displayChannel.isDisplayUnionPoint()){
				queryPointsType = "froadPoints";
			}else if(displayChannel.isDisplayBankPoint()){
				queryPointsType = "bankPoints";
			}
		}
		//查询积分记录
		model.putAll(integralSupport.getMemberIntegralRecord(queryPointsType, user.getLoginID(), clientId, startTime, endTime, pageSize, pageNumber));
	}
	

	/**
	 * pointAmount:查询用户的积分情况（对冲现金金额+积分值）
	 *
	 * @author 刘超 liuchao@f-road.com.cn
	 * 2015年11月26日 下午1:31:13
	 * @param model
	 * @param request
	 * @param memberCode
	 * @param userName
	 * 
	 */
	@Token
	@RequestMapping(value = "/integral/pointAmount", method = RequestMethod.GET)
	public void pointAmount(ModelMap model, HttpServletRequest request, @RequestHeader Long memberCode, @RequestParam("userName") String userName){		
		String clientId=getClient(request);
		LogCvt.info(String.format("查询用户积分值请求参数:[clientId=%s,userName=%s]",clientId,userName));
		if(StringUtil.isNotBlank(clientId)){
			
			//针对用户名为身份证加密的情况
			if(userName != null && userName.indexOf("*") != -1){
				UserSpecDto user = userEngineSupport.queryMemberByMemberCode(memberCode);
				if(user != null){
					userName = user.getLoginID();
				}
			}
			
			model.putAll(integralSupport.getUserPointsAmount(clientId, userName));
		}else{
			model.put("code", "9999");
			model.put("message", "必要参数为空");
		}
		
		
	}
	
}
