package com.froad.po;

public class AuditFilter {
	  /**
	   * 商品id
	   */
	  public String productId; // required
	  /**
	   * 用户id
	   */
	  public String userId; // required
	  /**
	   * 用户id
	   */
	  public String userName; // required
	  /**
	   * 用户角色id
	   */
	  public String roleId; // required
	  /**
	   * 时间
	   */
	  public long createTime; // required
	  /**
	   * 备注
	   */
	  public String auditRemark; // required
	  
	  private String auditState;
	public String getAuditState() {
		return auditState;
	}
	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public String getAuditRemark() {
		return auditRemark;
	}
	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}
	  
}
