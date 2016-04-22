package com.froad.cbank.coremodule.module.normal.user.utils;
 
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


/**
 * @Description: 3Des加密工具
 * @Author: liaolibiao@f-road.com.cn
 * @Date: 2015年9月7日 下午5:23:44
 */
public final class TripleDesUtil {
    
	private static final String ALGORITHM = "DESede";

	private static final String ALGORITHM_ENCODING = "UTF-8";
	
	public static String getSecretKey(){
		String desKey ="PAICFFFCESTOUNITELOGGIN703743863";// Configuration.getValue("auto.tripleDes.key","pa18shopautotripledesencrypt");
    	 return desKey;
	}
    
	public static String encrypt(String value, String desKey)throws Exception {
		String result = null;
		
		if(value == null || desKey == null){
			return null;
		}
		try {
			SecretKeySpec key = new SecretKeySpec(desKey.getBytes(), 0, 24, ALGORITHM);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			BASE64Encoder encoder = new BASE64Encoder();         
			result =encoder.encodeBuffer(cipher.doFinal(value.getBytes(ALGORITHM_ENCODING)));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return result;
	}
    
	public static String decrypt(String value, String desKey)throws Exception {
		String result = null;
		
		if(value == null || desKey == null){
			return null;
   	 	}
		try {
			SecretKeySpec key = new SecretKeySpec(desKey.getBytes(), 0, 24, ALGORITHM);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);
			result = new String(cipher.doFinal(new BASE64Decoder().decodeBuffer(value)), ALGORITHM_ENCODING);
       } catch (Exception e) {
    	   e.printStackTrace();
    	   throw e;
       }

		return result;
	}
    
	public static String decipher(String desKey, String value)throws Exception {
		return decrypt(value, desKey);
	}
//      public static String encrypt(String value)throws Exception{
//               String result = encrypt(value, getSecretKey());
//               return result;
//      }
//     
//      public static String decrypt(String value)throws Exception{
//               String result = decrypt(value, getSecretKey());
//               return result;
//      }
    
    
     public static void main(String[] args) throws Exception{
    	 String value = "中文解析?????";//TOATOSDBREGISTERSUCCESSA
    	 String desKey = "PAICFFFCESTOUNITELOGGIN703743863";
    	 System.out.println(desKey.length());
    	 String tdes = encrypt(value, desKey);
    	 System.out.println(tdes);
    	 String tdesDe = decrypt(tdes,desKey);
    	 System.out.println(tdesDe);
     }
 
}