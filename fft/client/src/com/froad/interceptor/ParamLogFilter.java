package com.froad.interceptor;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

public class ParamLogFilter implements Filter {

	private static Logger logger = Logger.getLogger(ParamLogFilter.class);
	
	@Override
	public void destroy() {}
	
	@Override
	public void init(FilterConfig config) throws ServletException {}
	
	private void getParams(HttpServletRequest req,boolean isPrint){
		logger.info("请求参数列表|begin" + "");
		if(isPrint){
			Enumeration<String> paramNames = req.getParameterNames();
			while (paramNames.hasMoreElements()) {
				String param = (String)paramNames.nextElement();
				String[] paramVals = req.getParameterValues(param);
				logger.info("参数名: " + param + " | 参数值: " + JSONArray.fromObject(paramVals).toString()+"");
			}
		}else{
			logger.info("敏感信息不予打印");
		}
		logger.info("请求参数列表|end" + "");
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		logger.info("==================================请求详细信息|begin=================================");
		logger.info("IP: " + req.getRemoteAddr() + "|URI: " + req.getServletPath() + "");
		boolean isPrint = true;
		String path = req.getRequestURI();
		if(path.endsWith("validateLogin.action") || path.endsWith("merchant_login.action")){
			isPrint = false;
		}
		getParams(req, isPrint);
		logger.info("==================================请求详细信息|end=================================");
		System.out.println();
		chain.doFilter(request, response);
	}

	
}
