package com.froad.db.bps.entity;

import java.util.Date;

public class Resource {
	
	private Long resourceId;		//资源id
	private Date createTime;		//创建时间
	private Date updateTime;		//最后修改时间
	private String resourceCode;	//资源编码(RESOURCE_TYPE=B时必须)
	private String resourceName;	//资源名称
	private String resourceType;	//M:菜单B:按钮
	private String fatherResource;	//父级资源id
	private String isleaf;			//是否叶节点Y是N否
	private String menuIndex;		//菜单排序
	private String status;			//0.不可用1.可用
	private String menuUrl;			//菜单链接
	private String menuPicture;		//菜单图片id??(1级,2级,3级)或(叶节点,分支节点)
	private String menuLevel;		//菜单级别(1级,2级,3级,4级)
	private String isAudit;			//
	private String auditTatil;		//
	public Long getResourceId() {
		return resourceId;
	}
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getResourceCode() {
		return resourceCode;
	}
	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public String getFatherResource() {
		return fatherResource;
	}
	public void setFatherResource(String fatherResource) {
		this.fatherResource = fatherResource;
	}
	public String getIsleaf() {
		return isleaf;
	}
	public void setIsleaf(String isleaf) {
		this.isleaf = isleaf;
	}
	public String getMenuIndex() {
		return menuIndex;
	}
	public void setMenuIndex(String menuIndex) {
		this.menuIndex = menuIndex;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	public String getMenuPicture() {
		return menuPicture;
	}
	public void setMenuPicture(String menuPicture) {
		this.menuPicture = menuPicture;
	}
	public String getMenuLevel() {
		return menuLevel;
	}
	public void setMenuLevel(String menuLevel) {
		this.menuLevel = menuLevel;
	}
	public String getIsAudit() {
		return isAudit;
	}
	public void setIsAudit(String isAudit) {
		this.isAudit = isAudit;
	}
	public String getAuditTatil() {
		return auditTatil;
	}
	public void setAuditTatil(String auditTatil) {
		this.auditTatil = auditTatil;
	}
	
	
}
