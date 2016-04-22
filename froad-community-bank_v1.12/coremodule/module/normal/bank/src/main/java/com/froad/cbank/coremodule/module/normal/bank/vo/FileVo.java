package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 图片信息实体类
 * @author Administrator
 *
 */
public class FileVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1234567890L;
	
	private String title;     //图片标题
	private String source;    //原图
	private String large;     //大图
	private String medium;    //中图
	private String thumbnail; //小图
	private boolean isDefault;//默认查询显示图片
	private String id;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getLarge() {
		return large;
	}
	public void setLarge(String large) {
		this.large = large;
	}
	public String getMedium() {
		return medium;
	}
	public void setMedium(String medium) {
		this.medium = medium;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public boolean isDefault() {
		return isDefault;
	}
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
