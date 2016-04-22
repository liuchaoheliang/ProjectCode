package com.froad.thirdparty.util;

/**
 * 用于处理敏感信息防止完全的明文显示
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015
 * @author:  dongr@f-road.com.cn
 * @time: 2015年4月10日 下午1:30:11
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
