package com.froad.cbank.coremodule.normal.boss.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.froad.cbank.coremodule.normal.boss.utils.Constants;



/**
 * 公共拦截
 * @ClassName BaseInterceptor
 * @author 
 * @date 2015年4月23日 下午7:32:28
 */
public class BaseInterceptor extends HandlerInterceptorAdapter{
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		return true;
		
	}
	
	@Override
	public void postHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if(modelAndView!=null){
			String code = (String)modelAndView.getModel().get("code");
			if(code!=null&&!Constants.RESULT_SUCCESS.equals(code)){
				response.setStatus(608);
			}
		}
		super.postHandle(request, response, handler, modelAndView);
	}
}
