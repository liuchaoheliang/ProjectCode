package com.froad.po;

import java.util.Date;

/**
 * 客户端启动页po
 * CbTerminalStart entity. 
 */
public class TerminalStart implements java.io.Serializable {

	private Long id; 				//主键ID
	private Date createTime;		//创建时间
	private String clientId;        //客户端ID 
	private String appType;			//app类型  1-个人 2-商户
	private String terminalType;	//终端类型 1-pc 2-andriod 3-ios
	private String imageId;			//图片标识Id
	private String imagePath;		//图片路径URL
	private Date beginTime;			//开始时间 
	private Date endTime;			//结束时间 
	private Boolean isEnabled;		//是否可用
	
	
	
	public TerminalStart() {
	}
	public TerminalStart(Long id, Date createTime, String clientId,
			String appType, String terminalType, String imageId,
			String imagePath, Date beginTime, Date endTime, Boolean isEnabled) {
		this.id = id;
		this.createTime = createTime;
		this.clientId = clientId;
		this.appType = appType;
		this.terminalType = terminalType;
		this.imageId = imageId;
		this.imagePath = imagePath;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.isEnabled = isEnabled;
	}
	
	
	
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
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public String getTerminalType() {
		return terminalType;
	}
	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Boolean getIsEnabled() {
		return isEnabled;
	}
	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	
	
}