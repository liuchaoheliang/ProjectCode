package com.froad.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PramasUtil {
	 
	 /**
	  * 当前时间
	  * @param date
	  * @return
	 * @throws ParseException 
	 * @throws NumberFormatException 
	  */
	 public static long DateFormatToString() throws NumberFormatException, ParseException{
		 SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String time=sf.format(new Date());
		 Date date=sf.parse(time);
		 long currentTime=date.getTime() + Long.valueOf(86400 * 1000) -1l;
		return currentTime;
	 }
	 
	 
	 public static String escapeExprSpecialWord(String keyword){
		 if (null != keyword || !("").equals(keyword) || "" != keyword) {
			 String[] fbsArr = { "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|" };
		       for (String key : fbsArr) {
		          keyword = keyword.replaceAll("\\" + key, "\\\\" + key);
		        }
	      }
	     return keyword;
	   }
	 
}
