package com.froad.cbank.coremodule.module.normal.bank.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.bank.util.Constants;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;



/**
 * 错误拦截
 * @ClassName ErrorInterceptor
 * @author zxl
 * @date 2015年4月8日 下午8:14:37
 */
public class BaseInterceptor extends HandlerInterceptorAdapter{
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String resource = request.getHeader("Referer");
//		String orgCode = request.getHeader("orgCode");
//		String username = request.getHeader("username");
//		String bankUserLoginFlag = request.getHeader("bankUserLoginFlag");
		if(resource ==null || "".equals(resource)) {
			this.respMessage(response,EnumTypes.noReferer);
	    	return false;
    	}
		String url = resource.replaceFirst("http://", "").replaceFirst("https://", "");
		String[] clientId = url.split("/");
		if(clientId.length < 1 && (clientId[1]==null || "".equals(clientId[1]))) {
			this.respMessage(response,EnumTypes.noClientId);
			return false;
		}
//		if(bankUserLoginFlag!=null && !"".equals(bankUserLoginFlag)) {
//			LogCvt.info("BaseInterceptor设置登录标识bankUserLoginFlag："+bankUserLoginFlag);
//			request.setAttribute(Constants.FLAG, bankUserLoginFlag);			
//		}
//		if(orgCode!=null && !"".equals(orgCode)) {
//			LogCvt.info("BaseInterceptor设置orgcode："+orgCode);
//			request.setAttribute(Constants.CODE, orgCode);			
//		}
//		if(username!=null && !"".equals(username)) {
//			LogCvt.info("BaseInterceptor设置username："+username);
//			request.setAttribute(Constants.USERNAME, username);	
//		}

		LogCvt.info("当前请求resource："+resource+" clientId："+clientId
				/*+" bankUserLoginFlag:"+bankUserLoginFlag+" orgCode:"+orgCode+" username:"+username*/
				);
		request.setAttribute(Constants.CLIENT_ID, clientId[1]);
        return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if(modelAndView!=null){
			String code = (String)modelAndView.getModel().get("code");
			if(code!=null&&!EnumTypes.success.getCode().equals(code)){
				response.setStatus(608);
			}
		}
		super.postHandle(request, response, handler, modelAndView);
	}
	
	private void respMessage(HttpServletResponse response,EnumTypes type) throws IOException{
		response.setContentType("application/json");
		response.setStatus(608);
		response.getWriter().write("{\"code\":\""+type.getCode()+"\",\"message\":\""+type.getMessage()+"\"}");
		response.flushBuffer();
	}
}
