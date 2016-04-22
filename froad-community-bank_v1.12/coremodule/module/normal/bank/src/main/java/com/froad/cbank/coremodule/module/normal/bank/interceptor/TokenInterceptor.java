package com.froad.cbank.coremodule.module.normal.bank.interceptor;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.bank.util.Constants;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.thrift.service.BankOperatorService;
import com.froad.thrift.service.OrgUserRoleService;
import com.froad.thrift.vo.BankOperatorCheckVoRes;


/**
 * TOKEN拦截器
 * 
 * @ClassName TokenInterceptor
 * @author zxl
 * @date 2015年3月26日 上午10:04:18
 */
public class TokenInterceptor extends HandlerInterceptorAdapter{
	
	@Resource
	BankOperatorService.Iface bankOperatorService;
	@Resource
	OrgUserRoleService.Iface orgUserRoleService;
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		try {
			String token = request.getHeader("token");
			String userId = request.getHeader("userId");
			String clientId = request.getAttribute(Constants.CLIENT_ID).toString();
			if (token == null || userId == null) {
				respError(response);
				return false;
			}
			LogCvt.info("银行PC登录的方式checkToken:参数 token：" + token + " userId："
					+ userId + " clientId：" + clientId);
			BankOperatorCheckVoRes resVo = bankOperatorService.checkToken(clientId, Long.valueOf(userId), token);
			LogCvt.info("token拦截器登录状态返回:" + JSON.toJSONString(resVo));
			if (!resVo.getResult().getResultCode().equals(EnumTypes.success.getCode())) {
				respError(response, EnumTypes.timeout.getCode(), resVo.getResult().getResultDesc());
				return false;
			}
			request.setAttribute(Constants.BANKOPERATOR, resVo.getBankOperator());
			request.setAttribute(Constants.USER_ID, resVo.getBankOperator().getId());
			return true;
		} catch (Exception e) {
			LogCvt.error(e.getMessage(),e);
			respError(response);
			return false;
		}
		
	}
	
	public static void respRoleError(HttpServletResponse response) throws IOException{
		response.setContentType("application/json");
		response.setStatus(608);
		response.getWriter().write("{\"code\":\""+EnumTypes.noRole.getCode()+"\",\"message\":\""+EnumTypes.noRole.getMessage()+"\"}");
		response.flushBuffer();
	}
	
	public static void respError(HttpServletResponse response) throws IOException{
		response.setContentType("application/json");
		response.setStatus(608);
		response.getWriter().write("{\"code\":\""+EnumTypes.timeout.getCode()+"\",\"message\":\""+EnumTypes.timeout.getMessage()+"\"}");
		response.flushBuffer();
	}
	
	public static void respError(HttpServletResponse response,String code,String msg) throws IOException{
		response.setContentType("application/json");
		response.setStatus(608);
		response.getWriter().write("{\"code\":\""+code+"\",\"message\":\""+msg+"\"}");
		response.flushBuffer();
	}
	
	@Override
	public void postHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

}
