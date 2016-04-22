package com.froad.fft.filter;

import java.io.IOException;
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

import org.apache.log4j.Logger;

import com.froad.fft.common.SessionKey;
import com.froad.fft.dto.UserEngineDto;
import com.froad.fft.util.NullValueCheckUtil;

/**
 * *******************************************************
 *<p> 工程: fft-client-chongqing </p>
 *<p> 类名: MemberPowerFilter.java </p>
 *<p> 描述: *-- <b>用于管理会员相关的访问页面控制</b> --* </p>
 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
 *<p> 时间: 2014年4月3日 下午12:15:04 </p>
 ********************************************************
 */
public class MemberPowerFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}
	@Override
	public void destroy() {}
	
	
	private static Logger logger = Logger.getLogger(MemberPowerFilter.class);
	
	final String loginURL = "/shop/login/index.jhtml";//登录页面
	final String overtimeURL = "/common/illegality.jhtml";//重复提交页面
	
	//无须处理的静态资源后缀集合
	final static String[] excludeSuffix = new String[]{
			"js","css","png","jpg","gif"
	};
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		
//		if(!NullValueCheckUtil.isStrEmpty(req.getHeader("X-Requested-With"))){//ajax 请求
//			chain.doFilter(request, response);
//			return;
//		}
		
		for (String suffix : excludeSuffix) { //如果访问的资源是静态资源则可以直接通过
			if(req.getServletPath().endsWith(suffix)){
				chain.doFilter(request, response);
				return;
			}
		}
		
		//SSO相关请求放行
		if(req.getServletPath().indexOf("/sso/core") != -1){
			logger.info("访问请求为sso相关请求，无需处理");
			chain.doFilter(request, response);
			return;
		}
		
		
		HttpSession session = req.getSession(false);
		
		if(session == null){//session信息不存在，用户未登录状态
			req.getRequestDispatcher(loginURL).forward(req, res);
			return;
		}else{
			Object object = session.getAttribute(SessionKey.LOGIN_IDENTIFICATION.key());
			if(object == null){//如果没有登录信息
				req.getRequestDispatcher(loginURL).forward(req, res);
				return;
			}else{
				//已获取到登录信息
				if(object.getClass() == UserEngineDto.class){
					//存入的用户信息是member类型
					UserEngineDto userEngineDto = (UserEngineDto)object;
					if(userEngineDto.getMemberCode() == null){//未登录状态
						req.getRequestDispatcher(loginURL).forward(req, res);
						return;
					}
					//-------------------------用户登录成功------------------
					
					//-------------------------进行页面tokenKey校验----------
					Object token = req.getParameter("tokenKey");
					if(token != null){//页面有token
						String getTokenKey = (String)token;
						if(!NullValueCheckUtil.isStrEmpty((getTokenKey))){
							logger.info("获取toketKey" + (String)getTokenKey);
							Object origToken = session.getAttribute(SessionKey.STSTEM_UUID_TOKEN_KEY.key());
							if(origToken != null ){//存在需要token校验数据
								String tokenKey = (String)origToken;
								logger.info("后台的tokenKey值为" + tokenKey);
								if(!NullValueCheckUtil.isStrEmpty(tokenKey)){
									if(tokenKey.equals(getTokenKey)){
										//该key被使用，重新生成
										session.setAttribute("system_uuid_token_key", UUID.randomUUID().toString());
									}else{
										//该页面存在相同的tokenKey ， 页面过期
										logger.info(req.getServletPath() + "页面重复提交");
										req.getRequestDispatcher(overtimeURL).forward(request, response);
										return;
									}
								}
							}
						}
					}
					chain.doFilter(request, response);
					return;
				}else{
					//sessionkey中存入的是其他非UserEngineDto对象
					req.getRequestDispatcher(loginURL).forward(req, res);
					return;
				}
			}
		}
	}
	
}
