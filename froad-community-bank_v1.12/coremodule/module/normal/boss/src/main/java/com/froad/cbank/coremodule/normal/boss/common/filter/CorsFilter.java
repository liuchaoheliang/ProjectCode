package com.froad.cbank.coremodule.normal.boss.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class CorsFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		 HttpServletResponse response = (HttpServletResponse) arg1;
		 
		 response.addHeader("Access-Control-Allow-Origin", "*");
		 response.addHeader("Access-Control-Allow-Headers", "accept,content-type,token,userId");
//		 response.addHeader("Access-Control-Allow-Credentials", "true");
		 
		 arg2.doFilter(arg0, arg1);
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
