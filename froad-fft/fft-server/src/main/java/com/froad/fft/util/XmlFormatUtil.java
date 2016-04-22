package com.froad.fft.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * xml格式化
 * @author FQ
 *
 */
public class XmlFormatUtil {

	private static Logger logger = Logger.getLogger(XmlFormatUtil.class);
	
	public static String format(String str) {
		SAXReader reader = new SAXReader();
		StringReader in = new StringReader(str);
		Document doc;
		StringWriter out = new StringWriter();
		try {
			doc = reader.read(in);
			OutputFormat formater = OutputFormat.createPrettyPrint();
			formater.setEncoding("UTF-8");
			XMLWriter writer = new XMLWriter(out, formater);
			writer.write(doc);
			writer.close();
		} catch (DocumentException e) {
			logger.error("格式化xml:" + str + ",发生异常", e);
		} catch (IOException e) {
			logger.error("格式化xml:" + str + ",发生异常", e);
		}		
		return out.toString();
	}

}
