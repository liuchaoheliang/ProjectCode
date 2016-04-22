package com.froad.interceptor;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import com.froad.action.support.merchantUserSet.MerchantClerkUrlActionSupport;
import com.froad.util.ApplicationContextUtil;
import com.froad.util.Assert;
import com.froad.util.command.MallCommand;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-3-23  
 * @version 1.0
 */
public class ClerkUrlIntercdptor extends AbstractInterceptor {
	/**
	 * UID
	 */
	private static final long serialVersionUID = 8056728015513395510L;
	private static Logger logger = Logger.getLogger(ClerkUrlIntercdptor.class);
	private static MerchantClerkUrlActionSupport impl = (MerchantClerkUrlActionSupport) ApplicationContextUtil.getApplicationContext().getBean("MerchantClerkUrlActionSupport");
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String flag = validateClerkAuthUrl(invocation);
		//10:普通操作员访问商户url(要拦截)  20:商户管理员访问资源的url 0：普通会员访问商户管理平台资源url(要拦截) 1：其他
		if("10".equals(flag)){
			return "merchant_home";//进行拦截直接跳到商户管理平台首页
		}else if("20".equals(flag)){
			return invocation.invoke();//不处理,跳过
		}else if("0".equals(flag)){
			return "login_page";//拦截，跳到登录页面
		}else if("1".equals(flag)){
			return invocation.invoke();
		}else{
			return invocation.invoke();
		}
	}
	
	public String validateClerkAuthUrl(ActionInvocation invocation){
		HttpServletRequest request = ServletActionContext.getRequest();
		Map session = invocation.getInvocationContext().getSession();
		String isMerchant = (String)session.get(MallCommand.LOGIN_MERCHANT_OR_USER);
		String isClerk = (String)session.get(MallCommand.LOGIN_MERCHANT_OR_CLERK);
		//获取商户管理平台clerk的访问所有资源
		Map<String,String> urlMap = impl.getUrlMap();
		String actionUrl = invocation.getInvocationContext().getName();
		actionUrl = actionUrl+".action";
		if(!Assert.empty(isMerchant) && "1".equals(isMerchant)){
			if(urlMap!=null && urlMap.containsKey(actionUrl) && "0".equals(isClerk) && "0".equals(urlMap.get(actionUrl))){//普通操作员访问商户url
				logger.info("普通操作员拒绝访问商户url:"+actionUrl+"返回商户管理首页!");
				return "10";
			}
			if(urlMap!=null && urlMap.containsKey(actionUrl) && "1".equals(isClerk)){//管理员访问起资源的url
				logger.info("商户管理员访问商户url:"+actionUrl);
				return "20";
			}
		}else if(urlMap!=null && urlMap.containsKey(actionUrl)){//非商户访问商户管理平台资源路径
			logger.info("普通操作员拒绝访问商户url:"+actionUrl+",跳到登录页面。");
			return "0";
		}
		return "1";
	}
}
