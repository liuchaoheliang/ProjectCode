package com.froad.action.api.command;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

//import org.apache.commons.codec.binary.Base64;


/**
 * 
 * ʹ��DES���������,�ɶ�byte[],String���ͽ��м��������
 * ���Ŀ�ʹ��String,byte[]�洢.
 * 
 * ����:
 * void getKey(String strKey)��strKey���������һ��Key
 * 
 * String getEncString(String strMing)��strMing���м���,����String����
 * String getDesString(String strMi)��strMin���н���,����String����
 * 
 *byte[] getEncCode(byte[] byteS)byte[]�͵ļ���
 *byte[] getDesCode(byte[] byteD)byte[]�͵Ľ���
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
	 * DES����ECB��DES��֧��CBC��CFB��OFB����3DESֻ֧��ECB��CBC����
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
     * ��ݲ������KEY
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
     * ������byte[]��������,byte[]�������
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
     * ������byte[]��������,��byte[]�������
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
     * ������byte[]��������,byte[]�������
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
     * ������byte[]��������,��byte[]�������
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
     * ������byte[]��������,byte[]�������
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
     * ������byte[]��������,��byte[]�������
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
     * ������byte[]��������,byte[]�������
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
     * ������byte[]��������,��byte[]�������
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
