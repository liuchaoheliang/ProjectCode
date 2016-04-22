package com.froad.cbank.coremodule.normal.boss.pojo.system;

/**
 * 组织机构详情信息
 * @author yfy
 */
public class OrgVoRes {

	/**
	 * 主键ID
	 */
	private Long id;
	/**
	 * 组织ID
	 */
	private String orgId;
	/**
	 * 上级组织ID
	 */
	private Long parentId;
	/**
	 * 上级组织名称
	 */
	private String parentName;
	/**
	 * 组织级别
	 */
	private Integer level;
	/**
	 * 平台名称（boss、bank、merchant）
	 */
	private String platform;
	/**
	 * 客户端
	 */
	private String clientId;
	/**
	 * 树路径
	 */
	private String treePath;
	/**
	 * 树路径名称
	 */
	private String treePathName;
	/**
	 * 组织代码（boss对应ID、bank对应机构号、merchant对应商户ID）
	 */
	private String code;
	/**
	 * 组织名（Boss对应方付通部门、Bank对应机构名、Merchant对应商户名）
	 */
	private String orgName;
	/**
	 * 电话号码
	 */
	private String phone;
	/**
	 * 地区id
	 */
	private Long areaId;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 是否删除
	 */
	private Boolean isDelete;
	/**
	 * 是否有子节点
	 */
	private Boolean isParent;
	/**
	 * 省
	 */
	private String areaCode;
	/**
	 * 市
	 */
	private String cityCode;
	/**
	 * 区
	 */
	private String countyCode;
	
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
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getTreePath() {
		return treePath;
	}
	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}
	public String getTreePathName() {
		return treePathName;
	}
	public void setTreePathName(String treePathName) {
		this.treePathName = treePathName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Long getAreaId() {
		return areaId;
	}
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Boolean getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}
	public Boolean getIsParent() {
		return isParent;
	}
	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getCountyCode() {
		return countyCode;
	}
	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}
	
}
