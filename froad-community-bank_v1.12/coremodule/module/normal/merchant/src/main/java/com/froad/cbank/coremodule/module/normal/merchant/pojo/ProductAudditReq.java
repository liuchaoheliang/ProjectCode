package com.froad.cbank.coremodule.module.normal.merchant.pojo;

/**
 * @author Administrator
 *
 */
public class ProductAudditReq extends BasePojo {
	private String types;// 商品类型  多个以逗号隔开
	private String productIds;// 商品id ，多个以逗号隔开
    private String userName;//操作员
    private String roleId;//角色id
    private String orgCode;//机构号x
    
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public String getProductIds() {
		return productIds;
	}

	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}

}
