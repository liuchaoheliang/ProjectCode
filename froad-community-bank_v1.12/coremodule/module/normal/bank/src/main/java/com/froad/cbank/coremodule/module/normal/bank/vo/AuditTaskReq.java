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
 * ClassName: AuditTaskReq
 * Function: 审核记录请求参数
 * date: 2015-8-19 下午01:31:26
 *
 * @author wufei
 * @version
 */
public class AuditTaskReq extends BaseVo implements Serializable{

	/**
	 * serialVersionUID:TODO
	 */
	private static final long serialVersionUID = -5141515643738737266L;

	private String auditId;   //审核流水号
	private String startTime;		  //起始时间
	private String endTime;		  //结束时间
	private String businessType;  //业务类型  1、商户  2、门店  3、团购商品  4、预售商品
	private String name;  //名称
	private String businessNo;    //业务号码
	private String isPigeonhole; //是否归档   1-在途 2-归档
	private String orgCode;       //所属机构
	private String pigeonholeStartTime;//归档开始时间
	private String pigeonholeEndTime;//归档结束时间
	private String auditState;  //审核状态
	private String myOrgCode;//当前登录人机构
	private String operator;   //当前登录人
	
	
	
	
	
	public String getMyOrgCode() {
		return myOrgCode;
	}
	public void setMyOrgCode(String myOrgCode) {
		this.myOrgCode = myOrgCode;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getAuditState() {
		return auditState;
	}
	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}
	public String getAuditId() {
		return auditId;
	}
	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
	public String getBusinessNo() {
		return businessNo;
	}
	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}
	public String getIsPigeonhole() {
		return isPigeonhole;
	}
	public void setIsPigeonhole(String isPigeonhole) {
		this.isPigeonhole = isPigeonhole;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getPigeonholeStartTime() {
		return pigeonholeStartTime;
	}
	public void setPigeonholeStartTime(String pigeonholeStartTime) {
		this.pigeonholeStartTime = pigeonholeStartTime;
	}
	public String getPigeonholeEndTime() {
		return pigeonholeEndTime;
	}
	public void setPigeonholeEndTime(String pigeonholeEndTime) {
		this.pigeonholeEndTime = pigeonholeEndTime;
	}
	
	
}
