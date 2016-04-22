package com.froad.cbank.coremodule.framework.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5
 * 
 * @author sun create 2013-2-4上午9:35:50
 */
public class MD5Util {

	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
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

	public static String generateSalt() {
		String chars = "0123456789abcdefghijklmnopqrstuvwxyz";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 6; i++) {
			int rand = (int) (Math.random() * chars.length());
			sb.append(chars.charAt(rand));
		}
		return sb.toString();
	}

	public static void main(String[] args) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {
		System.out.println(MD5Util.getMD5Str(MD5Util.getMD5Str("123456")
				+ "admin"));

	}
}
