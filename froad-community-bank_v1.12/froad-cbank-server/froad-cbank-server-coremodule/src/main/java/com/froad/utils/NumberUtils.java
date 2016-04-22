package com.froad.utils;

import java.text.DecimalFormat;

/**
*bruce
*2015年12月7日 上午11:18:17
*/
public class NumberUtils {
	
	public static String changeAmountLong(Long num,int dec){
		if(num==null || num.longValue()==0){
			return "0";
		}
		double d=((double)num)/1000;
		String _fix="";
		for(int i=0;i<dec;i++){
			_fix+="0";
		}
		DecimalFormat df = new DecimalFormat("0."+_fix);    
 		return df.format(d);
	}
	 
}
