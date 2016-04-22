/**
 * <p>Project: cbank</p>
 * <p>module: coremodule</p>
 * <p>@version: Copyright © 2008 F-Road All Rights Reserved</p>
 */
package com.froad.cbank.coremodule.module.normal.user.pojo;

/**
 * <p>标题: —— 商户分类POJO</p>
 * <p>说明: —— 简要描述职责、应用范围、使用注意事项等</p>
 * <p>创建时间：2015-4-11下午4:02:53</p>
 * <p>作者: 高峰</p>
 * <p>版本: 1.0</p>
 * <p>修订历史:（历次修订内容、修订人、修订时间等） </p>
 */
public class MerchantCategoryPojo {
	private Long id;
	private Long parentId;
	private String name;
	private String treePath;
	private String icoUrl;
	private Boolean nextFlag;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTreePath() {
		return treePath;
	}
	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}
	public String getIcoUrl() {
		return icoUrl;
	}
	public void setIcoUrl(String icoUrl) {
		this.icoUrl = icoUrl;
	}
	public Boolean getNextFlag() {
		return nextFlag;
	}
	public void setNextFlag(Boolean nextFlag) {
		this.nextFlag = nextFlag;
	}
	
}
