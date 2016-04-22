package com.froad.cbank.coremodule.module.normal.merchant.pojo;

public class Query_Merchant_Settlement_Req extends BasePojo {
	
	  /**
	   * 商户ID  （超级管理员传）
	   */
	  public String merchantId; 
	  /**
	   * 门店ID  （门店管理员传）
	   */
	  public String outletId; 
	  /**
	   * 操作员ID（门店操作员传）
	   */
	  public String userId; 
	  
	  /**
	   * 角色Id
	   */
	  public String roleId;

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getOutletId() {
		return outletId;
	}

	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	} 
	
}
