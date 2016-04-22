package com.froad.thirdparty.util;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.froad.thirdparty.common.UserEngineCommend;



/**
 * 支付控件
 * @author jeremy
 *
 */
public class PaymentControlsRsa {
	private final static String privatefile = UserEngineCommend.PAYPWD_RSA_PRIVATE_KEY;//"/opt/froad/config/o2obill/key/server_key.pem";
	//public  static String public_key_String = "30819f300d06092a864886f70d010101050003818d0030818902818100cd889fec17dc0e8ff23cf66f1054ae7287616302ecbca143e96711b41371fb3a653aceaf2ce393f4e1ed624162fc799cc1837ff1e01e652611e637e61d50a9532deb92f28f46c9e260a2ffebd3c7167d6a42057144faeb98719b8b3b754f2b7b1c46665a0ed7a1601d60adb672e5548ecf2ee30421ffcf5943ab7bc85fe9b5710203010001";
	//public  static String private_key_String = "30820276020100300d06092a864886f70d0101010500048202603082025c02010002818100cd889fec17dc0e8ff23cf66f1054ae7287616302ecbca143e96711b41371fb3a653aceaf2ce393f4e1ed624162fc799cc1837ff1e01e652611e637e61d50a9532deb92f28f46c9e260a2ffebd3c7167d6a42057144faeb98719b8b3b754f2b7b1c46665a0ed7a1601d60adb672e5548ecf2ee30421ffcf5943ab7bc85fe9b5710203010001028180023a06b18218aa37b9021c115bf5eee5e2bd955b04c18e65b3f39fe7798674984f5c71bcc819b712a217f6468b11fd274b99671b71b8229465013f7dc8b784fe12bab573c241bd5321d042e9c7efd1d23d7fe2cb72fb52ecc778f15886692ca8121e7e24d3f3e723f3ed4bec0b9b6a55d12c559d0f106025825c3acd0f140cd9024100e9a445d7c5591cc3c86cb651ac22167d69d9175ff00760c948b7ee2d4a2e9fc24cc8703b9a3dd371541a9fddad2a57aefcd64f216b8dd90363bd400ef09d3937024100e133c3ec053ddf48bb0b7a9f9e0da68f07935ecda064125807d14a51522d2426dcfd818a96f38d15579b339527ff8400731a46f19cb043899cf5bf39d6bdba970241009f5d3b7e87cfd6bcfc529ab16eb5f99d25bbbc23e637421f49889bdf2c804cdc5d3f42be84e0b2fed41d2cfa29897e318fa82665675563b6da2b562c5a97035502404f2cc4e7f8dbcce1a4291ffe7831f269fcceda18cf17ffbf055896994a9be646e79114f725a510e7fcf9eac3b8e1438668f14719f0eda381013700f913bf769702403bfc9bd265a22e210f7be492d9174a9bfaa0f60576dbb2ccb21ecd007ef2903b64c6be2bca4ba61e08076857e397c8b6c87b95e85b27d872b5c695e2c6418678";
	private final static String regex = "[(0-9)(a-zA-Z)]{6,16}";
	
	public static boolean validatePaymentPsw(String psw){
		if(psw == null) return false;
		return psw.matches(regex);
	}
	
	
	
	/**
	 * 得到公钥
	 * 
	 * @param key
	 *            密钥字符串（经过base64编码）
	 * @throws Exception
	 */
	public static PublicKey getPublicKey(String key) throws Exception {
		byte[] keyBytes;
		
		keyBytes = Base64.decodeBase64(key);;
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	/**
	 * 得到私钥
	 * 
	 * @param key
	 *            密钥字符串（经过base64编码）
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey(String key) throws Exception {
		byte[] keyBytes;
		keyBytes = Base64.decodeBase64(key);

		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}
	
	
	/**
	 * 解密
	 * @param keyName
	 * @return
	 * @throws Exception
	 */
	public static String decodeRSAString(String keyName) throws Exception {
	    //如果密文少于50
	    if(keyName == null || keyName.length() < 16) {
	        return keyName;
	    }
	    
	    RSAForPay rsaUtil = RSAForPay.getInstance(0);
	    //String private_key=get16to2KeyString(private_key_String);
	    //PrivateKey privateKey = getPrivateKey(private_key);
	    //PEMConvet pemConvet = PEMConvet.getInstance();
	    PrivateKey privateKey = RSAForPay.loadPrivateKeyFromPem(privatefile);
	    byte[] datas= decodeHex(keyName.toLowerCase());
	    byte[]deBytes = rsaUtil.decode(privateKey, datas); 
	    String returnKeytoString=new String(deBytes);
	    return returnKeytoString;
	}
	
	public static String get16to2KeyString(String keyString) throws Exception {
		byte [] decodebyte=decodeHex(keyString);
		String keydecodeToencode=getKeyString(decodebyte);
		return keydecodeToencode;
	}
	
	/**
	 * 得到密钥字符串（经过base64编码）
	 * 
	 * @return
	 */
	public static String getKeyString(byte[] keyBytes) throws Exception {
		String s = Base64.encodeBase64String(keyBytes);
		return s;
	}
	
	public static final String encodeHex(byte[] bytes)// 把二进制对象转化为16进制串（用字符串表示）
	{
		StringBuffer buf = new StringBuffer(bytes.length * 2);

		for (int i = 0; i < bytes.length; ++i) {
			if ((bytes[i] & 0xFF) < 16)
				buf.append("0");

			buf.append(Long.toString(bytes[i] & 0xFF, 16));
		}
		return buf.toString();
	}

	public static final byte[] decodeHex(String hex) {// 把字符串（该串标表示的是16进制）转化为二进制对象
		char[] chars = hex.toCharArray();
		byte[] bytes = new byte[chars.length / 2];
		int byteCount = 0;
		for (int i = 0; i < chars.length; i += 2) {
			
			byte newByte = 0;
			newByte = (byte) (newByte | hexCharToByte(chars[i]));
			newByte = (byte) (newByte << 4);
			newByte = (byte) (newByte | hexCharToByte(chars[(i + 1)]));
			bytes[byteCount] = newByte;
			++byteCount;
		}
		return bytes;
	}

	private static final byte hexCharToByte(char ch) {
		switch (ch) {
		case '0':
			return 0;
		case '1':
			return 1;
		case '2':
			return 2;
		case '3':
			return 3;
		case '4':
			return 4;
		case '5':
			return 5;
		case '6':
			return 6;
		case '7':
			return 7;
		case '8':
			return 8;
		case '9':
			return 9;
		case 'a':
			return 10;
		case 'b':
			return 11;
		case 'c':
			return 12;
		case 'd':
			return 13;
		case 'e':
			return 14;
		case 'f':
			return 15;
		}
		return 0;
	}
	
}
