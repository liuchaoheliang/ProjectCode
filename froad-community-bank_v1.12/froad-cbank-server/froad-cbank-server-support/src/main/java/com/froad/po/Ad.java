package com.froad.po;

import java.util.Date;

/**
 * CbAd entity. 
 */
public class Ad implements java.io.Serializable {

	private Long id; //主键ID
	private String clientId;         //客户端ID 
	private String title;//标题
	private Integer type;//类型 0-文本 1-图片 2-flash
	private Date beginTime;//开始时间 
	private Date endTime;//结束时间 
	private String content;//内容
	private String path;//路径
	private String link;//链接地址
	private Boolean isEnabled;//是否启用
	private Boolean isBlankTarge;//是否在新窗打开
	private Integer clickCount;//广告位ID 
	private Long adPositionId;//广告位ID 

	// Constructors

	/** default constructor */
	public Ad() {
	}

	/** minimal constructor */
	public Ad(String clientId, String title, Integer type, Date beginTime, Date endTime, Boolean isEnabled, Integer clickCount, Long adPositionId) {
		this.clientId = clientId;
		this.title = title;
		this.type = type;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.isEnabled = isEnabled;
		this.clickCount = clickCount;
		this.adPositionId = adPositionId;
	}

	/** full constructor */
	public Ad(String clientId, String title, Integer type, Date beginTime, Date endTime, String content, String path, String link, Boolean isEnabled, Boolean isBlankTarge, Integer clickCount, Long adPositionId) {
		this.clientId = clientId;
		this.title = title;
		this.type = type;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.content = content;
		this.path = path;
		this.link = link;
		this.isEnabled = isEnabled;
		this.isBlankTarge = isBlankTarge;
		this.clickCount = clickCount;
		this.adPositionId = adPositionId;
	}

	// Property accessors
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClientId() {
		return this.clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getBeginTime() {
		return this.beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Boolean getIsEnabled() {
		return this.isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Boolean getIsBlankTarge() {
		return this.isBlankTarge;
	}

	public void setIsBlankTarge(Boolean isBlankTarge) {
		this.isBlankTarge = isBlankTarge;
	}

	public Integer getClickCount() {
		return this.clickCount;
	}

	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}

	public Long getAdPositionId() {
		return this.adPositionId;
	}

	public void setAdPositionId(Long adPositionId) {
		this.adPositionId = adPositionId;
	}

}