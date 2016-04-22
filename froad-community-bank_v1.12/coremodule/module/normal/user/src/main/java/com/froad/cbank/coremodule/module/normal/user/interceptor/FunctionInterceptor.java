package com.froad.cbank.coremodule.module.normal.user.interceptor;

import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.froad.cbank.coremodule.module.normal.user.annotation.FunctionModule;
import com.froad.cbank.coremodule.module.normal.user.support.ClientConfigSupport;

/**
 * @Description: 功能模块拦截器
 * @Author: liaolibiao@f-road.com.cn
 * @Date: 2015年10月14日 下午6:12:47
 */
public class FunctionInterceptor extends HandlerInterceptorAdapter {

	@Resource
	private ClientConfigSupport clientConfigSupport;
	
	/**
	 * 程序处理请求完成之后执行
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

		if(handler instanceof HandlerMethod){
			HandlerMethod hm = (HandlerMethod)handler;
			Method method = hm.getMethod();
			FunctionModule fm = method.getAnnotation(FunctionModule.class);
			if(fm != null && modelAndView.getModel() != null){
				//查询功能模块 返回前端
				String clientId =(String) request.getAttribute("clientId");
				modelAndView.getModel().put("functionModule", clientConfigSupport.getClientFunctionModuleConfiguration(clientId));
			}
		}

	}
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
