package com.froad.util;


import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;

public class MD5
{

    
    public String getMD5ofStr(String myinfo) {
        byte[] digesta = null;
        try {
            java.security.MessageDigest alga = java.security.MessageDigest.
                    getInstance("MD5");
			alga.update(myinfo.getBytes("UTF-8"));
            digesta = alga.digest();

        } catch (java.security.NoSuchAlgorithmException ex) {
        	return null;
        } catch (UnsupportedEncodingException e) {
        	return null;
		}
        return this.byte2hex(digesta);
    }
    
    public String byte2hex(byte[] b) { //二行制转字符串
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
            if (n < b.length - 1) {
                hs = hs;
            }
        }
        return hs;
    }

}