package com.froad.cbank.coremodule.normal.boss.pojo.system;

import com.froad.cbank.coremodule.normal.boss.pojo.Page;

/**
 * 组织机构查询请求参数
 * @author yfy
 */
public class OrgVolistReq extends Page{

	/**
	 * 主键ID
	 */
	private Long id;
	/**
	 * 组织ID
	 */
	private String orgId;
	/**
	 * 平台名称（boss、bank、merchant）
	 */
	private String platform;
	/**
	 * 组织名（Boss对应方付通部门、Bank对应机构名、Merchant对应商户名）
	 */
	private String orgName;
	/**
	 * 是否删除
	 */
	private Boolean isDelete;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Boolean getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}
	
}
