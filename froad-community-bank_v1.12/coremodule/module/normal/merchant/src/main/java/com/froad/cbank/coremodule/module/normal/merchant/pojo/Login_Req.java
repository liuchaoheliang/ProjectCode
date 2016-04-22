package com.froad.cbank.coremodule.module.normal.merchant.pojo;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

public class Login_Req extends BasePojo {

	@NotEmpty(value = "登录名不为空")
	private String userName;

	@NotEmpty(value = "密码不为空")
	private String password;

	private String code;

	private String ko;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getKo() {
		return ko;
	}

	public void setKo(String ko) {
		this.ko = ko;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
