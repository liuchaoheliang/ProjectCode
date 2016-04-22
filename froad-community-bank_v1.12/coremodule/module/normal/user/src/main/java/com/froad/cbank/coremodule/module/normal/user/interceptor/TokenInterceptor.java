package com.froad.cbank.coremodule.module.normal.user.interceptor;

import static com.froad.cbank.coremodule.module.normal.user.utils.Constants.Token.COOKIE_KEY;
import static com.froad.cbank.coremodule.module.normal.user.utils.Constants.Token.TOKEN_REFRESH_TIME;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpHeaders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.froad.cbank.coremodule.framework.common.util.type.JsonUtil;
import com.froad.cbank.coremodule.framework.common.util.type.NumberUtil;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.common.util.web.ResponseUtils;
import com.froad.cbank.coremodule.framework.common.util.web.WebUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.framework.web.basic.common.Constants.Results;
import com.froad.cbank.coremodule.module.normal.user.annotation.Token;
import com.froad.cbank.coremodule.module.normal.user.enums.ResultCode;
import com.froad.cbank.coremodule.module.normal.user.support.UserEngineSupport;


/**
 * token拦截器
 * @Description: TODO(这里用一句话描述这个类的作用) 
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {

	@Resource
	private UserEngineSupport userEngineSupport;

	/**
	 * 程序处理请求之前
	 * @Description 验证token
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
	    String member="";
		if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Token annotation = method.getAnnotation(Token.class);
            if (annotation != null && !annotation.createKey()) {
            	//验证token
            	String token = request.getHeader("token");
            	String memberCode = request.getHeader("memberCode");
            	member=memberCode;
            	if(StringUtil.isNotBlank(token) && StringUtil.isNotBlank(memberCode)){
            		boolean result = userEngineSupport.verifyToken(token, NumberUtil.toLong(memberCode));
            		if(!result){
            			LogCvt.info(String.format("用户身份token校验失败：membercode : %s | token : %s | request uri : %s", memberCode, token, request.getRequestURI()));
            			//清除cookie
            			userEngineSupport.clearUbankTokenCookie(request,response);

            			return errorLogin(response, ResultCode.wrongToken.getCode(), "登录超时,请重新登录");
            		}
            	} else {
            		LogCvt.info(String.format("用户身份token校验参数为空：membercode : %s | token : %s | request uri : %s", memberCode, token, request.getRequestURI()));
            		return errorLogin(response, ResultCode.missingParam.getCode(), "登录校验参数为空");
            	}
            }else if(annotation == null){
            	//请求达一定次数，刷新token
            	int rt = NumberUtil.toInt(request.getHeader("rt"));
            	String token = request.getHeader("token");
            	String memberCode = request.getHeader("memberCode");
            	member=memberCode;
            	if(rt % TOKEN_REFRESH_TIME == 0 && StringUtil.isNotBlank(token) && StringUtil.isNotBlank(memberCode)){
            		//只刷新token,不校验结果
            		LogCvt.info("登录用户刷新token有效时间 >> memberCode：" + memberCode);
            		userEngineSupport.verifyToken(token, NumberUtil.toLong(memberCode));
            	}
            }
            request.setAttribute("memberCode", member);
            return super.preHandle(request, response, handler);
        } else {
            return super.preHandle(request, response, handler);
        }
	}

	/**
	 * 程序处理请求之后
	 * @Description 建立并传递token
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Token annotation = method.getAnnotation(com.froad.cbank.coremodule.module.normal.user.annotation.Token.class);
            if (annotation != null && annotation.createKey()) {//创建token
            	Object tokenObject = modelAndView.getModel().get("token");
            	Object userObject = modelAndView.getModel().get("userAcct");
            	if(tokenObject != null){
            		String tokenKey = StringUtil.toString(tokenObject);
                	modelAndView.getModel().remove("token");
                	//添加cookie
                	String COOKIE_DOMAIN = userEngineSupport.getUbankTokenCookieDomain(request);

        			Cookie cookie = new Cookie(COOKIE_KEY, tokenKey);
        			//cookie.setSecure(true);
        			cookie.setDomain(COOKIE_DOMAIN);
        			cookie.setPath("/");
        			response.addCookie(cookie);
                	//建立并传递token
            	}else if(userObject != null){//登录成功，但是token为空，打印错误日志
            		LogCvt.info(String.format("Token Cookie 创建失败!	请求路径：%s, 请求IP：%s, Header Referer: %s", request.getRequestURI(), WebUtil.getClientIpAddr(request), request.getHeader(HttpHeaders.REFERER)));
            	}


            }
        }
	}

	/**
	 * 所有请求处理完成之后被调用的(如视图呈现之后).
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,  
        HttpServletResponse response,Object handler,Exception ex)throws Exception{  
    }
	
	
	private boolean errorLogin(HttpServletResponse response, String errorCode, String errorMsg){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(Results.code, errorCode);
		map.put(Results.msg, errorMsg);
		response.setStatus(608);
		ResponseUtils.renderJson(response, JsonUtil.buildNormalBinder().toJson(map));
		return false;
	}







}
