package com.froad.cbank.coremodule.module.normal.merchant.interceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.merchant.exception.MerchantException;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.MerchantUser;
import com.froad.cbank.coremodule.module.normal.merchant.service.Common_Service;
import com.froad.cbank.coremodule.module.normal.merchant.utils.Constants;
import com.froad.cbank.coremodule.module.normal.merchant.utils.EnumTypes;
import com.froad.cbank.coremodule.module.normal.merchant.utils.RoleConstant;
import com.froad.thrift.vo.ResourceVo;


/**
 * TOKEN拦截器
 * @ClassName TokenInterceptor
 * @author zxl
 * @date 2015年3月26日 上午10:04:18
 */
public class TokenInterceptor extends HandlerInterceptorAdapter{
	
	@Resource
	Common_Service common_Service;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		try {
			String token=request.getHeader("token");
			String id=request.getHeader("uid");
			
			//用户登录检查
			MerchantUser user = common_Service.verifyUser(request, token, id);
			
			//权限检查
//			if(PlatType.merchant_pc.equals((PlatType)request.getAttribute(Constants.PLAT_TYPE))){
//				if(!authCheck(request,res.getMerchantRoleId())){
//					LogCvt.error("no auth: refere=" + request.getHeader("Referer") +" api=" + request.getRequestURI().replaceFirst("/merchant", ""));
//					respAuthError(response);
//					return false;
//				}
//			}
			
			request.setAttribute(Constants.MERCHANT_USER, user);
			
			return true;
		} catch(MerchantException e){
			respError(response,e.getMsg());
			return false;
		} catch (Exception e) {
			LogCvt.error(e.getMessage(),e);
			respError(response);
			return false;
		}
		
	}
	
	public static boolean authCheck(HttpServletRequest request,String roleId){
		
		HashMap<String, List<ResourceVo>> roleMap = RoleConstant.getRole();
		if(roleMap.isEmpty()){
			return false;
		}
		List<ResourceVo> listRole = roleMap.get(request.getAttribute(Constants.CLIENT_ID)+"_"+roleId);
		if(listRole == null || listRole.size()<=0){
			return false;
		}
		String uri = request.getRequestURI().replaceFirst("/merchant", "");
		String referer = request.getHeader("Referer");
		for(ResourceVo r : listRole){
			if(StringUtils.isNotBlank(r.getResource_url()) && referer.contains(r.getResource_url())){
				if(StringUtils.isNotBlank(r.getApi())&&r.getApi().contains(uri)){
					return true;
				}else{
					return false;
				}
			}
		}
		return false;
	}
	
	public static void respError(HttpServletResponse response,String msg) throws IOException{
		response.setContentType("application/json");
		response.setStatus(608);
		response.getWriter().write("{\"code\":\""+EnumTypes.timeout.getCode()+"\",\"message\":\""+msg+"\"}");
		response.flushBuffer();
	}
	
	public static void respError(HttpServletResponse response) throws IOException{
		response.setContentType("application/json");
		response.setStatus(608);
		response.getWriter().write("{\"code\":\""+EnumTypes.timeout.getCode()+"\",\"message\":\""+EnumTypes.timeout.getMsg()+"\"}");
		response.flushBuffer();
	}
	
	public static void respAuthError(HttpServletResponse response) throws IOException{
		response.setContentType("application/json");
		response.setStatus(608);
		response.getWriter().write("{\"code\":\""+EnumTypes.noauth.getCode()+"\",\"message\":\""+EnumTypes.noauth.getMsg()+"\"}");
		response.flushBuffer();
	}
	
	@Override
	public void postHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}
}
