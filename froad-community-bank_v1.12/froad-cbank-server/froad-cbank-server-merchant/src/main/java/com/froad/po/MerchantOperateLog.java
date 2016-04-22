package com.froad.po;



/**
 * 银行用户信息po
 * CbBankOperator entity.  
 */
 

public class MerchantOperateLog  implements java.io.Serializable {


    // Fields    

	 private Long id; //自增主键id
	 private Long userId; //创建时间
	 private String username; //客户端id
     private Long roleId; //登录名称
     private String description;//登录密码
     private String clientId;//机构编号
     private String orgCode;//手机号
     private String orgName;//e-mail
     private String operatorIp;//操作员名称
     private Long operatorTime; //角色id
     
     
     
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOperatorIp() {
		return operatorIp;
	}
	public void setOperatorIp(String operatorIp) {
		this.operatorIp = operatorIp;
	}
	public Long getOperatorTime() {
		return operatorTime;
	}
	public void setOperatorTime(Long operatorTime) {
		this.operatorTime = operatorTime;
	}
	
	 // Constructors
	public MerchantOperateLog(Long id, Long userId, String username, Long roleId,
			String description, String clientId, String orgCode, String orgName,
			String operatorIp, Long operatorTime) {
		super();
		this.id = id;
		this.userId = userId;
		this.username = username;
		this.roleId = roleId;
		this.description = description;
		this.clientId = clientId;
		this.orgCode = orgCode;
		this.orgName = orgName;
		this.operatorIp = operatorIp;
		this.operatorTime = operatorTime;
	}
	public MerchantOperateLog () {
		
	}

   





}