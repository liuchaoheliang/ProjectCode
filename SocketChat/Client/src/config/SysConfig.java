package config;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SysConfig {

	public static String PORT;
	public static String IP;	
	public static String LOCAL;
	public static String ADDRESS;
	
	
	
	
	
	/*=======================================================*/
	private static InputStream inputStream = null;
	private static Properties properties = null;
	
	
	static{
		inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("sys.properties");
		properties = new Properties();
		
		try {
			properties.load(inputStream);
			PORT = properties.getProperty("PORT");
			IP=properties.getProperty("IP");
			LOCAL=properties.getProperty("LOCAL");
			ADDRESS=properties.getProperty("ADDRESS");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
