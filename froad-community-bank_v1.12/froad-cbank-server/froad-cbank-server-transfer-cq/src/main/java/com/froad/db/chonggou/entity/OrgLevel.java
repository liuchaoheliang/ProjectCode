package com.froad.db.chonggou.entity;



/**
 * 银行联合登录-机构角色关系po
 * CbOrgLevel entity. 
 */

public class OrgLevel  implements java.io.Serializable {


    // Fields    

     private Long id; //自增主键id
     private String clientId;	//客户端id
     private Long roleId;  //角色id
     private String orgLevel; //机构级别


    // Constructors

    /** default constructor */
    public OrgLevel() {
    }

    
    /** full constructor */
    public OrgLevel(String clientId, Long roleId, String orgLevel) {
        this.clientId = clientId;
        this.roleId = roleId;
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
    
    

    public Long getRoleId() {
        return this.roleId;
    }
    
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
    
    

    public String getOrgLevel() {
        return this.orgLevel;
    }
    
    public void setOrgLevel(String orgLevel) {
        this.orgLevel = orgLevel;
    }
   








}