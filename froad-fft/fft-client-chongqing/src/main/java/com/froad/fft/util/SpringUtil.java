package com.froad.fft.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@Lazy(false)
public class SpringUtil implements ApplicationContextAware, DisposableBean {

	private static ApplicationContext applicationContext;
	
	//不可实例化
	private SpringUtil() {
	}
	
	@Override
	public void destroy() throws Exception {
		applicationContext = null;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SpringUtil.applicationContext = applicationContext;
	}
	
	/**
	 * 获取applicationContext
	 * @return
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 获取实例
	 * 
	 * @param name Bean名称
	 * @return 实例
	 */
	public static Object getBean(String name) {
		checkApplicationContext();
		Assert.hasText(name);
		return applicationContext.getBean(name);
	}
	
	/**
	 * 获取实例
	 * 
	 * @param name Bean名称
	 * @param type Bean类型
	 * @return 实例
	 */
	public static <T> T getBean(String name, Class<T> type) {
		checkApplicationContext();
		Assert.hasText(name);
		Assert.notNull(type);
		return applicationContext.getBean(name, type);
	}
	
	private static void checkApplicationContext() {  
        if (applicationContext == null) {  
            throw new IllegalStateException("applicaitonContext未注入");  
        }  
    }  

}
