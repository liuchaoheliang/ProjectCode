package com.froad.action.support;

import com.froad.client.user.MLoginManagerService;

public class LoginManagerActionSupport{
	private MLoginManagerService loginManagerService;

	public MLoginManagerService getLoginManagerService() {
		return loginManagerService;
	}

	public void setLoginManagerService(MLoginManagerService loginManagerService) {
		this.loginManagerService = loginManagerService;
	}
	
}
