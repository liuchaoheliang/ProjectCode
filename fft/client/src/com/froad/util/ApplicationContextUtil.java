package com.froad.util;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * <code>ApplicationContextUtil.java</code>
 * <p>
 * description:获得spring上下文
 * <p>
 * Copyright 上海方付通商务服务有限公司 2011 All right reserved.
 * 
 * @author 勾君伟 goujunwei@f-road.com.cn
 * @time:2011-3-25 下午10:18:07
 * @version 1.0
 */
public class ApplicationContextUtil {
	
	private static final ThreadLocal<ApplicationContext> threadSession = new ThreadLocal<ApplicationContext>();
	public static ApplicationContext getApplicationContext() {
		ApplicationContext ac = (ApplicationContext) threadSession.get();
		if (ac == null) {
			ac = new ClassPathXmlApplicationContext(new String[] { "client-beans.xml" });
			threadSession.set(ac);
		}
		return ac;
	}
	
}
