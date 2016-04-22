package com.froad.cbank.coremodule.framework.common.util.bus;

import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;

//import com.soft.csb.test.TestImpl;


/************************************************
 * Copyright (c)  by soap
 * All right reserved.
 * Create Date: 2012-1-23
 * Create Author: hujz
 * File Name:  工厂类
 * Last version:  1.0
 * Function:统一的工厂类
 * Last Update Date:
 * Change Log:
**************************************************/	
 
public class ApplicationContextUtil {
	public static final String SPRING="SPRING";
	public static final String JAVA="JAVA";
	/**
	 * 通过工厂类获取对应的服务
	 * @param serviceName 服务类名
	 * @return 服务类
	 */
	public Object getService(String serviceName){
		return getService(serviceName, SPRING);
	}
	/**
	 * 通过工厂类获取对应的服务
	 * @param serviceName 服务类名
	 * @param applicationType 工厂类型
	 * @return 服务类
	 */
	public Object getService(String serviceName,String applicationType){
		Object object =null;
		if(StringUtil.isBlank(applicationType))applicationType=SPRING;
		if(SPRING.equals(applicationType)){
			object=SpringApplicationContextUtil.getBean(serviceName);
		}else if(JAVA.equals(applicationType)){
			try {
				object=Class.forName(serviceName).newInstance();
			} catch (Exception e) {
			} 
		}
		return object;
	}
}
