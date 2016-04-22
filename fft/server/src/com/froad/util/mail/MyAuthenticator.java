package com.froad.util.mail;

import javax.mail.*;

/**
 * <code>MyAuthenticator.java</code>
 * <p>
 * description:邮件发送授权
 * <p>
 * Copyright 上海方付通商务服务有限公司 2011 All right reserved.
 * 
 * @author 勾君伟 goujunwei@f-road.com.cn
 * @time:2011-3-15 下午03:05:34
 * @version 1.0
 */
public class MyAuthenticator extends Authenticator {

	String userName = null;

	String password = null;

	public MyAuthenticator() {
	}

	public MyAuthenticator(String username, String password) {
		this.userName = username;
		this.password = password;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, password);
	}
}
