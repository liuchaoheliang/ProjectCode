package com.froad.po;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import net.sf.oval.constraint.Email;
import net.sf.oval.constraint.MaxLength;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.Range;

import com.alibaba.fastjson.annotation.JSONField;
import com.froad.db.mongo.OutletCommentMongo;

public class OutletPrefer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Date createTime;
	
	@MaxLength(value = 20, message = "客户端id不能超过{max}个字符")
	private String clientId;
	
	@MaxLength(value = 20, message = "商户id不能超过{max}个字符")
	private String merchantId;
	
	@MaxLength(value = 16, message = "一级机构码不能超过{max}个字符")
	private String proOrgCode;
	
	@MaxLength(value = 16, message = "二级机构码不能超过{max}个字符")
	private String cityOrgCode;
	
	@MaxLength(value = 16, message = "三级机构码不能超过{max}个字符")
	private String countyOrgCode;
	
	@MaxLength(value = 16, message = "商户所属机构码不能超过{max}个字符")
	private String orgCode;
	
	// 机构查询优化
	private List<String> orgCodesCondition;
	
	@MaxLength(value = 32, message = "商户名称不能超过{max}个字符")
	private String merchantName;
	
	@MaxLength(value = 64, message = "营业执照不能超过{max}个字符")
	private String license;
	
	private String outletName;
	
	private String outletId;
	
	private Date auditTime;
	
	@MaxLength(value = 1, message = "审核状态不能超过{max}个字符")
	private String auditState;

	private String editAuditState;
	
	private String qrcodeUrl;
	
	private Date startCreateTime;
	private Date endCreateTime;
	
	private Date startAuditTime;
	
	private Date endAuditTime;
	
	@MaxLength(value = 64, message = "商户地址不能超过{max}个字符")
	private String address;
	
	private String areaId;
	private String areaName;
	
	//机构名称
	private String orgName;

	private String disableStatus;
	
	private Boolean isDefault;
	
	private Boolean isEnable;
	
	private String treePathName;
	
	private Boolean preferStatus;
	
	private String discountRate;
	
	private List<String> disableStatusList;
	
	
	
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

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getProOrgCode() {
		return proOrgCode;
	}

	public void setProOrgCode(String proOrgCode) {
		this.proOrgCode = proOrgCode;
	}

	public String getCityOrgCode() {
		return cityOrgCode;
	}

	public void setCityOrgCode(String cityOrgCode) {
		this.cityOrgCode = cityOrgCode;
	}

	public String getCountyOrgCode() {
		return countyOrgCode;
	}

	public void setCountyOrgCode(String countyOrgCode) {
		this.countyOrgCode = countyOrgCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	public String getOutletId() {
		return outletId;
	}

	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getAuditState() {
		return auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}

	public String getQrcodeUrl() {
		return qrcodeUrl;
	}

	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}

	public Date getStartCreateTime() {
		return startCreateTime;
	}

	public void setStartCreateTime(Date startCreateTime) {
		this.startCreateTime = startCreateTime;
	}

	public Date getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(Date endCreateTime) {
		this.endCreateTime = endCreateTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getEditAuditState() {
		return editAuditState;
	}

	public void setEditAuditState(String editAuditState) {
		this.editAuditState = editAuditState;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getDisableStatus() {
		return disableStatus;
	}

	public void setDisableStatus(String disableStatus) {
		this.disableStatus = disableStatus;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public String getTreePathName() {
		return treePathName;
	}

	public void setTreePathName(String treePathName) {
		this.treePathName = treePathName;
	}

	public Date getStartAuditTime() {
		return startAuditTime;
	}

	public void setStartAuditTime(Date startAuditTime) {
		this.startAuditTime = startAuditTime;
	}

	public Date getEndAuditTime() {
		return endAuditTime;
	}

	public void setEndAuditTime(Date endAuditTime) {
		this.endAuditTime = endAuditTime;
	}

	public List<String> getOrgCodesCondition() {
		return orgCodesCondition;
	}

	public void setOrgCodesCondition(List<String> orgCodesCondition) {
		this.orgCodesCondition = orgCodesCondition;
	}

	public Boolean getPreferStatus() {
		return preferStatus;
	}

	public void setPreferStatus(Boolean preferStatus) {
		this.preferStatus = preferStatus;
	}

	public String getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(String discountRate) {
		this.discountRate = discountRate;
	}

	public List<String> getDisableStatusList() {
		return disableStatusList;
	}

	public void setDisableStatusList(List<String> disableStatusList) {
		this.disableStatusList = disableStatusList;
	}
}
