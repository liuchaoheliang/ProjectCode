package com.froad.po;

import java.util.Date;

public class ReportMerchantOutlet {
	private Integer id = 0;
	private Date createTime = null;//日期
	private String clientId = null;//客户端ID
	private String orgCode = null;//网点ID
	private String orgName = null;//网点名
	private String orgAddress = null;//网点地址
	private String forgCode = null;//一级机构号
	private String forgName = null;//一级机构名
	private String sorgCode = null;//二级机构号
	private String sorgName = null;//二级机构名
	private String torgCode = null;//三级机构号
	private String torgName = null;//三级机构名
	private String lorgCode = null;//四级机构号
	private String lorgName = null;//四级机构名
	private String merchantId = null;//商户ID
	private String merchantName = null;//商户名
	private String merchantOutletId = null;//门店ID
	private String merchantOutletName = null;//门店名
	private Integer newOutletCount = null;//新增门店数
	private Integer cancelOutletCount = null;//失效门店数
	private String disableStatus = null;//门店状态
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return the clientId
	 */
	public String getClientId() {
		return clientId;
	}
	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}
	/**
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	/**
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}
	/**
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	/**
	 * @return the orgAddress
	 */
	public String getOrgAddress() {
		return orgAddress;
	}
	/**
	 * @param orgAddress the orgAddress to set
	 */
	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}
	/**
	 * @return the forgCode
	 */
	public String getForgCode() {
		return forgCode;
	}
	/**
	 * @param forgCode the forgCode to set
	 */
	public void setForgCode(String forgCode) {
		this.forgCode = forgCode;
	}
	/**
	 * @return the forgName
	 */
	public String getForgName() {
		return forgName;
	}
	/**
	 * @param forgName the forgName to set
	 */
	public void setForgName(String forgName) {
		this.forgName = forgName;
	}
	/**
	 * @return the sorgCode
	 */
	public String getSorgCode() {
		return sorgCode;
	}
	/**
	 * @param sorgCode the sorgCode to set
	 */
	public void setSorgCode(String sorgCode) {
		this.sorgCode = sorgCode;
	}
	/**
	 * @return the sorgName
	 */
	public String getSorgName() {
		return sorgName;
	}
	/**
	 * @param sorgName the sorgName to set
	 */
	public void setSorgName(String sorgName) {
		this.sorgName = sorgName;
	}
	/**
	 * @return the torgCode
	 */
	public String getTorgCode() {
		return torgCode;
	}
	/**
	 * @param torgCode the torgCode to set
	 */
	public void setTorgCode(String torgCode) {
		this.torgCode = torgCode;
	}
	/**
	 * @return the torgName
	 */
	public String getTorgName() {
		return torgName;
	}
	/**
	 * @param torgName the torgName to set
	 */
	public void setTorgName(String torgName) {
		this.torgName = torgName;
	}
	/**
	 * @return the lorgCode
	 */
	public String getLorgCode() {
		return lorgCode;
	}
	/**
	 * @param lorgCode the lorgCode to set
	 */
	public void setLorgCode(String lorgCode) {
		this.lorgCode = lorgCode;
	}
	/**
	 * @return the lorgName
	 */
	public String getLorgName() {
		return lorgName;
	}
	/**
	 * @param lorgName the lorgName to set
	 */
	public void setLorgName(String lorgName) {
		this.lorgName = lorgName;
	}
	/**
	 * @return the merchantId
	 */
	public String getMerchantId() {
		return merchantId;
	}
	/**
	 * @param merchantId the merchantId to set
	 */
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	/**
	 * @return the merchantName
	 */
	public String getMerchantName() {
		return merchantName;
	}
	/**
	 * @param merchantName the merchantName to set
	 */
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	/**
	 * @return the merchantOutletId
	 */
	public String getMerchantOutletId() {
		return merchantOutletId;
	}
	/**
	 * @param merchantOutletId the merchantOutletId to set
	 */
	public void setMerchantOutletId(String merchantOutletId) {
		this.merchantOutletId = merchantOutletId;
	}
	/**
	 * @return the merchantOutletName
	 */
	public String getMerchantOutletName() {
		return merchantOutletName;
	}
	/**
	 * @param merchantOutletName the merchantOutletName to set
	 */
	public void setMerchantOutletName(String merchantOutletName) {
		this.merchantOutletName = merchantOutletName;
	}
	/**
	 * @return the newOutletCount
	 */
	public Integer getNewOutletCount() {
		return newOutletCount;
	}
	/**
	 * @param newOutletCount the newOutletCount to set
	 */
	public void setNewOutletCount(Integer newOutletCount) {
		this.newOutletCount = newOutletCount;
	}
	/**
	 * @return the cancelOutletCount
	 */
	public Integer getCancelOutletCount() {
		return cancelOutletCount;
	}
	/**
	 * @param cancelOutletCount the cancelOutletCount to set
	 */
	public void setCancelOutletCount(Integer cancelOutletCount) {
		this.cancelOutletCount = cancelOutletCount;
	}
	/**
	 * @return the disableStatus
	 */
	public String getDisableStatus() {
		return disableStatus;
	}
	/**
	 * @param disableStatus the disableStatus to set
	 */
	public void setDisableStatus(String disableStatus) {
		this.disableStatus = disableStatus;
	}
}
