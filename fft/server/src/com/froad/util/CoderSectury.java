package com.froad.util;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.froad.CB.AppException.AppException;

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
	private static final Log logger = LogFactory.getLog(CoderSectury.class);
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
	public static byte[] decryptBASE64(String key)  throws AppException {
		byte[] aa;
		try {
			aa = (new BASE64Decoder()).decodeBuffer(key);
		} catch (IOException e) {
			logger.error("异常", e);
			throw new AppException(e);
		}
		return aa;
	}

	/**
	 * BASE64加密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(byte[] key)  throws AppException {
		return (new BASE64Encoder()).encodeBuffer(key);
	}

	/**
	 * MD5加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptMD5(byte[] data)  throws AppException {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance(KEY_MD5);
		} catch (NoSuchAlgorithmException e) {
			logger.error("异常", e);
			throw new AppException("encryptMD5().....");
		}
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
	public static byte[] encryptSHA(byte[] data)  throws AppException{
		MessageDigest sha = null;
		try {
			sha = MessageDigest.getInstance(KEY_SHA);
		} catch (NoSuchAlgorithmException e) {
			logger.error("异常", e);
			throw new AppException("encryptSHA().....");
		}
		sha.update(data);
		return sha.digest();
	}

	/**
	 * 初始化HMAC密钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String initMacKey()  throws AppException {
		KeyGenerator keyGenerator = null;
		try {
			keyGenerator = KeyGenerator.getInstance(KEY_MAC);
		} catch (NoSuchAlgorithmException e) {
			logger.error("异常", e);
			throw new AppException("initMacKey().....");
		}
		SecretKey secretKey = keyGenerator.generateKey();
		return encryptBASE64(secretKey.getEncoded());
	}

	/**
	 * HMAC加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptHMAC(byte[] data, String key)  throws AppException {
		SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), KEY_MAC);
		Mac mac = null;
		try {
			mac = Mac.getInstance(secretKey.getAlgorithm());
			mac.init(secretKey);
		} catch (NoSuchAlgorithmException e) {
			logger.error("异常", e);
			throw new AppException("encryptHMAC()......");
		}catch (InvalidKeyException e) {
			logger.error("异常", e);
			throw new AppException("encryptHMAC()......");
		}
		return mac.doFinal(data);
	}
	/**
	 * HMAC加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptHMACMD5(byte[] data, String key)  throws AppException {
		SecretKey secretKey = new SecretKeySpec(key.getBytes(), KEY_MAC);
		Mac mac = null;
		try {
			mac = Mac.getInstance(secretKey.getAlgorithm());
			mac.init(secretKey);
		} catch (NoSuchAlgorithmException e) {
			logger.error("异常", e);
			throw new AppException("encryptHMAC()......");
		}catch (InvalidKeyException e) {
			logger.error("异常", e);
			throw new AppException("encryptHMAC()......");
		}
		return mac.doFinal(data);
	}

}
