package com.froad.po;

import java.util.Date;
import java.util.List;



/**
 * 银行操作日志po
 * CbBankOperator entity.  
 */
 

public class BankOperateLog  implements java.io.Serializable {


    // Fields    
	 private Long id;//操作日志id
	 private Date createTime;//创建时间
	 private Integer platType;//平台来源
	 private Long userId; //用户id
	 private String username; //登录名
	 private Long roleId;//角色id
	 private String roleName;
     private String description;//操作说明
     private String clientId;//客户端id
     private String orgCode;//机构编号
     private String orgName;//机构名称
     private String operatorIp;//操作ip
     private Boolean logType; //是否登录日志
     private Date startDate;//开始时间
     private Date endDate;//结束时间
     private List<String> orgCodes; //机构编号集合
     
     
     
     
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Integer getPlatType() {
		return platType;
	}
	public void setPlatType(Integer platType) {
		this.platType = platType;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
	
	public Boolean getLogType() {
		return logType;
	}
	public void setLogType(Boolean logType) {
		this.logType = logType;
	}
	public BankOperateLog () {
		
	}
	public BankOperateLog(Long id, Date createTime, Integer platType,
			Long userId, String username, Long roleId, String description,
			String clientId, String orgCode, String orgName, String operatorIp,Boolean logType,
			Date startDate, Date endDate) {
		super();
		this.id = id;
		this.createTime = createTime;
		this.platType = platType;
		this.userId = userId;
		this.username = username;
		this.roleId = roleId;
		this.description = description;
		this.clientId = clientId;
		this.orgCode = orgCode;
		this.orgName = orgName;
		this.operatorIp = operatorIp;
		this.logType = logType;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	public List<String> getOrgCodes() {
		return orgCodes;
	}
	public void setOrgCodes(List<String> orgCodes) {
		this.orgCodes = orgCodes;
	}
	
   





}