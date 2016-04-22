package com.froad.db.chonggou.entity;

import java.util.Date;


/**
 * 银行用户信息po
 * CbBankOperator entity.  
 */
 

public class BankOperatorCG  implements java.io.Serializable {


    // Fields    

	 private Long id; //自增主键id
	 private Date createTime; //创建时间
	 private String clientId; //客户端id
     private String username; //登录名称
     private String password;//登录密码
     private String orgCode;//机构编号
     private String mobile;//手机号
     private String email;//e-mail
     private String name;//操作员名称
     private Long roleId; //角色id
     private String department; //部门
     private String position; //职务
     private Boolean status; //是否可用
     private Boolean isReset; //密码是否重置
     private String remark; //备注
     private Boolean isDelete;//是否删除0-未删除1-删除


    // Constructors


	/** default constructor */
    public BankOperatorCG() {
    }

	/** minimal constructor */
    public BankOperatorCG(Date createTime,String clientId, String username, String password, String orgCode, Long roleId, Boolean status, Boolean isReset,Boolean isDelete) {
    	this.createTime=createTime;
        this.clientId = clientId;
        this.username = username;
        this.password = password;
        this.orgCode = orgCode;
        this.roleId = roleId;
        this.status = status;
        this.isReset = isReset;
        this.isDelete = isDelete;
    }
    
    /** full constructor */
    public BankOperatorCG(Date createTime,String clientId, String username, String password, String orgCode, String mobile, String email, String name, Long roleId, String department, String position, Boolean status, Boolean isReset, String remark,Boolean isDelete) {
    	this.createTime=createTime;
        this.clientId = clientId;
        this.username = username;
        this.password = password;
        this.orgCode = orgCode;
        this.mobile = mobile;
        this.email = email;
        this.name = name;
        this.roleId = roleId;
        this.department = department;
        this.position = position;
        this.status = status;
        this.isReset = isReset;
        this.remark = remark;
        this.isDelete = isDelete;
    }

   
    // Property accessors
     
    
    
    public Long getId() {
		return id;
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
    
     

    public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
     

    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
     

    public String getOrgCode() {
        return this.orgCode;
    }
    
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    
     

    public String getMobile() {
        return this.mobile;
    }
    
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    
     

    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
     

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
     

    public Long getRoleId() {
        return this.roleId;
    }
    
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
    
     

    public String getDepartment() {
        return this.department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
     

    public String getPosition() {
        return this.position;
    }
    
    public void setPosition(String position) {
        this.position = position;
    }
    
    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
   
    
    public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Boolean getIsReset() {
		return isReset;
	}

	public void setIsReset(Boolean isReset) {
		this.isReset = isReset;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}







}