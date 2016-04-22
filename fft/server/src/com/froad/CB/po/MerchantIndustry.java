package com.froad.CB.po;

/**
 * 类描述：商户行业实体
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2013
 * @author: 李金魁 lijinkui@f-road.com.cn
 * @time: Feb 6, 2013 5:23:23 PM
 */
public class MerchantIndustry {

	private Integer id;
	private String name;		//名称
	private String description;	//描述
	private String orderList;	//排序
	private String path;		//树路径
	private String parentId;	//父类ID
	private String state;		//状态(10-创建，20-录入，30-启用，40-停用，50-删除)
	private String createTime;	//创建时间
	private String updateTime;	//更新时间
	private String remark;		//备注

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
		this.name = name == null ? null : name.trim();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	public String getOrderList() {
		return orderList;
	}

	public void setOrderList(String orderList) {
		this.orderList = orderList == null ? null : orderList.trim();
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path == null ? null : path.trim();
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId == null ? null : parentId.trim();
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state == null ? null : state.trim();
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime == null ? null : createTime.trim();
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime == null ? null : updateTime.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}
}