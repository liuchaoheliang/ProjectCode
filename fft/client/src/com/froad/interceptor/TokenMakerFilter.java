package com.froad.interceptor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

	/**
	 * 类描述：生成令牌的过滤器
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2013 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: May 16, 2013 7:48:49 PM 
	 */
public class TokenMakerFilter implements Filter{

	private static final Logger logger=Logger.getLogger(TokenMakerFilter.class);
	
	private String[] urls=null;
	
	private String[] ignores=null;
	
	public void destroy() {
		logger.info("=========令牌过滤器析构=========");
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		HttpServletRequest req=(HttpServletRequest)request;
		
		HttpSession session=req.getSession(false);
		if(session == null){
			logger.info("session  已经被销毁");
			chain.doFilter(request, response);
			return;
		}
		String uri=req.getRequestURI();//以URI作为令牌的key
		String key=uri.substring(uri.lastIndexOf("/")+1);
		if(!isMakeToken(key,session)){
			chain.doFilter(request, response);
		}else{
			Object token=session.getAttribute(key);
			if(token==null){
				session.setAttribute(key, UUID.randomUUID().toString());
				chain.doFilter(request, response);
			}else{
				logger.info("===========TokenMakerFilter跳转到页面过期，请求的uri=="+uri+"============");
				session.removeAttribute(key);
				((HttpServletResponse)response).sendRedirect(req.getContextPath()+"/html/error_page_timeout.jsp");
			}
		}
	}

	private boolean isMakeToken(String key,HttpSession session){
		if(urls==null||session==null){
			return false;
		}
		boolean flag=false;
		for (int i = 0; i < urls.length; i++) {
			if(key.equals(urls[i])){
				flag=true;
				break;
			}
		}
		if(!flag&&!this.contain(ignores, key)){
			for (int i = 0; i < urls.length; i++) {
				session.removeAttribute(urls[i]);
			}
		}
		return flag;
	}
	
	private boolean contain(String[] arr,String str){
		if(arr==null||arr.length==0){
			return false;
		}
		for (int i = 0; i < arr.length; i++) {
			if(arr[i].equals(str)){
				return true;
			}
		}
		return false;
	}
	
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("=========令牌过滤器初始化=========");
		
		Properties props = new Properties();
		InputStream inputStream =TokenMakerFilter.class.getClassLoader().getResourceAsStream("action_white.properties");
		try {
			props.load(inputStream);
			inputStream.close();
			String path = props.getProperty("path");
			String ignoresProps=props.getProperty("ignores");
			urls=path.split("\\|");
			ignores=ignoresProps.split("\\|");
			logger.info("==============令牌过滤器初始化完毕==============");
		} catch (FileNotFoundException e) {
			logger.error("异常", e);
		} catch (IOException e) {
			logger.error("异常", e);
		}
	}

}

