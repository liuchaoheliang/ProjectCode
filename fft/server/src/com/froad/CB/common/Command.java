package com.froad.CB.common;

import java.util.HashSet;
import java.util.Set;


	/**
	 * 类描述：系统参数指令
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2012 
	 * @author: meicy meiwenxiang@f-road.com.cn
	 * @time: Oct 9, 2012 9:54:52 AM 
	 */
public class Command {
	
	public static String respCode_SUCCESS = "0";// 成功
	public static String respCode_FAIL = "1";// 失败
	public static String respCode_EXCEPTION="2";//异常
	
	public static Integer HTTP_CONNECTIONTIMEOUT = 600000;// 第三方连接时间
	public static Integer HTTP_SOTIMEOUT = 180000;// 第三方读取数据时间
	
	/**成功的响应码**/
	public static String RESP_CODE_SUCCESS="0000";

	/**是默认值**/
	public static final String IS_DEFAULT_NO="0";
	
	public static final String IS_DEFAULT_YES="1";
	
	/**通用的状态**/
	public static final String STATE_CREATE="10";
	
	public static final String STATE_RECORD="20";
	
	public static final String STATE_START="30";
	
	public static final String STATE_STOP="40";
	
	public static final String STATE_DELETE="50";
	
	public static final Set<String> STATES=new HashSet<String>();
	
	static{
		STATES.add(STATE_CREATE);
		STATES.add(STATE_RECORD);
		STATES.add(STATE_START);
		STATES.add(STATE_STOP);
		STATES.add(STATE_DELETE);
	}

}
