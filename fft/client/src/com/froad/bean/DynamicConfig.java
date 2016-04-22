package com.froad.bean;
/** 
 * @author FQ 
 * @date 2013-3-2 上午11:38:23
 * @version 1.0
 * 动态模板配置
 */
public class DynamicConfig {
	
	private String name;// 配置名称
	private String description;// 描述
	private String templateFilePath;// 模板文件路径

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTemplateFilePath() {
		return templateFilePath;
	}

	public void setTemplateFilePath(String templateFilePath) {
		this.templateFilePath = templateFilePath;
	}
}
