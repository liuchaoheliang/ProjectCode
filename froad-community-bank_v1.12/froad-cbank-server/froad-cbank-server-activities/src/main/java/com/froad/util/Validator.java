package com.froad.util;

import java.util.Date;

public class Validator {
	public static boolean isEmptyStr(String str) {
		return ("".equals(str) || (null == str));
	}

	/**
	  * @Title: checkActiveVilidTime
	  * @Description: 计算活动的有效期
	  * @author: zengfanting 2015年11月8日
	  * @modify: zengfanting 2015年11月8日
	  * @param startDate
	  * @param endDate
	  * @return
	 */
	public static long checkActiveVilidTime(Date startDate,Date endDate){
		 long expire = -1L;
		 long start = startDate.getTime();
		 long end = endDate.getTime();
		 if(end>=start){
			 expire=(end-start)/1000;
		 }
		 return expire;
	}
}
