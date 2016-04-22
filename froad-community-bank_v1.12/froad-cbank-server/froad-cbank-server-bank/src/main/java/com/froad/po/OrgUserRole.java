package com.froad.po;

import java.util.List;



/**
 * 银行联合登陆账号po
 * CbOrgUserRole entity. 
 */

public class OrgUserRole  implements java.io.Serializable {


    // Fields    

     private Long id; //自增主键id
     private String clientId; //客户端id
     private String roleName; //角色名称
     private Long roleId; //角色id
     private Long userId; //用户id
     private String orgCode; //机构编号
     private String username; //登录名
     private String orgLevel; //机构级别
     private List<String> orgCodes; //机构编号集合


    // Constructors

    /** default constructor */
    public OrgUserRole() {
    }

	/** minimal constructor */
    public OrgUserRole(String clientId, Long roleId, String orgCode, String username, String orgLevel) {
        this.clientId = clientId;
        this.roleId = roleId;
        this.orgCode = orgCode;
        this.username = username;
        this.orgLevel = orgLevel;
    }
    
    /** full constructor */
    public OrgUserRole(String clientId, String roleName, Long roleId, String orgCode, String username, String orgLevel) {
        this.clientId = clientId;
        this.roleName = roleName;
        this.roleId = roleId;
        this.orgCode = orgCode;
        this.username = username;
        this.orgLevel = orgLevel;
    }

   
    // Property accessors
    
    
    

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    

    public String getClientId() {
        return this.clientId;
    }
    
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    
    

    public String getRoleName() {
        return this.roleName;
    }
    
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    
    

    public Long getRoleId() {
        return this.roleId;
    }
    
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
    
    public String getOrgCode() {
        return this.orgCode;
    }
    
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    
    

    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    

    public String getOrgLevel() {
        return this.orgLevel;
    }
    
    public void setOrgLevel(String orgLevel) {
        this.orgLevel = orgLevel;
    }

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
   
    public List<String> getOrgCodes() {
		return orgCodes;
	}

	public void setOrgCodes(List<String> orgCodes) {
		this.orgCodes = orgCodes;
	}

}