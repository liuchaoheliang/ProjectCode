package com.froad.util.DES;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

//import org.apache.commons.codec.binary.Base64;


/**
 * 
 * 使用DES加密与解密,可对byte[],String类型进行加密与解密
 * 密文可使用String,byte[]存储.
 * 
 * 方法:
 * void getKey(String strKey)从strKey的字条生成一个Key
 * 
 * String getEncString(String strMing)对strMing进行加密,返回String密文
 * String getDesString(String strMi)对strMin进行解密,返回String明文
 * 
 *byte[] getEncCode(byte[] byteS)byte[]型的加密
 *byte[] getDesCode(byte[] byteD)byte[]型的解密
 */

public class DesEncrypt {
	
	/*
	 * DES    DES                     --DESECB
	 * DES    DES/CBC/PKCS5Padding    --DESCBC
	 * 
	 * DESede DESede                  --3DESECB
	 * 
	 * DESede DESede/CBC/PKCS5Padding --3DESCBC
	 * 
	 * 
	 * DES除了ECB，DES还支持CBC、CFB、OFB，而3DES只支持ECB和CBC两种
	 * 
	 */
    private SecretKey securekey;
    
    
    /*
    private static byte[] bkeyl={(byte)(0x3D),(byte)(0xD1),(byte)(0x86),(byte)(0x1E),(byte)(0xDC),(byte)(0xC3),(byte)0x91,(byte)0x2D,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    private static byte[] bkeyr={(byte)0xB6,(byte)0xE7,(byte)0x2B,(byte)(0x79),(byte)(0xEA),(byte)(0x21),(byte)(0xF1),(byte)(0x92),0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    private static byte[] iv={0,0,0,0,0,0,0,0};
    //private static byte[] key8={(byte)0xA7,(byte)(0xDD),(byte)(0x83),(byte)(0x87),(byte)(0xA5),(byte)(0xB0),(byte)0xD4,(byte)0x8E};
    private static byte[] key8=Tools.String2Hexbyte("3DD1861EDCC3912D");
    */

    /**
     * 根据参数生成KEY
     * @param strKey
     */
    public void setKey(byte[] key) {
        try {
            DESedeKeySpec dks = new DESedeKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            securekey = keyFactory.generateSecret(dks); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加密以byte[]明文输入,byte[]密文输出
     * @param byteS
     * @return
     */
    public byte[] get3DesEncCode(byte[] byteS) {
        byte[] byteFina = null;
        Cipher cipher;
        try {
        	//IvParameterSpec iv = new IvParameterSpec(biv);
        	cipher = Cipher.getInstance("DESede/ECB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, securekey);
            byteFina = cipher.doFinal(byteS);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cipher = null;
        }
        return byteFina;
    }

    /**
     * 解密以byte[]密文输入,以byte[]明文输出
     * @param byteD
     * @return
     */
    public byte[] get3DesDesCode(byte[] byteD) {
        Cipher cipher;
        byte[] byteFina = null;
        try {
        	//IvParameterSpec iv = new IvParameterSpec(biv);
            cipher = Cipher.getInstance("DESede/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, securekey);
            byteFina = cipher.doFinal(byteD);
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cipher = null;
        }
        return byteFina;

    }
    
    /**
     * 加密以byte[]明文输入,byte[]密文输出
     * @param byteS iv
     * @return
     */
    public byte[] get3DesCBCEncCode(byte[] byteS,byte[] iv) {
        byte[] byteFina = null;
        Cipher cipher;
        try {
        	IvParameterSpec ivp = new IvParameterSpec(iv);
            cipher = Cipher.getInstance("DESede/CBC/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, securekey,ivp);
            byteFina = cipher.doFinal(byteS);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cipher = null;
        }
        return byteFina;
    }

    /**
     * 解密以byte[]密文输入,以byte[]明文输出
     * @param byteD iv
     * @return
     */
    public byte[] get3DesCBCDesCode(byte[] byteD,byte[] iv) {
        Cipher cipher;
        byte[] byteFina = null;
        try {
        	IvParameterSpec ivp = new IvParameterSpec(iv);
            cipher = Cipher.getInstance("DESede/CBC/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, securekey,ivp);
            byteFina = cipher.doFinal(byteD);
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cipher = null;
        }
        return byteFina;

    }
    
    /**
     * 加密以byte[]明文输入,byte[]密文输出
     * @param byteS iv
     * @return
     */
    public byte[] getDesCBCEncCode(byte[] key,byte[] byteS,byte[] iv) {
        byte[] byteFina = null;
        Cipher cipher;
        try { 
        	SecretKey k = null;
       	  	k = new SecretKeySpec(key, "DES");
        	IvParameterSpec ivp = new IvParameterSpec(iv);
            cipher = Cipher.getInstance("DES/CBC/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, k,ivp);
            byteFina = cipher.doFinal(byteS);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cipher = null;
        }
        return byteFina;
    }

    /**
     * 解密以byte[]密文输入,以byte[]明文输出
     * @param byteD iv
     * @return
     */
    public byte[] getDesCBCDesCode(byte[] key,byte[] byteD,byte[] iv) {
        Cipher cipher;
        byte[] byteFina = null;
        try {
        	SecretKey k = null;
       	  	k = new SecretKeySpec(key, "DES");
        	IvParameterSpec ivp = new IvParameterSpec(iv);
            cipher = Cipher.getInstance("DES/CBC/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, k,ivp);
            byteFina = cipher.doFinal(byteD);
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cipher = null;
        }
        return byteFina;

    }
    /**
     * 加密以byte[]明文输入,byte[]密文输出
     * @param byteS 
     * @return
     */
    public byte[] getDesECBEncCode(byte[] key,byte[] byteS) {
        byte[] byteFina = null;
        Cipher cipher;
        try { 
        	SecretKey k = null;
       	  	k = new SecretKeySpec(key, "DES");
        	//IvParameterSpec ivp = new IvParameterSpec(iv);
            cipher = Cipher.getInstance("DES/ECB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, k);
            byteFina = cipher.doFinal(byteS);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cipher = null;
        }
        return byteFina;
    }

    /**
     * 解密以byte[]密文输入,以byte[]明文输出
     * @param byteD 
     * @return
     */
    public byte[] getDesECBDesCode(byte[] key,byte[] byteD) {
        Cipher cipher;
        byte[] byteFina = null;
        try {
        	SecretKey k = null;
       	  	k = new SecretKeySpec(key, "DES");
        	//IvParameterSpec ivp = new IvParameterSpec(iv);
            cipher = Cipher.getInstance("DES/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, k);
            byteFina = cipher.doFinal(byteD);
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cipher = null;
        }
        return byteFina;

    }

    

}
