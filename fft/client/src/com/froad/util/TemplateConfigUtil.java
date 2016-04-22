package com.froad.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.froad.bean.DynamicConfig;
import com.froad.bean.SmsConfig;
import com.froad.sms.impl.SmsServiceImpl;

/** 
 * @author FQ 
 * @date 2013-2-28 下午04:52:17
 * @version 1.0
 * 
 * 模板配置
 */
public class TemplateConfigUtil {
	
	public static final String CONFIG_FILE_NAME = "template.xml";// 模板配置文件名称

	public static final String SMS_CONFIG_LIST_CACHE_KEY = "smsConfigList";// 短信模板配置缓存Key 
	
	private static final Logger logger = Logger.getLogger(TemplateConfigUtil.class);
	
	/**
	 * 获取短信模板配置集合
	 * @return
	 */
	public static List<SmsConfig> getSmsConfigList() {
//		List<SmsConfig> smsConfigList = (List<SmsConfig>) OsCacheConfigUtil.getFromCache(SMS_CONFIG_LIST_CACHE_KEY);
//		if (smsConfigList != null) {
//			return smsConfigList;
//		}
		List<SmsConfig> smsConfigList=new ArrayList<SmsConfig>();
		
		
		File configFile = null;
		Document document = null;
		try {
			
//			String configFilePath = new File(Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath()).getParent() + "/templates/system/" + CONFIG_FILE_NAME;
//			String configFilePath =new File(TemplateConfigUtil.class.getResource("/").toURI()) +"/"+ CONFIG_FILE_NAME;
			String configFilePath =Command.TEMPLATE_PATH+"templates/system/" + CONFIG_FILE_NAME;
			logger.info("获取短信模版路径："+configFilePath);
			configFile = new File(configFilePath);
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(configFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Element smsConfigElement = (Element)document.selectSingleNode("/fenfentong/smsConfig");
		Iterator<Element> iterator = smsConfigElement.elementIterator();

//		smsConfigList = new ArrayList<SmsConfig>();
		while(iterator.hasNext()) {
	    	Element element = (Element)iterator.next();
	    	String description = element.element("description").getTextTrim();
	    	String templateFilePath = element.element("templateFilePath").getTextTrim();
	    	SmsConfig smsConfig = new SmsConfig();
	    	smsConfig.setName(element.getName());
	    	smsConfig.setDescription(description);
	    	smsConfig.setTemplateFilePath(templateFilePath);
	    	smsConfigList.add(smsConfig);
	    }
//		OsCacheConfigUtil.putInCache(SMS_CONFIG_LIST_CACHE_KEY, smsConfigList);
		return smsConfigList;
	}
	
	/**
	 * 根据短信模板配置名称获取SmsConfig对象
	 * @param name
	 * @return
	 */
	public static SmsConfig getSmsConfig(String name) {
		Document document = null;
		try {
//			String configFilePath = new File(Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath()).getParent() + "/templates/system/" + CONFIG_FILE_NAME;
//			String configFilePath =new File(TemplateConfigUtil.class.getResource("/").toURI()) +"/"+ CONFIG_FILE_NAME;
			String configFilePath =Command.TEMPLATE_PATH+"templates/system/" + CONFIG_FILE_NAME;
			logger.info("获取短信模版路径："+configFilePath);
			File configFile = new File(configFilePath);
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(configFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Element element = (Element)document.selectSingleNode("/fenfentong/smsConfig/" + name);
		String description = element.element("description").getTextTrim();
		String templateFilePath = element.element("templateFilePath").getTextTrim();
    	SmsConfig smsConfig = new SmsConfig();
		smsConfig.setName(element.getName());
		smsConfig.setDescription(description);
		smsConfig.setTemplateFilePath(templateFilePath);
		return smsConfig;
	}
	
	/**
	 * 根据SmsConfig对象读取模板文件内容
	 * @param smsConfig
	 * @return
	 */
	public static String readTemplateFileContent(SmsConfig smsConfig) {
		ServletContext servletContext = ServletActionContext.getServletContext();
		File templateFile = new File(servletContext.getRealPath(smsConfig.getTemplateFilePath()));
		String templateFileContent = null;
		try {
			templateFileContent = FileUtils.readFileToString(templateFile, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return templateFileContent;
	}
	
	/**
	 * 写入模板文件内容
	 * 
	 */
	public static String writeTemplateFileContent(SmsConfig smsConfig, String templateFileContent) {
		ServletContext servletContext = ServletActionContext.getServletContext();
		File templateFile = new File(servletContext.getRealPath(smsConfig.getTemplateFilePath()));
		try {
			FileUtils.writeStringToFile(templateFile, templateFileContent, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return templateFileContent;
	}
	
	
	
	/**
	 * 根据动态模板配置名称获取DynamicConfig对象
	 * 
	 * @return DynamicConfig对象
	 */
	public static DynamicConfig getDynamicConfig(String name) {
		Document document = null;
		try {
//			String configFilePath=new File(ClassLoader.getSystemResource("").toURI()).getParent()+ "/templates/system/" + CONFIG_FILE_NAME;
//			String configFilePath =new File(TemplateConfigUtil.class.getResource("/").toURI()) +"/"+ CONFIG_FILE_NAME;
			String configFilePath =Command.TEMPLATE_PATH+"templates/system/" + CONFIG_FILE_NAME;
			logger.info("获取短信模版路径："+configFilePath);
			File configFile = new File(configFilePath);
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(configFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Element element = (Element)document.selectSingleNode("/fenfentong/dynamicConfig/" + name);
		String description = element.element("description").getTextTrim();
		String templateFilePath = element.element("templateFilePath").getTextTrim();
    	DynamicConfig dynamicConfig = new DynamicConfig();
    	dynamicConfig.setName(element.getName());
    	dynamicConfig.setDescription(description);
    	dynamicConfig.setTemplateFilePath(templateFilePath);
		return dynamicConfig;
	}
	
	/**
	 * 根据DynamicConfig对象读取模板文件内容
	 * 
	 * @return 模板文件内容
	 */
	public static String readTemplateFileContent(DynamicConfig dynamicConfig) {
		ServletContext servletContext = ServletActionContext.getServletContext();
		File templateFile = new File(servletContext.getRealPath(dynamicConfig.getTemplateFilePath()));
		String templateFileContent = null;
		try {
			templateFileContent = FileUtils.readFileToString(templateFile, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return templateFileContent;
	}
	
	/**
	 * 写入模板文件内容
	 * 
	 */
	public static String writeTemplateFileContent(DynamicConfig dynamicConfig, String templateFileContent) {
		ServletContext servletContext = ServletActionContext.getServletContext();
		File templateFile = new File(servletContext.getRealPath(dynamicConfig.getTemplateFilePath()));
		try {
			FileUtils.writeStringToFile(templateFile, templateFileContent, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return templateFileContent;
	}

	
	
	
	 public static void main(String[]args)throws Exception{ 
//		 System.out.println(new File(Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath()).getParent());
//	      System.out.println(Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath()); 
//	      System.out.println(TemplateConfigUtil.class.getClassLoader().getResource("")); 
//	      System.out.println(ClassLoader.getSystemResource("")); 
//	      System.out.println(new File(ClassLoader.getSystemResource("").toURI()).getParent()); 
//	      System.out.println(TemplateConfigUtil.class.getResource("/"));//Class文件所在路径 
//	      System.out.println(new File("/").getAbsolutePath()); 
//	      System.out.println(System.getProperty("user.dir")); 
//		 System.out.println(TemplateConfigUtil.class.getResource("/").toURI());
	      } 

}
