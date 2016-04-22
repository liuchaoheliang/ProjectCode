package com.froad.fft.bean;

/**
 * 系统配置
 * @author FQ
 *
 */
public class SystemConfig {
	
	/**
	 * 验证码类型
	 */
	public enum CaptchaType {

		/** 会员登录 */
		memberLogin,

		/** 会员注册 */
		memberRegister,
		
		/** 后台登录 */
		merchantLogin
		
	}
	
	private String siteUrl;//网站地址
	
	private Boolean isLoginFailureLock; // 是否开启登录失败锁定账号功能
	private Integer loginFailureLockCount;// 同一账号允许连续登录失败的最大次数，超出次数后将锁定其账号
	private Integer loginFailureLockTime;// 账号锁定时间(单位：分钟,0表示永久锁定)
	
	private String cookiePath;//Cookie路径 
	private String cookieDomain;//Cookie作用域
	private String ftpUrl;//访问url

	public String getSiteUrl() {
		return siteUrl;
	}

	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}
	
	public String getCookiePath() {
		return cookiePath;
	}

	public void setCookiePath(String cookiePath) {
		this.cookiePath = cookiePath;
	}

	public String getCookieDomain() {
		return cookieDomain;
	}

	public void setCookieDomain(String cookieDomain) {
		this.cookieDomain = cookieDomain;
	}
	
	public Boolean getIsLoginFailureLock() {
		return isLoginFailureLock;
	}

	public void setIsLoginFailureLock(Boolean isLoginFailureLock) {
		this.isLoginFailureLock = isLoginFailureLock;
	}

	public Integer getLoginFailureLockCount() {
		return loginFailureLockCount;
	}

	public void setLoginFailureLockCount(Integer loginFailureLockCount) {
		this.loginFailureLockCount = loginFailureLockCount;
	}

	public Integer getLoginFailureLockTime() {
		return loginFailureLockTime;
	}

	public void setLoginFailureLockTime(Integer loginFailureLockTime) {
		this.loginFailureLockTime = loginFailureLockTime;
	}

	public String getFtpUrl() {
		return ftpUrl;
	}

	public void setFtpUrl(String ftpUrl) {
		this.ftpUrl = ftpUrl;
	}
	
}
