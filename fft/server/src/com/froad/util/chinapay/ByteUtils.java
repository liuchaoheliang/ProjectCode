package com.froad.util.chinapay;

public class ByteUtils {
	/**
	 * conv_len 为 bcd 码的长度，以字节为单位 将ASCII转为BCD码
	 * 
	 * @param ascii_str
	 * @param conv_len
	 * @return
	 */
	public static String ASCII2BCD(String ascii_str, int conv_len) {
		int cnt;
		char ch1, ch2;
		char ascii_buf[];
		String result = "";
		char bcd_buf[] = new char[conv_len];

		while (ascii_str.length() < (conv_len * 2)) { // 即使ASCII字符串很短，也可以转化为较长的BCD码
			ascii_str = '0' + ascii_str; // BCB码中左补0
			// ascii_str = ascii_str + '0'; //BCB码中右补0
		}
		ascii_buf = ascii_str.toCharArray();
		for (cnt = 0; cnt < conv_len; cnt++) {
			if (ascii_buf[2 * cnt] >= 'a')
				ch1 = (char) (ascii_buf[2 * cnt] - 'a' + 10);
			else if (ascii_buf[2 * cnt] >= 'A')
				ch1 = (char) (ascii_buf[2 * cnt] - 'A' + 10);
			else if (ascii_buf[2 * cnt] >= '0')
				ch1 = (char) (ascii_buf[2 * cnt] - '0');
			else
				ch1 = 0;

			if (ascii_buf[2 * cnt + 1] >= 'a')
				ch2 = (char) (ascii_buf[2 * cnt + 1] - 'a' + 10);
			else if (ascii_buf[2 * cnt + 1] >= 'A')
				ch2 = (char) (ascii_buf[2 * cnt + 1] - 'A' + 10);
			else if (ascii_buf[2 * cnt + 1] >= '0')
				ch2 = (char) (ascii_buf[2 * cnt + 1] - '0');
			else
				ch2 = 0;

			bcd_buf[cnt] = (char) (ch1 << 4 | ch2);
		}
		for (int i = 0; i < conv_len; i++)
			result += bcd_buf[i];
		return result;
	}

	/**
	 * 将byte转为16进制
	 * 
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int i = 0; i < b.length; i++) {
			stmp = Integer.toHexString(b[i] & 0xFF);
			if (stmp.length() == 1)
				hs += "0" + stmp;
			else
				hs += stmp;
		}
		return hs.toUpperCase();
	}

	/**
	 * 将BCD码转为ASCII码
	 * 
	 * @param bcd_str
	 * @return
	 */
	public static String BCD2ASCII(String bcd_str) {
		int cnt;
		int conv_len = bcd_str.length();
		char ascii_buf[] = new char[2 * conv_len];
		char bcd_buf[] = bcd_str.toCharArray();
		String ss = "";

		for (cnt = 0; cnt < conv_len; cnt++) {
			ascii_buf[2 * cnt] = (char) ((bcd_buf[cnt] >> 4));
			ascii_buf[2 * cnt] += ((ascii_buf[2 * cnt] > 9) ? ('A' - 10) : '0');
			ascii_buf[2 * cnt + 1] = (char) ((bcd_buf[cnt] & 0x0f));
			ascii_buf[2 * cnt + 1] += ((ascii_buf[2 * cnt + 1] > 9) ? ('A' - 10)
					: '0');
		}
		for (int i = 0; i < 2 * conv_len; i++)
			ss += ascii_buf[i];
		return ss;
	}

	/**
	 * Description: 将16进制的byte数组转化为16进制的字符串
	 * 
	 * @author fanxingqian 2011-10-11 下午15:20:21
	 * @param bytearr
	 * @return
	 */
	public static String ByteArr2HexString(byte[] bytearr) {
		 if (bytearr == null) {
		 return "null";
		 }
		 StringBuffer sb = new StringBuffer();
		
		 for (int k = 0; k < bytearr.length; k++) {
		 if ((bytearr[k] & 0xFF) < 16) {
		 sb.append("0");
		 }
		 sb.append(Integer.toString(bytearr[k] & 0xFF, 16));
		 }
		 return sb.toString();

	}

	/**
	 * Description: 将16进制字符串转化为16进制的byte数组
	 * 
	 * @author fanxingqian 2011-10-11 下午15:23:28
	 * @param bytearr
	 * @return
	 */
	public static byte[] HexString2ByteArr(String bytearr) {

		if (bytearr == null || bytearr.length() % 2 != 0) {
			return new byte[] {};
		}

		byte[] dest = new byte[bytearr.length() / 2];

		for (int i = 0; i < dest.length; i++) {
			String val = bytearr.substring(2 * i, 2 * i + 2);
			dest[i] = (byte) Integer.parseInt(val, 16);
		}
		return dest;
	}
	
	/**
	 * Description: 将16进制字符串转化为16进制的byte数组  
	 * @author fanxingqian
	 * 2009-8-25 下午04:09:08 
	 * @param bytearr
	 * @return
	 */
	public static byte[] String2Hex(String bytearr) {

		if (bytearr == null || bytearr.length() % 2 != 0) {
			return new byte[] {};
		}

		byte[] dest = new byte[bytearr.length() / 2];

		for (int i = 0; i < dest.length; i++) {
			String val = bytearr.substring(2 * i, 2 * i + 2);
			dest[i] = (byte) Integer.parseInt(val, 16);
		}
		return dest;
	}	
	
	/**
	 * Description: 将16进制的byte数组转化为16进制的字符串  
	 * @author Administrator 
	 * 2009-8-25 下午04:09:41 
	 * @param bytearr
	 * @return
	 */
	public static String Hex2String(byte[] bytearr) {
		if (bytearr == null) {
			return "null";
		}
		StringBuffer sb = new StringBuffer();

		for (int k = 0; k < bytearr.length; k++) {
			if ((bytearr[k] & 0xFF) < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(bytearr[k] & 0xFF, 16));
		}
		return sb.toString();

	}	
}
