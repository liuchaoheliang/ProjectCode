/**
 * Project Name:coremodule-bank
 * File Name:CheckPermissionInterceptor.java
 * Package Name:com.froad.cbank.coremodule.module.normal.bank.interceptor
 * Date:2015年9月30日上午11:27:37
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.bank.interceptor;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.common.util.web.CookieUtils;
import com.froad.cbank.coremodule.framework.common.valid.CheckPermission;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.bank.util.Constants;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.thrift.service.BankOperatorService;
import com.froad.thrift.service.FinityResourceService;
import com.froad.thrift.vo.BankOperatorVo;
import com.froad.thrift.vo.finity.FinityResourceVo;

/**
 * ClassName:CheckPermissionInterceptor
 * Reason:	 TODO ADD REASON.
 * Date:     2015年9月30日 上午11:27:37
 * @author   刘超 liuchao@f-road.com.cn
 * @version  
 * @see 	 
 */
public class CheckPermissionInterceptor extends HandlerInterceptorAdapter {
	
	@Resource
	BankOperatorService.Iface bankOperatorService;
	
	@Resource
	FinityResourceService.Iface finityResourceService;
	
    /**
     * Action执行 之前
     */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            CheckPermission annotation = method.getAnnotation(CheckPermission.class);
            //检验是否有权限校验注解并且是需要权限校验
            if (annotation != null && annotation.isForce()) {
            	String[] keys = annotation.keys();
            	LogCvt.info("当前请求包含的资源key:"+JSON.toJSONString(keys));
            	//获取用户角色，拉取资源数
            	String userId = request.getHeader("userId");
            	if(StringUtil.isEmpty(userId)){//如果userId为空则到cookie获取
            		userId=CookieUtils.getCookie(request, Constants.USER_ID_COOKIE).getValue();
            	}
            	if(StringUtil.isEmpty(userId)){//如果userId为空
            		return respError(response, EnumTypes.timeout.getCode(), EnumTypes.timeout.getMessage());
            	}
            	String clientId = request.getAttribute(Constants.CLIENT_ID)+"";
            	BankOperatorVo operatorVo =  bankOperatorService.getBankOperatorById(clientId, Long.valueOf(userId));
            	
            	if(operatorVo != null ){      
            		
            		//获取有效状态的资源列表
            		FinityResourceVo finityResourceVo = new FinityResourceVo();
            		finityResourceVo.setIsDelete(false);
             		finityResourceVo.setStatus(true);
             		finityResourceVo.setPlatform("bank");//设置获取银行平台资源 
             		
             		List<Long> roles = new ArrayList<Long>();
             		roles.add(operatorVo.getRoleId());
             		
					List<FinityResourceVo> resourceList = finityResourceService.getFinityResourceByRole(finityResourceVo,roles);
            		//LogCvt.info("当前用户的资源key:"+JSON.toJSONString(resourceList));
            		for(FinityResourceVo resource : resourceList ){
            			
            			String key=resource.getResourceKey();
            			//校验用户的权限树中是否含有调用接口需要的key
//            			String key="index";
            			boolean flag = indexof(keys, key);
            			if(flag){
            				LogCvt.info("权限校验通过");
            				return super.preHandle(request, response, handler);
            			}
            		}
            		LogCvt.info("权限校验未通过");
            		return respError(response, EnumTypes.noAuthority.getCode(), "对不起，权限不够");
            	}else{
            		return respError(response, EnumTypes.noRole.getCode(), "当前用户不存在");
            	}
            }
		}
		return super.preHandle(request, response, handler);
	}
	
	
	/**
	 * Indexof:()
	 *	判断字符串是否是数组中的一个
	 * @author 刘超 liuchao@f-road.com.cn
	 * 2015年9月30日 下午3:25:20
	 * @return
	 * 
	 */
	private boolean indexof(String[] arr, String reg) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].equals(reg)) {
				return true;
			}
		}
		return false;
	}

    /**
     * 生成视图之前执行
     */   
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}
	
	
	/**
	 * respError:()
	 *	
	 * @author 刘超 liuchao@f-road.com.cn
	 * 2015年9月30日 下午2:06:50
	 * @param response
	 * @param code
	 * @param msg
	 * @throws IOException
	 * 
	 */
	public static boolean respError(HttpServletResponse response,String code,String msg) throws IOException{
		response.setContentType("application/json");
		response.setStatus(608);
		response.getWriter().write("{\"code\":\""+code+"\",\"message\":\""+msg+"\"}");
		response.flushBuffer();
		return false;
	}
	
}
