package com.froad.po;



/**
 * 银行角色资源关系表-暂不用
 * CbBankRoleResource entity. 
 */

public class BankRoleResource  implements java.io.Serializable {


    // Fields    

     private Long roleId;
     private Long resourceId;


    // Constructors

    /** default constructor */
    public BankRoleResource() {
    }

    
    /** full constructor */
    public BankRoleResource(Long resourceId) {
        this.resourceId = resourceId;
    }

   
    // Property accessors
    
    
    

    public Long getRoleId() {
        return this.roleId;
    }
    
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
    
    

    public Long getResourceId() {
        return this.resourceId;
    }
    
    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }
   








}