package com.froad.fft.support.expand.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.froad.fft.bean.Template;
import com.froad.fft.common.Constants;
import com.froad.fft.support.base.AdSupport;
import com.froad.fft.support.expand.StaticSupport;
import com.froad.fft.support.expand.TemplateSupport;
import com.froad.fft.util.FreeMarkerUtil;

@Service
public class StaticSupportImpl implements StaticSupport, ServletContextAware {

	final static Logger logger = Logger.getLogger(StaticSupportImpl.class);

	private ServletContext servletContext;
	
	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Resource
	private TemplateSupport templateSupport;
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public int buildIndex() {
		logger.info("开始静态化首页");
		Template template=templateSupport.get("index");
		return build(template.getTemplatePath(), template.getStaticPath());
	}
	
	public int buildSitemap() {
		
		logger.info("开始静态化Sitemap");
		int buildCount = 0;
		Template sitemapIndexTemplate = templateSupport.get("sitemapIndex");
		Template sitemapTemplate = templateSupport.get("sitemap");
		
		Map<String, Object> model = new HashMap<String, Object>();
//		List<String> staticPaths = new ArrayList<String>();
		
		try {
			model.put("index", 0);
			String templatePath = sitemapTemplate.getTemplatePath();
			String staticPath = FreeMarkerUtil.process(sitemapTemplate.getStaticPath(), model);
			buildCount += build(templatePath, staticPath, model);
//			staticPaths.add(staticPath);
			model.clear();
			
			model.put("staticPaths", staticPath);
			buildCount += build(sitemapIndexTemplate.getTemplatePath(), sitemapIndexTemplate.getStaticPath(), model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buildCount;
	}

	@Override
	public int buildOther() {
		logger.info("开始静态化settings js");
		Template settingsJsTemplate = templateSupport.get("settingsJs");
		return build(settingsJsTemplate.getTemplatePath(), settingsJsTemplate.getStaticPath());
	}

	public int build(String templatePath, String staticPath,Map<String, Object> model) {
		Assert.hasText(templatePath);
		Assert.hasText(staticPath);

		FileOutputStream fileOutputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		Writer writer = null;
		try {
			freemarker.template.Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
			File staticFile = new File(servletContext.getRealPath(staticPath));
			File staticDirectory = staticFile.getParentFile();
			if (!staticDirectory.exists()) {
				staticDirectory.mkdirs();
			}
			fileOutputStream = new FileOutputStream(staticFile);
			outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
			writer = new BufferedWriter(outputStreamWriter);
			template.process(model, writer);
			writer.flush();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(writer);
			IOUtils.closeQuietly(outputStreamWriter);
			IOUtils.closeQuietly(fileOutputStream);
		}
		return 0;
	}

	public int build(String templatePath, String staticPath) {
		return build(templatePath, staticPath, null);
	}
}
