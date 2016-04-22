package com.froad.CB.common.start;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.BeanPostProcessor;  
import org.springframework.beans.BeansException;  



	/**
	 * 类描述：初始化容器
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2012 
	 * @author: meicy meiwenxiang@f-road.com.cn
	 * @time: Oct 9, 2012 9:52:20 AM 
	 */
public class InstantiationTracingBeanPostProcessor   implements BeanPostProcessor  {  
	private static Logger logger = Logger.getLogger(InstantiationTracingBeanPostProcessor.class);
	 int j=0;
	 int i=0;
    // simply return the instantiated bean as-is  
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {  
    
    	
        return bean; // we could potentially return any object reference here...  
    }  
  
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {  
    	
        logger.info("Bean '" + beanName + "' created : " + bean.toString());  
        if(j==1)
    	{
    		logger.info("已启动定时监听!!!");
    		
    	}else
    	{
    		logger.info("正在启动定时监听!!!");
        	j++;
    	}
        return bean;  
    }  
}  
