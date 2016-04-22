package com.froad.util.chinapay;

import javax.crypto.Cipher;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DesEncrypt {
	private static final Log logger = LogFactory.getLog(DesEncrypt.class);
	/**
	 * Description: des加密
	 * 
	 * @author fanxingqian 2011-10-11 下午14:08:37
	 * @param reqBytes
	 * @param key
	 * @return
	 */
	public static byte[] desEncrypt(byte[] reqBytes, byte[] key){
		SecretKeySpec keySpec = null;
		DESKeySpec deskey = null;
		byte[] cipherText = null;
		try {
			deskey = new DESKeySpec(key);
			keySpec = new SecretKeySpec(deskey.getKey(), "DES");

			Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, keySpec);

			cipherText = cipher.doFinal(reqBytes);
		} catch (Exception e) {
			logger.error("异常", e);
			return null;
		}
		return cipherText;
	}

	/**
	 * des解密
	 * 
	 * @param reqBytes
	 * @param key
	 * @return
	 */
	public static byte[] desDecrypt(byte[] reqBytes, byte[] key){
		SecretKeySpec keySpec = null;
		DESKeySpec deskey = null;
		byte[] cipherText = null;
		try {
			deskey = new DESKeySpec(key);
			keySpec = new SecretKeySpec(deskey.getKey(), "DES");

			Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, keySpec);

			cipherText = cipher.doFinal(reqBytes);
		} catch (Exception e) {
			logger.error("异常", e);
			return null;
		}
		return cipherText;
	}
}
