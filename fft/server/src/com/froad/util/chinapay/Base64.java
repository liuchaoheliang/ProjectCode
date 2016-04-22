package com.froad.util.chinapay;


	/**
	 * 类描述： base64加密与解密（银联)
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2011 
	 * @author: 范兴乾 fanxingqian@f-road.com.cn
	 * @time: Oct 18, 2011 5:38:20 PM 
	 */
public class Base64 {
	
	public Base64(){}
	
	/**
	  * 方法描述： base64解密（银联)
	  * @param: char data[]
	  * @return: byte[] out
	  * @version: 1.0
	  * @author: 范兴乾 fanxingqian@f-road.com.cn
	  * @time: Oct 18, 2011 4:06:07 PM
	  */
	public static byte[] decode(char data[]){
	        int tempLen = data.length;
	        for(int ix = 0; ix < data.length; ix++){
	            int value = codes[data[ix] & 255];
	            if(value < 0 && data[ix] != '=')
	                tempLen--;
	        }

	        int len = ((tempLen + 3) / 4) * 3;
	        if(tempLen > 0 && data[tempLen - 1] == '=')
	            len--;
	        if(tempLen > 1 && data[tempLen - 2] == '=')
	            len--;
	        byte out[] = new byte[len];
	        int shift = 0;
	        int accum = 0;
	        int index = 0;
	        for(int ix = 0; ix < data.length; ix++){
	            int value = codes[data[ix] & 255];
	            if(value >= 0){
	                accum <<= 6;
	                shift += 6;
	                accum |= value;
	                if(shift >= 8){
	                    shift -= 8;
	                    out[index++] = (byte)(accum >> shift & 255);
	                }
	            }
	        }

	        if(index != out.length)
	            throw new Error("Miscalculated data length (wrote " + index + " instead of " + out.length + ")");
	        else
	            return out;
	    }

	    
	    /**
	      * 方法描述： base64加密（银联)
	      * @param: byte data[]
	      * @return: char[] out
	      * @version: 1.0
	      * @author: 范兴乾 fanxingqian@f-road.com.cn
	      * @time: Oct 18, 2011 4:06:11 PM
	      */
	    public static char[] encode(byte data[]){
	        char out[] = new char[((data.length + 2) / 3) * 4];
	        int i = 0;
	        for(int index = 0; i < data.length; index += 4){
	            boolean quad = false;
	            boolean trip = false;
	            int val = 255 & data[i];
	            val <<= 8;
	            if(i + 1 < data.length){
	                val |= 255 & data[i + 1];
	                trip = true;
	            }
	            val <<= 8;
	            if(i + 2 < data.length){
	                val |= 255 & data[i + 2];
	                quad = true;
	            }
	            out[index + 3] = alphabet[quad ? val & 63 : 64];
	            val >>= 6;
	            out[index + 2] = alphabet[trip ? val & 63 : 64];
	            val >>= 6;
	            out[index + 1] = alphabet[val & 63];
	            val >>= 6;
	            out[index] = alphabet[val & 63];
	            i += 3;
	        }

	        return out;
	    }
	    
	    /**
	     * base64加密块（银联)
	     */

	    private static char alphabet[] = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();
	    private static byte codes[];

	    static {
	        codes = new byte[256];
	        for(int i = 0; i < 256; i++)
	            codes[i] = -1;

	        for(int i = 65; i <= 90; i++)
	            codes[i] = (byte)(i - 65);

	        for(int i = 97; i <= 122; i++)
	            codes[i] = (byte)((26 + i) - 97);

	        for(int i = 48; i <= 57; i++)
	            codes[i] = (byte)((52 + i) - 48);

	        codes[43] = 62;
	        codes[47] = 63;
	    }
}
