package com.froad.po;

import java.util.Date;

public class MerchantOutlet {
	private Date createTime = null;
	private String clientId = null;
	private String merchantId = null;
	private String outletId = null;
	private String outletName = null;
	private Boolean outletStatus = null;
	private String address = null;
	private Boolean isEnable = null;
	private String disableStatus = null;
	
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
	 * @return the outletId
	 */
	public String getOutletId() {
		return outletId;
	}
	/**
	 * @param outletId the outletId to set
	 */
	public void setOutletId(String outletId) {
		this.outletId = outletId;
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
	 * @return the outletStatus
	 */
	public Boolean getOutletStatus() {
		return outletStatus;
	}
	/**
	 * @param outletStatus the outletStatus to set
	 */
	public void setOutletStatus(Boolean outletStatus) {
		this.outletStatus = outletStatus;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the isEnable
	 */
	public Boolean getIsEnable() {
		return isEnable;
	}
	/**
	 * @param isEnable the isEnable to set
	 */
	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
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
