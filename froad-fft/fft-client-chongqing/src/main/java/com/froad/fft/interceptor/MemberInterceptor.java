package com.froad.fft.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 会员拦截器
 * @author FQ
 *
 */
public class MemberInterceptor extends HandlerInterceptorAdapter {

	private static final String REDIRECT_VIEW_NAME_PREFIX = "redirect:";//重定向视图名称前缀
	
	//默认登录URL
	private static final String DEFAULT_LOGIN_URL = "/login.jhtml";

	//登录URL
	private String loginUrl = DEFAULT_LOGIN_URL;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		return super.preHandle(request, response, handler);
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if (modelAndView != null) {
			String viewName = modelAndView.getViewName();
			
		}
	}
}
