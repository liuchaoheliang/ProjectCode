package com.froad.cbank.coremodule.framework.common.client;

import sun.misc.BASE64Decoder;

/**
 * Base 64 工具类
 * @author jdkleo
 *
 */
@SuppressWarnings("restriction")
public class Base64Utils {
	public Base64Utils() {
	}

	/**
	 * 对字符串进行BASE64加密
	 * @param s
	 * @return
	 */
	public static String encode(String s) {
		if (s == null)
			return null;
		return (new sun.misc.BASE64Encoder()).encode(s.getBytes());
	}

	/**
	 * 
	 * @Description: 对字节数组进行BASE64加密
	 * @Author: liaolibiao@f-road.com.cn
	 * @Date: 2015年9月16日 下午7:02:50
	 * @param b
	 * @return
	 */
	public static String encode(byte[] b){
		if(b == null || b.length == 0){
			return null;
		}
		return encode(new String(b));
	}
	
	/**
	 * 将 BASE64 编码的字符串 s 进行解码
	 * @param s
	 * @return
	 */
	public static String decode(String s) {
		if (s == null)
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer(s);
			return new String(b);
		} catch (Exception e) {
			return null;
		}
	}

}