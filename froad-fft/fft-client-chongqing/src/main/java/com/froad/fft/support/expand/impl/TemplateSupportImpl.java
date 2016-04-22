package com.froad.fft.support.expand.impl;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.froad.fft.bean.Template;
import com.froad.fft.bean.Template.Type;
import com.froad.fft.common.Constants;
import com.froad.fft.support.expand.TemplateSupport;

@Service
public class TemplateSupportImpl implements TemplateSupport {
	
	
	public Template get(String id) {
		try {
			File cssXmlFile = new ClassPathResource(Constants.FFT_XML_PATH).getFile();
			Document document = new SAXReader().read(cssXmlFile);
			Element element = (Element) document.selectSingleNode("/fft/template[@id='" + id + "']");
			Template template = getTemplate(element);
			return template;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取模板
	 * @param element
	 * @return
	 */
	private Template getTemplate(Element element) {
		String id = element.attributeValue("id");
		String type = element.attributeValue("type");
		String name = element.attributeValue("name");
		String templatePath = element.attributeValue("templatePath");
		String staticPath = element.attributeValue("staticPath");
		String description = element.attributeValue("description");

		Template template = new Template();
		template.setId(id);
		template.setType(Type.valueOf(type));
		template.setName(name);
		template.setTemplatePath(templatePath);
		template.setStaticPath(staticPath);
		template.setDescription(description);
		return template;
	}


}
