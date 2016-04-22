package com.froad.sso;

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
import com.froad.action.support.BuyersActionSupport;
import com.froad.action.support.UserActionSupport;
import com.froad.client.buyers.Buyers;
import com.froad.client.user.AppException_Exception;
import com.froad.client.user.User;
import com.froad.common.SessionKey;
import com.froad.sso.client.authentication.AttributePrincipal;
import com.froad.util.SpringContextUtil;
import com.froad.util.command.MallCommand;

/**
 * *******************************************************
 *<p> 工程: communityBusiness_client </p>
 *<p> 类名: NewFFTSSOFilter </p>
 *<p> 描述: *-- <b>用于获取SSO信息进行自动登录处理</b> --* </p>
 *<p> 作者: 赵肖瑶 </p>
 *<p> 时间: 2014-2-27 上午10:08:11 </p>
 ********************************************************
 */
public class NewFFTSSOFilter implements Filter{

	private static Logger logger = Logger.getLogger(NewFFTSSOFilter.class);
	
	@Override
	public void destroy() {}	
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			HttpServletRequest req = (HttpServletRequest)request;
			HttpServletResponse res = (HttpServletResponse)response;
			HttpSession session = req.getSession(false);
			if(session == null){
				chain.doFilter(request, response);
				return;
			}
			AttributePrincipal principal = (AttributePrincipal) req.getUserPrincipal();
			String ssoUserName = null;
			if(principal != null){
				ssoUserName = (String) principal.getAttributes().get("loginId");
			} 
			if(ssoUserName == null || ssoUserName.length() == 0){
				//没有获取到sso服务下推的用户名	
				if(session.getAttribute(SSOCons.LOCATION_LOGIN_NAME) != null){
					if(session.getAttribute(SSOCons.SSO_MERCHANT) == null){
						removeSession(session,req);
					}			
				}
				
				chain.doFilter(request, response);
				return;
			}else{
				//获取到sso服务器下推的用户名			
				if(session.getAttribute(SSOCons.LOCATION_LOGIN_NAME) == null){//该用户在本地为登录
					logger.info("--------------------------------------->>> " + ssoUserName + " 开始进行自动登录");
					autoLogin(ssoUserName, session);
					chain.doFilter(request, response);
				}else{
					//该用户在本地已登录
					logger.info("--------------------------------------->>> " + ssoUserName + " 已登录");
					if(req.getServletPath().endsWith("toLogin.action")){
						//用户其他系统登录并且处于登录页面，则重定向到首页
						req.getRequestDispatcher("index.action").forward(req, response);
						return;
					}
					if(req.getServletPath().endsWith("loginOut.action")){
						removeSession(session,req);
						req.getRequestDispatcher("toLogin.action").forward(req, response);
						return;
					}
					chain.doFilter(request, response);
				}
			}
		} catch (Exception e) {
			logger.error("-----------------------------sso poer error", e);
		}
		
		
	}

	//自动登录
	private void autoLogin(String ssoUserName , HttpSession session){
		
		UserActionSupport userActionSupport = (UserActionSupport) SpringContextUtil.getBean("UserActionSupport");
		BuyersActionSupport buyersActionSupport = (BuyersActionSupport) SpringContextUtil.getBean("BuyersActionSupport");
		try {
			
			User user = userActionSupport.queryUserByMobilephoneOrUsername(ssoUserName);			
			Buyers buyer = buyersActionSupport.getBuyerByUserId(user.getUserID());
			
			//自动添加买家信息
			if(buyer == null && user != null){
				logger.info("用户帐号：" + user.getUsername() + " 无买家信息，系统将自动添加卖家信息");
				buyer = new Buyers();
				buyer.setUserId(user.getUserID());
				buyer.setState("30");
				buyersActionSupport.changeToBuyers(buyer);
			}
			
			//该用户不是分分通注册用户，没有user的相关追加信息
			if(user.getFirstLogin() == null || user.getFirstLogin().length() == 0){ 
				boolean flag = userActionSupport.inserUserAppendInfo(user);
				logger.info("注册渠道:"+ user.getCreateChannel() + "|membercode:"+user.getMemberCode()+"|添加追加信息结果:"+flag);
			}
			
			logger.info("用户：" + ssoUserName +" 注册渠道为" + user.getCreateChannel());
			logger.info("自动登录成功");
			session.setAttribute(SSOCons.LOCATION_LOGIN_NAME, ssoUserName);
			session.setAttribute(MallCommand.LOGIN_USER_ID, user.getUserID());
			session.setAttribute(MallCommand.USER_ID, user.getUserID());
			session.setAttribute(MallCommand.LOGIN_MERCHANT_OR_USER, "0");
			session.setAttribute(user.getUserID(), ssoUserName);
			session.setAttribute(SessionKey.USER, user);
			logger.info("已完成本地Session创建");
		
		} catch (AppException_Exception e) {
			logger.error("查询用户信息异常", e);
		}
	}

	private void removeSession(HttpSession session,HttpServletRequest req){
		try{
			Enumeration<String> sessionKeys = session.getAttributeNames(); //获取所有的session Key
			while (sessionKeys.hasMoreElements()) {
				session.removeAttribute(sessionKeys.nextElement());//移除所有session Key 对应的Object
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
