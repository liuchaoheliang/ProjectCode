package com.froad.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.jboss.netty.handler.codec.base64.Base64Decoder;

import com.froad.logback.LogCvt;

public class SHAUtil {
	
	
	public static String encrypt(String password){
		String encryptPassword = "";
		if(StringUtils.isNotEmpty(password)){
			MessageDigest messageDigest;
			try {
				messageDigest = MessageDigest.getInstance("SHA-256");
				messageDigest.update(password.getBytes());
				encryptPassword = new String(Base64.encodeBase64(messageDigest.digest()));
			} catch (NoSuchAlgorithmException e) {
				LogCvt.error(e.getMessage(), e);
			}
		}
		return encryptPassword;
	}
	
	public static String decrypt(String password){
		String decryptPassword = "";
		if(StringUtils.isNotEmpty(password)){
			try {
				MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
				messageDigest.update(password.getBytes());
				Base64Decoder base64Decoder = new Base64Decoder();
				decryptPassword = new String(Base64.decodeBase64(messageDigest.digest()));
			} catch (NoSuchAlgorithmException e) {
				LogCvt.error(e.getMessage(), e);
			}
		}
		return decryptPassword;
	}
	
	
	public static void main(String[] args) {
		String str = "123456";
		String en = encrypt(str);
		String de = decrypt(en);
		System.out.println(en+" "+en.length());
		System.out.println(de);
	}
}
