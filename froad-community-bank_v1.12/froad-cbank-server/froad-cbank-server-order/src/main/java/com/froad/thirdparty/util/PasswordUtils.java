package com.froad.thirdparty.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.froad.thirdparty.common.PasswordCommand;



public class PasswordUtils {
	public static final String KEY_ALGORITHM = "RSA";
	// 锟斤拷钥
	public static RSAPublicKey publicKey = null;
	public static RSAPrivateKey privateKey = null;
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
	public static BouncyCastleProvider castleProvider = null;
	
	public static BouncyCastleProvider getProvider(){
		if(castleProvider == null){
			castleProvider = new BouncyCastleProvider();
		}
		return castleProvider;
	}
	
	public static PublicKey initCerPublicKey(String pubkeypathB) throws Exception {
		PublicKey pubkey = null;
		try {
			CertificateFactory certificatefactory = CertificateFactory
					.getInstance("X.509",getProvider());
			FileInputStream bais = new FileInputStream(pubkeypathB);
			X509Certificate Cert = (X509Certificate) certificatefactory
					.generateCertificate(bais);
			pubkey = Cert.getPublicKey();
			bais.close();
		} catch (Exception e) {
			throw e;
		}
		return pubkey;
	}
	
	 public static PrivateKey genPrivateKey(String keyPath, String stroePass, String alias, String pwd) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException {
			KeyStore ks;
			PrivateKey prikey = null;
			ks = KeyStore.getInstance("JKS");
			FileInputStream fin = new FileInputStream(keyPath);
			ks.load(fin, stroePass.toCharArray());
			prikey = (PrivateKey) ks.getKey(alias, pwd.toCharArray());
			
			return prikey;
		}

//	public static PrivateKey genPrivateKey(String keyPath, String alias, String key, String pwd) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException {
//		KeyStore ks;
//		PrivateKey prikey = null;
//		ks = KeyStore.getInstance("JKS");
//		FileInputStream fin = new FileInputStream(keyPath);
//		ks.load(fin, alias.toCharArray());
//		prikey = (PrivateKey) ks.getKey(key, pwd .toCharArray());		
//		return prikey;
//	}
	
	
	public static void main(String[] args) throws Exception{
	  
//		String aa = PasswordUtils.encrypt("111111"); 
//		String bb="1B35F3943BF4BB38C7031DC1EE3F36B415F561722F0B9042BB49921AE0792388C106C1230707159BEA366AB35693CE93C2EA59A1A865C55069FABE5863F7FC6FFE7A60A76888A40B55498BA7F062F99A3B3FD0508A324DAEB0D162E8636FA39BAA999752D9AB18E4261975B8E56F85F5648CD8DB3B1ED04E03D76B8722D72A96";
//String aaa="508F4DEF6E10A41D66D0E2343DDFE81C5EFFEB3595C91EC7A892F1FAD7F52D2AACE0F7ACE741E26DDB3029564B019CE88C7D7B7547359DDCFB33DD299519F9F78BA83769E2F8FEDA33611C97176EFAA2FBE5BFD585248979EF3EE62E87A4E6773656B4F109B0AECF7045712426831068C020A669D964825B7C2C5F462057FE6A";
//		System.out.println("加密后："+aa); 	
		String aa="41709D87851CB0F1715DECB6D68EECF8DB9E7B70FFB23653A1E37BD47884263169AE5957044E2A76C79225339B85A8E585B23B3E3D674350BC0A35B2AD4AE46766C0753150DFCA01DBC533356BC33C1A42221BDB5700785044F3D77EBAA02022CD63CEF5439CF2BF5339E9B44FD43AE1FCE6A841B098699F572AB5956CD4A17B";
		System.out.println("解密后："+PasswordUtils.decrypt(aa));
//AF84BC913CF9FD51AA8BD739677F0CD75E8AF261ACA8FC12CFD4765E5F0645C5B462D7AE90BC0764B55F03F6A7903A25EAA07E26D6B076D085612ABF382C1ECFE5385842D6A1C748CAE8D340B432530F9388CE90FA01E5C73863F7E0B3D820AA5DCFC157AA09E3328442074FDFD53F8609C705813EFDADB12D79634DAAB07333	
//
	}
	
