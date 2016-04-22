package com.froad.util;

import java.util.Map;

import com.froad.util.PropertiesUtil;

public class HessianUrlUtil {
	
	public static String VIP_SPEC_URL;
	
	public static String USER_URL;
	
	static {
		Map<String, String> thirdMap = PropertiesUtil.loadProperties("thirdparty");
		
		VIP_SPEC_URL = thirdMap.get("VIP_SPEC_URL");
		
		USER_URL = thirdMap.get("USER_ENGINE_URL");
	}
	
}
