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

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

/**
 * *******************************************************
 *<p> 工程: fft-client-chongqing </p>
 *<p> 类名: ParamLogFilter.java </p>
 *<p> 描述: *-- <b>系统请求参数捕获</b> --* </p>
 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
 *<p> 时间: 2014年4月29日 下午12:35:28 </p>
 ********************************************************
 */
public class ParamLogFilter implements Filter {

	private static Logger logger = Logger.getLogger(ParamLogFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}
	@Override
	public void destroy() {}
	
	//不予进行参数捕获的敏感请求
	final static String[] excludeSuffix = new String[]{
			"/login.jhtml",//用户登录
			"/new_password.jhtml",//修改用户密码确认原密码
			"/update_pwd.jhtml",//新密码修改
			"/reset_pwd.jhtml",//重置密码
			"/register/submit.jhml",//注册
	};
	
	private void printParams(HttpServletRequest req, boolean isPrint){
		if(isPrint){
			@SuppressWarnings("unchecked")
			Enumeration<String> paramNames = req.getParameterNames();
			int i = 0;
			String param;
			String[] paramVals;
			while (paramNames.hasMoreElements()) {
				i ++;
				param = (String)paramNames.nextElement();
				paramVals = req.getParameterValues(param);
				logger.info("\t\t\t第" + i + "组 --> 参数名: " + param + " | 参数值: " + JSONObject.toJSONString(paramVals));
			}
		}else{
			logger.info("\t\t\t忽略敏感信息");
		}
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		//如果启用调试模式，则进行参数捕获
		HttpServletRequest req = (HttpServletRequest)request;
		System.out.println();
		logger.info("======================请求详情|Begin======================");
		logger.info("IP: " + req.getRemoteAddr() + "| ServletPath: " + req.getServletPath());
		
		boolean isPrint = true;
		
		for (String suffix : excludeSuffix) { //敏感请求不予输出参数信息
			if(req.getServletPath().endsWith(suffix)){
				isPrint = false;
				break;
			}
		}
		
		logger.info("\t\t----------参数列表|Begin-------");
		printParams(req, isPrint);
		logger.info("\t\t----------参数列表|End---------");
		logger.info("======================请求详情|End========================");
		chain.doFilter(request, response);
	}
}
