package com.froad.action.api.command;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

public class Util {
	
	public static boolean ultrafast = false;;
	public static boolean useAsyncQueue = true;
	public static boolean commitWhenFailed = false;
	public static int peekMinInterval = 200; // ms
	
	public static String[] byteHexTable = {
	"00","01","02","03","04","05","06","07","08","09","0A","0B","0C","0D","0E","0F",
	"10","11","12","13","14","15","16","17","18","19","1A","1B","1C","1D","1E","1F",
	"20","21","22","23","24","25","26","27","28","29","2A","2B","2C","2D","2E","2F",
	"30","31","32","33","34","35","36","37","38","39","3A","3B","3C","3D","3E","3F",
	"40","41","42","43","44","45","46","47","48","49","4A","4B","4C","4D","4E","4F",
	"50","51","52","53","54","55","56","57","58","59","5A","5B","5C","5D","5E","5F",
	"60","61","62","63","64","65","66","67","68","69","6A","6B","6C","6D","6E","6F",
	"70","71","72","73","74","75","76","77","78","79","7A","7B","7C","7D","7E","7F",
	"80","81","82","83","84","85","86","87","88","89","8A","8B","8C","8D","8E","8F",
	"90","91","92","93","94","95","96","97","98","99","9A","9B","9C","9D","9E","9F",
	"A0","A1","A2","A3","A4","A5","A6","A7","A8","A9","AA","AB","AC","AD","AE","AF",
	"B0","B1","B2","B3","B4","B5","B6","B7","B8","B9","BA","BB","BC","BD","BE","BF",
	"C0","C1","C2","C3","C4","C5","C6","C7","C8","C9","CA","CB","CC","CD","CE","CF",
	"D0","D1","D2","D3","D4","D5","D6","D7","D8","D9","DA","DB","DC","DD","DE","DF",
	"E0","E1","E2","E3","E4","E5","E6","E7","E8","E9","EA","EB","EC","ED","EE","EF",
	"F0","F1","F2","F3","F4","F5","F6","F7","F8","F9","FA","FB","FC","FD","FE","FF",
	};

	public static String ByteBufferGetString(ByteBuffer b, int n){
		byte[] ba = new byte[n];
		b.get(ba, 0, n);
		return new String(ba);
	}
	
	public static String hex(byte[] dst) {
		StringBuffer sb = new StringBuffer();
		for (int i=0;i<dst.length;i++){
			sb.append(byteHexTable[dst[i]&0xff]);
		}
		return sb.toString();
	}

	public static String hex(byte b) {
		return byteHexTable[b&0xff];
	}
	
	public static String hex(int b) {
		return byteHexTable[b&0xff];
	}
	
	public static String hex(byte[] dst, boolean sp) {
		if (ultrafast) return "";
		
		StringBuffer sb = new StringBuffer();
		for(int j=0;j<dst.length;j+=16){
			// hex
			for(int i=j;i<j+16;i++){
				if ((i&0x7)==0) sb.append(' ');
				if(i<dst.length) {
					sb.append(byteHexTable[dst[i]&0xff]);
				}else{
					sb.append("  ");
				}
				sb.append(' ');
			}
			
			// text
			for(int i=j;i<j+16;i++){
				if ((i&0x7)==0) sb.append(' ');
				if(i<dst.length) {
					sb.append(printableChar(dst[i]));
				}else{
					sb.append(" ");
				}
			}
			sb.append('\n');
		}
		return sb.toString();
	}
	

	private static char printableChar(byte b) {
		if (b>=32 && b<127) return (char)b;
		return '.';
	}

	
	public static byte[] join(byte[] b1, byte[] b2) {
		byte[] out = new byte[b1.length + b2.length];
		ByteBuffer.wrap(out).put(b1).put(b2);
		return out;
	}
	
	public static List createList(){
		return Collections.synchronizedList(new LinkedList());
	}

	public static String ByteBufferGetLine(ByteBuffer in, char c) {
		byte[] ba = new byte[in.remaining()];
		for(int i=0;in.remaining()>0;i++){
			byte b = in.get();
			if (b!=c){
				ba[i] = b;
			}else{
				return new String(ba,0,i);
			}
		}
		return null;
	}

	public static byte[] ByteBufferGetByteArray(ByteBuffer in, int n) {
		byte[] ba = new byte[n];
		in.get(ba, 0, n);
		return ba;
	}
	
