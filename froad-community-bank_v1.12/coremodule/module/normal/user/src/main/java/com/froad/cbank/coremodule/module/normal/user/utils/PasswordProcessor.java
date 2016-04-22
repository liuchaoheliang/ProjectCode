package com.froad.cbank.coremodule.module.normal.user.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
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
public class PasswordProcessor {
	
	public static final String KEY_ALGORITHM = "RSA";
	 
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
	
//	 340121196701182533  13866160682 
	public static void main(String[] args) throws Exception{
//	  String mac="13866160666_34012771000000011_011_CB_P_ANDROID_2015-04-08 14:11:00";
//		String  aa= PasswordProcessor.encrypt(mac); 
//		String bb="1B35F3943BF4BB38C7031DC1EE3F36B415F561722F0B9042BB49921AE0792388C106C1230707159BEA366AB35693CE93C2EA59A1A865C55069FABE5863F7FC6FFE7A60A76888A40B55498BA7F062F99A3B3FD0508A324DAEB0D162E8636FA39BAA999752D9AB18E4261975B8E56F85F5648CD8DB3B1ED04E03D76B8722D72A96";
//425BCF2CBCA9299D3656139955FECCDFB7041286D97704B1A1D90227935567C81257BF262CA2BF5D91CC0AAD1995F6CF13D6404C549BEC41D0E606333A6138CDB3925331DCE17304758C3EDA0647024EA71C70376BD0D9241E4A8EC5D7DC1DD94C4DA5619A249FAB604CDA481E42D00D2CA75533AAC793BAF901AA9028A5DF7F
//		System.out.println("加密：========="+aa);
//		aa ="25FA2E02E7930ECDDAAA39185DE1D64F395B1B20BA04C36B36AFF043312A2AA5F6872C10DE5BBE9E72D127AA577B814ADCCEB962789F364E73DD0E9DD40DB62E56EAD4F2C8CE22E84F357DEEF0D4485DF32C118613A1E9D7064E8E10078931F3B04610E1187C6C29E838506557641DDF0EE68A9CAEF8608BCF8376D87C94B6FE" ; 
//		bb="67B40D372C90A987090851D912BD8B7A5D7760E0D2D9C7FCEDB8D2B6A0B68058D3E55EF469BD7F4C103FC2BEAC3F310F9D590DE63C35E795A602FD2B481D3C4A86D0AC6F6AC3A820291CFDCF5C1B7523FAA92441277F5637B8883D4BC2682974ED231243E580CE78E6CEBD69BC6431E23E80242AA8831558CDCC952C75FC67C9";
//	aa="25664A2895A77B3B3DB2CCDF21C69510F2BEA339E8C98609FD4B76D1C282CD6BA5A91BED2083C08074DBBBFCF091D3CE8AA4B294F2813DD2A7A679A9D2C5E7D7F6D6E61D189A56A68445297A53878DF11EEA9F17BB4E8C56F460ED88594DDA21BBDCD3B28463B058D727B7E4BE3B315D1D3DB090A3A5BD8C7877CF75F5FCB34A";
//		System.out.println("========="+PasswordProcessor.decrypt(aa));
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
			// 公钥
			publicKey = (RSAPublicKey) initCerPublicKey(Constants.PUBLICKEY);   //"d:/key/test1.cer");

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
//			System.out.println("加后的信息 =" + byte2hex(encrypt));
			
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
	public static String  decrypt(String charstr) throws Exception{
		try {
			if(charstr==null) return "";
			KeyPairGenerator keyPairGen = KeyPairGenerator
					.getInstance(KEY_ALGORITHM);
			keyPairGen.initialize(1024);
			 // 私钥
			privateKey = (RSAPrivateKey) genPrivateKey(Constants.PRIVATEKEY,  Constants.PWD,Constants.ALIAS, Constants.KEYNAME);   //"d:/key/cqzx.jks"
//			privateKey = (RSAPrivateKey) genPrivateKey("/data/ubank/config/web/user/anhui_mobile.jks", "mobileClient", "mobileClientpass", "mobilePassAnhui");   //"d:/key/cqzx.jks"
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
//			System.out.println("解密后的信息=" + byte2hex(decrypt));
//			System.out.println("解密后的信 =" + new String(decrypt));

		    return new String(decrypt);

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
		} catch (Exception e) {
			e.printStackTrace();
		}  
		throw new Exception("解密失败"); 
		 
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
