package com.froad.cbank.coremodule.normal.boss.pojo.product;

import java.io.Serializable;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

public class ProductCategoryVoReq implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 主键id */
	private Long id;
	/** 客户端id */
	@NotEmpty(value = "clientId不能为空!")
	private String clientId;
	/** 分类名称 */
	@NotEmpty(value = "分类名称不能为空!")
	private String name;
	/** 上级id */
	private Long parentId;
	/** 启用状态 */
	private Boolean isEnable;
	/** 树形机构 */
	private String treePath;
	/** 图标 */
	@NotEmpty(value = "图标不能为空!")
	private String icoUrl;
	/** 排序 */
	@NotEmpty(value = "排序不能为空!")
	private String orderValue;
	
	/**是否参与类目营销 默认为false   */
	private Boolean isMarket;
	
	/**商品类型  true=精品商品   false=普通商品    */
	private Boolean isMall;
	
	
	
	
	 
	public Boolean getIsMarket() {
		return isMarket;
	}
	public void setIsMarket(Boolean isMarket) {
		this.isMarket = isMarket;
	}
	public Boolean getIsMall() {
		return isMall;
	}
	public void setIsMall(Boolean isMall) {
		this.isMall = isMall;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	
	public Boolean getIsEnable() {
		return isEnable;
	}
	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
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
	public String getOrderValue() {
		return orderValue;
	}
	public void setOrderValue(String orderValue) {
		this.orderValue = orderValue;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
