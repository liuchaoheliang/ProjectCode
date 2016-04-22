package com.froad.fft.util;

import java.util.Random;

/**
 * *******************************************************
 * <p>工程: fft-client-chongqing</p>
 * <p>类名: SMSCommonCodeUtil.java</p>
 * <p> 描述: *-- <b>生成常用的随机4位数字作为短信验证码</b> --* </p>
 * <p>作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn/p>
 * <p> 时间: 2014年3月29日 下午2:20:54</p>
 ******************************************************** 
 */
public class SMSCommonCodeUtil {

	/**
	 * 随机验证码长度
	 */
	private static int codeLength = 4;

	private static int randomNum() {
		Random random = new Random();
		return random.nextInt(10);
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>获取验证码</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年3月29日 下午2:26:32 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public static String getRandomCode(){
		StringBuffer sb = new StringBuffer();
		for(int i = 1 ; i <= codeLength ; i ++){
			sb.append(randomNum());
		}		
		return sb.toString();
	}
}
