package com.froad.access;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.froad.sso.SSOCons;
import com.froad.util.Assert;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LoginInterceptor extends AbstractInterceptor{
	private Logger logger = Logger.getLogger(LoginInterceptor.class);
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ctx = invocation.getInvocationContext();
		Map session = ctx.getSession();
		String userId = (String)session.get("userId");
		
		if(Assert.empty(userId)) {
			logger.info("用户没有登录 !");
			
			
			if(session.get("service") == null ){
				session.put("session", SSOCons.LOCAL_SSO_CFG + "index.action");
				logger.info("创建一个sso   service");
			}
			
			
			
			return "login_page";
		}
		 
		return invocation.invoke();
	}
	
}
