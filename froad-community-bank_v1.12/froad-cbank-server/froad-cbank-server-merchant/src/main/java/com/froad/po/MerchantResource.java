package com.froad.po;

import net.sf.oval.constraint.MaxLength;
import net.sf.oval.constraint.Range;
import net.sf.oval.guard.Guarded;

/**
 * CbMerchantResource po. 
 */

@Guarded
public class MerchantResource implements java.io.Serializable {

	// Fields

	
	/**
	 * @Fields serialVersionUID : TODO
	 */
	
	private static final long serialVersionUID = -311894786625781433L;
	private Long id;
	@MaxLength(value = 20, message = "客户端id不能超过{max}个字符")
	private String clientId;
	@MaxLength(value = 32, message = "资源名称不能超过{max}个字符")
	private String name;
	@MaxLength(value = 32, message = "资源图标不能超过{max}个字符")
	private String icon;
	@MaxLength(value = 64, message = "资源URL不能超过{max}个字符")
	private String url;
//	@MaxLength(value = 1, message = "资源类型不能超过{max}个字符")
	@Range(min = 0, max = 3, message = "资源类型必须{min}至{max}范围之内")
	private String type;
	private Long parentId;
	@MaxLength(value = 255, message = "树路径不能超过{max}个字符")
	private String treePath;
	private Boolean isEnabled;
	@MaxLength(value = 255, message = "资源接口不能超过{max}个字符")
	private String api;
	@MaxLength(value = 32, message = "资源编号不能超过{max}个字符")
	private String sn;
	@MaxLength(value = 32, message = "权限编号不能超过{max}个字符")
	private String rightSn;
	private Boolean isParent;
	Integer orderValue;
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
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getTreePath() {
		return treePath;
	}
	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}
	public Boolean getIsEnabled() {
		return isEnabled;
	}
	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	public String getApi() {
		return api;
	}
	public void setApi(String api) {
		this.api = api;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getRightSn() {
		return rightSn;
	}
	public void setRightSn(String rightSn) {
		this.rightSn = rightSn;
	}
	public Boolean getIsParent() {
		return isParent;
	}
	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}
	public Integer getOrderValue() {
		return orderValue;
	}
	public void setOrderValue(Integer orderValue) {
		this.orderValue = orderValue;
	}
	
	
	
	

}