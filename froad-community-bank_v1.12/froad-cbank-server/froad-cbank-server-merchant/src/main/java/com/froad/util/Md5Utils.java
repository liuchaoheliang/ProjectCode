package com.froad.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import com.froad.logback.LogCvt;

/**
 * @ClassName: MD5
 * @Package com.froad.util
 * @Description: MD5 算法的Java Bean
 */

public class Md5Utils {
	public static String MAC_KEY_VALUE;             //MD5密钥
	private static Map<String ,String> md5Map = null;
	static{
		 md5Map =  PropertiesUtil.loadProperties(Constants.MD5_FILE_NAME);
	}
	
	/**
	 * 根据客户端适配KEY密钥.
	 * 
	 * @param clientId
	 * @return
	 */
	public static String getMd5KeyValue(String clientId){
			return MAC_KEY_VALUE =  md5Map.get(clientId)==null?"":md5Map.get(clientId);
	}
	
	
	/**
	 * 生成MD5转换Str.
	 * @param str
	 * @return
	 */
	public static String getMD5ofStr(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("GBK"));
		} catch (NoSuchAlgorithmException e) {
			LogCvt.error("NoSuchAlgorithmException caught!"+ e.getMessage(), e);
		} catch (UnsupportedEncodingException e) {
			LogCvt.error(e.getMessage(), e);
		}
		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString();
	}	
}