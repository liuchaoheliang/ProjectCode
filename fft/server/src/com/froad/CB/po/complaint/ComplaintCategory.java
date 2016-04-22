package com.froad.CB.po.complaint;

import java.io.Serializable;

public class ComplaintCategory implements Serializable{

	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public static final String PATH_SEPARATOR = ",";// 树路径分隔符
	
	private Integer id;
	private String name;//分类名称
	private String description;//描述
	private String orderList;//排序
	private String path;//树路径 ，分割 如：一级ID,二级ID
	private String parentId;//父类ID
	private String state;//状态(10-创建，20-录入，30-启用，40-停用，50-删除)
	private String createTime;
	private String updateTime;
	private String remark;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOrderList() {
		return orderList;
	}
	public void setOrderList(String orderList) {
		this.orderList = orderList;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
