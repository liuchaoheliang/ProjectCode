package com.service.impl;

import com.po.User;
import com.service.HelloService;

public class HelloServiceImpl implements HelloService {

	
	public User sayHello() {
		System.out.println("=======================hello ");
		User u = new User();
		u.setUserName("liuchao");
		u.setUserId(1000);
		return u;
	}

	
	public User sayBye() {
		System.out.println("==============================goodBye");
		User u = new User();
		u.setUserName("heliang");
		u.setUserId(9999);
		return u;
	}

}
