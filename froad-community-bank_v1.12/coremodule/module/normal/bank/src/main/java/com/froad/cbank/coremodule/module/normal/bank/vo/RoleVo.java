package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 角色
 * 
 * @author ylchu
 *
 */
public class RoleVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6364648144589063301L;

	private Long roleId; // 主键id
	private String roleName; // 角色名
	private String remark; // 备注
	private boolean status;
	private String tag; // 角色身份
	private List<Long> resourceList; // 资源id
	private String orgCode; //机构号
	private String clientId;
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<Long> getResourceList() {
		return resourceList;
	}

	public void setResourceList(List<Long> resourceList) {
		this.resourceList = resourceList;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

}
