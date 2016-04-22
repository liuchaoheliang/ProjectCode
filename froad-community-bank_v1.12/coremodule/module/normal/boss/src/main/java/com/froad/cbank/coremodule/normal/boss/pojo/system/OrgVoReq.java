package com.froad.cbank.coremodule.normal.boss.pojo.system;

import java.util.List;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;
import com.froad.cbank.coremodule.framework.common.valid.Regular;

/**
 * 新增或修改组织机构信息
 * @author yfy
 */
public class OrgVoReq {

	/**
	 * 主键ID
	 */
	private Long id;
	/**
	 * 组织ID即同步银行机构主键ID
	 */
	private String orgId;
	/**
	 * 上级组织ID
	 */
	@NotEmpty("上级组织不能为空")
	private Long parentId;
	/**
	 * 组织级别
	 */
	@NotEmpty("组织级别不能为空")
	private Integer level;
	/**
	 * 平台名称（boss、bank、merchant）
	 */
	private String platform;
	/**
	 * 组织代码（boss对应ID、bank对应机构号、merchant对应商户ID）
	 */
	private String code;
	/**
	 * 一级组织代码 platform=bank的时候该值不可为空	
	 */
	private String ProCode;
	/**
	 * 二级组织代码 platform=bank的时候该值不可为空	
	 */
    private String CityCode;
	/**
	 * 三级组织代码 platform=bank的时候该值不可为空（暂时不用）
	 */
    private String CountyCode;
	/**
	 * 组织名称（Boss对应方付通部门、Bank对应机构名、Merchant对应商户名）
	 */
	@NotEmpty("组织名称不能为空")
	@Regular(reg="^.{1,32}$",value="组织名称不能超过32个字符")
	private String orgName;
	/**
	 * 客户端ID
	 */
	private String clientId;
	/**
	 * 电话号码
	 */
	@NotEmpty("电话号码不能为空")
	@Regular(reg="^.{1,15}$",value="电话号码不能超过15个字符")
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
	 * 角色分配
	 */
	private List<Long> roleIds;
	/**
	 * 数据权限
	 */
	private List<String> reOrgIds;
	
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getProCode() {
		return ProCode;
	}
	public void setProCode(String proCode) {
		ProCode = proCode;
	}
	public String getCityCode() {
		return CityCode;
	}
	public void setCityCode(String cityCode) {
		CityCode = cityCode;
	}
	public String getCountyCode() {
		return CountyCode;
	}
	public void setCountyCode(String countyCode) {
		CountyCode = countyCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
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
	public List<Long> getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}
	public List<String> getReOrgIds() {
		return reOrgIds;
	}
	public void setReOrgIds(List<String> reOrgIds) {
		this.reOrgIds = reOrgIds;
	}
	
}
