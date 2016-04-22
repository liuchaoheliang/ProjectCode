package com.service;

import javax.jws.WebService;

import com.po.User;

@WebService
public interface HelloService {
	
	public User sayHello();
	
	public User sayBye();
}
