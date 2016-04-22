package com.froad.security.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;


import com.froad.util.ApplicationContextUtil;



	/**
	 * 类描述：重写的springsecurity退出过滤 
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2011 
	 * @author: 何庆均 heqingjun@f-road.com.cn
	 * @update:
	 * @time: 2011-5-27 上午12:43:06 
	 */
public class MyFBULogoutFilter  extends LogoutFilter  {

   // private static MLoginManagerActionSupport mloginmanagerActionSupport = (MLoginManagerActionSupport) ApplicationContextUtil.getApplicationContext().getBean("MLoginManagerActionSupport");
    private static Logger logger = Logger.getLogger(MyFBULogoutFilter.class);
    
    public MyFBULogoutFilter(LogoutSuccessHandler logoutSuccessHandler,
	    LogoutHandler[] handlers) {
	super(logoutSuccessHandler, handlers);
	// TODO Auto-generated constructor stub
    }
    
    public MyFBULogoutFilter(String logoutSuccessUrl, LogoutHandler[] handlers) {
	super(logoutSuccessUrl, handlers);
	// TODO Auto-generated constructor stub
    }
    
    @Override
    protected boolean requiresLogout(HttpServletRequest request,
            HttpServletResponse response) {
	boolean flag = super.requiresLogout(request, response);
	
	 if(flag){
	     HttpSession session = request.getSession(false);
	     	//获得sessionid
	     String sessionid = (String)session.getAttribute("sessionid");
		    //删除sessionid
//		    LoginManager loginm = null;
//		    try {
//			loginm = mloginmanagerActionSupport.delSessionID(sessionid);
//				logger.info(loginm.getRespMsg());
//		    } catch (AppException_Exception e) {
//		    	logger.info("删除sessionid失败,sql执行异常");
//		    }
	 }
	 
	 
        return flag;
    }
    
    
}
