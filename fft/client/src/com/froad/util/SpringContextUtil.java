package com.froad.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtil implements ApplicationContextAware{
	
	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		context=ctx;
	}

	public static ApplicationContext getContext(){
		return context;
	}
	
	public static Object getBean(String beanName){
		if(beanName==null||"".equals(beanName)){
			return null;
		}
		return context.getBean(beanName);
	}
	
}
