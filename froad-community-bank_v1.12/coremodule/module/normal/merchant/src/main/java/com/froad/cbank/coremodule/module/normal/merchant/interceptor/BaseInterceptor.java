package com.froad.cbank.coremodule.module.normal.merchant.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.merchant.utils.Constants;
import com.froad.cbank.coremodule.module.normal.merchant.utils.EnumTypes;
import com.froad.cbank.coremodule.module.normal.merchant.utils.TargetObjectFormat;
import com.froad.thrift.vo.PlatType;


/**
 * 公共拦截
 * @ClassName BaseInterceptor
 * @author zxl
 * @date 2015年4月23日 下午7:32:28
 */
public class BaseInterceptor extends HandlerInterceptorAdapter{
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String referer = request.getHeader("Referer");
		if(referer == null){
			LogCvt.error("referer is null");
			return false;
		}
		LogCvt.info("Referer: "+referer);
		String url = referer.replaceFirst("http://", "").replaceFirst("https://", "");
		String[] clientId = url.split("/");
		if(clientId.length < 2) {
			return false;
		}
		request.setAttribute(Constants.CLIENT_ID, clientId[1]);
		if(referer.contains("/admin/mmerchant")){
			request.setAttribute(Constants.PLAT_TYPE, PlatType.merchant_h5);
		}else{
			request.setAttribute(Constants.PLAT_TYPE, PlatType.merchant_pc);
		}
		request.setAttribute(Constants.CLIENT_IP, TargetObjectFormat.getIpAddr(request));
		return super.preHandle(request, response, handler);
		
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
}
