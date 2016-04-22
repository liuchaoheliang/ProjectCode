package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 银行资源
 * @author ylchu
 *
 */
public class ResourceVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8424724896027645613L;

	private Long resourceId;	//主键
	private String resourceName;	//资源名称
	private Long parentResourceId;	//⽗父级资源ID
	private List<ResourceVo> rList;	//子资源
	private String resourceUrl;	//资源url
	private String resourceIcon;	//资源图标
	private String treePath;	//资源路径
	private Integer orderValue;  //排序
	private String clientId;
	
	public Long getResourceId() {
		return resourceId;
	}
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public Long getParentResourceId() {
		return parentResourceId;
	}
	public void setParentResourceId(Long parentResourceId) {
		this.parentResourceId = parentResourceId;
	}
	public String getResourceUrl() {
		return resourceUrl;
	}
	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}
	public String getResourceIcon() {
		return resourceIcon;
	}
	public void setResourceIcon(String resourceIcon) {
		this.resourceIcon = resourceIcon;
	}
	public String getTreePath() {
		return treePath;
	}
	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}
	public List<ResourceVo> getrList() {
		if(rList == null){
			rList = new ArrayList<ResourceVo>();
		}
		return rList;
	}
	public void setrList(List<ResourceVo> rList) {
		this.rList = rList;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public Integer getOrderValue() {
		return orderValue;
	}
	public void setOrderValue(Integer orderValue) {
		this.orderValue = orderValue;
	}
	
}
