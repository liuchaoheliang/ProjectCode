package com.froad.cbank.coremodule.framework.common.util.bus;

import javax.servlet.ServletContext;

import org.quartz.SchedulerException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/************************************************
 * Copyright (c)  by soap
 * All right reserved.
 * Create Date: 2012-1-23
 * Create Author: hujz
 * File Name:  spring工厂类
 * Last version:  1.0
 * Function:通过工厂类获取对应的实例化的服务
 * Last Update Date:
 * Change Log:
**************************************************/	
 
@Component
public class SpringApplicationContextUtil implements ApplicationContextAware, InitializingBean{
	private static ApplicationContext applicationContext;
	public ApplicationContext getApplicationContext() throws SchedulerException {
		return SpringApplicationContextUtil.applicationContext;
	}
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringApplicationContextUtil.applicationContext=applicationContext;
	}
	/**
	 * 通过工厂类获取对应的服务
	 * @param actionName 服务类名
	 * @return 服务类
	 */
	public static Object getBean(String name){
		Object object=null;
		try{
		    if(SpringApplicationContextUtil.applicationContext != null){
		        object=SpringApplicationContextUtil.applicationContext.getBean(name);
		    }
		}catch(Exception e){
			e.printStackTrace();
		}
		return object;
	}
	/**
	 * 获取指定class类型的服务类
	 * @param name 名称
	 * @param requiredType class对象
	 * @return 服务类
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object getBean(String name,Class requiredType){
		Object object=null;
		try{
			object=SpringApplicationContextUtil.applicationContext.getBean(name,requiredType);
		}catch(NoSuchBeanDefinitionException e){
		}catch(Exception e){
		}
		return object;
	}
	/**
	 * 是否存在执行名称的服务类
	 * @param name 名称
	 * @return true 存在  flase 不存在
	 */
	public static boolean containsBean(String name){
		return SpringApplicationContextUtil.applicationContext.containsBean(name);
	}
	/**
	 * 获取注册class类型
	 * @param name
	 * @return
	 */
	public static String[] getAliases(String name){
		String[] s=null;
		try{
			s=SpringApplicationContextUtil.applicationContext.getAliases(name);
		}catch(NoSuchBeanDefinitionException e){
			
		}catch(Exception e){
		}
		return s;
	}
	
	public static WebApplicationContext getWebApplicationContext(ServletContext sc) {  
        return WebApplicationContextUtils.getRequiredWebApplicationContext(sc);  
    }
	@Override
	public void afterPropertiesSet() throws Exception {
		
	}
}
