/**
 * Project Name:froad-cbank-server-common-1.8.0-SNAPSHOT
 * File Name:Provider.java
 * Package Name:com.froad.po
 * Date:2015年11月26日上午10:26:48
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.po;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName:Provider
 * Reason:	 TODO ADD REASON.
 * Date:     2015年11月26日 上午10:26:48
 * @author   huangyihao
 * @version  
 * @see 	 
 */
public class Provider implements Serializable {

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = -6570947010284249923L;

	// id
	private Long id;
	
	// 创建时间
	private Date createTime;
	
	// 更新时间
	private Date updateTime;
	
	// 供应商ID
	private String merchantId;
	
	// 供应商名称
	private String merchantName;
	
	// 地址
	private String address;
	
	// 电话
	private String phone;
	
	// 状态: 0禁用, 1启用
	private String status;
	
	// 供应商描述
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