	private static byte h2b(byte c){
		if (c<='9' && c>='0'){
			return (byte) (c-'0');
		}else if (c>='a' && c<='z'){
			return (byte) (c-'a'+10);
		}else{
			return (byte) (c-'A'+10);
		}
	}

	/*
	public static String unhex(String ss) {
		ss = ss.replace(" ", "");
		byte[] ba1 = ss.getBytes();
		byte[] ba = new byte[ss.length()/2];
		
		for(int i=0;i<ss.length();i+=2){
			ba[i/2] = (byte) (h2b(ba1[i])*0x10 + h2b(ba1[i+1]));
		}

		return new String(ba);
	}*/
	
	public static byte[] unhexba(String ss){
		ss = ss.replaceAll(" ", "");
		byte[] ba1 = ss.getBytes();
		byte[] ba = new byte[ss.length()/2];
		
		for(int i=0;i<ss.length();i+=2){
			ba[i/2] = (byte) (h2b(ba1[i])*0x10 + h2b(ba1[i+1]));
		}
		return ba;
	}

	/*
	public static String[] cutStringBy(String s, int size){
		Vector v = new Vector();
		char[] ba = s.toCharArray();
		int n=0, offset=0, next=0, i;
		for(i=0;i<ba.length;i++){
			n += ba[i]>256?2:1;
			if (n<size) continue;
			if (n==size) i++;
			i--;
			next = i+1;
			v.add(new String(ba, offset, next-offset));
			offset = next;
			n = 0;
		}
		next = i;
		v.add(new String(ba, offset, next-offset));
		
		return Vector2ArrayStr(v);
	}*/
	
	public static String[] cutStringBy(String s, int size){
		Vector v = new Vector();
		byte[] ba = s.getBytes();
		int n=0, n1, offset=0;
		while(n<ba.length){
			n1 = n;
			n += (ba[n]&0x80)!=0?2:1;
			//System.out.println(new String(ba, offset, n-offset));
			if (n-offset<size) continue;
			if (n-offset>size) n=n1;
			v.add(new String(ba, offset, n-offset));
			offset = n;
		}
		v.add(new String(ba, offset, n-offset));
		
		return Vector2ArrayStr(v);
	}
	
	public static String[] cutStringByCN(String s, int size){
		Vector v = new Vector();
		byte[] ba = s.getBytes();
		int n=0, i=0, offset=0;
		while(n<ba.length){
			n += (ba[n]&0x80)!=0?2:1;
			i++;
			if (i<size) continue;
			v.add(new String(ba, offset, n-offset));
			offset = n;
			i=0;
		}
		if (n>offset){
			v.add(new String(ba, offset, n-offset));
		}
		
		return Vector2ArrayStr(v);
	}
	
	private static String[] Vector2ArrayStr(Vector v) {
		String[] ss = new String[v.size()];
		for(int i=0;i<v.size();i++) ss[i] = (String) v.get(i);
		return ss;
	}

