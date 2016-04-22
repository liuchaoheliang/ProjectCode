package com.froad.fft.util;

/**
 * *******************************************************
 *<p> 工程: fft-client-chongqing </p>
 *<p> 类名: EncryptSensInfoUtil.java </p>
 *<p> 描述: *-- <b>用于处理敏感信息防止完全的明文显示</b> --* </p>
 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
 *<p> 时间: 2014年4月1日 上午11:08:49 </p>
 ********************************************************
 */
public class EncryptSensInfoUtil {

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>处理手机号码 出来后的手机号码为 [123****1234]</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月1日 上午11:09:56 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public static String encryptPhoneNum(String phoneNum){
		String temp = null;
		try {
			temp = phoneNum.substring(0, 3) + "****" + phoneNum.substring(7,11);
		} catch (Exception e) {
			return null;
		}
		return temp;
	}
}
