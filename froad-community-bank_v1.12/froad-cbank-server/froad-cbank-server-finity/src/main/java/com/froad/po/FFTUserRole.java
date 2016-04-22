package com.froad.po;

/**
 * 用户角色关系po
 * @author froad-ll
 * @createtime 20160104 
 *
 */
public class FFTUserRole implements java.io.Serializable {

    
    private Long id;                    //id(自增主键)
	private Long userId;				//用户Id
	private Long roleId;				//角色Id
	private Integer source;				//来源(0-继承自组织，1-用户直接分配)
	private String orgId;				//组织Id
	
	
	public FFTUserRole(){}


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


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public Long getRoleId() {
		return roleId;
	}


	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}


	public Integer getSource() {
		return source;
	}


	public void setSource(Integer source) {
		this.source = source;
	}

	
	
    
}