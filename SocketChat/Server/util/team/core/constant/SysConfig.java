package team.core.constant;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SysConfig {

	public static int PORT;
	public static String VERSION;	
	
	
	
	
	
	/*=======================================================*/
	private static InputStream inputStream = null;
	private static Properties properties = null;
	
	static{
		inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("sys.properties");
		properties = new Properties();
		
		try {
			properties.load(inputStream);
			PORT = Integer.parseInt(properties.getProperty("PORT"));
			VERSION = properties.getProperty("VERSION");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
