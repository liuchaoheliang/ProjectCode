package com.froad.cbank.coremodule.normal.boss.pojo.product;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

/**
 * 审核商品请求类
 * @author luwanquan@f-road.com.cn
 * @date 2015年7月29日 下午2:09:32
 */
public class AuditProductReq {
	@NotEmpty("商品ID为空")
	private String productId;
	private String auditRemark;
	@NotEmpty("审核状态为空")
	private String auditState;//1-审核通过、2-审核未通过
	private String userId;
	private String roleId;
	private Long auditTime;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getAuditRemark() {
		return auditRemark;
	}
	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}
	public String getAuditState() {
		return auditState;
	}
	public void setAuditState(String auditState) {
		this.auditState = auditState;
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
	public Long getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Long auditTime) {
		this.auditTime = auditTime;
	}
}
