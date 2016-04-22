package com.froad.action.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.views.freemarker.FreemarkerManager;

import com.froad.baseAction.BaseActionSupport;
import com.froad.bean.DynamicConfig;
import com.froad.util.TemplateConfigUtil;

/** 
 * @author FQ 
 * @date 2013-3-2 上午11:47:30
 * @version 1.0
 * 动态模板
 */
public class TemplateDynamicAction extends BaseActionSupport {
	
	private FreemarkerManager freemarkerManagerConfig;

	private DynamicConfig dynamicConfig;
	private String templateFileContent;
	
	/**
	 * 测试
	 * 
	 * 读取指定模板  http://localhost:8080/communityBusiness_client/template_index_change_edit.action?dynamicConfig.name=indexChange
	 *
	 * 更新指定模板 参数（dynamicConfig.name和templateFileContent）
	 *
	 */
	public String input(){
		
		return "input";
	}
	
	// 编辑
	public String edit() {
		log.info("动态模板 编辑");
		dynamicConfig = TemplateConfigUtil.getDynamicConfig(dynamicConfig.getName());
		templateFileContent = TemplateConfigUtil.readTemplateFileContent(dynamicConfig);
		
		return ajaxText(templateFileContent);
	}
	
	// 更新
	public String update() {
		log.info("动态模板 更新");
		dynamicConfig = TemplateConfigUtil.getDynamicConfig(dynamicConfig.getName());
		TemplateConfigUtil.writeTemplateFileContent(dynamicConfig, templateFileContent);
		try {
			ServletContext servletContext = ServletActionContext.getServletContext();
			freemarkerManagerConfig.getConfiguration(servletContext).clearTemplateCache();
		} catch (Exception e){
			log.error("Update TemplateDynamic:",e);
			return ajaxJsonErrorMessage("失败");
		}
		return ajaxJsonSuccessMessage("成功");
	}
	
	public FreemarkerManager getFreemarkerManagerConfig() {
		return freemarkerManagerConfig;
	}

	public void setFreemarkerManagerConfig(FreemarkerManager freemarkerManagerConfig) {
		this.freemarkerManagerConfig = freemarkerManagerConfig;
	}

	public DynamicConfig getDynamicConfig() {
		return dynamicConfig;
	}

	public void setDynamicConfig(DynamicConfig dynamicConfig) {
		this.dynamicConfig = dynamicConfig;
	}

	public String getTemplateFileContent() {
		return templateFileContent;
	}

	public void setTemplateFileContent(String templateFileContent) {
		this.templateFileContent = templateFileContent;
	}
}
