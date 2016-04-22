package com.froad.db.bps.entity;

import java.util.Date;

public class BankOrganization {
	
	private Long id;				//流水号
	private Date createTime;		//创建时间
	private Date updateTime;		//更新时间
	private String orgCode;			//机构代码
	private String orgName;			//机构名称
	private String bankunionCode;	//行号
	private String bankunionName;	//行名
	private String groupId;			//
	private String bankCode;		//银行代码（1000安徽农信 3000重庆农商行...)
	private String status;			//状态： 1可用 0 不可用
	private String path;			//路径（org_code^^org_code^^org_code^^）
	private Integer level;			//层级（1,2,3)
	private String parentOrgCode;	//父级机构代码,如果是顶级机构，则父机构代码为0
	private String parentOrgName;	//父机构名称
	private String managerNumber;	//机构管理号
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
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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
	public String getBankunionCode() {
		return bankunionCode;
	}
	public void setBankunionCode(String bankunionCode) {
		this.bankunionCode = bankunionCode;
	}
	public String getBankunionName() {
		return bankunionName;
	}
	public void setBankunionName(String bankunionName) {
		this.bankunionName = bankunionName;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getParentOrgCode() {
		return parentOrgCode;
	}
	public void setParentOrgCode(String parentOrgCode) {
		this.parentOrgCode = parentOrgCode;
	}
	public String getParentOrgName() {
		return parentOrgName;
	}
	public void setParentOrgName(String parentOrgName) {
		this.parentOrgName = parentOrgName;
	}
	public String getManagerNumber() {
		return managerNumber;
	}
	public void setManagerNumber(String managerNumber) {
		this.managerNumber = managerNumber;
	}
	
	
}
