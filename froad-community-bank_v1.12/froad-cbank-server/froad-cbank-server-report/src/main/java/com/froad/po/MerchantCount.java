package com.froad.po;

import java.io.Serializable;

public class MerchantCount implements Serializable{
	
	private static final long serialVersionUID = 4406375017442282217L;
	
	private String clientId;			//客户端号
	private String orgCode;				//机构号
	private String proOrgCode;			//省联社
	private String cityOrgCode;			//法人行社
	private String countyOrgCode;		//网点
	private Long balanceMerchantTotal;	//普通商户总数
	private String contractStaff;		//商户签约人
	private String userOrgCode;			//签约人机构号
	private String merchantIdList;//商户ID集合
	
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
	public Long getBalanceMerchantTotal() {
		return balanceMerchantTotal;
	}
	public void setBalanceMerchantTotal(Long balanceMerchantTotal) {
		this.balanceMerchantTotal = balanceMerchantTotal;
	}
	public String getContractStaff() {
		return contractStaff;
	}
	public void setContractStaff(String contractStaff) {
		this.contractStaff = contractStaff;
	}
	/**
	 * @return the userOrgCode
	 */
	public String getUserOrgCode() {
		return userOrgCode;
	}
	/**
	 * @param userOrgCode the userOrgCode to set
	 */
	public void setUserOrgCode(String userOrgCode) {
		this.userOrgCode = userOrgCode;
	}
	public String getMerchantIdList() {
		return merchantIdList;
	}
	public void setMerchantIdList(String merchantIdList) {
		this.merchantIdList = merchantIdList;
	}
	
}
