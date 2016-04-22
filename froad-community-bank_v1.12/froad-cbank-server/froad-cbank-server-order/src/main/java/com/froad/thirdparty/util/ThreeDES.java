package com.froad.thirdparty.util;



import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @ClassName: ThreeDES
 * @Description: 3DES加解密工具类
 * @author chenxiangde
 * @date 2014-9-1 下午03:53:30
 */
public class ThreeDES {
	static final Log logger = LogFactory.getLog(ThreeDES.class);
	
	public static final String VERIFY_KEY = "22113f588821303828257951cbdd556677297398313136e2";
	
	/*static {
		try {
			// 添加新安全算法,如果使用JCE就要把它添加进去
			Security.addProvider(new com.sun.crypto.provider.SunJCE());
		} catch (Exception e) {
			logger.info("添加新安全算法异常!");
            throw new RuntimeException(e);
		}
	}*/
	
	/**
     * 在java中调用sun公司提供的3DES加密解密算法时,需要使用$JAVA_HOME/jre/lib/目录下如下的4个jar包
     * jce.jar security/US_export_policy.jar security/local_policy.jar ext/sunjce_provider.jar
     */
	private static final String Algorithm = "DESede"; // 定义 加密算法
	
	/**
	 * @Title: encryptMode
	 * @Description: 3DES加密
	 * @param keybyte 加密密钥,长度为24字节
	 * @param src 加密的数据缓冲区(源)
	 * @return byte[]
	 * @throws
	 * @author chenxiangde
	 */
	public static byte[] encryptMode(byte[] keybyte, byte[] src) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
			// 加密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (NoSuchAlgorithmException e) {
			logger.info("3DES加密NoSuchAlgorithmException异常:"+e);
		} catch (NoSuchPaddingException e) {
			logger.info("3DES加密NoSuchPaddingException异常:"+e);
		} catch (Exception e) {
			logger.info("3DES加密Exception异常:"+e);
		}
		return null;
	}
	
	/**
	 * @Title: decryptMode
	 * @Description: 3DES解密
	 * @param keybyte 加密密钥,长度为24字节
	 * @param src 加密后的缓冲区
	 * @return byte[]
	 * @throws
	 * @author chenxiangde
	 */
	public static byte[] decryptMode(byte[] keybyte, byte[] src) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
			// 解密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (NoSuchAlgorithmException e) {
			logger.info("3DES解密NoSuchAlgorithmException异常:"+e);
		} catch (NoSuchPaddingException e) {
			logger.info("3DES解密NoSuchPaddingException异常:"+e);
		} catch (Exception e) {
			logger.info("3DES解密发生异常:"+e);
		}
		return null;
	}
	
	/**
	 @Fields hexStr : 小写十六进制字符
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
		int len = hexString.length()/2;
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
	
	/**
	 * @Title: BinaryToHexString
	 * @Description: 将字节数组转换为十六进制字符串输出
	 * @param bytes
	 * @return String
	 * @throws
	 * @author chenxiangde
	 */
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
	
	/**
	 * @Title: hex2byte
	 * @Description: 将十六进制字符串转换成字节数组
	 * @param String
	 * @return byte[]
	 * @throws
	 * @author chenxiangde
	 */
	public static byte[] hex2byte(String str) {
		if (str == null) {
			return null;
		}
		str = str.trim();
		int len = str.length();
		if ((len == 0) || (len % 2 == 1))
			return null;
		byte[] b = new byte[len/2];
		try {
			for (int i = 0; i < str.length(); i += 2) {
				b[(i/2)] = (byte) Integer.decode("0x" + str.substring(i, i + 2)).intValue();
			}
			return b;
		} catch (Exception e) {
			logger.info("十六进制转换成字节数组发生异常:"+e);
		}
		return null;
	}
	
	/**
	 * @Title: byte2hex
	 * @Description: 将字节数组转换成十六进制字符串
	 * @param byte[]
	 * @return String
	 * @throws
	 * @author chenxiangde
	 */
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
	
	public static String encrypt(String src, String key) throws Exception {
		byte[] encoded =  encryptMode(HexStringToBinary(key), src.getBytes("utf-8"));
		return BinaryToHexString(encoded);
	}
	
	public static String decrypt(String src, String key) throws Exception {
		byte[] decoded =  decryptMode(HexStringToBinary(key), HexStringToBinary(src));
		return new String(decoded,"utf-8");
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String szSrc = "abc";
		System.out.println("加密前的字符串:" + szSrc);

		byte[] encoded = encryptMode(HexStringToBinary(ThreeDES.VERIFY_KEY), szSrc.getBytes());
		System.out.println("加密后的字符串:" + BinaryToHexString(encoded));

		byte[] srcBytes = decryptMode(HexStringToBinary(ThreeDES.VERIFY_KEY), encoded);
		System.out.println("解密后的字符串:" + (new String(srcBytes)));
	}

}
