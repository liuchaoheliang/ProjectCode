package com.froad.util;

import java.io.File;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * XStream转换工具类
 * @ClassName: XStreamHandle 
 * @Description: TODO
 * @author: huangyihao
 * @date: 2015年4月24日
 */
public class XStreamHandle {
	
	/**
	 * 把xml文件内容转换成bean对象
	 * @Title: toBean 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年4月24日
	 * @modify: froad-huangyihao 2015年4月24日
	 * @param xml
	 * @param cls
	 * @return
	 * @throws
	 */
	public static <T> T toBean(File xml, Class<T> cls) {
        XStream xstream = new XStream(new DomDriver());
        xstream.processAnnotations(cls);
        T t = (T) xstream.fromXML(xml);
        return t;
    }
}