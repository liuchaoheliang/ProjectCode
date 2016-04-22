package com.froad.cbank.coremodule.framework.common;

import com.froad.cbank.coremodule.framework.common.util.type.PropertiesUtil;

public class Constants {
	private static final String PROPERTIES_PATH = "conf/soft/properties/framework-common";
	
	/**
	 * HTTP POST请求
	 */
	public static final String POST = "POST";
	
	/**
	 * UTF-8编码
	 */
	public static final String UTF8 = "UTF-8";
	
	public static enum PROPERTIES_NAME{
		
		/**
		 * 手机号码正则表达式
		 */
		MOBILE
	}
	
	public static final class API {
		public static final String TAOBAO = get("api.taobao");
		public static final String BAIDU = get( "api.baidu");
		public static final String BAIDU_AK = get( "api.baidu.ak");
	}
	
	private static String get(String key){
		PropertiesUtil sys = PropertiesUtil.getInstance(PROPERTIES_PATH);
		return sys.getValue(key);
	}
	
	public static String getPatternMobile(){
		return get(PROPERTIES_NAME.MOBILE.name());
	}
}
