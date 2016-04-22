package com.froad.fft.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.ssl.PKCS8Key;
import org.apache.log4j.Logger;

public class RsaUtil {

	private static Logger logger = Logger.getLogger(RsaUtil.class);

	// 初始化私钥
	public static PrivateKey initPrivateKey(String path, String pwd) {
		try {

			KeyFactory keyFactory = KeyFactory.getInstance("RSA");

			File file = new File(path);
			byte[] b = null;

			InputStream in = new FileInputStream(file);

			PKCS8Key pkcs8 = new PKCS8Key(in, pwd.toCharArray());

			b = pkcs8.getDecryptedBytes();

			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(b);

			PrivateKey prikey = keyFactory.generatePrivate(keySpec);

			return prikey;

		} catch (NoSuchAlgorithmException e) {
			logger.error("异常", e);
			Runtime.getRuntime().exit(-100);
		} catch (FileNotFoundException e) {
			logger.error("异常", e);
			Runtime.getRuntime().exit(-100);
		} catch (GeneralSecurityException e) {
			logger.error("异常", e);
			Runtime.getRuntime().exit(-100);
		} catch (IOException e) {
			logger.error("异常", e);
			Runtime.getRuntime().exit(-100);
		}
		return null;

	}

	// 初始化公钥
	public static PublicKey initPublicKey(String str) {
		KeyFactory keyFactory;
		try {
			keyFactory = KeyFactory.getInstance("RSA");

			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(
					(Base64.decodeBase64(str)));

			PublicKey pubkey = keyFactory.generatePublic(keySpec);

			return pubkey;

		} catch (NoSuchAlgorithmException e) {
			logger.error("异常", e);
			Runtime.getRuntime().exit(-100);
		} catch (InvalidKeySpecException e) {
			logger.error("异常", e);
			Runtime.getRuntime().exit(-100);
		}

		return null;

	}

	// 签名
	public static String signPrivateKey(String content, PrivateKey key,
			String charsetSet) throws NoSuchAlgorithmException, IOException,
			InvalidKeySpecException, InvalidKeyException, SignatureException {

		PrivateKey prikey = key;

		Signature signature = Signature.getInstance("SHA1WithRSA");

		signature.initSign(prikey);

		signature.update(content.getBytes(charsetSet));

		byte[] signBytes = signature.sign();

		String sign = new String(Base64.encodeBase64(signBytes));
		return sign;

	}

	// 签名
	public static String signPrivateKey(String content, PrivateKey key)
			throws NoSuchAlgorithmException, IOException,
			InvalidKeySpecException, InvalidKeyException, SignatureException {
		PrivateKey prikey = key;

		Signature signature = Signature.getInstance("SHA1WithRSA");
		signature.initSign(prikey);
		signature.update(content.getBytes("UTF-8"));
		byte[] signBytes = signature.sign();
		String sign = new String(Base64.encodeBase64(signBytes));

		return sign;
	}

	// 验签
	public static boolean verifyPublicKey(String content, String sign,
			PublicKey key) throws NoSuchAlgorithmException, IOException,
			InvalidKeySpecException, InvalidKeyException, SignatureException {

		PublicKey pubkey = key;

		byte[] signed = Base64.decodeBase64(sign.getBytes());

		Signature signature = Signature.getInstance("SHA1WithRSA");

		signature.initVerify(pubkey);

		signature.update(content.getBytes("UTF-8"));

		return signature.verify(signed);

	}

}
