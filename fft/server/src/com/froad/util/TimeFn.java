package com.froad.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * *******************************************************
 *<p> 工程: communityBusiness </p>
 *<p> 类名: TimeFn.java </p>
 *<p> 描述: *-- <b>工具类关于时间的相关操作</b> --* </p>
 *<p> 作者: 赵肖瑶 </p>
 *<p> 时间: 2013-9-24 上午10:35:01 </p>
 ********************************************************
 */
public class TimeFn {

	public final static String TIME_TYPE = "yyyy-MM-dd|HH:mm:ss";
	
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>获取指定时间样式的SimpleDateFormat</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-9-23 下午04:31:36 </p>
	 *<p> 版本: 1.0.1 </p>
	 **********************************************************
	 */
	public static SimpleDateFormat getTimeFormat(String timeType){
		return new SimpleDateFormat(timeType);
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>获取String类型的当前时间，时间类型为TIME_TYPE</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-9-23 下午04:32:03 </p>
	 *<p> 版本: 1.0.1 </p>
	 **********************************************************
	 */
	public static String getNowTimeDefaultType(){
		return getTimeFormat(TIME_TYPE).format(new Date());
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>获取String类型的指定时间，时间类型为TIME_TYPE</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-9-24 上午10:38:33 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public static String formatTimeToString(Date date){
		if(date == null){
			return null;
		}
		return getTimeFormat(TIME_TYPE).format(date);
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>将字符串安装指定格式转换成Date类型</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-9-24 上午11:04:43 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public static Date formatTimeToDate(String date){
		if(Assert.empty(date)){
			return null;
		}
		try {
			return getTimeFormat(TIME_TYPE).parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
}
