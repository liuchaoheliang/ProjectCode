package com.froad.cbank.coremodule.module.normal.user.pojo;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

/**
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年4月24日 下午5:48:06
 * @version 1.0
 * @desc 提货人信息POJO
 */
public class DeliveryPojo {
	private String deliveryId;
	
	@NotEmpty("联系人不可为空")
	private String consignee;
	
	@NotEmpty("联系电话不可为空")
	private String phone;
	private String isDefault;
	
	@NotEmpty("地区ID不可为空")
	private Long areaId;
	private String treePath;
	private String treePathName;
	
	public String getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(String deliveryId) {
		this.deliveryId = deliveryId;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	public Long getAreaId() {
		return areaId;
	}
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	public String getTreePath() {
		return treePath;
	}
	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}
	public String getTreePathName() {
		return treePathName;
	}
	public void setTreePathName(String treePathName) {
		this.treePathName = treePathName;
	}
}
