package com.froad.cbank.coremodule.normal.boss.pojo.merchant;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;


/**
 * 
 * 类描述:新增（修改）商户vo
 * 
 * @Copyright www.f-road.com.cn Corporation 2015
 * @author "chenzhangwei"
 * @time 2015年9月17日 上午9:36:15
 * @email "chenzhangwei@f-road.com.cn"
 */
public class AddMerchantCategoryVo {

	/**
	 * 分类ID
	 */
	private String id; // required
	/**
	 * 客户端ID
	 */
	
	@NotEmpty(value = "客户端id不能为空")
	private String clientId; // required
	/**
	 * 分类名称
	 */
	@NotEmpty(value = "商户分类名称不能为空")
	private String name; // required
	/**
	 * 分类ID树
	 */
	/*@NotEmpty(value = "树路径不能为空")*/
	private String treePath; // required
	/**
	 * 上级ID
	 */
	private Long parentId; // required
	/**
	 * 是否禁用
	 */

	@NotEmpty(value = "是否禁用不能为空")
	private Boolean isEnable; // required
	/**
	 * 图标URL
	 */
	@NotEmpty(value = "分类图标不能为空")
	private String icoUrl; // required
	/**
	 * 排序
	 */
	@NotEmpty(value = "排序不能为空")
	private Integer orderValue; // required
	
	private String enable;
	
	private String userId;
	private String token;
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	/*public Boolean isIsEnable() {
		return isEnable;
	}
	public void setIsEnable(String isEnable) {
		this.isEnable ="1".equals(isEnable) ? true : false;
	}*/
	
	public String getIcoUrl() {
		return icoUrl;
	}
	public Boolean getIsEnable() {
		return isEnable;
	}
	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}
	public void setIcoUrl(String icoUrl) {
		this.icoUrl = icoUrl;
	}
	public Integer getOrderValue() {
		return orderValue;
	}
	public void setOrderValue(Integer orderValue) {
		this.orderValue = orderValue;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
		this.isEnable ="1".equals(enable) ? true : false;
	}
	
	
	
	
	
}
