package com.froad.CB.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.lowagie.text.pdf.hyphenation.TernaryTree.Iterator;

public class UserCommand {
	private static InputStream inputStream = null;
	private static Properties properties = null;
	private static Map<Integer, String> ReCodeMap =new HashMap<Integer, String>();
	static{
		inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("config/froadServerAPI_Error.properties");
		properties = new Properties();		
		
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getResMsg(Integer key){
		if(key==null){
			return "操作成功";
		}else{
			return properties.getProperty(key.toString());
		}
		
	}
	
	
	public static void main(String[] args) {
//		Set<Integer> se=ReCodeMap.keySet();
//		java.util.Iterator<Integer> it=se.iterator();
//		int n=0;
//		while(it.hasNext()){
//			n++;
//			System.out.println(new UserCommand().getResMsg(it.next()));
//		}
//		System.out.println(n);
//		System.out.println(new UserCommand().getResMsg(230));
//		try {
//			properties.load(inputStream);
//			System.out.println(properties.getProperty("230"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		System.out.println(getResMsg(2009));
	}
	
}
