package com.froad.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ConfigFilter extends FilterSecurityInterceptor implements Filter {

	public ConfigFilter() {
	}
	
	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		// TODO Auto-generated method stub

	}

	public void init(FilterConfig arg0) throws ServletException {
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(arg0.getServletContext());  
		BaseService.getInstance().init(ctx); 
	    System.out.println("公告了==========================================");
	}

}
