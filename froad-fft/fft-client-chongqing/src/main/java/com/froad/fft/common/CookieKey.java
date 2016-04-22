package com.froad.fft.common;

/**
 * *******************************************************
 *<p> 工程: fft-client-chongqing </p>
 *<p> 类名: SessionKey.java </p>
 *<p> 描述: *-- <b>系统session Key总集，所有session Key 需要在这里申明</b> --* </p>
 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
 *<p> 时间: 2014年3月25日 下午10:33:57 </p>
 ********************************************************
 */
public enum CookieKey {

	/**
	 * 登录用户名cookie
	 */
	COOKIE_LOGIN_NAME("cookie_login_name"),
	;
	
	private String value;
	private CookieKey(String value){
		this.value = value;
	}
	public String key(){
		return value;
	}
}
