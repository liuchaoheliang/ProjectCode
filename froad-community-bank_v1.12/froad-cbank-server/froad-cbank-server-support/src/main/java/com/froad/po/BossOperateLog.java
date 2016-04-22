package com.froad.po;



/**
 * Boss操作信息po
 */
 

public class BossOperateLog  implements java.io.Serializable {


    // Fields    

	 private Long id; 
	 private Long userId; 
	 private String username; 
     private Long roleId; 
     private String description;
     private String clientId;
     private String orgCode;
     private String orgName;
     private String operatorIp;
     private Long operatorTime; 
     
     
     
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
	public BossOperateLog(Long id, Long userId, String username, Long roleId,
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
	public BossOperateLog () {
		
	}

   





}