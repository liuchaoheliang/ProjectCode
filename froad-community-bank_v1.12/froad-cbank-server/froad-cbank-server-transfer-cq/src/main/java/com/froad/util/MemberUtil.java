package com.froad.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.froad.Constants;
import com.froad.logback.LogCvt;

public class MemberUtil {

	private static Map<String,String> memberMap = new HashMap<String,String>();
	
	public static void load(){
		//读文件
		String fileName = "member";
		FileReader fr = null;
		BufferedReader br = null;
		try {
			String path = System.getProperty(Constants.CONFIG_PATH)+File.separatorChar+fileName+".properties";
			fr = new FileReader(path);
//			resourceBundle = new PropertyResourceBundle(fr);
//			enumeration = resourceBundle.getKeys();
//			while (enumeration.hasMoreElements()){
//				key = enumeration.nextElement();
//				value = resourceBundle.getString(key);
//				memberMap.put(key, value);
//			}
			br = new BufferedReader(fr);
			String line = null;
			while((line=br.readLine())!=null){
				String[] lines = line.split("=");
				if(lines.length == 2)
					memberMap.put(lines[0], lines[1]);
			}
			
		} catch (Exception e){
			LogCvt.error("Error while loading properties file: " + fileName);
			LogCvt.error(e.getMessage(), e);
		} finally{
			try{if(fr!=null){fr.close();}}catch(Exception e){}
			try{if(br!=null){br.close();}}catch(Exception e){}
		}
		
	}
	
	public static String getMemberName(Long memberCode){
		try {
			if(memberCode == null){
				return null;
			}
			String memberInfo = memberMap.get(memberCode.toString());
			if(StringUtils.isNotEmpty(memberInfo)) {
				return memberInfo.split(",")[0];
			} else {
				return null;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			LogCvt.error(e.getMessage(),e);
		}
		return null;
	}
	
	public static String getMemberMobile(Long memberCode){
		try {
			if(memberCode == null){
				return null;
			}
			String memberInfo = memberMap.get(memberCode.toString());
			if(StringUtils.isNotEmpty(memberInfo)) {
				return memberInfo.split(",")[1];
			} else {
				return null;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			//LogCvt.error(e.getMessage(),e);
		}
		return null;
	}
}

