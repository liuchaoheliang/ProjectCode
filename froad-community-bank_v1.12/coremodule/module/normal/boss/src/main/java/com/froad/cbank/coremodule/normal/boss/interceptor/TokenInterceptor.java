package com.froad.cbank.coremodule.normal.boss.interceptor;

import java.io.IOException;
import java.lang.annotation.Annotation;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.annotation.ImpExp;
import com.froad.cbank.coremodule.normal.boss.annotation.NoToken;
import com.froad.cbank.coremodule.normal.boss.support.LoginBossSupport;
import com.froad.cbank.coremodule.normal.boss.utils.ErrorEnums;



/**
 * TOKEN拦截器
 * @ClassName TokenInterceptor
 * @author zxl
 * @date 2015年3月26日 上午10:04:18s
 */
public class TokenInterceptor extends HandlerInterceptorAdapter{
	
	@Resource
	LoginBossSupport loginBossSupport;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		if(handler instanceof HandlerMethod){
			try {
				
				HandlerMethod method = (HandlerMethod)handler;
				
				Annotation annotation = method.getMethodAnnotation(NoToken.class);
				if(annotation!=null){
					return true;
				}
				
				String token = null;
				String id = null;
				
				annotation = method.getMethodAnnotation(ImpExp.class);
				if(annotation!=null){
					token = request.getParameter("token");
					id = request.getParameter("userId");
				}else{
					token = request.getHeader("token");
					id = request.getHeader("userId");
				}
				
				if (token == null || id == null) {
					respError(response);
					return false;
				}
				long i = Long.parseLong(id);
				
				loginBossSupport.tokenCheck(request,token, i);
					
				return true;

			} catch (Exception e) {
				LogCvt.error(e.getMessage(), e);
				respError(response);
				return false;
			}
		}else{
			return super.preHandle(request, response, handler);
		}
	}
	
	public static void respError(HttpServletResponse response) throws IOException{
		response.setContentType("application/json");
		response.setStatus(608);
		response.getWriter().write("{\"code\":\""+ErrorEnums.timeout.getCode()+"\",\"message\":\""+ErrorEnums.timeout.getMsg()+"\"}");
		response.flushBuffer();
	}
	
	@Override
	public void postHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}
}
