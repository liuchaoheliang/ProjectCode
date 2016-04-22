package com.froad.cbank.coremodule.module.normal.merchant.pojo;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

public class Fallow_Execute_Service_Req extends BasePojo {
	@NotEmpty(value="审核状态不能为空")
	private String auditState;
	@NotEmpty(value="编辑审核状态不能为空")
	private String editAuditState;
	@NotEmpty(value="门店id不能为空")
	private String outletId;
	@NotEmpty(value="操作员不能为空")
    private String userName;
	@NotEmpty(value="角色id不能为空")
    private String roleId;
    
    public String processId; // required；
    

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
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

	public String getAuditState() {
		return auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}

	public String getEditAuditState() {
		return editAuditState;
	}

	public void setEditAuditState(String editAuditState) {
		this.editAuditState = editAuditState;
	}

	public String getOutletId() {
		return outletId;
	}

	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}

}
