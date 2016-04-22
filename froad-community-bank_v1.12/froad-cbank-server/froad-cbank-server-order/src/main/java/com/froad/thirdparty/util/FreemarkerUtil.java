package com.froad.thirdparty.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

import com.froad.Constants;
import com.froad.logback.LogCvt;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Copyright © 2015 F-Road. All rights reserved.
 * @ClassName: FreemarkerUtil
 * @Description: 配置不使用Spring托管的Freemarker工具
 * @Author: zhaoxiaoyao@f-road.com.cn
 * @Date: 2015年3月19日 下午12:55:22
 */
public class FreemarkerUtil {

	private static Configuration cfg;

	private static String ENCODING = "utf-8";
	
	static {
		
		Properties props = new Properties();
		String configPath = System.getProperty(Constants.CONFIG_PATH);
		try {
//			props.load(FreemarkerUtil.class.getClassLoader().getResourceAsStream(System.getProperty(Constants.CONFIG_PATH)+File.separatorChar+"thirdparty.properties"));
		    FileReader fr = new FileReader(new File(configPath+"/thirdparty.properties"));
            props.load(fr);
		} catch (FileNotFoundException e) {
			LogCvt.error("加载配置文件信息错误", e);
		} catch (IOException e) {
			LogCvt.error("加载配置文件信息错误", e);
		}
		
		String basePath = props.getProperty("FREEMARKER_BASE_TEMP_URI","./config") + "/template/thirdparty/";
		//--配置全局的cfg实例， 全局的参数只需要初始化一次
		cfg = new Configuration();
		cfg.setDefaultEncoding(ENCODING);
		try {
		    cfg.setDirectoryForTemplateLoading(new File(basePath));
		} catch (IOException e) {
			LogCvt.error("初始化freemarker 配置失败", e);
		}
		cfg.setObjectWrapper(new DefaultObjectWrapper());
	}
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: templateProcess
	 * @Description: 处理数据和模块生成字符串结果体
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param ftlPath
	 * @param model
	 * @return
	 * @Return: String
	 */
	public static String templateProcess(String ftlPath,Map<String, Object> model){
		try {
			Template temp = cfg.getTemplate(ftlPath);
			StringWriter sw = new StringWriter();
			temp.process(model, sw);
			return sw.toString();
		} catch (IOException e) {
			LogCvt.error("freemarker处理模块与数据失败", e);
		} catch (TemplateException e) {
			LogCvt.error("freemarker处理模块与数据失败", e);
		}
		return null;
	}
}
