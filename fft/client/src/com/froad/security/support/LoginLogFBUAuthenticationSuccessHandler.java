package com.froad.security.support;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.froad.action.support.MerchantActionSupport;
import com.froad.action.support.PointActionSupport;
import com.froad.action.support.SellersActionSupport;
import com.froad.action.support.UserActionSupport;
import com.froad.client.point.PointsAccount;
import com.froad.client.sellers.Seller;
import com.froad.common.SessionKey;
import com.froad.util.ApplicationContextUtil;
import com.froad.util.Assert;
import com.froad.util.command.BankCommand;
import com.froad.util.command.MallCommand;


	/**
	 * 类描述：登陆成功
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2012 
	 * @author: 何庆均 heqingjun@f-road.com.cn
	 * @update:
	 * @time: 2012-2-18 下午3:36:45 
	 */
public class LoginLogFBUAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{
	private static Logger logger = Logger.getLogger(LoginLogFBUAuthenticationSuccessHandler.class);
	private static UserActionSupport impl = (UserActionSupport) ApplicationContextUtil.getApplicationContext().getBean("UserActionSupport");
	private static MerchantActionSupport MERCHANT=(MerchantActionSupport) ApplicationContextUtil.getApplicationContext().getBean("MerchantActionSupport");
	private static SellersActionSupport sellerActionSupport=(SellersActionSupport) ApplicationContextUtil.getApplicationContext().getBean("SellersActionSupport");
	private static PointActionSupport pointActionSupport=(PointActionSupport) ApplicationContextUtil.getApplicationContext().getBean("PointActionSupport");
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws ServletException, IOException {
		
		
		
		String username = ((User)authentication.getPrincipal()).getUsername();
		
		com.froad.client.user.User user = new com.froad.client.user.User();
		user.setUsername(username);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		user.setLastTime(df.format(new Date()));
		user.setLastIP(getIpAddr(request));
		user.setBank(BankCommand.PC_BANKID); //修改登陆 渠道
		HttpSession session = request.getSession(false);
		logger.info("用户"+username+"登陆：");
		logger.info("IP:"+ user.getLastIP());
		logger.info("时间:"+user.getLastTime());
		logger.info("方式:"+BankCommand.PC_BANKID);
		
		try {
			//user  = impl.updateUserLastInfoByUsername(user);
			user = impl.getUserInfoByUsername(user.getUsername());
			boolean isSellerSendPoints=sellerActionSupport.isSellerSendPoints(user.getUserID());
			request.getSession().setAttribute("isSellerSendPoints", isSellerSendPoints);
			 session.removeAttribute(MallCommand.LOGIN_FAILURE_SHOW_USERNAME);
			 session.setAttribute(MallCommand.SESSION_LASTTIME, user.getLastTime());
			 //session放入userId
			 if("0".equals(user.getRespCode())){
				 logger.info("===============springSecurity=================="+user.getUserID());
				 session.setAttribute(SessionKey.USER, user);
				 session.setAttribute(MallCommand.USER_ID, user.getUserID());
				 session.setAttribute(MallCommand.LOGIN_USER_ID, user.getUserID());
				 session.setAttribute(user.getUserID(), username);
				 
//				 if(!Assert.empty(user.getFirstLogin()) && "0".equals(user.getFirstLogin())){
//					 logger.info("======FIRST_LOGIN START========");
//					 session.setAttribute(MallCommand.FIRST_LOGIN, "0");
//					 com.froad.client.user.User userreq = new com.froad.client.user.User();
//					 userreq.setUsername(user.getUsername());
//					 userreq.setFirstLogin("1");
//					 impl.updateUserLastInfoByUsername(userreq);
//					 logger.info("======FIRST_LOGIN END========  username:"+userreq.getUsername());
//				 }else{
//					 session.setAttribute(MallCommand.FIRST_LOGIN, "1");
//				 }
				//商户判断并设置session标识
				 //com.froad.client.merchant.Merchant merchant = null;
				// merchant = MERCHANT.getMerchantInfo(user.getUserID());
				 //if(merchant==null || !user.getUserID().equals(merchant.getUserId())){
					 session.setAttribute(MallCommand.LOGIN_MERCHANT_OR_USER, "0");//isMerchantFlag是否商户标识0：普通用户 1：商户
					 //getRedirectStrategy().sendRedirect(request, response, "/member_main.action");
				 //}else{
				//	 session.setAttribute(MallCommand.LOGIN_MERCHANT_OR_USER, "1");
					
				 //}
				 
				 Map<String,PointsAccount> pointsTypePointsAccountMap =pointActionSupport.queryPointsByUsername(user.getUsername());
				 if(!Assert.empty(pointsTypePointsAccountMap))
					 session.setAttribute(SessionKey.POINTS_ACCOUNT_MAP, pointsTypePointsAccountMap);
			 }
		} catch (Exception e) {
			session.invalidate();
			logger.error(e);
			throw new BadCredentialsException("用户信息查询发送异常。");
		}
		
		// TODO Auto-generated method stub
		
		super.onAuthenticationSuccess(request, response, authentication);
	}
	
	/**
	  * 方法描述：获取用户访问ip
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	  * @update:
	  * @time: Sep 27, 2011 3:39:46 PM
	  */
	public String getIpAddr(HttpServletRequest request) {        
	    String ip = request.getHeader("x-forwarded-for");        
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {        
	        ip = request.getHeader("Proxy-Client-IP");        
	    }        
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {        
	        ip = request.getHeader("WL-Proxy-Client-IP");        
	    }        
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {        
	        ip = request.getRemoteAddr();        
	    }        
	    return ip;        
	}     
}
