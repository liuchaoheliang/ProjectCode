package com.froad.util;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.froad.logback.LogCvt;


public class TripleDesUtils {
	

	private static final String DEFAULT_KEY = "11223f588810303828257951cbdd556677297398303036e2";


	private static final String Algorithm = "DESede"; // 定义 加密算法

	public static byte[] encryptMode(byte[] keybyte, byte[] src) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
			// 加密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (NoSuchAlgorithmException e) {
			LogCvt.info("3DES加密NoSuchAlgorithmException异常:" + e);
		} catch (NoSuchPaddingException e) {
			LogCvt.info("3DES加密NoSuchPaddingException异常:" + e);
		} catch (Exception e) {
			LogCvt.info("3DES加密Exception异常:" + e);
		}
		return null;
	}

	public static byte[] decryptMode(byte[] keybyte, byte[] src) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
			// 解密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (NoSuchAlgorithmException e) {
			LogCvt.info("3DES解密NoSuchAlgorithmException异常:" + e);
		} catch (NoSuchPaddingException e) {
			LogCvt.info("3DES解密NoSuchPaddingException异常:" + e);
		} catch (Exception e) {
			LogCvt.info("3DES解密发生异常:" + e);
		}
		return null;
	}

	/**
	 * @Fields hexStr : 小写十六进制字符
	 */
	private static String hexStr = "0123456789abcdef";

	/**
	 * @Title: HexStringToBinary
	 * @Description: 将十六进制字符串转换为字节数组
	 * @param hexString
	 * @return byte[]
	 * @throws
	 * @author chenxiangde
	 */
	public static byte[] HexStringToBinary(String hexString) {
		// hexString的长度对2取整,作为bytes的长度
		int len = hexString.length() / 2;
		byte[] bytes = new byte[len];
		byte high = 0;// 字节高四位
		byte low = 0;// 字节低四位
		for (int i = 0; i < len; i++) {
			// 右移四位得到高位
			high = (byte) ((hexStr.indexOf(hexString.charAt(2 * i))) << 4);
			low = (byte) hexStr.indexOf(hexString.charAt(2 * i + 1));
			bytes[i] = (byte) (high | low);// 高位做或运算
		}
		return bytes;
	}

	public static String BinaryToHexString(byte[] bytes) {
		String result = "";
		String hex = "";
		for (int i = 0; i < bytes.length; i++) {
			// 字节高4位
			hex = String.valueOf(hexStr.charAt((bytes[i] & 0xF0) >> 4));
			// 字节低4位
			hex += String.valueOf(hexStr.charAt(bytes[i] & 0x0F));
			result += hex;
		}
		return result;
	}

	public static byte[] hex2byte(String str) {
		if (str == null) {
			return null;
		}
		str = str.trim();
		int len = str.length();
		if ((len == 0) || (len % 2 == 1))
			return null;
		byte[] b = new byte[len / 2];
		try {
			for (int i = 0; i < str.length(); i += 2) {
				b[(i / 2)] = (byte) Integer.decode(
						"0x" + str.substring(i, i + 2)).intValue();
			}
			return b;
		} catch (Exception e) {
			LogCvt.info("十六进制转换成字节数组发生异常:" + e);
		}
		return null;
	}

	public static String byte2hex(byte[] b) {
		StringBuffer hs = new StringBuffer(b.length);
		String stmp = "";
		int len = b.length;
		for (int n = 0; n < len; n++) {
			stmp = Integer.toHexString(b[n] & 0xFF);
			if (stmp.length() == 1)
				hs = hs.append("0").append(stmp);
			else {
				hs = hs.append(stmp);
			}
		}
		return String.valueOf(hs);
	}

	public static String encrypt(String src) throws Exception {
		byte[] encoded = encryptMode(HexStringToBinary(TripleDesUtils.DEFAULT_KEY),
				src.getBytes("utf-8"));
		return BinaryToHexString(encoded);
	}

	public static String decrypt(String src) throws Exception {
		byte[] decoded = decryptMode(HexStringToBinary(TripleDesUtils.DEFAULT_KEY),
				HexStringToBinary(src));
		return new String(decoded, "utf-8");
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		String szSrc = "This is a 3DES test. 测试";
		System.out.println("加密前的字符串:" + szSrc);

		byte[] encoded = encryptMode(HexStringToBinary(TripleDesUtils.DEFAULT_KEY),
				szSrc.getBytes());
		System.out.println("加密后的字符串:" + BinaryToHexString(encoded));

		byte[] srcBytes = decryptMode(HexStringToBinary(TripleDesUtils.DEFAULT_KEY),
				encoded);
		System.out.println("解密后的字符串:" + (new String(srcBytes)));
	}

}
