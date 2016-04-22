/**
 * Project Name:Froad Cbank Server Fallow
 * File Name:Origin.java
 * Package Name:com.froad.test.po
 * Date:2015年10月16日下午2:28:32
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.po;

import com.froad.enums.PlatTypeEnum;

/**
 * ClassName:Origin
 * Reason:	 TODO ADD REASON.
 * Date:     2015年10月16日 下午2:28:32
 * @author   vania
 * @version  
 * @see 	 
 */
public class Origin {

	/**
	 * 平台代码
	 * 
	 * @see PlatTypeEnum
	 */
	private PlatTypeEnum platType; // required
	/**
	 * 操作员id(说明:如果是个人操作则传用户id;如果是银行操作则传银行操作员id;如果是商户用户操作则传商户用户id)
	 */
	private Long operatorId; // required
	/**
	 * 操作员username(说明:如果是个人操作则传用户名;如果是银行操作则传银行操作员用户名;如果是商户用户操作则传商户用户名)
	 */
	private String operatorUserName;
	/**
	 * 终端ip
	 */
	private String operatorIp; // required
	/**
	 * 操作说明
	 */
	private String description; // required
	/**
	 * 客户端Id
	 */
	private String clientId; // required
	/**
	 * 操作人角色id
	 */
	private String roleId; // required
	/**
	 * 操作人所属组织(说明:如果是个人操作则传空;如果是银行操作则传银行操作员所在orgCode;如果是商户用户操作则传商户id;如果是门店用户操作则传门店id;)
	 */
	private String orgId;
	
	
	public Origin() {
		super();
	}
	
	public Origin(PlatTypeEnum platType, Long operatorId, String operatorUserName, String operatorIp, String description, String clientId, String roleId, String orgId) {
		super();
		this.platType = platType;
		this.operatorId = operatorId;
		this.operatorUserName = operatorUserName;
		this.operatorIp = operatorIp;
		this.description = description;
		this.clientId = clientId;
		this.roleId = roleId;
		this.orgId = orgId;
	}

	public PlatTypeEnum getPlatType() {
		return platType;
	}
	public void setPlatType(PlatTypeEnum platType) {
		this.platType = platType;
	}
	public Long getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}
	public String getOperatorUserName() {
		return operatorUserName;
	}
	public void setOperatorUserName(String operatorUserName) {
		this.operatorUserName = operatorUserName;
	}
	public String getOperatorIp() {
		return operatorIp;
	}
	public void setOperatorIp(String operatorIp) {
		this.operatorIp = operatorIp;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

}
