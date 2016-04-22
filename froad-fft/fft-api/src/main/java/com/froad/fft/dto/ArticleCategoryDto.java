package com.froad.fft.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章分类
 * 
 * @author FQ
 *
 */
public class ArticleCategoryDto extends TreeDto{


	private String name;// 分类名称
	private String seoTitle;//页面标题
	private String seoKeywords;//页面关键词 
	private String seoDescription;//页面描述
	private Integer orderValue;// 排序

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSeoTitle() {
		return seoTitle;
	}
	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}
	public String getSeoKeywords() {
		return seoKeywords;
	}
	public void setSeoKeywords(String seoKeywords) {
		this.seoKeywords = seoKeywords;
	}
	public String getSeoDescription() {
		return seoDescription;
	}
	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}
	public Integer getOrderValue() {
		return orderValue;
	}
	public void setOrderValue(Integer orderValue) {
		this.orderValue = orderValue;
	}
	
}
