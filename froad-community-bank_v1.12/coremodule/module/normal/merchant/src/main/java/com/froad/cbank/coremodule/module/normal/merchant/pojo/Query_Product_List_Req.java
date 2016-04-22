package com.froad.cbank.coremodule.module.normal.merchant.pojo;


public class Query_Product_List_Req extends BasePojo{
	
	private String productId;
	private String type;
	private String name;
	private String fullName;
	private String isMarketable;
	private String auditState;
	private String startTime;
	private String endTime;
	private String expireTime;
	private String status;
	
	//审核开始时间
	private String auditStartTime; 
	
	//审核结束时间
	private String auditEndTime;  
	
	
	public String getAuditStartTime() {
		return auditStartTime;
	}
	public void setAuditStartTime(String auditStartTime) {
		this.auditStartTime = auditStartTime;
	}
	public String getAuditEndTime() {
		return auditEndTime;
	}
	public void setAuditEndTime(String auditEndTime) {
		this.auditEndTime = auditEndTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIsMarketable() {
		return isMarketable;
	}
	public void setIsMarketable(String isMarketable) {
		this.isMarketable = isMarketable;
	}
	public String getAuditState() {
		return auditState;
	}
	public void setAuditState(String auditState) {
		this.auditState = auditState;
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
	public String getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

}
