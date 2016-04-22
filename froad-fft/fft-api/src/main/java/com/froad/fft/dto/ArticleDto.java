package com.froad.fft.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章
 * @author FQ
 *
 */
public class ArticleDto implements Serializable{
	
	private Long id;
	private Date createTime;
	private String title;// 标题
	private String author;// 作者
	private String content;// 内容
	private String seoTitle;//页面标题
	private String seoKeywords;//页面关键词
	private String seoDescription;//页面描述
	private Boolean isPublication;// 是否发布
	private Boolean isTop;// 是否置顶
	private Boolean isRecommend;// 是否为推荐文章
	private Integer pageCount;// 文章页数
	private String htmlPath;// HTML静态文件路径
	private Integer hits;// 点击数
	private Long articleCategoryId;//文章分类ID
	private Long clientId;//所属客户端
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public Boolean getIsPublication() {
		return isPublication;
	}
	public void setIsPublication(Boolean isPublication) {
		this.isPublication = isPublication;
	}
	public Boolean getIsTop() {
		return isTop;
	}
	public void setIsTop(Boolean isTop) {
		this.isTop = isTop;
	}
	public Boolean getIsRecommend() {
		return isRecommend;
	}
	public void setIsRecommend(Boolean isRecommend) {
		this.isRecommend = isRecommend;
	}
	public Integer getPageCount() {
		return pageCount;
	}
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	public String getHtmlPath() {
		return htmlPath;
	}
	public void setHtmlPath(String htmlPath) {
		this.htmlPath = htmlPath;
	}
	public Integer getHits() {
		return hits;
	}
	public void setHits(Integer hits) {
		this.hits = hits;
	}
	public Long getArticleCategoryId() {
		return articleCategoryId;
	}
	public void setArticleCategoryId(Long articleCategoryId) {
		this.articleCategoryId = articleCategoryId;
	}
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
}
