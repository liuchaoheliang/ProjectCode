package com.froad.cbank.coremodule.module.normal.merchant.pojo;

public class Query_Product_Category_Res {
	  /**
	   * id
	   */
	  public long id; // required
	  /**
	   * 客户端id
	   */
	  public String clientId; // required
	  /**
	   * name
	   */
	  public String name; // required
	  /**
	   * 树路径 空格间隔
	   */
	  public String treePath; // required
	  /**
	   * 父节点id
	   */
	  public long parentId; // required
	  /**
	   * 小图标
	   */
	  public String icoUrl; // required
	  /**
	   * 排序
	   */
	  public short orderValue; // required
	  /**
	   * 是否有子分类
	   */
	  public boolean isHaveChild; // required
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
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
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public String getIcoUrl() {
		return icoUrl;
	}
	public void setIcoUrl(String icoUrl) {
		this.icoUrl = icoUrl;
	}
	public short getOrderValue() {
		return orderValue;
	}
	public void setOrderValue(short orderValue) {
		this.orderValue = orderValue;
	}
	public boolean isHaveChild() {
		return isHaveChild;
	}
	public void setHaveChild(boolean isHaveChild) {
		this.isHaveChild = isHaveChild;
	}
	  
}
