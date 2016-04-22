package com.froad.cbank.coremodule.module.normal.user.pojo;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

/**
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年4月14日 上午12:00:34
 * @version 1.0
 * @desc 收货地址POJO
 */
public class ReceiverPojo {
	private String recvId;
	
	@NotEmpty("联系人不可为空")
	private String consignee;
	
	@NotEmpty("联系电话不可为空")
	private String phone;
	
	private String address;
	
	private Long areaId;
	
	private String isDefault;
	private String treePath;
	private String treePathName;
	
	private String type;
	private String isDeliverDefault;
	
	public String getRecvId() {
		return recvId;
	}
	public void setRecvId(String recvId) {
		this.recvId = recvId;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIsDeliverDefault() {
		return isDeliverDefault;
	}
	public void setIsDeliverDefault(String isDeliverDefault) {
		this.isDeliverDefault = isDeliverDefault;
	}
	
}
