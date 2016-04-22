package com.froad.util;

import java.util.Random;

public class CreateRandomCode {
		
	 /**
	  * @Title: getRandomString
	  * @Description: 生成随机字符串
	  * @author: shenshaocheng 2015年12月1日
	  * @modify: shenshaocheng 2015年12月1日
	  * @param length 生成字符串的长度
	  * @return
	 */	
	public static String getRandomString(int length) { 
	    String base = "123456789";   
	    Random random = new Random();   
	    StringBuffer sb = new StringBuffer();   
	    for (int i = 0; i < length; i++) {   
	        int number = random.nextInt(base.length());   
	        sb.append(base.charAt(number));   
	    }   
	    return sb.toString();   
	 }   

}
