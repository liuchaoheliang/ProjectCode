package com.froad.cbank.coremodule.normal.boss.interceptor;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.normal.boss.annotation.Auth;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.enums.Platform;
import com.froad.cbank.coremodule.normal.boss.pojo.BossUser;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.ErrorEnums;
import com.froad.thrift.service.FinityResourceService;
import com.froad.thrift.vo.finity.FinityResourceVo;



/**
 * 权限拦截器
 * @ClassName AuthInterceptor
 * @author zxl
 * @date 2016年1月4日 下午2:28:42
 */
public class AuthInterceptor extends HandlerInterceptorAdapter{
	
	@Resource
	FinityResourceService.Iface finityResourceService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		if(handler instanceof HandlerMethod){
			try {
				
				HandlerMethod method = (HandlerMethod)handler;
				
				Auth auth = method.getMethodAnnotation(Auth.class);
				if(auth == null){
					return true;
				}else{
					String[] keys = auth.keys();
					LogCvt.info("权限检查keys:"+JSON.toJSONString(keys));
					
					//获取用户信息
					BossUser user = (BossUser)request.getAttribute(Constants.BOSS_USER);
					Long uid = user.getId();
					
					//获取角色资源
					FinityResourceVo vo = new FinityResourceVo();
					vo.setPlatform(Platform.boss.getCode());
					List<FinityResourceVo> list = finityResourceService.getFinityResourceByUser(vo, uid, 0);
					
					if(list !=null && list.size()>0){
						//检查资源key
						if(!checkResource(list,keys)){
							respError(response,ErrorEnums.noauth.getMsg());
							return false;
						}
						return true;
					}else{
						LogCvt.info("user resource is null");
						respError(response,ErrorEnums.noauth.getMsg());
						return false;
					}
				}

			} catch (Exception e) {
				LogCvt.error(e.getMessage(), e);
				respError(response,"权限检查异常");
				return false;
			}
		}else{
			return super.preHandle(request, response, handler);
		}
	}
	
	private static boolean checkResource(List<FinityResourceVo> list,String[] keys){
		for(FinityResourceVo vo : list){
			for(String key : keys){
				if(key.equals(vo.getResourceKey())){
					return true;
				}
			}
		}
		return false;
	}
	
	public static void respError(HttpServletResponse response,String msg) throws IOException{
		response.setContentType("application/json");
		response.setStatus(608);
		response.getWriter().write("{\"code\":\""+ErrorEnums.noauth.getCode()+"\",\"message\":\""+msg+"\"}");
		response.flushBuffer();
	}
	
	@Override
	public void postHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}
}
