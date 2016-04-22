package com.froad.security.interceptor;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

import org.apache.log4j.Logger;


import com.froad.util.ApplicationContextUtil;


	/**
	 * 类描述：session过期
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2011 
	 * @author: 何庆均 heqingjun@f-road.com.cn
	 * @update:
	 * @time: 2011-8-14 下午05:12:41 
	 */
public class MyFBUHttpSessionEventPublisher extends org.springframework.security.web.session.HttpSessionEventPublisher {
	
	//private static MLoginManagerActionSupport mloginmanagerActionSupport = (MLoginManagerActionSupport) ApplicationContextUtil.getApplicationContext().getBean("MLoginManagerActionSupport");
	private static Logger logger = Logger.getLogger(MyFBUHttpSessionEventPublisher.class);
	 
	@Override
	public void sessionDestroyed(HttpSessionEvent event) {

//		
//		HttpSession session = event.getSession();
//     	//获得sessionid
//		Object sessionId = session.getAttribute("sessionid");
//		if(sessionId != null){
//		    String sessionid = (String)sessionId;
//		    //删除sessionid
//		    LoginManager loginm = null;System.out.println("session被销毁了");
//		    try {
//			loginm = mloginmanagerActionSupport.delSessionID(sessionid);
//			logger.info(loginm.getRespMsg());
//			
//		    } catch (AppException_Exception e) {
//			logger.info("删除sessionid失败,session已经被销毁了");
//		    }
//		}
		super.sessionDestroyed(event);
	}
	
}