	public static void sleep(int wt) {
		try {
			Thread.sleep(wt);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static byte[] iobuffer2array(ByteBuffer buf) {
		byte[] x = new byte[buf.position()];		
		buf.flip();
		buf.get(x);
		return x;
	}

	public static byte[] IoBufferGetArray(ByteBuffer buf, int n) {
		byte[] ba = new byte[n];
		buf.get(ba);
		return ba;
	}

//	public static void iosessionPutStr(ByteBuffer buf, String name, int i) {
//		byte[] x = name.getBytes();
//		buf.put((byte) x.length);
//		buf.put(UtilC.padorcut(x, 31));
//	}
	
	public static String iosessionGetStr(ByteBuffer buf, int i) {
		byte x = buf.get();
		if (x+1>i) throw new Error("wrong size of : " + x);
		byte[] y = new byte[x];
		buf.get(y);
		for(int j=0;j<i-1-x;j++) buf.get();
//		buf.skip(i-1-x);
		return new String(y);
	}

	public static int min(int i, int j) {
		return i>j?j:i;
	}

	public static byte[] cutBytes(byte[] datas, int nSent, int n) {
		byte[] x = new byte[n];
		System.arraycopy(datas, nSent, x, 0, n);
		return x;
	}

	public static void writeFile(String name, byte[] data) {
		try {
			BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(name));
			fos.write(data);
			//new FileInputStream(name).read(data);
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return data;		
	}
	
	public static void writeFile(String name, String d) {
		writeFile(name, d.getBytes());
	}
	
	public static byte[] readFile(String name) {
		File f = new File(name);
		byte[] data = new byte[(int) f.length()];
		try {
			new FileInputStream(name).read(data);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	
	public static byte[] readFile(URI u) {
		File f = new File(u);
		byte[] data = new byte[(int) f.length()];
		try {
			new FileInputStream(f).read(data);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	
	public static byte[] ioBufferReadAll(ByteBuffer buf) {
		byte[] x = new byte[buf.remaining()];
		buf.get(x);
		return x;
	}

	public static void waitFileExist(String string) {
		while(true){
			File f = new File(string);
			if (f.exists()) break;;
			sleep(100);
		}
	}

	public static void deleteFile(String string) {
		new File(string).delete();
		
	}

	public static void runAndWait(String string) throws IOException {
		Process x = Runtime.getRuntime().exec("test.bat");
		while(true){
			try {
				x.waitFor();
				break;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}		
	}

	public static String pad(String a, String b, int i) {
		while(a.length()<i) a+=b;
		return a;
	}

	public static String ascii(String x) {
		return Util.hex(x.getBytes());
	}

	/**
	  * 方法描述：获取用户访问ip
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	  * @update:
	  * @time: Sep 27, 2011 3:39:46 PM
	  */
	public static String getIpAddr(HttpServletRequest request) {        
	    String ip = request.getHeader("x-forwarded-for");        
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {        
	        ip = request.getHeader("Proxy-Client-IP");        
	    }        
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {        
	        ip = request.getHeader("WL-Proxy-Client-IP");        
	    }        
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {        
	        ip = request.getRemoteAddr();        
	    }        
	    return ip;        
	}  
	public static byte [] pinFill(String pin){
		pin = LeftFill(String.valueOf(pin.length()/2), 2, "0") + pin;
		byte[] bsrc = unhexba(pin);
		int i = 0,j,srcLen,tpLen;
		tpLen=srcLen=bsrc.length;
		while(tpLen%8!=0)tpLen++;
		byte[] tempsrc = new byte[tpLen];
	    while(i<srcLen){
	       /* 分段 + 补位 */
	       for(j=0;j<8;) {
	           if(i<srcLen) {
	               tempsrc[i] = bsrc[i];
				   j++;
			   }
	           else {
	               tempsrc[i] = 0x20;
				   j++;
			   }
	           i++;
	       }
	    }
	    return tempsrc;
	}


	public  static String LeftFill(String src,int count,String in)
    {
    	if(count>0)
    	{
    		if(src.length()<count)
    		{
	    		int i;
	    		String fills="";
	    		if(in==null || in.equals(""))
	    			in="0";
	    		for(i=0;i<count;i++)
	    		{
	    			fills+=in;
	    		}
	    		return fills.substring(0,fills.length()-src.length())+src;
    		}else
    		{
    			return src;
    		}
    	}else
    	{
    		return src;
    	}
    }
	
	public static String getPayMethod(String cashPrice,String fftPointPrice,String bankPointPrice){
		if((fftPointPrice != null && !"".equals(fftPointPrice.trim())) && (bankPointPrice != null && !"".equals(bankPointPrice.trim()))){
			return "error";
		}
		
		//Currency("02"),//支付方式：纯现金
		if((cashPrice != null && !"".equals(cashPrice.trim())) && //不为空
				(fftPointPrice == null || "".equals(fftPointPrice.trim())) && //为空
						(bankPointPrice == null || "".equals(bankPointPrice.trim())) //为空
								){
			return "02";
		}
		
		//FFT_Points("00"),//支付方式：纯分分通积分 
		if((cashPrice == null || "".equals(cashPrice.trim())) && //为空
				(fftPointPrice != null && !"".equals(fftPointPrice.trim())) && //不为空
						(bankPointPrice == null || "".equals(bankPointPrice.trim())) //为空
				){
			return "00";
		}
		
		//Bank_Points("01"),//支付方式：纯银行积分
		if((cashPrice == null || "".equals(cashPrice.trim())) && //为空
				(fftPointPrice == null && "".equals(fftPointPrice.trim())) && //为空
						(bankPointPrice != null && !"".equals(bankPointPrice.trim()))){ //不为空
			return "01";
		}
		
		if((cashPrice != null && !"".equals(cashPrice.trim())) && (fftPointPrice != null && !"".equals(fftPointPrice.trim()))){
			return "03";
		}
		
		if((cashPrice != null && !"".equals(cashPrice.trim())) && (bankPointPrice != null && !"".equals(bankPointPrice.trim()))){
			return "04";
		}
		
		return "error";		
	}
	
	/*
	public static String hex(String x) {
		return hex(x.getBytes());
	}*/
}
