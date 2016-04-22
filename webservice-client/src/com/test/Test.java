package com.test;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.service.HelloService;

import com.service.User;

public class Test {
	
	public static void main(String[] args) {
		String wdsl_url = "http://127.0.0.1:8989/TestSpringMVC/HelloService"; 
		JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
		factoryBean.setAddress(wdsl_url);
		factoryBean.setServiceClass(HelloService.class);
		HelloService hello =  (HelloService) factoryBean.create();
		User res = hello.sayHello();
//		 
		System.out.println("============"+res.getUserName()+"===="+res.getUserId());  
		
		User res1 = hello.sayBye();
		System.out.println("============"+res1.getUserName()+"===="+res1.getUserId()); 
	}
	
	
	
}
