package com.froad.util;

import java.util.Map;

public class DateConfigUtil {
	
	public static Integer QUERY_MAX_DAY;
	public static Integer INITIAL_START_DAY;
	public static Integer INITIAL_END_DAY;
	
	static {
		Map<String ,String> configInfo = PropertiesUtil.loadProperties("date-config");
		QUERY_MAX_DAY = Integer.valueOf(configInfo.get("query_max_day"));
		INITIAL_START_DAY = Integer.valueOf(configInfo.get("initial_start_day"));
		INITIAL_END_DAY = Integer.valueOf(configInfo.get("initial_end_day"));
	}
}
