package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 角色request
 * 
 * @author ylchu
 *
 */
public class RoleReq extends BaseVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6464645742767623736L;

	private Long roleId;
	private String remark; // 备注
	private String roleName; // 角色名
	public boolean status;
	public String orgCode;  //机构号
	
	public Long operUserId; //查询来源用户

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Long getOperUserId() {
		return operUserId;
	}

	public void setOperUserId(Long operUserId) {
		this.operUserId = operUserId;
	}

}
