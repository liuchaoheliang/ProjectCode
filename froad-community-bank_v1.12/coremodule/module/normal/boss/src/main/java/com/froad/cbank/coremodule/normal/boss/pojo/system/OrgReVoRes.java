package com.froad.cbank.coremodule.normal.boss.pojo.system;

/**
 * 组织数据权限响应参数
 * @author yfy
 */
public class OrgReVoRes {

	/**
	 * 主键ID
	 */
	private Long id;
	/**
	 * 组织ID
	 */
	private String orgId;
	/**
	 * 权限组织Id
	 */
	private String reOrgId;
	/**
	 * 权限组织树
	 */
	private String reOrgIdTreePath;
	/**
	 * 权限组织树名称
	 */
	private String reOrgIdTreePathName;
	/**
	 * 组织代码
	 */
	private String code;
	/**
	 * 平台名称
	 */
	private String platform;
	
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
	public String getReOrgId() {
		return reOrgId;
	}
	public void setReOrgId(String reOrgId) {
		this.reOrgId = reOrgId;
	}
	public String getReOrgIdTreePath() {
		return reOrgIdTreePath;
	}
	public void setReOrgIdTreePath(String reOrgIdTreePath) {
		this.reOrgIdTreePath = reOrgIdTreePath;
	}
	public String getReOrgIdTreePathName() {
		return reOrgIdTreePathName;
	}
	public void setReOrgIdTreePathName(String reOrgIdTreePathName) {
		this.reOrgIdTreePathName = reOrgIdTreePathName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	
}
