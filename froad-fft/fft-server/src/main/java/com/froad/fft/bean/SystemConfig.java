package com.froad.fft.bean;

import java.io.Serializable;

/**
 * 系统相关配置
 * @author FQ
 *
 */
public class SystemConfig implements Serializable{
	
	public static final String CONFIG_FILE_NAME = "systemConfig.xml";// 系统配置文件名称
	public static final String CACHE_NAME = "systemConfig";//缓存名称
	public static final Integer CACHE_KEY = 0;//缓存Key
	
	public static final String EXTENSION_SEPARATOR = ",";// 文件扩展名分隔符
	
	public static final String UPLOAD_IMAGE_DIR = "/image/";// 图片文件上传目录
	public static final String UPLOAD_FILE_DIR = "/file/";// 其它文件上传目录
	
	public enum CaptchaType{
		/**
		 * 后台登录
		 */
		adminLogin,
		
		/** 
		 * 会员登录 
		 */
		memberLogin,

		/** 
		 * 会员注册 
		 */
		memberRegister,
		
		/**
		 * 商户登录
		 */
		merchantLogin
	}
	
	private String systemName;//系统名称
	private String systemVersion;//系统版本
	
	private Boolean isSystemDebug;//系统是否调试
	
	private Boolean isLoginFailureLock; // 是否开启登录失败锁定账号功能
	private Integer loginFailureLockCount;// 同一账号允许连续登录失败的最大次数，超出次数后将锁定其账号
	private Integer loginFailureLockTime;// 账号锁定时间(单位：分钟,0表示永久锁定)
	
	private Integer uploadMaxSize;//上传文件最大限制
	private String allowedUploadImageExtension;// 允许上传的图片文件扩展名（为空表示不允许上传图片文件）
	private String allowedUploadFileExtension;// 允许上传的文件扩展名（为空表示不允许上传文件）
	
	private String cookiePath;//Cookie路径 
	private String cookieDomain;//Cookie作用域
	
	private String ftpHost;//ip地址
	private Integer ftpPort;//端口
	private String ftpUsername;//用户名
	private String ftpPassword;//密码
	private String ftpEncoding;//编码
	private String ftpDirectoryPath;//目录路径 空为根目录
	private String ftpUrl;//访问url
	
	private String clientSiteUrl_243;//重庆 客户网址
	
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	public String getSystemVersion() {
		return systemVersion;
	}
	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}
	public Boolean getIsSystemDebug() {
		return isSystemDebug;
	}
	public void setIsSystemDebug(Boolean isSystemDebug) {
		this.isSystemDebug = isSystemDebug;
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
	public Integer getUploadMaxSize() {
		return uploadMaxSize;
	}
	public void setUploadMaxSize(Integer uploadMaxSize) {
		this.uploadMaxSize = uploadMaxSize;
	}
	public String getAllowedUploadImageExtension() {
		return allowedUploadImageExtension;
	}
	public void setAllowedUploadImageExtension(String allowedUploadImageExtension) {
		this.allowedUploadImageExtension = allowedUploadImageExtension;
	}
	public String getAllowedUploadFileExtension() {
		return allowedUploadFileExtension;
	}
	public void setAllowedUploadFileExtension(String allowedUploadFileExtension) {
		this.allowedUploadFileExtension = allowedUploadFileExtension;
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
	public String getFtpHost() {
		return ftpHost;
	}
	public void setFtpHost(String ftpHost) {
		this.ftpHost = ftpHost;
	}
	public Integer getFtpPort() {
		return ftpPort;
	}
	public void setFtpPort(Integer ftpPort) {
		this.ftpPort = ftpPort;
	}
	public String getFtpUsername() {
		return ftpUsername;
	}
	public void setFtpUsername(String ftpUsername) {
		this.ftpUsername = ftpUsername;
	}
	public String getFtpPassword() {
		return ftpPassword;
	}
	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}
	public String getFtpEncoding() {
		return ftpEncoding;
	}
	public void setFtpEncoding(String ftpEncoding) {
		this.ftpEncoding = ftpEncoding;
	}
	public String getFtpDirectoryPath() {
		return ftpDirectoryPath;
	}
	public void setFtpDirectoryPath(String ftpDirectoryPath) {
		this.ftpDirectoryPath = ftpDirectoryPath;
	}
	public String getFtpUrl() {
		return ftpUrl;
	}
	public void setFtpUrl(String ftpUrl) {
		this.ftpUrl = ftpUrl;
	}
	public String getClientSiteUrl_243() {
		return clientSiteUrl_243;
	}
	public void setClientSiteUrl_243(String clientSiteUrl_243) {
		this.clientSiteUrl_243 = clientSiteUrl_243;
	}
}
