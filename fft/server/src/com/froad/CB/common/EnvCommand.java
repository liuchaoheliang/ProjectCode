package com.froad.CB.common;

import java.io.InputStream;
import java.util.Properties;


	/**
	 * 类描述：项目的环境常量
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2013 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Jun 8, 2013 12:51:52 PM 
	 */
public class EnvCommand {

	public static String ENV_PATH;
	
	static{
		InputStream inputStream=EnvCommand.class.getClassLoader().getResourceAsStream("env.properties");
		try {
			Properties props=new Properties();
			props.load(inputStream);
			inputStream.close();
			ENV_PATH=props.getProperty("env.path");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		System.out.println(ENV_PATH);
	}
}
