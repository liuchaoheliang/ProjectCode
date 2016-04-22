package com.froad.util;
import java.security.MessageDigest;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * <code>CoderSectury.java</code>
 * <p>
 * description:常用加密算法
 * <p>
 * Copyright 上海方付通商务服务有限公司 2011 All right reserved.
 * 
 * @author 勾君伟 goujunwei@f-road.com.cn
 * @time:2011-3-14 下午09:25:54
 * @version 1.0
 */
public abstract class CoderSectury {

	public static final String KEY_SHA = "SHA";

	public static final String KEY_MD5 = "MD5";

	/**
	 * * MAC算法可选以下多种算法
	 * 
	 * <pre>
	 *  * HmacMD5 
	 *       * HmacSHA1 
	 *       * HmacSHA256 
	 *       * HmacSHA384 
	 *       * HmacSHA512
	 * </pre>
	 */
	public static final String KEY_MAC = "HmacMD5";

	/**
	 * BASE64解密
	 * 
	 * @param key
	 * @return *
	 * @throws Exception
	 */
	public static byte[] decryptBASE64(String key) throws Exception {
		return (new BASE64Decoder()).decodeBuffer(key);
	}

	/**
	 * BASE64加密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(byte[] key) throws Exception {
		return (new BASE64Encoder()).encodeBuffer(key);
	}

	/**
	 * MD5加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptMD5(byte[] data) throws Exception {
		MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
		md5.update(data);
		return md5.digest();
	}

	/**
	 * SHA加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptSHA(byte[] data) throws Exception {
		MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
		sha.update(data);
		return sha.digest();
	}

	/**
	 * 初始化HMAC密钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String initMacKey() throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);
		SecretKey secretKey = keyGenerator.generateKey();
		return encryptBASE64(secretKey.getEncoded());
	}
	
	
	/**
	  * 方法描述：加盐MD5加密（盐值为用户名）
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 勾君伟 goujunwei@f-road.com.cn
	  * @time: May 3, 2011 12:04:53 PM
	  */
	public static String MD5EncoderWithSalt(String password,String username){
		String pass=new Md5PasswordEncoder().encodePassword(password, username);
		return pass;
	}

	/**
	 * HMAC加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptHMAC(byte[] data, String key) throws Exception {
		SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), KEY_MAC);
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);
		return mac.doFinal(data);
	}
}
