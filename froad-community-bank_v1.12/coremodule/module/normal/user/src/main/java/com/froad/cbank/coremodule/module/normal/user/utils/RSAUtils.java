package com.froad.cbank.coremodule.module.normal.user.utils;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
 


import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
 
/** *//**
* <p>
* RSA公钥/私钥/签名工具包
* </p>
* <p>
* 罗纳德·李维斯特（Ron [R]ivest）、阿迪·萨莫尔（Adi [S]hamir）和伦纳德·阿德曼（Leonard [A]dleman）
* </p>
* <p>
* 字符串格式的密钥在未在特殊说明情况下都为BASE64编码格式<br/>
* 由于非对称加密速度极其缓慢，一般文件不使用它来加密而是使用对称加密，<br/>
* 非对称加密算法可以用来对对称加密的密钥加密，这样保证密钥的安全也就保证了数据的安全
* </p>
*
 * @author IceWee
* @date 2012-4-26
* @version 1.0
*/
public class RSAUtils {
 
         /** *//**
         * 算法供应商
         */
         public static final String ALG_PROVIDER = "mockAlgorithm";
        
    /** *//**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";
   
    /** *//**
     * 01-身份证
     */
    public static final String ID_CARD= "01";
   
    /** *//**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
 
    /** *//**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";
   
    /** *//**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";
   
    private static final int KEY_SIZE = 2048;    
    
    /** *//**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = KEY_SIZE / 8 - 11;
   
    /** *//**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = KEY_SIZE / 8;
   
   
 
    /** *//**
     * <p>
     * 生成密钥对(公钥和私钥)
     * </p>
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> genKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(KEY_SIZE);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }
   
    /** *//**
     * <p>
     * 用私钥对信息生成数字签名
     * </p>
     *
     * @param data 已加密数据
     * @param privateKey 私钥(BASE64编码)
     *
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);
        return Base64.encodeBase64String(signature.sign());
    }
 
    /** *//**
     * <p>
     * 校验数字签名
     * </p>
     *
     * @param data 已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @param sign 数字签名
     *
     * @return
     * @throws Exception
     *
     */
    public static boolean verify(byte[] data, String publicKey, String sign)
            throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(Base64.decodeBase64(sign));
    }
 
    /** *//**
     * <P>
     * 私钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey)
            throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }
 
    /** *//**
     * <p>
     * 公钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey)
            throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }
 
    /** *//**
     * <p>
     * 公钥加密
     * </p>
     *
     * @param data 源数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey)
            throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }
 
    /** *//**
     * <p>
     * 私钥加密
     * </p>
     *
     * @param data 源数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey)
            throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }
 
    /** *//**
     * <p>
     * 获取私钥
     * </p>
     *
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Base64.encodeBase64String(key.getEncoded());
    }
 
    /** *//**
     * <p>
     * 获取公钥
     * </p>
     *
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64.encodeBase64String(key.getEncoded());
    }
 
    public static String getEighteenIDCard(String fifteenIDCard){ 
                   StringBuilder sb = new StringBuilder(); 
                   sb.append(fifteenIDCard.substring(0, 6)) 
                   .append("19") 
                   .append(fifteenIDCard.substring(6)); 
                   sb.append(getVerifyCode(sb.toString())); 
                   return sb.toString(); 
    } 
    
    /**
     * 获取校验码
     * @param idCardNumber 不带校验位的身份证号码（17位）
     * @return 校验码
     * @throws Exception 如果身份证没有加上19，则抛出异常
     */ 
    private static char getVerifyCode(String idCardNumber){ 
         char[] Ai = idCardNumber.toCharArray(); 
         int[] Wi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2}; 
         char[] verifyCode = {'1','0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'}; 
         int S = 0; 
         int Y; 
         for(int i = 0; i < Wi.length; i++){ 
                   S += (Ai[i] - '0') * Wi[i]; 
         } 
         Y = S % 11; 
         return verifyCode[Y]; 
    } 
    
    
    
    
    public static void main(String[] args) throws Exception {
//      System.out.println(genKeyPair());
    	String RSAPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCJW1CYKI3s2gxtChlNbfT8XRsbio5eDFaZL9lMjrSonZW5uravClSWBGzRcP2R6UUSVbQY+7mzuhwzSBkTg2Z3lWQDlFw3LirRPx0u35KguOzxZtcB4bWzCIEI03pI54JsVdyXZdAawuGt0GHQ4FqOw7T4POYGUMsPBOfwhqLPWwIDAQAB";
    	String RSAPrivateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIlbUJgojezaDG0KGU1t9PxdGxuKjl4MVpkv2UyOtKidlbm6tq8KVJYEbNFw/ZHpRRJVtBj7ubO6HDNIGRODZneVZAOUXDcuKtE/HS7fkqC47PFm1wHhtbMIgQjTekjngmxV3Jdl0BrC4a3QYdDgWo7DtPg85gZQyw8E5/CGos9bAgMBAAECgYEAh4XXUSnCTo0dcYBjUqhI7a6Xjmb47QZTczVou0aaLTu+fNAiazZQGuwmyccoCQ0Vn298EKJdqVzZcieSxlQBhk2I2E9STIMqLbeVErbtNAvtnaAIEPnZm05lsWhAjsu1wsYz1+klH3NxTeqA846bIayRDP49/2uNUZsaNNGiCSECQQC96ITBUDJyrpWl0S9cTZVj+/KiptVGeBH9WIRooD7y9dIBDpB9+aTqM8zPvwu29VjlvGn0iPW2VbHh8VOxYBsZAkEAuSjSu1h9+/u+CuD73M7Qt7J90lC2sMB8pOJZDNkj1qX2bULn0rtew04m0zTNquCUyFHpy3/HdS9fBPhnBbVAkwJABccvUtpnzol1UCg/MqXBp9sglMZSsuQwV+77iFtnzuifzRxuVQbld6K/1opPhu+MFuFWr/QC1dthoChBjaII8QJATSqfUKbbspXDKSOEtAyZXcWMvIcY7Gb8S3dt53XjgsSeBAE7Pk8Keljqw2EhSdLZDvdceYwiY0s/zYF85hsY8wJAdO/sd2Ctz7p40+nkhukXJuGf10+XVjvDhpKBebfEq/wT2qQIKOQ+JkehXvbI6mCR1adV1fTMFNvfXnUPUS8LWw==";
        String encryptParamStr =Base64.encodeBase64String(encryptByPublicKey("能解析出来吗???".getBytes(), RSAPublicKey));
        System.out.println(encryptParamStr);
        byte[] decryptParamStr =decryptByPrivateKey(Base64.decodeBase64(encryptParamStr),RSAPrivateKey);
        System.out.println(new String(decryptParamStr));
    }
}