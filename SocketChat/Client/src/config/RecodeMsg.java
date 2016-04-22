package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class RecodeMsg {
	private static InputStream inputStream = null;
	private static Properties properties = null;
	private static Map<String, String> ReCodeMap =new HashMap<String, String>();
	
	static{
		inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("response.properties");
		properties = new Properties();
		
		
		
		try {
			properties.load(inputStream);
			ReCodeMap.put("200", properties.getProperty("200"));
			ReCodeMap.put("404", properties.getProperty("404"));
			ReCodeMap.put("500", properties.getProperty("500"));
			ReCodeMap.put("0001", properties.getProperty("0001"));
			ReCodeMap.put("0002", properties.getProperty("0002"));
			ReCodeMap.put("0003", properties.getProperty("0003"));
			ReCodeMap.put("0004", properties.getProperty("0004"));
			ReCodeMap.put("0005", properties.getProperty("0005"));
			ReCodeMap.put("0006", properties.getProperty("0006"));
			ReCodeMap.put("0007", properties.getProperty("0007"));
			ReCodeMap.put("0008", properties.getProperty("0008"));
			ReCodeMap.put("0009", properties.getProperty("0009"));
			ReCodeMap.put("0010", properties.getProperty("0010"));
			ReCodeMap.put("0011", properties.getProperty("0011"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getReCodeMessage(String key){
		return ReCodeMap.get(key);
	}
//	public static void main(String[] args) {
//		System.out.println(new RecodeConfig().ReCodeMap.get("0003"));
//	}
	
}
