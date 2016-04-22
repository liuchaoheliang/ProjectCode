package com.froad.security.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.TextEscapeUtils;

import com.froad.action.support.MerchantActionSupport;
import com.froad.action.support.UserActionSupport;
import com.froad.client.user.AppException_Exception;
import com.froad.client.user.User;
import com.froad.util.ApplicationContextUtil;
import com.froad.util.Command;
import com.froad.util.Validate;
import com.froad.util.command.MallCommand;




/**
 * 类描述：重写的springsecurity 登陆过滤器
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2011 
 * @author: 何庆均 heqingjun@f-road.com.cn
 * @update:
 * @time: 2011-5-26 下午02:52:32 
 */
public class MyFBUUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private static Logger logger = Logger.getLogger(MyFBUUsernamePasswordAuthenticationFilter.class);
	private static UserActionSupport USER=(UserActionSupport) ApplicationContextUtil.getApplicationContext().getBean("UserActionSupport");
	private static MerchantActionSupport MERCHANT=(MerchantActionSupport) ApplicationContextUtil.getApplicationContext().getBean("MerchantActionSupport");
	private boolean postOnly = true;		

	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		if (postOnly && !request.getMethod().equals("POST")) {
	        throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
	    }
		
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		
		if (username == null) {
	        username = "";
	    } 
	
	    if (password == null) {
	        password = "";
	    }
//	    JSONObject jObject = new JSONObject();
	    HttpSession session = request.getSession();
	    
	    //验证码
		//String validateCode = request.getParameter(MallCommand.LOGIN_VALIDATE_CODE);
		//String validateCode1 = (String)session.getAttribute(MallCommand.LOGIN_VALIDATE_CODE);
		//session.setAttribute(MallCommand.LOGIN_FAILURE_SHOW_USERNAME, username);
		
		//限制普通会员登录，只允许商户从此登录
		com.froad.client.user.User user=new com.froad.client.user.User();
		//com.froad.client.merchant.Merchant merchant = null;
		try {
			user = USER.queryUserByMobilephoneOrUsername(username);
			if(user != null && Command.respCode_SUCCESS.equals(user.getRespCode())){
				username = user.getUsername();
			}
			session.setAttribute(MallCommand.LOGIN_FAILURE_SHOW_USERNAME, username);
			//merchant = MERCHANT.getMerchantInfo(user.getUserID());
			//if(merchant==null || !user.getUserID().equals(merchant.getUserId())){
			//	validateCode1 = "abcd";
			//	validateCode = validateCode1;//普通用户登录不验证码
			//}
		} catch (AppException_Exception e) {
			e.printStackTrace();
			logger.error("登录框架验证查询用户信息出错");
		}
		
		
		
		
//		jObject.put(MallCommand.AUTO_LOGIN_FLAG, false); //默认非自动登陆
		
		username = "".equals(username)?"":usernameDetail(username.trim());
		
//		String key = (String)session.getAttribute(MallCommand.AUTO_LOGIN_KEY);
//		if(key != null && MallCommand.AUTO_LOGIN_KEY.equals(key)){
//			jObject.put(MallCommand.AUTO_LOGIN_FLAG, true);//自动登陆
//			username = "".equals(username)?request.getParameter(MallCommand.CUSTOMER_SERVICE_BUY_CSNAME).trim():username ;//客服名
//			password = MallCommand.AUTO_LOGIN_JUDGE_MARK_KEY;
//		} else 
		//if(validateCode == null || validateCode1 == null || !validateCode1.equalsIgnoreCase(validateCode)){
		//	throw new BadCredentialsException("验证码错误");
		//} 
		
		//jObject.accumulate(MallCommand.AUTO_LOGIN_NAME, username);
		
		
	    UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
	    
	    // Place the last username attempted into HttpSession for views
	    
	    if (session != null || getAllowSessionCreation()) {
	        request.getSession().setAttribute(SPRING_SECURITY_LAST_USERNAME_KEY, TextEscapeUtils.escapeEntities(username));
	    }
	    
	    // Allow subclasses to set the "details" property
	    setDetails(request, authRequest);
	    
	    return this.getAuthenticationManager().authenticate(authRequest);
	}
	
	
	/**
	  * 方法描述：用户登陆判断
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	  * @update:
	  * @time: 2012-3-13 下午2:12:34
	  */
	private String usernameDetail(String username){
		String ne = username;
		
		User user = null;
		try {
			user = USER.queryUserByMobilephoneOrMailOrSn(username);
		} catch (AppException_Exception e) {
			e.printStackTrace();
		}
		if(Command.respCode_SUCCESS.equals(user.getRespCode())){
			username = user.getUsername();
			if(Validate.validateOfPhone(ne)){
				logger.info("用户" + username + "通过手机号码登录  " + ne);
			} else if(Validate.validateOfMail(ne)){
				logger.info("用户" + username + "通过邮箱登录   " + ne);
			} else {
				logger.info("用户" + username + "通过logicNo登录   " + ne);
			}
		}
		
		return username;
	}
	

	public boolean isPostOnly() {
		return postOnly;
	}

	@Override
	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}

}