	/**
	 * 加密信息
	 * @param aa 明文信息
	 * @return   密文信息
	 * @throws Exception
	 */
	public static String  encrypt(String str) throws Exception {
		String encryptstr="";
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator
					.getInstance(KEY_ALGORITHM);
			keyPairGen.initialize(1024);
			KeyPair keyPair = keyPairGen.generateKeyPair();
			// 公钥
			publicKey = (RSAPublicKey) initCerPublicKey(PasswordCommand.PUBLICKEY);   //"d:/key/test1.cer");

			String pubKey = Base64.encodeBase64String(publicKey.getEncoded());
			
			
			/**************取得公钥************/
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(
					Base64.decodeBase64(pubKey));
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM, getProvider());
			Key publicKey = keyFactory.generatePublic(x509KeySpec);

 			// 对数据加密
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm(), getProvider());
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] encrypt = cipher.doFinal(str.getBytes());
			encryptstr = byte2hex(encrypt);
			
//			System.out.println("加密前的信息 =" + byte2hex(str.getBytes()));
			System.out.println("加后的信息 =" + encryptstr);
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}
		return encryptstr;
	}
	
	
	/**
	 *  解密信息
	 * @param 密文信息
	 * @return 明文信息
	 * @throws Exception
	 */
	public static String  decrypt(String charstr) throws Exception {
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator
					.getInstance(KEY_ALGORITHM);
			keyPairGen.initialize(1024);
			KeyPair keyPair = keyPairGen.generateKeyPair();
			 // 私钥
			privateKey = (RSAPrivateKey) genPrivateKey(PasswordCommand.PRIVATEKEY,  PasswordCommand.PWD,PasswordCommand.ALIAS, PasswordCommand.KEY);   //"d:/key/cqzx.jks"
//			privateKey = (RSAPrivateKey) genPrivateKey(Constants.PRIVATEKEY, Constants.ALIAS, Constants.KEY, Constants.PWD);   //"d:/key/cqzx.jks"
			String priKey = Base64.encodeBase64String(privateKey.getEncoded());
			  
			/*******取得私钥********/
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(
					Base64.decodeBase64(priKey));
			KeyFactory keyFactory2 = KeyFactory.getInstance(KEY_ALGORITHM, getProvider());
			Key privateKey = keyFactory2.generatePrivate(pkcs8KeySpec);
 
//			System.out.println(charstr);
			Cipher cipher2 = Cipher.getInstance(keyFactory2.getAlgorithm(),getProvider());
			cipher2.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] decrypt = cipher2.doFinal(Hex.decodeHex(charstr.toCharArray()));
//			System.out.println("解密后的信 =" + new String(decrypt));
		    return new String(decrypt,"utf-8");

/*			// 用私钥对数据进行签名

			// 构造PKCS8EncodedKeySpec对象
			PKCS8EncodedKeySpec pkcs8KeySpecSig = new PKCS8EncodedKeySpec(
					Base64.decodeBase64(priKey));

			// KEY_ALGORITHM 指定的加密算法
			KeyFactory keyFactorySig = KeyFactory.getInstance(KEY_ALGORITHM);
			// 取私钥匙对象
			PrivateKey priKeySig = keyFactorySig
					.generatePrivate(pkcs8KeySpecSig);

			// 用私钥对信息生成数字签名
			Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
			signature.initSign(priKeySig);
			signature.update(encrypt);
			String sin = Base64.encodeBase64String(signature.sign());
			System.out.println("生成签名信息=" + sin);
			boolean verRes = verify(pubKey, encrypt, sin);
			System.out.println("验签结果=" + verRes);
			
			
			*/
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}  
		return "";
	}

	public static String byte2hex(byte[] b) // 二行制转字符串
	{
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (n < b.length - 1)
				hs = hs + "";
		}
		return hs.toUpperCase();
	}

	public static boolean verify(String pubKey, byte[] data, String sign) {

		try {
			// 构造X509EncodedKeySpec对象
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(
					Base64.decodeBase64(pubKey));
			// KEY_ALGORITHM 指定的加密算法
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			// 取公钥匙对象
			PublicKey pk = keyFactory.generatePublic(keySpec);
			Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
			signature.initVerify(pk);
			signature.update(data);
			// 验证签名是否正常
			return signature.verify(Base64.decodeBase64(sign));

		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}

		return false;
	}

	public void read() {
	}
	
}
