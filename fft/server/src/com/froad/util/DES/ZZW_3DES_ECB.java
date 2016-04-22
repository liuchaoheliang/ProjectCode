package com.froad.util.DES;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.log4j.Logger;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


	/**
	 * 类描述： Java版3DES加密解密 （未启用）
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2012 
	 * @author: zhuzhiwei zhuzhiwei@f-road.com.cn
	 * @time: Mar 1, 2012 10:58:00 AM 
	 */
public class ZZW_3DES_ECB {

	private static SecretKey secretKey = null;//key对象
	private static Cipher cipher = null;   //私鈅加密对象Cipher
	private static String keyString = "AKlMU89D3FchIkhKyMma6FiE";//密钥
	private static Logger log = Logger.getRootLogger();
		 
	static{
		try {
			secretKey = new SecretKeySpec(keyString.getBytes(), "DESede");//获得密钥
		   /*获得一个私鈅加密类Cipher，DESede是算法，ECB是加密模式，PKCS5Padding是填充方式*/
		   cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		} catch (Exception e) {
		   log.error(e.getMessage(), e);
		}
	}
		 
	/**
	  * 方法描述：加密
	  * @param: message
	  * @return: 
	  * @version: 1.0
	  * @author: zhuzhiwei zhuzhiwei@f-road.com.cn
	  * @update: 
	  * @time: Mar 1, 2012 11:00:04 AM
	  */
	public static String desEncrypt(String message) {
		String result = "";   //DES加密字符串
		String newResult = "";//去掉换行符后的加密字符串
		try {
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);     //设置工作模式为加密模式，给出密钥
		    byte[] resultBytes = cipher.doFinal(message.getBytes("UTF-8")); //正式执行加密操作
		    BASE64Encoder enc = new BASE64Encoder();
		    result = enc.encode(resultBytes);//进行BASE64编码
		    newResult = filter(result);      //去掉加密串中的换行符
		} catch (Exception e) {
		   log.error(e.getMessage(), e);
		}
		return newResult;
	}
		    

	
	/**
	  * 方法描述：解密
	  * @param: message
	  * @return: 
	  * @version: 1.0
	  * @author: zhuzhiwei zhuzhiwei@f-road.com.cn
	  * @update: 
	  * @time: Mar 1, 2012 11:00:28 AM
	  */
	public static String desDecrypt(String message) throws Exception {
		String result = "";
		try {
			BASE64Decoder dec = new BASE64Decoder();
			byte[] messageBytes = dec.decodeBuffer(message); // 进行BASE64编码
			cipher.init(Cipher.DECRYPT_MODE, secretKey); // 设置工作模式为解密模式，给出密钥
			byte[] resultBytes = cipher.doFinal(messageBytes);// 正式执行解密操作
			result = new String(resultBytes, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
		 
	
	/**
	  * 方法描述：去掉加密字符串换行符
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: zhuzhiwei zhuzhiwei@f-road.com.cn
	  * @update: str
	  * @time: Mar 1, 2012 11:01:51 AM
	  */
	public static String filter(String str) {
		String output = "";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			int asc = str.charAt(i);
			if (asc != 10 && asc != 13) {
				sb.append(str.subSequence(i, i + 1));
			}
		}
		output = new String(sb);
		return output;
	}  
	
	/**
	  * 方法描述：加密解密测试
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: zhuzhiwei zhuzhiwei@f-road.com.cn
	  * @update: 
	  * @time: Mar 1, 2012 11:02:14 AM
	  */
	public static void main(String[] args) {
		try {
			String strText = "Hello world!";
			String deseResult = desEncrypt(strText);//加密   
			System.out.println("加密结果：" + deseResult);

			String desdResult = desDecrypt(deseResult);//解密
			System.out.println("解密结果：" + desdResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
