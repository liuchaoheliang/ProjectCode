package com.froad.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.froad.CB.common.SpringContextUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


	/**
	 * 类描述：操作freemarker的工具类
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2014 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Feb 28, 2014 10:14:49 AM 
	 */
public class FreemarkerUtils {
	
	private static final Logger logger = Logger.getLogger(FreemarkerUtils.class);
	

	private FreemarkerUtils() {
	}

	public static String process(String template, Map<String, ?> model) throws IOException, TemplateException {
		Configuration configuration = null;
		FreeMarkerConfigurer freeMarkerConfigurer = (FreeMarkerConfigurer)SpringContextUtil.getBean("freeMarkerConfigurer");
		if (freeMarkerConfigurer != null) {
			configuration = freeMarkerConfigurer.getConfiguration();
		}
		return process(template, model, configuration);
	}
	
	
	public static String process(String template, Map<String, ?> model, Configuration configuration) throws IOException, TemplateException {
		if (template == null) {
			return null;
		}
		if (configuration == null) {
			configuration = new Configuration();
		}
		StringWriter out = new StringWriter();
		new Template("template", new StringReader(template), configuration).process(model, out);
		return out.toString();
	}

	
	/**
	  * 方法描述：将模板文件转换成字符串
	  * @param: template 模板文件
	  * @param: rootMap 动态数据
	  * @return: String
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2013-10-15 下午3:41:36
	  */
	public static String process(File template,Map<String, Object> rootMap){
		if(template==null||!template.exists()){
			logger.info("文件不存在");
			return null;
		}
		try {
			FileInputStream in=new FileInputStream(template);
			BufferedReader reader=new BufferedReader(new InputStreamReader(in,"UTF-8"));
			Template tmp=new Template("template", reader,null);
			StringWriter out=new StringWriter();
			tmp.process(rootMap, out);
			out.close();
			reader.close();
			in.close();
			return out.toString();
		} catch (IOException e) {
			logger.error("模板读取出现异常",e);
		}catch (Exception e) {
			logger.error("模板读取出现异常",e);
		}
		return null;
	}
	
	
	/**
	  * 方法描述：从classpath中读取模板文件，设置动态数据后转换成字符串
	  * @param: template 模板文件
	  * @param: rootMap 动态数据
	  * @return: String
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2013-10-15 下午3:41:36
	  */
	public static String processFromClassPath(String path,Map<String, ?> rootMap){
		try {
			InputStream input = FreemarkerUtils.class.getClassLoader().getResourceAsStream(path);
			if(input==null){
				logger.error("文件不存在，传入的classpath是："+path);
				return null;
			}
			BufferedReader reader=new BufferedReader(new InputStreamReader(input,"UTF-8"));
			Template tmp=new Template("template", reader,null);
			StringWriter out=new StringWriter();
			tmp.process(rootMap, out);
			out.close();
			reader.close();
			input.close();
			return out.toString();
		} catch (IOException e) {
			logger.error("模板读取出现异常",e);
		}catch (Exception e) {
			logger.error("模板读取出现异常",e);
		}
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println("包名："+FreemarkerUtils.class.getPackage().getName().replaceAll("\\.", "/"));
		InputStream input = FreemarkerUtils.class.getClassLoader().getResourceAsStream("com/froad/CB/thirdparty/withdraw_points.ftl");
		System.out.println("输入流："+input);
	}
}
