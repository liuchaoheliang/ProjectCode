/**
 * Project Name:coremodule-merchant
 * File Name:Query_Order_Detail_PC_DeliverInfo_Res.java
 * Package Name:com.froad.cbank.coremodule.module.normal.merchant.pojo
 * Date:2015年9月17日下午6:05:52
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.merchant.pojo;
/**
 * ClassName:Query_Order_Detail_PC_DeliverInfo_Res
 * Reason:	 TODO ADD REASON.
 * Date:     2015年9月17日 下午6:05:52
 * @author   mi
 * @version  
 * @see 	 
 */
public class Query_Order_Detail_PC_DeliverInfo_Res {
	
	/**
	   * 提货信息编号
	   */
	  public String deliveryId; // optional
	  /**
	   * 提货人
	   */
	  public String consignee; // optional
	  /**
	   * 提货电话
	   */
	  public String phone; // optional
	  /**
	   * 是否为默认,0:否 1:是
	   */
	  public String isDefault; // optional
	  /**
	   * 地区Id
	   */
	  public long areaId; // optional
	  /**
	   * 树路径
	   */
	  public String treePath; // optional
	  /**
	   * 树路径名称
	   */
	  public String treePathName; // optional
	  
	  
	  
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
	public long getAreaId() {
		return areaId;
	}
	public void setAreaId(long areaId) {
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
