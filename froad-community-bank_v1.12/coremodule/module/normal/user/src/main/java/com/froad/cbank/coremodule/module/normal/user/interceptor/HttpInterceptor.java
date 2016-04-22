package com.froad.cbank.coremodule.module.normal.user.interceptor;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpHeaders;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController.Results;
import com.froad.cbank.coremodule.module.normal.user.enums.ResultCode;

/** 
 * Http拦截器 - Http请求和返回结果处理
 * @ClassName: HttpInterceptor 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @Create Author: hjz
 * @Create Date: 2015年4月8日 下午6:16:21 
 */ 
public class HttpInterceptor extends HandlerInterceptorAdapter {
	/**
     * 最后执行，可用于释放资源
     */
    public void afterCompletion(HttpServletRequest res,HttpServletResponse rep, Object arg2, Exception e) throws Exception {
    	
    }
    
    /**
     * 生成视图之前执行
     */
    public void postHandle(HttpServletRequest res, HttpServletResponse rep,
            Object handler, ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub
    	if(modelAndView != null){
    		Object code = modelAndView.getModel().get(Results.code);
        	if(StringUtil.isNotBlank(code) && !ResultCode.success.getCode().equals(code.toString())){
        		rep.setStatus(608);
        	}
    	}
    	
    }
    
    /**
     * Action之前执行
     */
    public boolean preHandle(HttpServletRequest res, HttpServletResponse rep, Object handler) throws Exception {
    	//参数打印
    	showParaValues(res);
    	//clientId获取
    	String resource = res.getHeader(HttpHeaders.REFERER);
	    if(resource ==null || "".equals(resource)) {
	    	LogCvt.info("Http referer 异常：" + resource);
		    return false;
	    }
    	
    	String[] client = resource.split("/");
    	
    	if(client.length < 4 && (client[3]==null || "".equals(client[3]))) {
			LogCvt.info("Http referer 异常：" + resource);
			return false;
    	}
    	
    	res.setAttribute("clientId", client[3]);
    	
        return true;
        
    }
    
    
    
	
	/**
	  * 方法描述：将HttpServletRequest请求所有参数打印
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年3月26日 下午4:44:20
	  */
	public void showParaValues(HttpServletRequest req){  
		Map<String, Object> dataReq = new HashMap<String, Object>();
		Enumeration<String> paramNames=req.getParameterNames();
		while(paramNames.hasMoreElements()){
			 String paramName = (String) paramNames.nextElement();
			 String[] paramValues = req.getParameterValues(paramName);
			 if(paramValues==null || paramValues.length == 0){
				 dataReq.put(paramName, null);
			 }else if(paramValues.length == 1){
				 dataReq.put(paramName, paramValues[0]);
			 }else{
				 dataReq.put(paramName, paramValues);
			 }
		}
		LogCvt.debug("请求参数拦截："+JSON.toJSONString(dataReq));
	} 
}

