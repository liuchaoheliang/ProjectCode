package com.froad.cbank.coremodule.normal.boss.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;

public class PramasUtil {

	 public static long DateFormat(String date) throws ParseException{
		 String framat=null;
		 long reTime=0;
		 if(StringUtil.isNotEmpty(date)){
			 if(date.indexOf(":")!=-1)framat="yyyy-MM-dd HH:mm:ss";
			 else if(date.indexOf("/")!=-1)framat="yyyy/MM/dd";
			 else if(date.indexOf("-")!=-1)framat="yyyy-MM-dd";
			 else framat="yyyyMMdd";
			 SimpleDateFormat sf=new SimpleDateFormat(framat);
			 Date time=sf.parse(date);
			 reTime=time.getTime();
		 }

		 return reTime;
	 }
	 
	 //日期加一天
	public static long DateFormatEnd(String date) throws ParseException {
		String framat = null;
		long reTime = 0;
		if (StringUtil.isNotEmpty(date)) {
			if (date.indexOf(":") != -1)
				framat = "yyyy-MM-dd HH:mm:ss";
			else if (date.indexOf("/") != -1)
				framat = "yyyy/MM/dd";
			else if (date.indexOf("-") != -1)
				framat = "yyyy-MM-dd";
			else
				framat = "yyyyMMdd";
			SimpleDateFormat sf = new SimpleDateFormat(framat);
			Date time = sf.parse(date);
			reTime=time.getTime() + Long.valueOf(86400 * 1000) -1l;
		}
		return reTime;
	}
}
