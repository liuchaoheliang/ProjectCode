package com.froad.db.chonggou.entity;

import java.io.Serializable;

public class OrgUserRole implements Serializable{
   /**
	  * @Fields serialVersionUID : TODO
	*/
	
	private static final long serialVersionUID = 8922759595458845383L;
private String id;
   private String clientId;
   private String roleName;
   private Long roleId;
   private Long userId;
   private String orgCode;
   private String userName;
   private int orgLevel;
public String getId() {
	return id;
}
public void setId(String id) {
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
public Long getRoleId() {
	return roleId;
}
public void setRoleId(Long roleId) {
	this.roleId = roleId;
}
public Long getUserId() {
	return userId;
}
public void setUserId(Long userId) {
	this.userId = userId;
}
public String getOrgCode() {
	return orgCode;
}
public void setOrgCode(String orgCode) {
	this.orgCode = orgCode;
}
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public int getOrgLevel() {
	return orgLevel;
}
public void setOrgLevel(int orgLevel) {
	this.orgLevel = orgLevel;
}
   
	 
	
	
	
}

