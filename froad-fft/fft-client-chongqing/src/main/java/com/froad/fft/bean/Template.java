package com.froad.fft.bean;

import java.io.Serializable;

/**
 * 模板
 * @author FQ
 *
 */
public class Template implements Serializable {
	
	/**
	 * 类型
	 */
	public enum Type {

		//页面模板
		page,

		//邮件模板 
		mail

	}

	private String id;
	private Type type;//类型
	private String name;//名称
	private String templatePath;//模板文件路径
	private String staticPath;//静态文件路径
	private String description;//描述
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTemplatePath() {
		return templatePath;
	}
	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}
	public String getStaticPath() {
		return staticPath;
	}
	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
