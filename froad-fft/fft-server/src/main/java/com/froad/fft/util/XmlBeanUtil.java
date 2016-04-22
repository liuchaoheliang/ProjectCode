package com.froad.fft.util;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.thoughtworks.xstream.XStream;

/**
 * xml与对象互转
 * @author FQ
 *
 */
public class XmlBeanUtil {

	/**
	 * xml--->bean
	 * @param xmlRoot
	 * @param clazz
	 * @param xml
	 * @return
	 */
	public static Object xmlToBean(String xmlRoot, Class clazz, String xml) {

		XStream xStream = new XStream();
		
		xStream.alias(xmlRoot, clazz);

		Object obj = xStream.fromXML(xml);

		return obj;
	}

	/**
	 * xml--->bean
	 * @param classMap
	 * @param xmlStr
	 * @return
	 */
	public static Object xmlToBean(Map<String, Class> classMap, String xmlStr) {

		XStream xStream = new XStream();
		
		for (Object o : classMap.keySet()) {
			xStream.alias((String) o, classMap.get(o));
		}

		Object obj = xStream.fromXML(xmlStr);
		return obj;
	}

	public static String BeanToXml(Object obj) {
		XStream xStream = new XStream();
		
		String xml = xStream.toXML(obj);
		return xml;
	}
}
