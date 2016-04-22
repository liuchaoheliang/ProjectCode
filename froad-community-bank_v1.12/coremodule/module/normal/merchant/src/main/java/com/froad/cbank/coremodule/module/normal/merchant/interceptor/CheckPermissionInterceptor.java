/**
 * Project Name:coremodule-bank
 * File Name:CheckPermissionInterceptor.java
 * Package Name:com.froad.cbank.coremodule.module.normal.bank.interceptor
 * Date:2015年9月30日上午11:27:37
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.merchant.interceptor;

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
import com.froad.cbank.coremodule.framework.common.valid.CheckPermission;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.merchant.exception.MerchantException;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.MerchantUser;
import com.froad.cbank.coremodule.module.normal.merchant.service.Common_Service;
import com.froad.cbank.coremodule.module.normal.merchant.utils.Constants;
import com.froad.cbank.coremodule.module.normal.merchant.utils.EnumTypes;
import com.froad.thrift.service.BankOperatorService;
import com.froad.thrift.service.FinityResourceService;
import com.froad.thrift.service.FinityRoleResourceService;
import com.froad.thrift.service.OrgUserRoleService;
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
	Common_Service common_Service;
	
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
            	String token=request.getHeader("token");
    			String id=request.getHeader("uid");
    			
    			//用户登录检查
    			try {
    				MerchantUser user = common_Service.verifyUser(request, token, id);
  
		        	if(user != null ){      
		        		
		        		//获取有效状态的资源列表
		        		FinityResourceVo finityResourceVo = new FinityResourceVo();
		        		finityResourceVo.setIsDelete(false);
		         		finityResourceVo.setStatus(true);
		         		finityResourceVo.setPlatform("merchant");//设置获取商户平台资源 
		         		
		         		//资源类型（1-平台，2-模块，3-元素）
		         		int type=1;
						List<FinityResourceVo> resourceList = finityResourceService.getFinityResourceByUser(finityResourceVo,user.getId(),type);
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
		        		return respError(response, EnumTypes.noresult.getCode(), "当前用户不存在");
		        	}
				}catch(MerchantException e){
					LogCvt.info("用户登录校验未通过");
					return respError(response, e.getCode(), e.getMsg());
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
