package com.froad.cbank.coremodule.framework.expand.log.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;


/**
 * 日志拦截器
 * @ClassName LogInterceptor
 * @author zxl
 * @date 2015年3月20日 上午9:49:36
 */
public class LogInterceptor extends HandlerInterceptorAdapter {
	
	private long begtime = 0l;

	@Override
	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
//		LogCvt.info("完成请求["+(System.currentTimeMillis()-begtime)+"]:"+request.getRequestURL());
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Thread.currentThread().setName(request.getSession().getId());
		begtime = System.currentTimeMillis();
		LogCvt.info("接收请求:"+request.getRequestURL());
		return super.preHandle(request, response, handler);
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) throws Exception {
		if(ex != null){
			LogCvt.error(ex.getMessage(), ex);
		}
		LogCvt.info("完成请求["+(System.currentTimeMillis()-begtime)+"ms]:"+request.getRequestURL());
		super.afterCompletion(request, response, handler, ex);
	}

}

