package com.froad.sso;

import java.io.IOException;
import java.util.Map;

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
 *<p> 类名: SSOFilter.java </p>
 *<p> 描述: *-- <b>用于获取SSO信息进行自动登录处理</b> --* </p>
 *<p> 作者: 赵肖瑶 </p>
 *<p> 时间: 2014-2-27 上午10:08:11 </p>
 ********************************************************
 */
public class FFTSSOFilter implements Filter{

	private static Logger logger = Logger.getLogger(FFTSSOFilter.class);
	
	@Override
	public void destroy() {}
	
	//自动登录
	private String autoLogin(String ssoUserName , HttpSession session,HttpServletRequest req,HttpServletResponse res){
		String errorLoginMsg = null;
		UserActionSupport userActionSupport = (UserActionSupport) SpringContextUtil.getBean("UserActionSupport");
		BuyersActionSupport buyersActionSupport = (BuyersActionSupport) SpringContextUtil.getBean("BuyersActionSupport");
		try {
			User user = userActionSupport.queryUserByMobilephoneOrUsername(ssoUserName);	
			if(user.getMobilephone() == null || user.getMobilephone().length() == 0){
				logger.info("没有绑定手机号码，请到O2O平台绑定手机号码");
				errorLoginMsg = "尊敬的手机银行联盟用户，您还未绑定手机号码，无法继续交易 <span style=\'cursor:pointer;color:green;\' onclick='o2oUrl();'>点此绑定</span>";
				try {
					req.setAttribute("ssoerrorMsg", errorLoginMsg);
					req.getRequestDispatcher("toLogin.action").forward(req, res);
				} catch (ServletException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}		
			
			session.setAttribute(SSOCons.LOCATION_LOGIN_NAME, ssoUserName);
			session.setAttribute(MallCommand.LOGIN_USER_ID, user.getUserID());
			session.setAttribute(MallCommand.USER_ID, user.getUserID());
			session.setAttribute(MallCommand.LOGIN_MERCHANT_OR_USER, "0");
			session.setAttribute(user.getUserID(), ssoUserName);
			session.setAttribute(SessionKey.USER, user);
			
			logger.info("已完成本地Session创建");
			Buyers buyer = buyersActionSupport.getBuyerByUserId(user.getUserID());
			if(buyer == null){
				logger.info("用户帐号：" + user.getUsername() + " 无买家信息，系统将自动添加卖家信息");
				buyer = new Buyers();
				buyer.setUserId(user.getUserID());
				buyer.setState("30");
				buyersActionSupport.changeToBuyers(buyer);
			}
			
			
			if(user.getFirstLogin() == null || user.getFirstLogin().length() == 0){ 
				//该用户不是分分通注册用户，没有user的相关追加信息
				boolean flag = userActionSupport.inserUserAppendInfo(user);
				logger.info("注册渠道:"+ user.getCreateChannel() + "|membercode:"+user.getMemberCode()+"|添加追加信息结果:"+flag);
			}
			logger.info("用户：" + ssoUserName +" 注册渠道为" + user.getCreateChannel());
			logger.info("自动登录成功");
			session.setAttribute(SSOCons.LOCATION_LOGIN_NAME, ssoUserName);
			errorLoginMsg = "ssoLogin";
			return errorLoginMsg;
	
		
		} catch (AppException_Exception e) {
			logger.error("查询用户信息异常", e);
			errorLoginMsg = "系统繁忙请稍候再试";
			return errorLoginMsg;
		}
	}
	
	@SuppressWarnings("unchecked")
	private String ssoLoginFaild(JSONArray json){
		Map<Object, String> jsonData = (Map<Object, String>) json.get(0);
		Object info = jsonData.get("demo");
		if(info == null || info.equals("")){
			return jsonData.get("errorMsg");
		}
		json = JSONArray.fromObject("["+info.toString()+"]");
		jsonData = (Map<Object, String>) json.get(0);
		return (String) jsonData.get("msg");
		
	}
	
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest)request;
		//获取转跳地址
		
