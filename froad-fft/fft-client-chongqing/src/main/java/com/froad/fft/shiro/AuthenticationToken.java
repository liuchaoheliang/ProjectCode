package com.froad.fft.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 登录令牌
 * @author FQ
 *
 */
public class AuthenticationToken extends UsernamePasswordToken{
	
	private String captchaId;//验证码ID
	private String captcha;//验证码
	
	/**
	 * 
	 * @param username 用户名
	 * @param password 密码
	 * @param captchaId 验证码ID
	 * @param captcha 验证码
	 * @param rememberMe 记住我
	 * @param host IP
	 */
	public AuthenticationToken(String username, String password, String captchaId, String captcha, boolean rememberMe, String host) {
		super(username, password, rememberMe,host);
		this.captchaId = captchaId;
		this.captcha = captcha;
	}
	
	public String getCaptchaId() {
		return captchaId;
	}

	public void setCaptchaId(String captchaId) {
		this.captchaId = captchaId;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

}
