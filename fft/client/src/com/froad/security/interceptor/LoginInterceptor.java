package com.froad.security.interceptor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.froad.action.IUser;
import com.froad.action.support.UserActionSupport;
import com.froad.client.user.User;
import com.froad.util.ApplicationContextUtil;
import com.froad.util.command.MallCommand;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 获取user对象信息拦截器
 * action中的user信息包装
 * @author liqiaopeng
 * @date 2013-3-20
 */
public class LoginInterceptor extends AbstractInterceptor {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 5753777523890533824L;
	private static Logger logger = Logger.getLogger(LoginInterceptor.class);
	private static UserActionSupport impl = (UserActionSupport) ApplicationContextUtil.getApplicationContext().getBean("UserActionSupport");
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		User loginUser = setLoginUser(invocation);
		return invocation.invoke();
	}
	
	@SuppressWarnings("unchecked")
	public User getUser(ActionInvocation invocation){
		HttpServletRequest request = ServletActionContext.getRequest();
		Map session = invocation.getInvocationContext().getSession();
		User user = null;
		String userId = (String)session.get(MallCommand.USER_ID);
		if(userId!=null){
			user = impl.queryUserAllByUserID(userId);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			user.setLastTime(df.format(new Date()));
			user.setLastIP(getIpAddr(request));			
		}
		if(user == null){
			user = new User();
		}		
		return user;
	}
	/**
	 * 向action中注入User
	 * @param invocation
	 */
	public User setLoginUser(ActionInvocation invocation){
		User user = this.getUser(invocation);
		if(invocation.getAction() instanceof IUser){
			IUser loginuserImpl = (IUser)invocation.getAction();
			loginuserImpl.setLoginUser(user);
		}
		return user;
	}
	
	/**
	  * 方法描述：获取用户访问ip
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
