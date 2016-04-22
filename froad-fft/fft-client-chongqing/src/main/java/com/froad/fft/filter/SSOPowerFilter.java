package com.froad.fft.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.froad.fft.common.SessionKey;
import com.froad.fft.dto.UserEngineDto;
import com.froad.fft.support.base.impl.UserEngineSupportImpl;
import com.froad.fft.util.NullValueCheckUtil;
import com.froad.fft.util.SpringUtil;
import com.froad.sso.client.authentication.AttributePrincipal;

/**
 * *******************************************************
 *<p> 工程: fft-client-chongqing </p>
 *<p> 类名: SSOPowerFilter.java </p>
 *<p> 描述: *-- <b>获取SSO服务器信息</b> --* </p>
 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
 *<p> 时间: 2014年4月23日 下午3:07:28 </p>
 ********************************************************
 */
public class SSOPowerFilter implements Filter {

	private static Logger logger = Logger.getLogger(SSOPowerFilter.class);

	final String loginURL = "/shop/login/index.jhtml";//登录页面
	final String logoutURL = "/common/logout.jhtml";
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}
	
	@Override
	public void destroy() {}

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>为自动登录提供服务</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月23日 下午4:28:25 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	private void ssoLogin(String remoteUser,HttpSession session,HttpServletRequest req){
		session.setAttribute(SessionKey.SSO_MEMBER_INFO.key(), remoteUser);
		logger.info("已创建本地SSO登录信息");
		UserEngineSupportImpl userEngineSupportImpl = (UserEngineSupportImpl) SpringUtil.getBean("userEngineSupportImpl");
		UserEngineDto userEngineDto = userEngineSupportImpl.updateUserPoints(remoteUser);		
		logger.info("为该用户执行登录动作：" + JSONObject.toJSONString(userEngineDto));
		session.setAttribute(SessionKey.LOGIN_IDENTIFICATION.key(), userEngineDto);
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>为自动退出提供服务</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月24日 上午9:37:14 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	private void ssoLogout(HttpSession session){
		logger.info("清除session信息");
		@SuppressWarnings("unchecked")
		Enumeration<String> sessionKeys = session.getAttributeNames(); //获取所有的session Key
		while (sessionKeys.hasMoreElements()) {
			session.removeAttribute(sessionKeys.nextElement());//移除所有session Key 对应的Object
		}
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		try {
			HttpSession session = req.getSession(false);
			if(session == null){
				chain.doFilter(request, response);//session信息不存在
				return;
			}
			AttributePrincipal principal = (AttributePrincipal) req.getUserPrincipal();
			String remoteUser = null;
			if(principal != null){
				remoteUser = (String) principal.getAttributes().get("loginId");
			}
			Object ssoMemberInfo = session.getAttribute(SessionKey.SSO_MEMBER_INFO.key());
			if(!NullValueCheckUtil.isStrEmpty(remoteUser)){
				if(ssoMemberInfo == null){//如果本地并无该用户的SSO信息
					//TODO: 是否判断商户登录
					logger.info("获取SSO用户为：" + remoteUser);
					ssoLogin(remoteUser, session, req);
					return;
				}else if(((String)ssoMemberInfo).equals(remoteUser) || ((String)ssoMemberInfo).equals(remoteUser + "\\|")){
					//该用户已经登录
					if(req.getServletPath().endsWith(logoutURL)){//如果登录用户访问退出请求地址
						res.sendRedirect("/"+req.getRequestURI().split("/")[1] + logoutURL);
						return;
					}
					chain.doFilter(request, response);
					return;
				}else{
					logger.info("从SSO服务器上获取的用户信息和本地当前登录的信息不一致");
					logger.info("注销当前用户，登录新用户");
					ssoLogout(session);
					ssoLogin(remoteUser, session, req);
					chain.doFilter(request, response);
					return;
				}
			}else{
				if(ssoMemberInfo == null){
					//未获取到远程用户信息且本地系统没有登录信息，逻辑继续
					chain.doFilter(request, response);
					return;
				}else{
					//本地登录信息存在，但未从SSO服务器上获取到用户信息，所以SSO用户被远端退出
					ssoLogout(session);
					chain.doFilter(request, response);
					return;
				}
			}
		} catch (Exception e) {
			logger.error("---------------------SSO信息过滤器处理异常，页面重定向到首页", e);
			res.sendRedirect("/"+req.getRequestURI().split("/")[1] + loginURL);
		}
	}
}
