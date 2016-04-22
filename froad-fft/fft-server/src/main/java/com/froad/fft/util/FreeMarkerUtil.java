package com.froad.fft.util;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 模板工具类
 * @author FQ
 *
 */
@Component
public class FreeMarkerUtil {
	
	final static Logger logger = Logger.getLogger(FreeMarkerUtil.class);
	
	@Resource
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@Resource
	private Configuration configuration;
	
	public String getContent(String templateName, Map<String, Object> model) {
		try {
			Template t = freeMarkerConfigurer.getConfiguration().getTemplate(
					templateName);
			return FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			try {
				Template t = configuration.getTemplate(templateName);
				return FreeMarkerTemplateUtils.processTemplateIntoString(t,
						model);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(ex);
			}
		}

		return null;
	}
}