		HttpSession session = req.getSession(false);
		if(session == null){
			
			chain.doFilter(request, response);
			return;
		}
		
		if(session.getAttribute(SSOCons.SSO_MERCHANT) != null){//如果商户登录则不进行用户判断
			chain.doFilter(request, response);
			return;
		}
		
		AttributePrincipal principal = (AttributePrincipal) req.getUserPrincipal();		
		
		String ssoUserName = null;		
		if(principal != null){
			ssoUserName = (String) principal.getAttributes().get("loginId");
		}
		
		String loginErrorInfo = req.getParameter("SSO_UserResult");
		
		if(ssoUserName != null){
			logger.info("接收到SSO服务器下发的已登录用户名： " + ssoUserName);
		}
		
		if(ssoUserName != null || (loginErrorInfo != null && loginErrorInfo.length() != 0)){//获得了SSO登录信息
			
			//是否登录成功
			if(loginErrorInfo != null && loginErrorInfo.length() != 0){
				//登录失败
				logger.info("用户登录失败，loginErrorInfo ：" + loginErrorInfo);
				JSONArray json = null;
				json = JSONArray.fromObject("["+(loginErrorInfo.replace("\"{", "{")).replace("}\"", "}")+"]");
				String info = ssoLoginFaild(json);
				req.setAttribute("ssoerrorMsg",info);
				req.getRequestDispatcher("toLogin.action").forward(req,(HttpServletResponse)response);
				//chain.doFilter(request, response);
				return;
			}
			
				//如果不是do something
			
				//如果是
			
				//本地session是否存在
			
					//不存在 自动登录
			if(session.getAttribute(SSOCons.LOCATION_LOGIN_NAME) == null){ 
				String ssoErrorMsg = autoLogin(ssoUserName, session,req,(HttpServletResponse)response);
				if(ssoErrorMsg != null){
					req.setAttribute("ssoerrorMsg",ssoErrorMsg);
				}else{
					return;
				}
				
				chain.doFilter(request, response);
				return;
			}else{				
				if(!((String)session.getAttribute(SSOCons.LOCATION_LOGIN_NAME)).equals(ssoUserName)){//如果SSO下发的已登录帐号发生变化

					session.setAttribute(SSOCons.LOCATION_LOGIN_NAME, ssoUserName);
					String ssoErrorMsg = autoLogin(ssoUserName, session,req,(HttpServletResponse)response);
					if(ssoErrorMsg != null){
						req.setAttribute("ssoerrorMsg",ssoErrorMsg);
					}
					
					chain.doFilter(request, response);
					return;
				}
				
				//存在 就直接放行
				if(req.getServletPath().endsWith("toLogin.action")){
					req.getRequestDispatcher("index.action").forward(req,(HttpServletResponse)response);
					return;
				}
				chain.doFilter(request, response);
				return;
			}
		}else{
			
			if( req.getHeader("X-Requested-With") == null){
				if(session.getAttribute(SSOCons.SSO_MERCHANT) == null){
					logger.info("无SSO服务器信息，注销本地登录信息");
					//未能获取sso服务器信息  移除本地全部信息
					session.removeAttribute(SSOCons.LOCATION_LOGIN_NAME);
					session.removeAttribute(MallCommand.LOGIN_USER_ID);
					session.removeAttribute(MallCommand.USER_ID);
					session.removeAttribute(MallCommand.LOGIN_MERCHANT_OR_USER);
					session.removeAttribute(SessionKey.USER);
					session.removeAttribute(SSOCons.SSO_MERCHANT);
					
					session.removeAttribute(SessionKey.BANK_POINTS_ACCOUNT);
					session.removeAttribute(SessionKey.FFT_POINTS_ACCOUNT);
					session.removeAttribute(SessionKey.POINTS_ACCOUNT_MAP);
					session.removeAttribute(SessionKey.CHANGE_POINTS);
					session.removeAttribute(SessionKey.MERCHANT);
				}
			}			
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {}

}
