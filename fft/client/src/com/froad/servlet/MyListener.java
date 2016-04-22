package com.froad.servlet;

import javax.servlet.ServletContextEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class MyListener extends ContextLoaderListener{
	MyListener a=new MyListener();
	public void contextInitialized(ServletContextEvent event){
//		super.contextInitialized(event);
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext());  
		BaseService.getInstance().init(ctx);  
	}
}
