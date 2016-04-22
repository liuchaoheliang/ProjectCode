/**
 * Project Name:froad-cbank-server-boss-1.10.0-SNAPSHOT
 * File Name:BossUtil.java
 * Package Name:com.froad.util
 * Date:2015年12月29日下午3:08:45
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadBusinessException;
import com.froad.thrift.vo.ResultVo;

/**
 * ClassName:BossUtil
 * Reason:	 TODO ADD REASON.
 * Date:     2015年12月29日 下午3:08:45
 * @author   huangyihao
 * @version  
 * @see 	 
 */
public class BossUtil {

	public static final String IDCARD_PATTERN1 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
	public static final String IDCARD_PATTERN2 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{4}$";
	
	public static final String HIDE_IDCARD_STR1 = "******";
	public static final String HIDE_IDCARD_STR2 = "********";
	
	/**
	 * 
	 * getStrPattren:(字符串模糊查询正则表达式).
	 *
	 * @author huangyihao
	 * 2015年11月27日 下午3:27:02
	 * @param orderId
	 * @return
	 *
	 */
	public static Pattern getFuzzyStrPattern(String str){
		if(str == null){
			return null;
		}
		String modStr = Util.escapeExprSpecialWord(str.trim());
		return Pattern.compile("^.*" + modStr + ".*$", Pattern.CASE_INSENSITIVE);
	}

	public static boolean getIdCardPattern(String str){
		if(str == null){
			return false;
		}
		if(str.length() == 15){
			//15位身份证号
			Pattern p = Pattern.compile(IDCARD_PATTERN1);
			Matcher match = p.matcher(str);
			return match.find();
		}else if(str.length() == 18){
			//18位身份证号
			Pattern p = Pattern.compile(IDCARD_PATTERN2);
			Matcher match = p.matcher(str);
			return match.find();
		}else{
			return false;
		}
	}
	
	
	public static String hideIdCardNum(String str){
		String newStr = "";
		if(getIdCardPattern(str)){
			if(str.length() == 18){
				//显示身份证前六位后四位，中间隐藏显示，其余直接显示
				String subStr = str.substring(6, str.length()-4);
				newStr = str.replace(subStr, HIDE_IDCARD_STR2);
			}else if(str.length() == 15){
				String subStr = str.substring(6, str.length()-3);
				newStr = str.replace(subStr, HIDE_IDCARD_STR1);
			}
		}else{
			newStr = str;
		}
		return newStr;
	}
	
	public static void main(String[] args) {
		String s1 = "431123199107250045";
		
		//显示身份证前六位后四位，中间隐藏显示，其余直接显示
		String n = hideIdCardNum(s1);
		System.out.println(n);
		
		
		
		String s2 = "431123910725004";
		String n2 = hideIdCardNum(s2);
		System.out.println(n2);
		
	}
	
	
	
	
	
	
	
	
	
	
}
