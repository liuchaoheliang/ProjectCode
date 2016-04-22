/**
 * Project Name:coremodule-bank
 * File Name:AuditTaskRes.java
 * Package Name:com.froad.cbank.coremodule.module.normal.bank.vo
 * Date:2015-8-19下午01:17:19
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 
 * ClassName: AuditTaskRes
 * Function: 审核任务记录实体
 * date: 2015-8-19 下午01:17:30
 *
 * @author wufei
 * @version
 */
public class AuditTaskRes implements Serializable{

	/**
	 * serialVersionUID:TODO
	 */
	private static final long serialVersionUID = -5141515643738737266L;

	private String auditId;   //审核流水号
	private String auditStatus;	  //审核状态
	private String businessType;  //业务类型
	private String name;  //名称
	private Long createTime;	  //创建时间
	private String orgName;       //所属机构
	private String parentOrgName; //所属机构上级机构
	private Long pigeonholeTime; //归档时间
	private String lincese; //营业执照
	private String creater; //创建人
	private String createOrgName;  //创建机构
	private String merchantId; //商户id
	private String merchantUserId; //商户用户id
	private String businessNo;//业务号码
	private String auditType; //审核类型
	private String outletId; //门店编号
	private String productId; // 商品id
	private String merchantName; // 商户名称
	
	
	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getOutletId() {
		return outletId;
	}
	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}
	public String getAuditType() {
		return auditType;
	}
	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}
	public String getBusinessNo() {
		return businessNo;
	}
	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	public String getMerchantUserId() {
		return merchantUserId;
	}
	public void setMerchantUserId(String merchantUserId) {
		this.merchantUserId = merchantUserId;
	}
	public String getAuditId() {
		return auditId;
	}
	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getLincese() {
		return lincese;
	}
	public void setLincese(String lincese) {
		this.lincese = lincese;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public String getCreateOrgName() {
		return createOrgName;
	}
	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}
	public String getParentOrgName() {
		return parentOrgName;
	}
	public void setParentOrgName(String parentOrgName) {
		this.parentOrgName = parentOrgName;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getPigeonholeTime() {
		return pigeonholeTime;
	}
	public void setPigeonholeTime(Long pigeonholeTime) {
		this.pigeonholeTime = pigeonholeTime;
	}
	
	
	
	
}
