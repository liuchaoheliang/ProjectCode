package com.froad.cbank.coremodule.framework.web.basic.common;

import com.froad.cbank.coremodule.framework.common.util.type.PropertiesUtil;

public class Constants {
	
	private static final String PROPERTIES_PATH = "conf/soft/properties/web-basic";
	
	public static class Results{
		/**
		 * 状态码
		 * @Title code
		 * @type Results
		 * @Create Author: hjz
		 * @date 2015年4月3日 下午2:51:31
		 * 含义 TODO
		 */
		public static final String code = get("json.results.code");
		/**
		 * 返回信息
		 * @Title msg
		 * @type Results
		 * @Create Author: hjz
		 * @date 2015年4月3日 下午2:51:37
		 * 含义 TODO
		 */
		public static final String msg = get("json.results.message");
		
		/**
		 * 返回数据
		 * @Title result
		 * @type Results
		 * @Create Author: hjz
		 * @date 2015年4月3日 下午2:51:47
		 * 含义 TODO
		 */
		public static final String result = get("json.results.result");
	}
	
	
	public static String get(String key){
		PropertiesUtil sys = PropertiesUtil.getInstance(PROPERTIES_PATH);
		return sys.getValue(key);
	}
}
