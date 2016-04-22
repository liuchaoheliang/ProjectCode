package com.froad.db.chonggou.entity;



/**
 * 银行角色表po
 * CbBankRole entity. 
 */

public class BankRole  implements java.io.Serializable {


    // Fields    

     private Long id;//自增主键id
     private String clientId;//客户端id
     private String roleName;//角色名称
     private Boolean status;//是否可用
     private String remark;//备注
     private Boolean isDelete;//是否删除0-未删除1-删除
     private String tag;//标明角色身份，身份比如管理员、操作员(管理员暂时定为0)
     private String orgCode;//创建角色的机构号
     

    // Constructors

    /** default constructor */
    public BankRole() {
    }

	/** minimal constructor */
    public BankRole(String clientId, Boolean status,Boolean isDelete) {
        this.clientId = clientId;
        this.status = status;
        this.isDelete = isDelete;
    }
    
    /** full constructor */
    public BankRole(String clientId, String roleName, Boolean status, String remark,Boolean isDelete,String tag,String orgCode) {
        this.clientId = clientId;
        this.roleName = roleName;
        this.status = status;
        this.remark = remark;
        this.isDelete = isDelete;
        this.tag = tag;
        this.orgCode = orgCode;
    }

    
    
    
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

   
    // Property accessors
    
    

}