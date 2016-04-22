package com.froad.cbank.coremodule.framework.common.util.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *********************************************** 
 * @Copyright (c) by soap All right reserved.
 * @Create Date: 2014-2-26 下午3:39:36
 * @Create Author: hujz
 * @File Name: MD5Encrypt
 * @Function: 把字符串进行MD5加密后在保存到持久层中
 * @Last version: 1.0
 * @Last Update Date:
 * @Change Log:
 ************************************************* 
 */
public class MD5Encrypt {

    /**
     * 对字符串进行MD5加密处理
     * @param str 需要加密的字符串
     * @return 加密后的字符串 
     */
    public static String MD5(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] md5 = md.digest(password.getBytes());
            StringBuffer sb = new StringBuffer();
            String part = null;
            for (int i = 0; i < md5.length; i++) {
                part = Integer.toHexString(md5[i] & 0xFF);
                if (part.length() == 1) {
                    part = "0" + part;
                }
                sb.append(part);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return password;
        }
    }
    
    /**
     * @Project pcsvr
     * @Package com.froad.cbank.coremodule.framework.common.util.encrypt
     * @Method MD5Password方法.<br>
     * @Description 数据库密码加班算法
     * @author 胡久洲
     * @date 2014-2-26 下午3:41:48
     * @param password	未加密的密码
     * @param code	一般传ID
     * @return
     */
    public static String MD5Password(String password, String code){
    	return MD5Encrypt.MD5(MD5Encrypt.MD5(password)+code);
    }
    
    public static void main(String[] args) {
        String s = MD5Password("adminadmin","1111");
        System.out.println("s1111="+s);
    }
}
