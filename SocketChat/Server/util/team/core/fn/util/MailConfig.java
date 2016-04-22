package team.core.fn.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MailConfig {

	public static String serviceMail = null;
	public static String servicePwd = null;
	private static InputStream inputStream = null;
	private static Properties properties = null;
	
	static{
		inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("mailconfig.properties");
		properties = new Properties();
		try {
			properties.load(inputStream);
			serviceMail = properties.getProperty("SERVICEMAIL");
			servicePwd = properties.getProperty("SERVICEPWD");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String getRegisterMail(String[] msgVal){
		String msg = properties.getProperty("REGISTERMSG");
		for(int i = 0; i < msgVal.length ; i ++){
			msg = msg.replace("{"+i+"}", msgVal[i]);
		}
		return msg;
	}
	public static String getRegisterMailTitle(){
		String title = properties.getProperty("REGISTERTITLE");
		return title;
	}
	
	
	public static String getPwdUpdateMailTitle(){
		String title = properties.getProperty("PWDUPDATETITLE");
		return title;
	}
	public static String PwdUpdateMail(String[] msgVal){
		String msg = properties.getProperty("PWDUPDATEMSG");
		for(int i = 0; i < msgVal.length ; i ++){
			msg = msg.replace("{"+i+"}", msgVal[i]);
		}
		return msg;
	}
}
