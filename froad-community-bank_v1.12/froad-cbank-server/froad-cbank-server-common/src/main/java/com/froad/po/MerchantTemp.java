package com.froad.po;

import java.util.Date;

/**
 * 商户修改审核变更表 po
 * @author ll 20150814 add
 *
 */
public class MerchantTemp  implements java.io.Serializable {

	private Long id;						//主键Id
	private Date createTime;				//创建时间
	private String clientId;				//客户端Id
	private String auditId;					//审核流水号 -对应AuditTask的auditId
	private String  merchantId;				//商户Id		
	private String contactName;				//联系人姓名
	private Long merchantCategoryId;		//商户分类Id
	private String merchantCategoryName;	//分户分类名称
	private String merchantTypeId;			//商户类型ID，用逗号分隔
	private String merchantTypeName;		//商户类型名称，用逗号分隔
	private String merchantTypeValue;		//商户类型值，用逗号分隔
	private String legalName;				//法人姓名
	private String legalCredentType;		//法人证件号类型
	private String legalCredentNo;			//法人证件号
	private String orgCode;					//开户行机构号
	private String orgName;					//开户行机构名称
	private String cityOrgCode;				//所属分行机构代码
	private String cityOrgName;				//所属分行机构名称
	private String countyOrgCode;			//所属网点机构代码
	private String countyOrgName;			//所属网点机构名称
	private String accountName;				//收款账户名
	private String acountNo;				//收款账户号
	private String loginName;				//登录账号
	private String loginMobile;				//登录人手机号
	private String primeval;                //商户原始信息（json格式）
	
	//20151019 ll add 商户修改都是必审字段
	private String merchantName;            //商户名称
	private String phone;                	//联系人电话
	private String contactPhone;            //联系人手机
	private String license;                 //营业执照号
	private String contractStaff;           //签约人
	
	//20151125 trimray add 外包审核字段
	private Boolean isOutsource;            //是否外包
	private Long companyId;                 //外包公司Id
	
	
	
	
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public MerchantTemp(){
		
	}
	public MerchantTemp(Date createTime, String clientId,
			String auditId, String merchantId) {
		this.createTime = createTime;
		this.clientId = clientId;
		this.auditId = auditId;
		this.merchantId = merchantId;
	}


	public MerchantTemp(Date createTime, String clientId,
			String auditId, String merchantId, String contactName,
			Long merchantCategoryId, String merchantCategoryName,
			String merchantTypeId, String merchantTypeName,
			String merchantTypeValue, String legalName,
			String legalCredentType, String legalCredentNo, String orgCode,
			String orgName, String cityOrgCode, String cityOrgName,
			String countyOrgCode, String countyOrgName, String accountName,
			String acountNo, String loginName, String loginMobile, String primeval,
			String merchantName, String phone, String contactPhone, String license,
			String contractStaff, Boolean isOutsource,Long companyId) {
		this.createTime = createTime;
		this.clientId = clientId;
		this.auditId = auditId;
		this.merchantId = merchantId;
		this.contactName = contactName;
		this.merchantCategoryId = merchantCategoryId;
		this.merchantCategoryName = merchantCategoryName;
		this.merchantTypeId = merchantTypeId;
		this.merchantTypeName = merchantTypeName;
		this.merchantTypeValue = merchantTypeValue;
		this.legalName = legalName;
		this.legalCredentType = legalCredentType;
		this.legalCredentNo = legalCredentNo;
		this.orgCode = orgCode;
		this.orgName = orgName;
		this.cityOrgCode = cityOrgCode;
		this.cityOrgName = cityOrgName;
		this.countyOrgCode = countyOrgCode;
		this.countyOrgName = countyOrgName;
		this.accountName = accountName;
		this.acountNo = acountNo;
		this.loginName = loginName;
		this.loginMobile = loginMobile;
		this.primeval = primeval;
		this.merchantName = merchantName;
		this.phone = phone;
		this.contactPhone = contactPhone;
		this.license = license;
		this.contractStaff = contractStaff;
		this.isOutsource = isOutsource;
		this.companyId = companyId;
	}


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

