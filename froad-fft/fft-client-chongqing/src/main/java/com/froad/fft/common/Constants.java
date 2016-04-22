package com.froad.fft.common;

/**
 * 常量
 * @author FQ
 *
 */
public class Constants {
	
	//日期格式配比 
	public static final String[] DATE_PATTERNS = new String[] { "yyyy", "yyyy-MM", "yyyyMM", "yyyy/MM", "yyyy-MM-dd", "yyyyMMdd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm:ss" };
	
	// fft-client.xml文件路径 
	public static final String FFT_XML_PATH = "/fft-client.xml";
	
	//Sitemap最大地址数 
	public static final Integer SITEMAP_MAX_SIZE = 40000;
	
	//重定向URL(来源URL)
	public static final String REDIRECT_URL_PARAMETER_NAME = "redirectUrl";
	
	//首页显示预售商品数量
	public static final Integer INDEX_PRESELL_NUM = 6;
	
	
	/****  SessionName Constants  ****/
	//登录用户 session
	public static final String LOGIN_MEMBER_PRINCIPAL_SESSION_NAME="loginMember.PRINCIPAL";
	
	
	
	/****  SessionName Constants  ****/
	
	
	
	/****  CookieName Constants  ****/
	//登录用户 cookie
	public static final String LOGIN_MEMBER_USERNAME_COOKIE_NAME="loginMember.username";
	
	
	
	/****  CookieName Constants  ****/
	
	//预售详情页,预售动态显示数量
	public static final Integer PRESELL_DYNAMIC_NUM = 6;
	
}
