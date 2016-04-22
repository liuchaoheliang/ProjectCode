package com.froad.cbank.coremodule.module.normal.merchant.pojo;


public class Query_Merchant_User_Req extends BasePojo{

	private String id; 
	private String outletId; 
	private String merchantRoleId; 
	private String username; 
	private String realname;
	
	private Boolean isDelete;
	
	private Long operatorUserId;
	
	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getOutletId() {
		return outletId;
	}

	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}

	public String getMerchantRoleId() {
		return merchantRoleId;
	}

	public void setMerchantRoleId(String merchantRoleId) {
		this.merchantRoleId = merchantRoleId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public Long getOperatorUserId() {
		return operatorUserId;
	}

	public void setOperatorUserId(Long operatorUserId) {
		this.operatorUserId = operatorUserId;
	}
	
	
}