	public String getAuditId() {
		return auditId;
	}

	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}


	public String getMerchantId() {
		return merchantId;
	}


	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}


	public String getContactName() {
		return contactName;
	}


	public void setContactName(String contactName) {
		this.contactName = contactName;
	}


	public Long getMerchantCategoryId() {
		return merchantCategoryId;
	}


	public void setMerchantCategoryId(Long merchantCategoryId) {
		this.merchantCategoryId = merchantCategoryId;
	}


	public String getMerchantCategoryName() {
		return merchantCategoryName;
	}


	public void setMerchantCategoryName(String merchantCategoryName) {
		this.merchantCategoryName = merchantCategoryName;
	}


	public String getMerchantTypeId() {
		return merchantTypeId;
	}


	public void setMerchantTypeId(String merchantTypeId) {
		this.merchantTypeId = merchantTypeId;
	}


	public String getMerchantTypeName() {
		return merchantTypeName;
	}


	public void setMerchantTypeName(String merchantTypeName) {
		this.merchantTypeName = merchantTypeName;
	}


	public String getMerchantTypeValue() {
		return merchantTypeValue;
	}


	public void setMerchantTypeValue(String merchantTypeValue) {
		this.merchantTypeValue = merchantTypeValue;
	}


	public String getLegalName() {
		return legalName;
	}


	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}


	public String getLegalCredentType() {
		return legalCredentType;
	}


	public void setLegalCredentType(String legalCredentType) {
		this.legalCredentType = legalCredentType;
	}


	public String getLegalCredentNo() {
		return legalCredentNo;
	}


	public void setLegalCredentNo(String legalCredentNo) {
		this.legalCredentNo = legalCredentNo;
	}



	public String getOrgName() {
		return orgName;
	}


	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}


	public String getCityOrgCode() {
		return cityOrgCode;
	}


	public void setCityOrgCode(String cityOrgCode) {
		this.cityOrgCode = cityOrgCode;
	}


	public String getCityOrgName() {
		return cityOrgName;
	}


	public void setCityOrgName(String cityOrgName) {
		this.cityOrgName = cityOrgName;
	}


	public String getCountyOrgCode() {
		return countyOrgCode;
	}


	public void setCountyOrgCode(String countyOrgCode) {
		this.countyOrgCode = countyOrgCode;
	}


	public String getCountyOrgName() {
		return countyOrgName;
	}


	public void setCountyOrgName(String countyOrgName) {
		this.countyOrgName = countyOrgName;
	}


	public String getAccountName() {
		return accountName;
	}


	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}


	public String getAcountNo() {
		return acountNo;
	}


	public void setAcountNo(String acountNo) {
		this.acountNo = acountNo;
	}


	public String getLoginName() {
		return loginName;
	}


	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}


	public String getLoginMobile() {
		return loginMobile;
	}


	public void setLoginMobile(String loginMobile) {
		this.loginMobile = loginMobile;
	}
	
	public String getPrimeval() {
		return primeval;
	}
	
	public void setPrimeval(String primeval) {
		this.primeval = primeval;
	}
	
	public String getMerchantName() {
		return merchantName;
	}
	
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getContactPhone() {
		return contactPhone;
	}
	
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	
	public String getLicense() {
		return license;
	}
	
	public void setLicense(String license) {
		this.license = license;
	}
	
	public String getContractStaff() {
		return contractStaff;
	}
	
	public void setContractStaff(String contractStaff) {
		this.contractStaff = contractStaff;
	}
	
	public Boolean getIsOutsource() {
		return isOutsource;
	}
	
	public void setIsOutsource(Boolean isOutsource) {
		this.isOutsource = isOutsource;
	}
	
	public Long getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	
	
	
	

}
