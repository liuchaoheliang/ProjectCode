package com.froad.cbank.coremodule.normal.boss.pojo.common;

import java.io.Serializable;

/**
 * 类描述：银行机构相关实体类
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road.com.cn
 * @time: 2015-5-11上午10:47:50 
 */
public class BankOrgInfoVo implements Serializable{
	
	/**
	 * UID
	 */
	private static final long serialVersionUID = -6045564829309088036L;
	private Long orgId;        //机构ID
	private String orgCode;    //机构号
	private String orgName;    //机构名
	private String partenOrgCode; //上级机构
	private String partenOrgName; //上级机构名称
	private String proinceAgency; //一级机构
	private String cityAgency;  //二级机构
	private String countyAgency;//三级机构
	private String orgLevel;    //机构级别
	private Boolean orgType;    //机构类型（0-false-部门机构，1-true-业务机构）
	private Boolean state;       //机构状态
	private Long areaCode;    //省
	private Long cityCode;    //市
	private Long countyCode;  //区
	private String address;      //详细地址
	private String longitude;    //经度
	private String latitude;    //纬度
	private String tel;       //联系电话
	private Boolean isMutualAudit; //是否需要双人审核
	

	private String clientId;     //客户端ID
	private String merchantId; //商户ID
	private String outletId;   //门店ID
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
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
	public String getPartenOrgCode() {
		return partenOrgCode;
	}
	public void setPartenOrgCode(String partenOrgCode) {
		this.partenOrgCode = partenOrgCode;
	}
	public String getPartenOrgName() {
		return partenOrgName;
	}
	public void setPartenOrgName(String partenOrgName) {
		this.partenOrgName = partenOrgName;
	}
	public String getProinceAgency() {
		return proinceAgency;
	}
	public void setProinceAgency(String proinceAgency) {
		this.proinceAgency = proinceAgency;
	}
	public String getCityAgency() {
		return cityAgency;
	}
	public void setCityAgency(String cityAgency) {
		this.cityAgency = cityAgency;
	}
	public String getCountyAgency() {
		return countyAgency;
	}
	public void setCountyAgency(String countyAgency) {
		this.countyAgency = countyAgency;
	}
	public String getOrgLevel() {
		return orgLevel;
	}
	public void setOrgLevel(String orgLevel) {
		this.orgLevel = orgLevel;
	}
	public Boolean getOrgType() {
		return orgType;
	}
	public void setOrgType(Boolean orgType) {
		this.orgType = orgType;
	}
	public Boolean getState() {
		return state;
	}
	public void setState(Boolean state) {
		this.state = state;
	}
	public Long getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(Long areaCode) {
		this.areaCode = areaCode;
	}
	public Long getCityCode() {
		return cityCode;
	}
	public void setCityCode(Long cityCode) {
		this.cityCode = cityCode;
	}
	public Long getCountyCode() {
		return countyCode;
	}
	public void setCountyCode(Long countyCode) {
		this.countyCode = countyCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Boolean getIsMutualAudit() {
		return isMutualAudit;
	}
	public void setIsMutualAudit(Boolean isMutualAudit) {
		this.isMutualAudit = isMutualAudit;
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
	public String getOutletId() {
		return outletId;
	}
	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}
	
}
