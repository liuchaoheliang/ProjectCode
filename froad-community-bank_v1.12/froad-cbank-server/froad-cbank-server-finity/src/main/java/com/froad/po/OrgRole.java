package com.froad.po;

/**
 * 组织角色po
 * @author froad-ll
 * @createtime 20160104 
 *
 */
public class OrgRole implements java.io.Serializable {

    
    private Long id;                    //id(自增主键)
	private String orgId;				//组织Id
	private Long roleId;				//角色Id
	private String roleName;			//角色名称
	
	
	public OrgRole(){}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getOrgId() {
		return orgId;
	}


	public void setOrgId(String orgId) {
		this.orgId = orgId;
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
	
	
	
	
    
}