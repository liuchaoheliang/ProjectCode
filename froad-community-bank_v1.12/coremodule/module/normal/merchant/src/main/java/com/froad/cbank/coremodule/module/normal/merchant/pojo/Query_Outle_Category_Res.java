/**
 * Project Name:coremodule-merchant
 * File Name:Query_Outle_Category_Res.java
 * Package Name:com.froad.cbank.coremodule.module.normal.merchant.pojo
 * Date:2015年11月4日上午10:40:14
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.merchant.pojo;
/**
 * ClassName:Query_Outle_Category_Res
 * Reason:	 TODO ADD REASON.
 * Date:     2015年11月4日 上午10:40:14
 * @author   mi
 * @version  
 * @see 	 
 */
public class Query_Outle_Category_Res {
	
	/**
	   * 主键ID
	   */
	  public long id; 
	  /**
	   * 客户端ID
	   */
	  public String clientId; 
	  /**
	   * 上级分类ID
	   */
	  public long parentId; 
	  /**
	   * 分类名称
	   */
	  public String name; 
	  /**
	   * 树路径
	   */
	  public String treePath; 
	  /**
	   * 商户分类图标
	   */
	  public String icoUrl; 
	  /**
	   * 是否删除
	   */
	  public boolean isDelete; 
	  /**
	   * 排序
	   */
	  public int orderValue; 
	  /**
	   * 是否存在下级标志  有=true 无=false
	   */
	  public boolean nextFlag; 
	  
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
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
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
	public boolean isDelete() {
		return isDelete;
	}
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	public int getOrderValue() {
		return orderValue;
	}
	public void setOrderValue(int orderValue) {
		this.orderValue = orderValue;
	}
	public boolean isNextFlag() {
		return nextFlag;
	}
	public void setNextFlag(boolean nextFlag) {
		this.nextFlag = nextFlag;
	}
}
