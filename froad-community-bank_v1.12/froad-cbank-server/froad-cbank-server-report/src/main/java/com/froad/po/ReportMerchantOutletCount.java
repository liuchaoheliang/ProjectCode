package com.froad.po;

public class ReportMerchantOutletCount {
	/**
	 * 支行名称 *
	 */
	public String orgName;
	/**
	 * 网点名称 *
	 */
	public String outletName;
	/**
	 * 网点地址 *
	 */
	public String outletAddress;
	/**
	 * 商户名称 *
	 */
	public String merchantName;
	/**
	 * 商户门店数 *
	 */
	public int merchantOutletCount;
	
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
	 * @return the outletName
	 */
	public String getOutletName() {
		return outletName;
	}
	/**
	 * @param outletName the outletName to set
	 */
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}
	/**
	 * @return the outletAddress
	 */
	public String getOutletAddress() {
		return outletAddress;
	}
	/**
	 * @param outletAddress the outletAddress to set
	 */
	public void setOutletAddress(String outletAddress) {
		this.outletAddress = outletAddress;
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
	 * @return the merchantOutletCount
	 */
	public int getMerchantOutletCount() {
		return merchantOutletCount;
	}
	/**
	 * @param merchantOutletCount the merchantOutletCount to set
	 */
	public void setMerchantOutletCount(int merchantOutletCount) {
		this.merchantOutletCount = merchantOutletCount;
	}
	
}
